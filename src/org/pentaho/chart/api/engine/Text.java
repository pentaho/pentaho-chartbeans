package org.pentaho.chart.api.engine;

import java.awt.Paint;

public interface Text {

  public String getText();
  public void setText(String str);
  public Paint getColor();
  public void setColor(Paint color);
  public Paint getBackgroundColor();
  public void setBackgroundColor(Paint color);
  public String getTextAlignment();
  public void setTextAlignment(String align);
  

}
