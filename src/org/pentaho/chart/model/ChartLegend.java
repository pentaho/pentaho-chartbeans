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
