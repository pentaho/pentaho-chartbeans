/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.chart.plugin.jfreechart.utils;

import java.awt.Color;

import org.pentaho.chart.core.ChartElement;
import org.pentaho.reporting.libraries.css.keys.color.ColorStyleKeys;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class ColorFactory {

  private static ColorFactory colorFacObj;

  private ColorFactory() {
  }

  /**
   * Returns a singleton StrokeFactory object.
   * @return StrokeFactory Returns a singleton object of this class.
   */
  public static synchronized ColorFactory getInstance() {
    if (ColorFactory.colorFacObj == null) {
      ColorFactory.colorFacObj = new ColorFactory();
    }
    return ColorFactory.colorFacObj;
  }
  
  public Color getColor(ChartElement elem) {
    return getColor(elem, ColorStyleKeys.COLOR);
  }
  
  public Color getColor(ChartElement elem, StyleKey key) {
    final CSSValue colorCSSValue = elem.getLayoutStyle().getValue(key);
    return JFreeChartUtils.getColorFromCSSValue(colorCSSValue);
  }
  

}
