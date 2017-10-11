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
import java.util.HashMap;
import java.util.Map;

public class CssStyle extends HashMap<String, String> implements Serializable {

  
  public static final String BACKGROUND_COLOR_STYLE = "background-color";
  public static final String BORDER_WIDTH_STYLE = "border-top-width";
  public static final String BORDER_COLOR_STYLE = "border-top-color";
  public static final String BORDER_LINE_STYLE = "border-top-style";
  public static final String FONT_SIZE_STYLE = "font-size";
  public static final String FONT_WEIGHT_STYLE = "font-weight";
  public static final String FONT_STYLE = "font-style";
  public static final String FONT_FAMILY_STYLE = "font-family";
  public static final String COLOR_STYLE = "color";
  public static final String VISIBILITY_STYLE = "color";
  public static final String TEXT_ALIGNMENT_STYLE = "text-align";
  public static final String OPACITY_STYLE = "opacity";
  
  public enum FontWeight {NORMAL, BOLD};
  public enum FontStyle {NORMAL,ITALIC,OBLIQUE};
  public enum TextAlignment {LEFT, CENTER, RIGHT};
  public enum LineStyle {SOLID, DASHED, DOTTED};
  public enum VisibilityStyle {VISIBLE, HIDDEN};
  
  public LineStyle getBorderLineStyle() {
    LineStyle style = LineStyle.SOLID;
    String str = get(BORDER_LINE_STYLE);
    if (str != null) {
      try {
        style = Enum.valueOf(LineStyle.class, str.toUpperCase());
      } catch (NumberFormatException ex) {
        // Do nothing we'll return a solid line style.
      }
    }
    return style;
  }
  
  /**
   * The visual style of the line around the associated element.
   * <p>
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>SOLID</td></tr>
   *   <tr><td>DASHED</td></tr>
   *   <tr><td>DOTTED</td></tr>
   * </table>
   * </p>
   * 
   * @param lineStyle
   */
  public void setBorderLineStyle(LineStyle lineStyle) {
    if (lineStyle == null) {
      remove(BORDER_LINE_STYLE);
    } else {
      put(BORDER_LINE_STYLE, lineStyle.toString().toLowerCase());
    }
  }
  
  public Integer getBorderWidth() {
    Integer width = null;
    String str = get(BORDER_WIDTH_STYLE);
    if (str != null) {
      try {
        width = Integer.parseInt(str);
      } catch (NumberFormatException ex) {
        // Do nothing we'll return null.
      }
    }
    return width;
  }
  
  /**
   * The width of the around the associated element in pixels.
   * @param width
   */
  public void setBorderWidth(Integer width) {
    if (width == null) {
      remove(BORDER_WIDTH_STYLE);
    } else {
      put(BORDER_WIDTH_STYLE, width.toString());
    }
  }
  
  public Integer getBorderColor() {
    Integer color = null;
    String str = get(BORDER_COLOR_STYLE);
    if (str != null) {
      int index = str.indexOf("#");
      try {
        color = Integer.parseInt(str.substring(index + 1), 16);
      } catch (Exception ex) {
        // Do nothing we'll return null.
      }
    }
    return color;
  }
  
  /**
   * {@link org.pentaho.chart.model.ChartModel#setBorderColor(Integer)}
   * @param color
   */
  public void setBorderColor(Integer color) {
    if (color == null) {
      remove(BORDER_COLOR_STYLE);
    } else {
      StringBuffer colorStr = new StringBuffer("#" + Integer.toString(color & 0xFFFFFF, 16));
      while (colorStr.length() < 7) {
        colorStr.insert(1, 0);
      }
      put(BORDER_COLOR_STYLE, colorStr.toString());
    }
  }
  
  public Integer getBackgroundColor() {
    Integer color = null;
    String str = get(BACKGROUND_COLOR_STYLE);
    if (str != null) {
      int index = str.indexOf("#");
      try {
        color = Integer.parseInt(str.substring(index + 1), 16);
      } catch (Exception ex) {
        // Do nothing we'll return null.
      }
    }
    return color;
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setBackgroundColor(Integer)}
   * @param color
   */
  public void setBackgroundColor(Integer color) {
    if (color == null) {
      remove(BACKGROUND_COLOR_STYLE);
    } else {
      StringBuffer colorStr = new StringBuffer("#" + Integer.toString(color & 0xFFFFFF, 16));
      while (colorStr.length() < 7) {
        colorStr.insert(1, 0);
      }
      put(BACKGROUND_COLOR_STYLE, colorStr.toString());
    }
  }
  
  public FontStyle getFontStyle() {
    FontStyle fontStyle = null;
    String str = get(FONT_STYLE);
    if (str != null) {
      try {
        fontStyle = Enum.valueOf(FontStyle.class, str.toUpperCase());
      } catch (Exception ex) {
        // Do nothing we'll return null
      }
    }
    return fontStyle == null ? FontStyle.NORMAL : fontStyle;
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * 
   */
  public void setFontStyle(FontStyle style) {
    if ((style == null) || (style == FontStyle.NORMAL)) {
      remove(FONT_STYLE);
    } else {
      put(FONT_STYLE, style.toString().toLowerCase());
    }
  }
  
  public FontWeight getFontWeight() {
    FontWeight fontWeight = null;
    String str = get(FONT_WEIGHT_STYLE);
    if (str != null) {
      try {
        fontWeight = Enum.valueOf(FontWeight.class, str.toUpperCase());
      } catch (Exception ex) {
        // Do nothing we'll return null
      }
    }
    return fontWeight == null ? FontWeight.NORMAL : fontWeight;
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * 
   */
  public void setFontWeight(FontWeight weight) {
    if ((weight == null) || (weight == FontWeight.NORMAL)) {
      remove(FONT_WEIGHT_STYLE);
    } else {
      put(FONT_WEIGHT_STYLE, weight.toString().toLowerCase());
    }
  }
  
  public Integer getFontSize() {
    Integer size = null;
    String str = get(FONT_SIZE_STYLE);
    if (str != null) {
      try {
        if (str.endsWith("px")) {
          str = str.substring(0, str.indexOf("px"));
        }
        size = Integer.parseInt(str);
      } catch (Exception ex) {
        // Do nothing we'll return null.
      }
    }
    return size;
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * 
   */
  public void setFontSize(Integer size) {
    if (size == null) {
      remove(FONT_SIZE_STYLE);
    } else {
      put(FONT_SIZE_STYLE, size.toString() + "px");
    }
  }
  
  public String getFontFamily() {
    return get(FONT_FAMILY_STYLE);
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * 
   */
  public void setFontFamily(String family) {
    if (family == null) {
      remove(FONT_FAMILY_STYLE);
    } else {
      put(FONT_FAMILY_STYLE, family);
    }
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * 
   */
  public void setFont(String family, Integer size) {  
    setFontFamily(family);
    setFontSize(size);
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setFont(String, Integer, FontStyle, FontWeight)}
   * 
   */
  public void setFont(String family, Integer size, FontStyle fontStyle, FontWeight fontWeight) {  
    setFontFamily(family);
    setFontSize(size);
    setFontStyle(fontStyle);
    setFontWeight(fontWeight);
  }
  
  public Integer getColor() {
    Integer color = null;
    String str = get(COLOR_STYLE);
    if (str != null) {
      int index = str.indexOf("#");
      try {
        color = Integer.parseInt(str.substring(index + 1), 16);
      } catch (Exception ex) {
        // Do nothing we'll return null.
      }
    }
    return color;
  }
  
  /**
   * Hex RGB notation of the desired color
   * <p>
   * <table>
   *   <tr><th colspan="2">Basic colors</th></tr>
   *   <tr><td>White </td><td>#FFFFFF</td></tr>
   *   <tr><td>Black </td><td>#000000</td></tr>
   *   <tr><td>Grey  </td><td>#888888</td></tr>
   *   <tr><td>Red   </td><td>#FF0000</td></tr>
   *   <tr><td>Green </td><td>#00FF00</td></tr>
   *   <tr><td>Blue  </td><td>#0000FF</td></tr>
   * </table>
   * </p>
   * 
   * @param color
   */
  public void setColor(Integer color) {
    if (color == null) {
      remove(COLOR_STYLE);
    } else {
      StringBuffer colorStr = new StringBuffer("#" + Integer.toString(color & 0xFFFFFF, 16));
      while (colorStr.length() < 7) {
        colorStr.insert(1, 0);
      }
      put(COLOR_STYLE, colorStr.toString());
    }
  }
  
  public VisibilityStyle getVisibility() {
    VisibilityStyle style = VisibilityStyle.VISIBLE;
    
    String str = get(VISIBILITY_STYLE);
    if (str != null) {
      try {
        style = Enum.valueOf(VisibilityStyle.class, str.toUpperCase());
      } catch (IllegalArgumentException ex) {
        // Do nothing we'll return visible.
      }
    }
    return style;  
  }
  
  /**
   * The visibility of the associated element.
   * <p>
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>VISIBLE</td></tr>
   *   <tr><td>HIDDEN</td></tr>
   * </table>
   * </p>
   * 
   * @param lineStyle
   */
  public void setVisibility(VisibilityStyle visibility) {
    if (visibility == null) {
      remove(VISIBILITY_STYLE);
    } else {
      put(VISIBILITY_STYLE, visibility.toString().toLowerCase());
    }
  }
  
  /**
   * A value between 0.00 (transparent) and 1.00 (opaque)
   * @param opacity
   */
  public void setOpacity(Float opacity) {
    if (opacity == null) {
      remove(OPACITY_STYLE);
    } else {
      put(OPACITY_STYLE, opacity.toString());
    }
  }
  
  public Float getOpacity() {
    Float opacity = null;
    String str = get(OPACITY_STYLE);
    if (str != null) {
      try {
        opacity = Float.parseFloat(str);
      } catch (Exception ex) {
        // Do nothing we'll return null.
      }
    }
    return opacity;
  }

  @Override
  public String toString() {
    return "CssStyle[" + getStyleString() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
  }
  
  public String getStyleString() {
    StringBuffer cssStyleString = new StringBuffer();
    for (Map.Entry<String, String> entry : entrySet()) {
      if (cssStyleString.length() > 0) {
        cssStyleString.append(" ");
      }
      cssStyleString.append(entry.getKey() + ": " + entry.getValue() + ";");
    }
    return cssStyleString.toString();
  }
  
  public boolean getBorderVisible() {
    return getBorderWidth() != null;
  }

  /**
   * Not implemented
   * @param visible
   */
  public void setBorderVisible(boolean visible) {
    if (!visible) {
      setBorderColor(null);
      setBorderWidth(null);
    } else if (getBorderWidth() == null){
      setBorderWidth(1);
    }
  }
  
  /**
   * Set additional style properties in a standard CSS format (name-one: value; name-two: value;)
   * @param cssStyle
   */
  public void setStyleString(String cssStyle) {
    clear();
    if (cssStyle != null) {
      String[] styles = cssStyle.split(";");
      for (String styleStr : styles) {
        String[] styleNameValue = styleStr.split(":");
        if (styleNameValue.length == 2) {
          String styleName = styleNameValue[0].trim();
          String styleValue = styleNameValue[1].trim();
          if ((styleName.length() > 0) && (styleValue.length() > 0)) {
            put(styleName, styleValue);
          }
        }
      }
    }
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setAlignment(TextAlignment)}
   * @param textAlignment
   */
  public void setTextAlignment(TextAlignment textAlignment) {
    if (textAlignment == null) {
      remove(TEXT_ALIGNMENT_STYLE);
    } else {
      put(TEXT_ALIGNMENT_STYLE, textAlignment.toString().toLowerCase());
    }
  }
  
  public TextAlignment getTextAlignment() {
    TextAlignment textAlignment = null;
    String str = get(TEXT_ALIGNMENT_STYLE);
    if (str != null) {
      try {
        textAlignment = Enum.valueOf(TextAlignment.class, str.toUpperCase());
      } catch (Exception ex) {
        // Do nothing we'll return null
      }
    }
    return textAlignment;
  }
}
