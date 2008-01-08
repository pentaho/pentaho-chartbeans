package org.pentaho.chart.api.engine;

import java.awt.Paint;

public interface BarSeries extends Series{
  
  public Paint getColor();
  public void setColor(Paint color);

}
