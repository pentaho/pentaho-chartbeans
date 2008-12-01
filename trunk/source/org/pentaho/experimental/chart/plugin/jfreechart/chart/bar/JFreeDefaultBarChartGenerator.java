package org.pentaho.experimental.chart.plugin.jfreechart.chart.bar;

import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.data.ChartTableModel;

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
  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, JFreeBarChartTypes.DEFAULT);
    /*
     * NOTE: The chart object will be updated.
     */
    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
