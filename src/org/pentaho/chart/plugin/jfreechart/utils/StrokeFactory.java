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

package org.pentaho.chart.plugin.jfreechart.utils;

import java.awt.BasicStroke;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyle;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.keys.border.BorderWidth;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * This class provides a BasicStroke object that implements border-style/line-style and line-width.
 * Author: Ravi Hasija
 * Date: May 12, 2008
 * Time: 12:18:40 PM
 */
public class StrokeFactory {
  
  private static final Log logger = LogFactory.getLog(StrokeFactory.class);
  
  private static StrokeFactory strokeFacObj;
  private static final float THIN = 1f;
  private static final float MEDIUM = 2f;
  private static final float THICK = 4f;
  private static final String CENTIMETER = "cm"; //$NON-NLS-1$
  private static final String PIXEL = "px"; //$NON-NLS-1$
  private static final float CENTIMETER_TO_PIXEL = 37.80f;

  private StrokeFactory() {
  }

  /**
   * Returns a singleton StrokeFactory object.
   * @return StrokeFactory Returns a singleton object of this class.
   */
  public static synchronized StrokeFactory getInstance() {
    if (StrokeFactory.strokeFacObj == null) {
      StrokeFactory.strokeFacObj = new StrokeFactory();
    }
    return StrokeFactory.strokeFacObj;
  }

  /**
   * Overriding Object class's clone method so that clone() is not supported.
   * 
   * @return Throws CloneNotSupportedException and returns nothing. 
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }

  /**
   * Returns a basic stroke object that implements the border-style and border-width style keys.
   *
   * NOTE: We do not support separate borders for top, bottom, left and right side of
   * the bar. So we accept the first value and implement it for border-style and
   * border-width.
   *
   * @param element  The current series element
   * @return BasicStroke  The basic stroke object that implements the boder-style and boder-width.
   */
  public BasicStroke getBorderStroke(final ChartElement element) {
    if (element == null) {
      return null;
    } else {
      return getBasicStroke(element, BorderStyleKeys.BORDER_TOP_STYLE, BorderStyleKeys.BORDER_TOP_WIDTH);
    }
  }

  /**
   * Returns a basic stroke object that implements the line-style and line-width style keys.
   *
   * @param element The current series element
   * @return BasicStroke  The basic stroke object that implements the line-style and line-width.
   */
  public BasicStroke getLineStroke(final ChartElement element) {
    if (element == null) {
      return null;
    } else {
      return getBasicStroke(element, ChartStyleKeys.LINE_STYLE, ChartStyleKeys.LINE_WIDTH);
    }
  }

    /**
   * This method creates a BasicStroke object for border-style/line-style like dotted, solid etc
   * It also incorporates border-width for the border/line width for the line.
   * <p/>
   * NONE, HIDDEN, SOLID, DASHED, DOT-DASH and DOTTED are the only border-style/line-style
   * that we currently support.
   * The border-width/line-width: thin, medium and thick have been mapped to static widths.
   *
   * @param chartElement The current series element
   * @param widthStyleKey The Width style key. 
   * @param styleStyleKey The Style style key.
   * @return BasicStroke  The basic stroke object that implements the style and width.
   */
  private BasicStroke getBasicStroke(final ChartElement chartElement, final StyleKey styleStyleKey, final StyleKey widthStyleKey) {
    CSSValue cssValue = chartElement.getLayoutStyle().getValue(widthStyleKey);
    final String borderWidth = (cssValue != null ? cssValue.getCSSText() : null);

    float width = 0f;
    if (borderWidth != null) {
      if (borderWidth.equalsIgnoreCase(BorderWidth.THIN.toString())) {
        width = THIN;
      } else if (borderWidth.equalsIgnoreCase(BorderWidth.MEDIUM.toString())) {
        width = MEDIUM;
      } else if (borderWidth.equalsIgnoreCase(BorderWidth.THICK.toString())) {
        width = THICK;
      } else if (borderWidth.endsWith(PIXEL)) {
        final String borderWidthPixels = (borderWidth.substring(0, borderWidth.indexOf(PIXEL))).trim();
        width = Integer.parseInt(borderWidthPixels);
      } else if (borderWidth.endsWith(CENTIMETER)) {
        final String borderWidthCms = (borderWidth.substring(0, borderWidth.indexOf(CENTIMETER))).trim();
        // Convert centimeter to pixels
        width = Integer.parseInt(borderWidthCms) * CENTIMETER_TO_PIXEL;          
      }
    }

    final CSSValue borderStyle = chartElement.getLayoutStyle().getValue(styleStyleKey);
    if ((borderStyle == null) || BorderStyle.NONE.getCSSText().equals(borderStyle.getCSSText())) {
      // TODO mlowery figure out why logging won't output a "lesser" priority for this call
      logger.warn(String.format("************style %s has value %s; stroke will be null", styleStyleKey.name, BorderStyle.NONE.getCSSText()));
      return null;
    }
    
    BasicStroke stroke = null;

    if (BorderStyle.SOLID.equals(borderStyle)) {
      stroke = new BasicStroke(width);
    } else if (BorderStyle.DASHED.equals(borderStyle)) {
      stroke = new BasicStroke(width, BasicStroke.CAP_BUTT,
              BasicStroke.JOIN_MITER,
              10.0F,
              new float[]{10.0F, 3.0F},
              0.F);
    } else if (BorderStyle.DOT_DASH.equals(borderStyle)) {
      stroke = new BasicStroke(width, BasicStroke.CAP_BUTT,
              BasicStroke.JOIN_MITER,
              10.0F,
              new float[]{10.0F, 3.0F, 2.0F, 2.0F},
              0.F);
    } else if (BorderStyle.DOTTED.equals(borderStyle)) {
      stroke = new BasicStroke(width, BasicStroke.CAP_ROUND,
              BasicStroke.JOIN_ROUND,
              0,
              new float[]{0, 6, 0, 6},
              0);
    }
    return stroke;
  }
}
