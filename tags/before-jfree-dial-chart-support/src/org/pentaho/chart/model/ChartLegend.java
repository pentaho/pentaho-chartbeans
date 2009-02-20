package org.pentaho.chart.model;

import java.io.Serializable;

public class ChartLegend implements Serializable {
  boolean visible = false;

  public boolean getVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }
}
