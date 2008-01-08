package org.pentaho.chart.api.engine;

import java.awt.Paint;

public interface LineSeries extends Series {
  
  public Paint getColor();
  public void setColor(Paint color);
  public boolean isShapesVisible();
  public void setShapesVisible(boolean vis);

}
