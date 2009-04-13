package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PiePlot extends Plot implements Serializable {
  private static final int DEFAULT_START_ANGLE = 25;  
  private static final float DEFAULT_OPACITY = 0.85f;
  
  public static class Slice implements Serializable {
    boolean exploded;

    public boolean isExploded() {
      return exploded;
    }

    public void setExploded(boolean exploded) {
      this.exploded = exploded;
    }
  }
  
  List<Slice> slices = new ArrayList<Slice>();
  boolean animate = false;
  Integer startAngle;

  public List<Slice> getSlices() {
    return slices;
  }

  public void setSlices(List<Slice> slices) {
    this.slices = slices;
  }
  
  public boolean getAnimate() {
    return animate;
  }

  public void setAnimate(boolean animate) {
    this.animate = animate;
  }

  public Integer getStartAngle() {
    return startAngle == null ? DEFAULT_START_ANGLE : startAngle;
  }

  public void setStartAngle(Integer startAngle) {
    this.startAngle = startAngle;
  }

  public Float getOpacity() {
    Float opacity = super.getOpacity();
    return opacity == null ? DEFAULT_OPACITY : opacity;
  }

}
