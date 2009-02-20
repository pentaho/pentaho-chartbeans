package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PiePlot extends Plot implements Serializable {
  
  public static class Wedge implements Serializable {
    Integer foregroundColor;
    boolean exploded;

    public Integer getForegroundColor() {
      return foregroundColor;
    }

    public void setForegroundColor(Integer foregroundColor) {
      this.foregroundColor = foregroundColor;
    }

    public boolean isExploded() {
      return exploded;
    }

    public void setExploded(boolean exploded) {
      this.exploded = exploded;
    }
  }
  
  List<Wedge> wedges = new ArrayList<Wedge>();

  public List<Wedge> getWedges() {
    return wedges;
  }

  public void setWedges(List<Wedge> wedges) {
    this.wedges = wedges;
  }
}
