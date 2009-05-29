package org.pentaho.chart.model;

import java.io.Serializable;

public class ScatterPlot extends GraphPlot implements Serializable {
  private static final float DEFAULT_OPACITY = 0.45f;
  
  public ScatterPlot() {
    setOpacity(DEFAULT_OPACITY);
  }
}
