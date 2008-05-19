package org.pentaho.experimental.chart.plugin.jfreechart.chart.area;

import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.css.styles.ChartAreaStyle;
import org.pentaho.experimental.chart.data.ChartTableModel;

/**
 * Creates basic/default area chart. Also, does some extra work like creating range axis etc.
 * </p> 
 * Author: Ravi Hasija
 * Date: May 16, 2008
 * Time: 12:45:07 PM
 */
public class JFreeDefaultAreaChartGenerator extends JFreeAreaChartGenerator {
  public JFreeChart createChart(ChartDocumentContext chartDocContext, ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, ChartAreaStyle.AREA);
    
    /*
     * NOTE: The chart object will be updated.
     */
    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
