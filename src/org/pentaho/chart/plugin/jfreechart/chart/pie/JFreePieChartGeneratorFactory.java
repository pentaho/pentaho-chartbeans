package org.pentaho.chart.plugin.jfreechart.chart.pie;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Factory class that will return a complete Pie chart ready for output/rendering.
 * </p>
 * Author: Ravi Hasija
 * Date: May 16, 2008
 * Time: 12:44:33 PM
 */
public class JFreePieChartGeneratorFactory {

  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    final JFreePieChartGenerator pieChartGenerator = new JFreePieChartGenerator();
    return pieChartGenerator.createChart(chartDocContext, data);
  }
}