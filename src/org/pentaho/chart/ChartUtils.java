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

package org.pentaho.chart;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.styles.ChartCSSFontSizeMappingConstants;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.font.FontSizeConstant;
import org.pentaho.reporting.libraries.css.keys.font.FontStyle;
import org.pentaho.reporting.libraries.css.keys.font.FontStyleKeys;
import org.pentaho.reporting.libraries.css.keys.font.FontWeight;
import org.pentaho.reporting.libraries.css.keys.font.RelativeFontSize;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class ChartUtils {

  private static Map<String, String> cssFamilyToAwtFamilyMap;
  
  static {
    cssFamilyToAwtFamilyMap = new HashMap<String, String>();
    cssFamilyToAwtFamilyMap.put("monospace", "Monospaced");
    cssFamilyToAwtFamilyMap.put("serif", "Serif");
    cssFamilyToAwtFamilyMap.put("sans-serif", "SansSerif");
  }
  
  public static Font getFont(String fontFamily, org.pentaho.chart.model.CssStyle.FontStyle fontStyle, org.pentaho.chart.model.CssStyle.FontWeight fontWeight, Integer fontSize) {
    Font font = null;
    if (fontFamily != null) {
      if (cssFamilyToAwtFamilyMap.get(fontFamily) != null) {
        fontFamily = cssFamilyToAwtFamilyMap.get(fontFamily);
      }
      int styleFlag = Font.PLAIN;

      if (org.pentaho.chart.model.CssStyle.FontStyle.ITALIC.equals(fontStyle))
      {
        styleFlag |= Font.ITALIC;
      }

      if (org.pentaho.chart.model.CssStyle.FontWeight.BOLD.equals(fontWeight))
      {
        styleFlag |= Font.BOLD;
      }
      
      // Creating the requisite font and setting the default size of 10. This will be overwritten below.
      font = new Font(fontFamily, styleFlag, fontSize == null ? 10 : fontSize);
    }
    return font;
  }
  
  /**
   * This method creates a Font object based on the font-family, font-size, and
   * font-style defined in the chart xml doc for the current series
   *
   * @param currentSeries Current series element
   * @return Font  The font object created based on the current series font css style
   */
  public static Font getFont(final ChartElement currentSeries) {
    Font font = null;
    if (currentSeries != null) {
      final LayoutStyle layoutStyle = currentSeries.getLayoutStyle();
      CSSValue cssValue = layoutStyle.getValue(FontStyleKeys.FONT_FAMILY);
      String fontFamily = getFontFamily(currentSeries);
      if (cssFamilyToAwtFamilyMap.get(fontFamily) != null) {
        fontFamily = cssFamilyToAwtFamilyMap.get(fontFamily);
      }
      
      if (fontFamily != null) {
        final CSSValue fontStyle = getFontStyle(currentSeries);
        final CSSValue fontWeight = getFontWeight(currentSeries);

        // Font Style default
        int styleFlag = Font.PLAIN;

        if (FontStyle.ITALIC.equals(fontStyle))
        {
          styleFlag |= Font.ITALIC;
        }

        if (FontWeight.BOLD.equals(fontWeight))
        {
          // todo: Font weight can also be a numeric value.
          styleFlag |= Font.BOLD;
        }
        // Creating the requisite font and setting the default size of 10. This will be overwritten below.
        font = new Font(fontFamily, styleFlag, 10);

        // Modifying the size of the font since we cannot create a Font with size of type float.
        // We can only modify it's size to float value and not create it with float value.
        font = font.deriveFont(getFontSize(currentSeries));
      }
    }
    return font;
  }
  
  public static String getFontFamily(ChartElement currentSeries) {
    String fontFamily = null;
    if (currentSeries != null) {
      final LayoutStyle layoutStyle = currentSeries.getLayoutStyle();
      CSSValue cssValue = layoutStyle.getValue(FontStyleKeys.FONT_FAMILY);
      fontFamily = cssValue != null ? cssValue.getCSSText() : null;
      
    }
    return fontFamily;
  }
  
  public static CSSValue getFontWeight(final ChartElement element) {
    final LayoutStyle layoutStyle = element.getLayoutStyle();
    return layoutStyle.getValue(FontStyleKeys.FONT_WEIGHT);
  }
  
  public static CSSValue getFontStyle(final ChartElement element) {
    final LayoutStyle layoutStyle = element.getLayoutStyle();
    return layoutStyle.getValue(FontStyleKeys.FONT_STYLE);
  }
  

  /**
   * CSSFontSize values: xx-small, x-small, small, medium, large, x-large, xx-large
   * are mapped to certain integer values based on ChartCSSFontSizeMappingConstants file.
   * CSSFontSize values: smaller, larger, and % are based on parent values. Hence for that
   * we establish the current series font size based on the parent font size value.
   *
   * @param element The current series element that would be looked for the font style
   * @return float   The font size for the current series element.
   */
  public static float getFontSize(final ChartElement element) {
    float size = 0;
    final LayoutStyle layoutStyle = element.getLayoutStyle();
    final CSSValue fontSizeValue = layoutStyle.getValue(FontStyleKeys.FONT_SIZE);

    if (FontSizeConstant.XX_SMALL.equals(fontSizeValue)) {
      size = ChartCSSFontSizeMappingConstants.XX_SMALL;
    } else if (FontSizeConstant.X_SMALL.equals(fontSizeValue)) {
      size = ChartCSSFontSizeMappingConstants.X_SMALL;
    } else if (FontSizeConstant.SMALL.equals(fontSizeValue)) {
      size = ChartCSSFontSizeMappingConstants.SMALL;
    } else if (FontSizeConstant.MEDIUM.equals(fontSizeValue)) {
      size = ChartCSSFontSizeMappingConstants.MEDIUM;
    } else if (FontSizeConstant.LARGE.equals(fontSizeValue)) {
      size = ChartCSSFontSizeMappingConstants.LARGE;
    } else if (FontSizeConstant.X_LARGE.equals(fontSizeValue)) {
      size = ChartCSSFontSizeMappingConstants.X_LARGE;
    } else if (FontSizeConstant.XX_LARGE.equals(fontSizeValue)) {
      size = ChartCSSFontSizeMappingConstants.XX_LARGE;
    } else if (RelativeFontSize.SMALLER.equals(fontSizeValue)) {
      final ChartElement parentElement = element.getParentItem();
      final float parentSize = getFontSize(parentElement);
      if (parentSize >= ChartCSSFontSizeMappingConstants.XX_SMALL &&
              parentSize <= ChartCSSFontSizeMappingConstants.XX_LARGE) {
        size = (parentSize - 2);
      } else {
        size = ChartCSSFontSizeMappingConstants.SMALLER;
      }
    } else if (RelativeFontSize.LARGER.equals(fontSizeValue)) {
      final ChartElement parentElement = element.getParentItem();
      final float parentSize = getFontSize(parentElement);
      if (parentSize >= ChartCSSFontSizeMappingConstants.XX_SMALL &&
              parentSize <= ChartCSSFontSizeMappingConstants.XX_LARGE) {
        size = (parentSize + 2);
      } else {
        size = ChartCSSFontSizeMappingConstants.LARGER;
      }
    } else if (fontSizeValue instanceof CSSNumericValue) {
      final CSSNumericValue fontSize = (CSSNumericValue) fontSizeValue;
      final CSSNumericType fontSizeType = fontSize.getNumericType();
      if (CSSNumericType.PERCENTAGE.equals(fontSizeType)) {
        final ChartElement parentElement = element.getParentItem();
        final float parentSize = getFontSize(parentElement);
        if (parentSize >= ChartCSSFontSizeMappingConstants.XX_SMALL &&
                parentSize <= ChartCSSFontSizeMappingConstants.XX_LARGE) {
          size = (parentSize * ((float) fontSize.getValue()) / 100);
        }
      } else {
        size = (float) fontSize.getValue();
      }
    }

    //If we do not have a font defined at current level 
    // then we return the parent font size
    if (size <= 0) {
      final ChartElement parentElement = element.getParentItem();
      if (parentElement != null) {
        size = getFontSize(parentElement);
      } else {
        size = 0;
      }
    }
    return size;
  }
}
