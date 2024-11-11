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

  /**
   * Determine if the legend will be visible on the chart.
   * <p>
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>true</td></tr>
   *   <tr><td>false</td></tr>
   * </table>
   * </p>
   * 
   * @param visible
   */
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

  /**
   * {@link org.pentaho.chart.model.CssStyle#setBorderColor(Integer)}
   * @param color
   */
  public void setBorderColor(Integer color) {
    style.setBorderColor(color);
  }

  /**
   * {@link org.pentaho.chart.model.CssStyle#setBorderVisible(boolean)}
   * @param visible
   */
  public void setBorderVisible(boolean visible) {
    style.setBorderVisible(visible);
  }

  /**
   * {@link org.pentaho.chart.model.CssStyle#setBorderWidth(Integer)}
   * @param width
   */
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

  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * @param family
   * @param size
   * @param fontStyle
   * @param fontWeight
   */
  public void setFont(String family, Integer size, FontStyle fontStyle, FontWeight fontWeight) {
    style.setFont(family, size, fontStyle, fontWeight);
  }

  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * @param family
   * @param size
   */
  public void setFont(String family, Integer size) {
    style.setFont(family, size);
  }

  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * @param family
   */
  public void setFontFamily(String family) {
    style.setFontFamily(family);
  }

  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * @param size
   */
  public void setFontSize(Integer size) {
    style.setFontSize(size);
  }

  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * @param fontStyle
   */
  public void setFontStyle(FontStyle fontStyle) {
    style.setFontStyle(fontStyle);
  }

  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * @param weight
   */
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
