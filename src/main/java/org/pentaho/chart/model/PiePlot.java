/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;

public class PiePlot extends Plot implements Serializable {
  private static final int DEFAULT_START_ANGLE = 25;  
  private static final float DEFAULT_OPACITY = 0.85f;
  
  public static class PieLabels implements Serializable{
    boolean visible = true;
    CssStyle style = new CssStyle();
    
    public boolean getVisible() {
      return visible;
    }
    
    public void setVisible(boolean visible) {
      this.visible = visible;
    }
    
    public CssStyle getStyle() {
      return style;
    }

    public Integer getBackgroundColor() {
      return style.getBackgroundColor();
    }

    public Integer getColor() {
      return style.getColor();
    }

    public String getFontFamily() {
      return style.getFontFamily();
    }

    public Integer getFontSize() {
      return style.getFontSize();
    }

    public FontStyle getFontStyle() {
      return style.getFontStyle();
    }

    public FontWeight getFontWeight() {
      return style.getFontWeight();
    }

    public void setBackgroundColor(Integer color) {
      style.setBackgroundColor(color);
    }

    public void setColor(Integer color) {
      style.setColor(color);
    }

    public void setFont(String family, Integer size, FontStyle fontStyle, FontWeight fontWeight) {
      style.setFont(family, size, fontStyle, fontWeight);
    }

    public void setFont(String family, Integer size) {
      style.setFont(family, size);
    }

    public void setFontFamily(String family) {
      style.setFontFamily(family);
    }

    public void setFontSize(Integer size) {
      style.setFontSize(size);
    }

    public void setFontStyle(FontStyle style) {
      this.style.setFontStyle(style);
    }

    public void setFontWeight(FontWeight weight) {
      style.setFontWeight(weight);
    }
  }
  
  public static class Slice implements Serializable {
    boolean exploded;

    public boolean isExploded() {
      return exploded;
    }

    /**
     * Not implemented
     * @param exploded
     */
    public void setExploded(boolean exploded) {
      this.exploded = exploded;
    }
  }
  
  List<Slice> slices = new ArrayList<Slice>();
  boolean animate = false;
  Integer startAngle;
  PieLabels labels = new PieLabels();

  public List<Slice> getSlices() {
    return slices;
  }

  /**
   * @see Slice
   * @param slices
   */
  public void setSlices(List<Slice> slices) {
    this.slices = slices;
  }
  
  public boolean getAnimate() {
    return animate;
  }

  /**
   * Animate the chart when it loads
   * <p>
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>true</td></tr>
   *   <tr><td>false</td></tr>
   * </table>
   * </p>
   * 
   * @param animate
   */
  public void setAnimate(boolean animate) {
    this.animate = animate;
  }

  public Integer getStartAngle() {
    return startAngle == null ? DEFAULT_START_ANGLE : startAngle;
  }

  /**
   * The starting angle of the pie chart. Valid values are 0-360.
   * @param startAngle
   */
  public void setStartAngle(Integer startAngle) {
    this.startAngle = startAngle;
  }

  public Float getOpacity() {
    Float opacity = super.getOpacity();
    return opacity == null ? DEFAULT_OPACITY : opacity;
  }

  public PieLabels getLabels() {
    return labels;
  }

}
