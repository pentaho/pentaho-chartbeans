package org.pentaho.chart.model;

import java.io.Serializable;

import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;

public class ChartLegend implements Serializable {
  boolean visible = true;
  CssStyle style = new CssStyle();

  public ChartLegend() {
  }
  
  public boolean getVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public Integer getBorderColor() {
    return style.getBorderColor();
  }

  public boolean getBorderVisible() {
    return style.getBorderVisible();
  }

  public Integer getBorderWidth() {
    return style.getBorderWidth();
  }

  public void setBorderColor(Integer color) {
    style.setBorderColor(color);
  }

  public void setBorderVisible(boolean visible) {
    style.setBorderVisible(visible);
  }

  public void setBorderWidth(Integer width) {
    style.setBorderWidth(width);
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

  public void setFontStyle(FontStyle fontStyle) {
    style.setFontStyle(fontStyle);
  }

  public void setFontWeight(FontWeight weight) {
    style.setFontWeight(weight);
  }

  public CssStyle getStyle() {
    return style;
  }

  public String toString() {
    return style.getStyleString();
  }

}
