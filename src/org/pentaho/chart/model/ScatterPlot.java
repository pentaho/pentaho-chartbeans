package org.pentaho.chart.model;

import java.io.Serializable;

public class ScatterPlot extends TwoAxisPlot implements Serializable {
  private static final float DEFAULT_OPACITY = 0.45f;
  
  public ScatterPlot() {
    super(new NumericAxis(), new NumericAxis());
    setOpacity(DEFAULT_OPACITY);
    setPalette(new Palette(0x000000));
  }
  
  public NumericAxis getXAxis() {
    return (NumericAxis)getHorizontalAxis();
  }
  
  public NumericAxis getYAxis() {
    return (NumericAxis)getVerticalAxis();
  }
}
