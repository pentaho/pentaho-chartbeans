/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.plugin.jfreechart.chart.bar;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartBarStyle;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * Generate a particular type of bar chart based on the bar style mentioned in the current chart document. 
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 1:18:42 PM 
 */
public class JFreeBarChartGeneratorFactory {
  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    boolean stacked = false;
    boolean stackedPct = false;
    boolean cylinder = false;
    boolean interval = false;
    boolean layered = false;
    boolean waterfall = false;
    boolean threeD = false;
    boolean stacked100Pct = false;

    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    for (final ChartElement element : elements) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.BAR_STYLE);
      stacked |= ChartBarStyle.STACKED.equals(value);
      stackedPct |= ChartBarStyle.STACK_PERCENT.equals(value);
      cylinder |= ChartBarStyle.CYLINDER.equals(value);
      interval |= ChartBarStyle.INTERVAL.equals(value);
      layered |= ChartBarStyle.LAYERED.equals(value);
      waterfall |= ChartBarStyle.WATERFALL.equals(value);
      stacked100Pct |= ChartBarStyle.STACK_100_PERCENT.equals(value);
      threeD |= ChartBarStyle.THREED.equals(value);

      // Pick the first one that is set.
      if (stacked || stackedPct || stacked100Pct || cylinder || interval || layered || waterfall || threeD) {
        break;
      }
    }

    final JFreeBarChartGenerator barChartGenerator;

    if (cylinder) {
      barChartGenerator = new JFreeCylinderBarChartGenerator();
    } else if (layered) {
      barChartGenerator = new JFreeLayeredBarChartGenerator();
    } else if (stacked) {
      barChartGenerator = new JFreeStackedBarChartGenerator();
    } else if (stackedPct) {
      barChartGenerator = new JFreeStackedPercentBarChartGenerator();
    } else if (stacked100Pct) {
      barChartGenerator = new JFreeStacked100PercentBarChartGenerator();
    } else if (waterfall) {
      barChartGenerator = new JFreeWaterfallBarChartGenerator();
    } else if (threeD) {
      barChartGenerator = new JFree3DBarChartGenerator();
    } else {
      barChartGenerator = new JFreeDefaultBarChartGenerator();
    }

    return barChartGenerator.createChart(chartDocContext, data);
  }
}
