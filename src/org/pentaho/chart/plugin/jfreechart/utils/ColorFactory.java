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
