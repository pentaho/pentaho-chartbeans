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
  
  /**
   * The text content of the item
   * @param text
   */
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

  /**
   * <p>
   * The font-family is the name of the font or group of fonts to be used.
   * </p>
   * 
   * <p>
   * <table>
   *   <tr><th>For example</th></tr>
   *   <tr><td>arial</td></tr>
   *   <tr><td>monospace</td></tr>
   *   <tr><td>times new roman</td></tr>
   *   <tr><td>verdana</td></tr>
   * </table>
   * </p>
   * 
   * <p>
   * The font-size is the size in pixels of the font.
   * </p>
   * 
   * <p>
   * The font-style is the visual styling of the font.
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>NORMAL</td></tr>
   *   <tr><td>ITALIC</td></tr>
   *   <tr><td>OBLIQUE</td></tr>
   * </table>
   * </p>
   * 
   * <p>
   * The font-weight is the visual density of the text.
   *   <table>
   *     <tr><th>Possible values</th></tr>
   *     <tr><td>NORMAL</td></tr>
   *     <tr><td>BOLD</td></tr>
   *   </table>
   * </p>
   * 
   * @param family
   * @param size
   * @param fontStyle
   * @param fontWeight
   */
  public void setFont(String family, Integer size, FontStyle fontStyle, FontWeight fontWeight) {
    style.setFont(family, size, fontStyle, fontWeight);
  }
  
  /**
   * {@link #setFont(String, Integer, FontStyle, FontWeight)}
   */
  public void setFont(String family, Integer size) {
    style.setFont(family, size);
  }

  /**
   * {@link #setFont(String, Integer, FontStyle, FontWeight)}
   */
  public void setFontFamily(String family) {
    style.setFontFamily(family);
  }

  /**
   * {@link #setFont(String, Integer, FontStyle, FontWeight)}
   */
  public void setFontSize(Integer size) {
    style.setFontSize(size);
  }

  /**
   * {@link #setFont(String, Integer, FontStyle, FontWeight)}
   */
  public void setFontStyle(FontStyle fontStyle) {
    style.setFontStyle(fontStyle);
  }

  /**
   * {@link #setFont(String, Integer, FontStyle, FontWeight)}
   */
  public void setFontWeight(FontWeight weight) {
    style.setFontWeight(weight);
  }

  public Integer getBackgroundColor() {
    return style.getBackgroundColor();
  }

   /**
   * Hex RGB notation of the desired background color
   * <p>
   * <table>
   * <tr><th colspan="2">Basic colors</tr></th>
   *   <tr><td>White: </td><td>#FFFFFF</td></th>
   *   <tr><td>Black: </td><td>#000000</td></th>
   *   <tr><td>Grey:  </td><td>#888888</td></th>
   *   <tr><td>Red:   </td><td>#FF0000</td></th>
   *   <tr><td>Green: </td><td>#00FF00</td></th>
   *   <tr><td>Blue:  </td><td>#0000FF</td></th>
   * </table>
   * </p>
   * 
   * @param color
   */
  public void setBackgroundColor(Integer color) {
    style.setBackgroundColor(color);
  }

  public Integer getColor() {
    return style.getColor();
  }

  /**
   * {@link org.pentaho.chart.model.CssStyle#setColor(Integer)}
   * @param color
   */
  public void setColor(Integer color) {
    style.setColor(color);
  }
  
  public CssStyle getStyle() {
    return style;
  }

  public TextAlignment getAlignment() {
    return style.getTextAlignment();
  }

  /**
   * Alignment of text within its given context.
   * <p>
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>LEFT</td></tr>
   *   <tr><td>CENTER</td></tr>
   *   <tr><td>RIGHT</td></tr>
   * </table>
   * </p>
   * 
   * @param textAlignment
   */
  public void setAlignment(TextAlignment textAlignment) {
    style.setTextAlignment(textAlignment);
  }

}
