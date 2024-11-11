/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart.plugin.jfreechart.chart.bar;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.util.SortOrder;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.bar.JFreeBarChartGenerator;
import org.pentaho.chart.plugin.jfreechart.chart.bar.JFreeBarChartTypes;

/**
 * Creates Layered Bar Chart.
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 1:15:27 PM
 */
public class JFreeLayeredBarChartGenerator extends JFreeBarChartGenerator {
  /**
   * Creates layered bar chart and creates range axis for it.
   * </p>
   * @param chartDocContext Current chart's document context
   * @param data Current chart data
   * @return Returns JFree layered bar chart.
   */
  protected JFreeChart doCreateChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, JFreeBarChartTypes.DEFAULT);
    /*
     * Doing all the render stuff and then off to create range axis.
     * NOTE: The chart object will be updated.
     */
    final LayeredBarRenderer renderer = new LayeredBarRenderer();
    renderer.setDrawBarOutline(false);
    chart.getCategoryPlot().setRenderer(renderer);
    chart.getCategoryPlot().setRowRenderingOrder(SortOrder.DESCENDING);

    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
