package org.pentaho.chart.model;

import java.io.Serializable;

public class Axis implements Serializable {
  public enum LabelOrientation {HORIZONTAL, VERTICAL, DIAGONAL}; 
  
  LabelOrientation labelOrientation = LabelOrientation.HORIZONTAL;
  StyledText legend = new StyledText();
  
  public StyledText getLegend() {
    return legend;
  }
  
  public void setLegend(StyledText legend) {
    this.legend = legend;
  }
  
  public LabelOrientation getLabelOrientation() {
    return labelOrientation;
  }
  
  public void setLabelOrientation(LabelOrientation labelOrientation) {
    this.labelOrientation = labelOrientation;
  }
}
