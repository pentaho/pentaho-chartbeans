/*!
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
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

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
