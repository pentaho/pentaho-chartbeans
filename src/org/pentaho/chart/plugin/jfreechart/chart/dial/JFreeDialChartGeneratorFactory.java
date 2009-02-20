package org.pentaho.chart.plugin.jfreechart.chart.dial;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

public class JFreeDialChartGeneratorFactory {

  public JFreeChart createChart(final ChartDocumentContext chartDocContext, final ChartTableModel data) {
    final JFreeDialChartGenerator dialChartGenerator = new JFreeDialChartGenerator();
    return dialChartGenerator.createChart(chartDocContext, data);
  }

}
