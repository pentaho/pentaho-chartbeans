package org.pentaho.chart.model;

import java.io.Serializable;

public class AreaPlot extends TwoAxisPlot implements Serializable {
  private static final float DEFAULT_OPACITY = 0.45f;
  
  public AreaPlot() {
    super(new Axis(), new NumericAxis());
    setOpacity(DEFAULT_OPACITY);
  }
}
