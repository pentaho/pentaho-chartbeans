/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.chart.model;

import java.io.Serializable;

import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;

public class Axis implements Serializable {
  public static final int DEFAULT_AXIS_COLOR = 0x808080;
  public enum LabelOrientation {HORIZONTAL, VERTICAL, DIAGONAL}; 
  
  LabelOrientation labelOrientation = LabelOrientation.HORIZONTAL;
  CssStyle style = new CssStyle();
  StyledText legend = new StyledText();
  
  public Axis() {
    setColor(DEFAULT_AXIS_COLOR);
  }
  
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

  public String getFontFamily() {
    return legend.getFontFamily();
  }

  public Integer getFontSize() {
    return legend.getFontSize();
  }

  public FontStyle getFontStyle() {
    return legend.getFontStyle();
  }

  public FontWeight getFontWeight() {
    return legend.getFontWeight();
  }

  public void setFont(String family, Integer size, FontStyle fontStyle, FontWeight fontWeight) {
    legend.setFont(family, size, fontStyle, fontWeight);
  }

  public void setFont(String family, Integer size) {
    legend.setFont(family, size);
  }

  public void setFontFamily(String family) {
    legend.setFontFamily(family);
  }

  public void setFontSize(Integer size) {
    legend.setFontSize(size);
  }

  public void setFontStyle(FontStyle fontStyle) {
    legend.setFontStyle(fontStyle);
  }

  public void setFontWeight(FontWeight weight) {
    legend.setFontWeight(weight);
  }

  public Integer getColor() {
    return style.getColor();
  }

  public void setColor(Integer color) {
    style.setColor(color);
  }

  public CssStyle getStyle() {
    return style;
  }
}
