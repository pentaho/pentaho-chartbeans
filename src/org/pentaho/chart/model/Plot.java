package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Plot implements Serializable {
  public enum Orientation {VERTICAL, HORIZONTAL};
  Orientation orientation = Orientation.VERTICAL;
  
  Integer backgroundColor;
  HashMap<String, String> styles = new HashMap<String, String>();

  public Integer getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(Integer backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public HashMap<String, String> getStyles() {
    return styles;
  }  
  
  public Orientation getOrientation() {
    return orientation;
  }

  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

}
