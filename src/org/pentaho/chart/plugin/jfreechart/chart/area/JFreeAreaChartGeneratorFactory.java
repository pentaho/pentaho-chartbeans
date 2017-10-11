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

package org.pentaho.chart.plugin.jfreechart.chart.area;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartAreaStyle;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * Factory class that will return a complete area chart ready for output/rendering,
 * </p>
 * Author: Ravi Hasija
 * Date: May 16, 2008
 * Time: 12:44:33 PM
 */
public class JFreeAreaChartGeneratorFactory {

  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    boolean stacked = false;
    boolean xy = false;

    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    for (final ChartElement element : elements) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.AREA_STYLE);
      stacked |= value.equals(ChartAreaStyle.STACKED);
      xy |= value.equals(ChartAreaStyle.XY);

      if (stacked || xy) {
        break;
      }
    }

    JFreeAreaChartGenerator areaChartGenerator = null;

    if (stacked) {
      areaChartGenerator = new JFreeStackedAreaChartGenerator();
    } else if (xy) {
    } else {
      areaChartGenerator = new JFreeDefaultAreaChartGenerator();
    }
    
    return areaChartGenerator.createChart(chartDocContext, data);
  }
}
