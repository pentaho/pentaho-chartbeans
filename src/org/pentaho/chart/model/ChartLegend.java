/*
 * This program is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software 
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this 
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html 
 * or from the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright 2008 Pentaho Corporation.  All rights reserved.
 */
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
