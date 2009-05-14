package org.pentaho.chart.model;

import java.io.Serializable;

/**
 * A plot with X and Y axes.
 */
public class GraphPlot extends Plot implements Serializable {

  Number minValue = null;

  Number maxValue = null;

  Axis xAxis = new Axis();

  Axis yAxis = new Axis();

  public Axis getXAxis() {
    return xAxis;
  }

  public Axis getYAxis() {
    return yAxis;
  }

  public Number getMinValue() {
    return minValue;
  }

  public void setMinValue(Number minValue) {
    this.minValue = minValue;
  }

  public Number getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Number maxValue) {
    this.maxValue = maxValue;
  }

}
