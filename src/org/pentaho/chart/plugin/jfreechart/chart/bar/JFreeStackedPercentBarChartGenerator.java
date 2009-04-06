package org.pentaho.chart.plugin.jfreechart.chart.bar;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Creates stacked percent bar chart.
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 5:08:48 PM
 */
public class JFreeStackedPercentBarChartGenerator extends JFreeStackedBarChartGenerator {

  /**
   * Creates stacked percent bar chart and creates range axis for it.
   * </p>
   * @param chartDocContext Current chart's document context
   * @param data Current chart data
   * @return Returns JFree stacked percent bar chart.
   */
  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                               final ChartTableModel data) {
    final JFreeChart chart = super.createChart(chartDocContext, data);
    /*
     * Doing all the render stuff and then off to create range axis.
     * NOTE: The chart object will be updated.
     */
    ((StackedBarRenderer)chart.getCategoryPlot().getRenderer()).setRenderAsPercentages(true);
    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
