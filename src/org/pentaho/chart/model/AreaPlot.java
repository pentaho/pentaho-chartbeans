package org.pentaho.chart.model;

import java.io.Serializable;

public class AreaPlot extends GraphPlot implements Serializable {
  private static final float DEFAULT_OPACITY = 0.45f;
  
  public AreaPlot() {
    setOpacity(DEFAULT_OPACITY);
  }
}
