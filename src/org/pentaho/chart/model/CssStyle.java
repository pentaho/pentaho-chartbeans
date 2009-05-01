package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CssStyle extends HashMap<String, String> implements Serializable {

  
  public static final String BACKGROUND_COLOR_STYLE = "background-color";
  public static final String BORDER_WIDTH_STYLE = "border-top-width";
  public static final String BORDER_COLOR_STYLE = "border-top-color";
  public static final String FONT_SIZE_STYLE = "font-size";
  public static final String FONT_WEIGHT_STYLE = "font-weight";
  public static final String FONT_STYLE = "font-style";
  public static final String FONT_FAMILY_STYLE = "font-family";
  public static final String COLOR_STYLE = "color";
  public static final String TEXT_ALIGNMENT_STYLE = "text-align";
  public static final String OPACITY_STYLE = "opacity";
  
  public enum FontWeight {NORMAL, BOLD};
  public enum FontStyle {NORMAL,ITALIC,OBLIQUE};
  public enum TextAlignment {LEFT, CENTER, RIGHT};
  
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
  
  public void setFontFamily(String family) {
    if (family == null) {
      remove(FONT_FAMILY_STYLE);
    } else {
      put(FONT_FAMILY_STYLE, family);
    }
  }
  
  public void setFont(String family, Integer size) {  
    setFontFamily(family);
    setFontSize(size);
  }
  
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
  
  public String toString() {
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

  public void setBorderVisible(boolean visible) {
    if (!visible) {
      setBorderColor(null);
      setBorderWidth(null);
    } else if (getBorderWidth() == null){
      setBorderWidth(1);
    }
  }
  
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
