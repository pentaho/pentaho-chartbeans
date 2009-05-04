package org.pentaho.chart.model;

import java.io.Serializable;

import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;

public class ChartTitle extends StyledText implements Serializable {
  public enum TitleLocation {BOTTOM, LEFT, TOP, RIGHT};
  
  TitleLocation location = TitleLocation.TOP;

  public ChartTitle() {
    super();
  }
  
  public ChartTitle(String text) {
    super(text);
  }
  
  public ChartTitle(String text, String fontFamily, FontStyle fontStyle, FontWeight fontWeight, int fontSize) {
    super(text, fontFamily, fontStyle, fontWeight, fontSize);
  }
  
  public TitleLocation getLocation() {
    return location;
  }

  public void setLocation(TitleLocation location) {
    this.location = location;
  }
}
