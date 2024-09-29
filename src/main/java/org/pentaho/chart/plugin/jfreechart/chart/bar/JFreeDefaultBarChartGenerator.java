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
import org.pentaho.chart.data.ChartTableModel;

/**
 * Generates simple basic bar chart.
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 2:24:48 PM 
 */
public class JFreeDefaultBarChartGenerator extends JFreeBarChartGenerator {
  /**
   * Generate basic bar chart and create range axis for it.
   * </p>
   * @param chartDocContext -- Current chart's document context
   * @param data            -- Current chart data
   * @return JFreeChart object that is a bar chart.
   */
  protected JFreeChart doCreateChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, JFreeBarChartTypes.DEFAULT);
    /*
     * NOTE: The chart object will be updated.
     */
    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
