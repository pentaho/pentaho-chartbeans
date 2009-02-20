package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.HashMap;

public class Series implements Serializable {
  Integer foregroundColor;
  HashMap<String, String> styles = new HashMap<String, String>();

  public Integer getForegroundColor() {
    return foregroundColor;
  }

  public void setForegroundColor(Integer foregroundColor) {
    this.foregroundColor = foregroundColor;
  }

  public HashMap<String, String> getStyles() {
    return styles;
  }
}
