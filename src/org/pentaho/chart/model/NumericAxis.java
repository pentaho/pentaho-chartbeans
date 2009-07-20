package org.pentaho.chart.model;

import java.io.Serializable;

public class NumericAxis extends Axis implements Serializable {

  Number minValue = null;
  Number maxValue = null;
  
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
