/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart.model;

import java.io.Serializable;

public class AreaPlot extends TwoAxisPlot implements Serializable {
  private static final float DEFAULT_OPACITY = 0.45f;
  
  public AreaPlot() {
    super(new Axis(), new NumericAxis());
    setOpacity(DEFAULT_OPACITY);
  }
}
