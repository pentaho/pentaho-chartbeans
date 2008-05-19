package org.pentaho.experimental.chart.plugin.jfreechart.chart;

import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.data.ChartTableModel;

/**
 * Implement this interface to generate any kind of JFreeChart chart eg: bar, line, area etc...
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 1:08:16 PM
 */
public interface IJFreeChartGenerator {
  public JFreeChart createChart(final ChartDocumentContext chartDocContext, final ChartTableModel data);
}
