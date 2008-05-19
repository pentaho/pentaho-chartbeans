package org.pentaho.experimental.chart.plugin.jfreechart.chart.bar;

import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.CylinderRenderer;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.bar.JFreeBarChartGenerator;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.bar.JFreeBarChartTypes;

/**
 * Generates cylinder bar chart.
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 1:33:56 PM
 */
public class JFreeCylinderBarChartGenerator extends JFreeBarChartGenerator {

  /**
   * Creates cylinder bar chart and creates range axis for it.
   * </p>
   * @param chartDocContext Current chart's document context
   * @param data Current chart data
   * @return Returns JFreeChart object that is a layered bar chart.
   */
  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, JFreeBarChartTypes.DEFAULT);
    final CylinderRenderer renderer = new CylinderRenderer();
    chart.getCategoryPlot().setRenderer(renderer);
    /*
     * Doing all the render stuff and then off to create range axis.
     * NOTE: The chart object will be updated.
     */
    createRangeAxis(chartDocContext, data, chart);    
    return chart;
  }
}
