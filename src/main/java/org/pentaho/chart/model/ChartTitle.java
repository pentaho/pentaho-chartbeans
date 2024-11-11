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

import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;

/**
 * The Chart Title appears prominently on the chart to give succinct explanation
 * of the chart. The Chart Title supports all of the parameters of StyledText.
 * @see StyledText
 *
 */
public class ChartTitle extends StyledText implements Serializable {
  public enum TitleLocation {BOTTOM, LEFT, TOP, RIGHT};
  
  TitleLocation location = TitleLocation.TOP;

  public ChartTitle() {
    super();
  }
  
  public ChartTitle(String text) {
    super(text);
  }
  
  public ChartTitle(String text, String fontFamily, FontStyle fontStyle, FontWeight fontWeight, int fontSize) {
    super(text, fontFamily, fontStyle, fontWeight, fontSize);
  }
  
  public TitleLocation getLocation() {
    return location;
  }

  /**
   * Not implemented. Presently all titles will appear at the Chart Engines default location.
   * @param location
   */
  public void setLocation(TitleLocation location) {
    this.location = location;
  }
}
