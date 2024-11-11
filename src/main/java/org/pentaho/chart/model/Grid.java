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
