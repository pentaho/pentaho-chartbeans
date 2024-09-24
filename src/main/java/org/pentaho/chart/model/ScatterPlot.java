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

public class ScatterPlot extends TwoAxisPlot implements Serializable {
  private static final float DEFAULT_OPACITY = 0.45f;
  
  public ScatterPlot() {
    super(new NumericAxis(), new NumericAxis());
    setOpacity(DEFAULT_OPACITY);
    setPalette(new Palette(0x000000));
  }
  
  public NumericAxis getXAxis() {
    return (NumericAxis)getHorizontalAxis();
  }
  
  public NumericAxis getYAxis() {
    return (NumericAxis)getVerticalAxis();
  }
}
