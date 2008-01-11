package org.pentaho.chart.plugin.jfreechart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.pentaho.chart.api.engine.Axis;
import org.pentaho.chart.api.engine.Series;

public class BaseJFreeChartBean extends AbstractBean {
  
  private static final long serialVersionUID = 6340035741916476121L;

  protected JFreeChart chart;

  protected Axis[] axes;

  protected Series[] series;

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

  public String getTitle() {
    return chart.getTitle().getText();
  }

  public void setTitle(String title) {
    if (chart.getTitle() == null) {
      TextTitle tt = new TextTitle();
      chart.setTitle(tt);
    }
    chart.getTitle().setText(title);
  }

  /**
   * @return the axes
   */
  public final Axis[] getAxes() {
    return axes;
  }

  public final Axis getAxes(int index) {
    return axes[index];
  }

  /**
   * @param axes the axes to set
   */
  public final void setAxes(Axis[] axes) {
    this.axes = axes;
  }

  public final void setAxes(int index, Axis axis) {
    axes[index] = axis;
  }

  /**
   * @return the series
   */
  public final Series[] getSeries() {
    return series;
  }

  public final Series getSeries(int index) {
    return series[index];
  }

  /**
   * @param series the series to set
   */
  public final void setSeries(Series[] series) {
    this.series = series;
  }

  public final void setSeries(int index, Series series) {
    this.series[index] = series;
  }


}
