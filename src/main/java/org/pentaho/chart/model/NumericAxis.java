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

public class NumericAxis extends Axis implements Serializable {

  Number minValue = null;
  Number maxValue = null;
  
  public Number getMinValue() {
    return minValue;
  }

  /**
   * The minumum value of the axis
   * @param minValue
   */
  public void setMinValue(Number minValue) {
    this.minValue = minValue;
  }

  public Number getMaxValue() {
    return maxValue;
  }

  /**
   * The maximum value of the axis
   * @param maxValue
   */
  public void setMaxValue(Number maxValue) {
    this.maxValue = maxValue;
  }
}
