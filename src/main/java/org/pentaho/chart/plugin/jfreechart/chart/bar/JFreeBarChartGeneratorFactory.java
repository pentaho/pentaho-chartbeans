/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

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
