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
import org.pentaho.chart.model.CssStyle.TextAlignment;

public class StyledText implements Serializable{
  String text;
  CssStyle style = new CssStyle();
  
  public StyledText(String text) {
    this();
    setText(text);
    
  }
  
  public StyledText(String text, String fontFamily, FontStyle fontStyle, FontWeight fontWeight, int fontSize) {
    this();
    setText(text);
    setFontFamily(fontFamily);
    setFontStyle(fontStyle);
    setFontWeight(fontWeight);
    setFontSize(fontSize);
  }
  
  public StyledText() {
    setFont("arial", 14);
  }
  
  public String getText() {
    return text;
  }
  
  public void setText(String text) {
    this.text = text;
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

  public Integer getBackgroundColor() {
    return style.getBackgroundColor();
  }

  public void setBackgroundColor(Integer color) {
    style.setBackgroundColor(color);
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

  public TextAlignment getAlignment() {
    return style.getTextAlignment();
  }

  public void setAlignment(TextAlignment textAlignment) {
    style.setTextAlignment(textAlignment);
  }

}
