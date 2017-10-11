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

import org.pentaho.chart.model.CssStyle.VisibilityStyle;

public class Grid implements Serializable {
  public static final int DEFAULT_GRID_COLOR = 0xDFDFDF;
  
  CssStyle verticalLines = new CssStyle();
  CssStyle horizontalLines = new CssStyle();
  
  public Grid() {
  }
  
  public Integer getHorizontalLineColor() {
    return horizontalLines.getColor();
  }
  
  /**
   * Sets the color of the horizontal grid lines
   * {@link org.pentaho.chart.model.CssStyle#setColor(Integer)}
   * 
   * @param color
   */
  public void setHorizontalLineColor(Integer color) {
    horizontalLines.setColor(color);
  }
  
  public Integer getVerticalLineColor() {
    return verticalLines.getColor();
  }
  
  /**
   * Sets the color of the vertical grid lines
   * {@link org.pentaho.chart.model.CssStyle#setColor(Integer)}
   * 
   * @param color
   */
  public void setVerticalLineColor(Integer color) {
    verticalLines.setColor(color);
  }
  
  public CssStyle getVerticalLineStyle() {
    return verticalLines;
  }
  
  public CssStyle getHorizontalLineStyle() {
    return horizontalLines;
  }

  public boolean getHorizontalLinesVisible() {
    return horizontalLines.getVisibility() != VisibilityStyle.HIDDEN;
  }

  /**
   * Sets the visibility of the horizontal grid lines
   * {@link org.pentaho.chart.model.CssStyle#setVisibility(Visibility)}
   * 
   * @param visible
   */
  public void setHorizontalLinesVisible(boolean horizontalLinesVisible) {
    horizontalLines.setVisibility(horizontalLinesVisible ? null : VisibilityStyle.HIDDEN);
  }

  public boolean getVerticalLinesVisible() {
    return verticalLines.getVisibility() != VisibilityStyle.HIDDEN;
  }

  /**
   * Sets the visibility of the vertical grid lines
   * {@link org.pentaho.chart.model.CssStyle#setVisibility(Visibility)}
   * 
   * @param visible
   */
  public void setVerticalLinesVisible(boolean verticalLinesVisible) {
    verticalLines.setVisibility(verticalLinesVisible ? null : VisibilityStyle.HIDDEN);
  }
  
  /**
   * Sets the visibility of the horizontal and vertical grid lines
   * {@link org.pentaho.chart.model.CssStyle#setVisibility(Visibility)}
   * 
   * @param visible
   */
  public void setVisible(boolean visible) {
    setVerticalLinesVisible(visible);
    setHorizontalLinesVisible(visible);
  }
  
  public boolean getVisible() {
    return getVerticalLinesVisible() || getHorizontalLinesVisible();
  }
  
  /**
   * Sets the color of the horizontal and vertical grid lines
   * {@link org.pentaho.chart.model.CssStyle#setColor(Integer)}
   * 
   * @param color
   */
  public void setColor(Integer color) {
    setVerticalLineColor(color);
    setHorizontalLineColor(color);
  }
}
