package org.pentaho.chart.plugin.jfreechart;

import org.jfree.chart.JFreeChart;

public class BaseJFreeChartBean extends AbstractBean {
  
  private static final long serialVersionUID = 6340035741916476121L;

  // TODO: 
  // This needs to be wrapped as a bean
  protected JFreeChart chart;

  /**
   * 
   */
  public BaseJFreeChartBean() {
    super();
  }

  /**
   * @return the chart
   */
  public final JFreeChart getChart() {
    return chart;
  }

  /**
   * @param chart the chart to set
   */
  public final void setChart(JFreeChart chart) {
    this.chart = chart;
  }


}
