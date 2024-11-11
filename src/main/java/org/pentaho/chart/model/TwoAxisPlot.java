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

public class TwoAxisPlot extends Plot implements Serializable {
  
  Grid grid = new Grid();
  Axis verticalAxis;
  Axis horizontalAxis;
  
  protected TwoAxisPlot(Axis horizontalAxis, NumericAxis verticalAxis) {
    this.verticalAxis = verticalAxis;
    this.horizontalAxis = horizontalAxis;
  }
  
  public Axis getHorizontalAxis() {
    return horizontalAxis;
  }
  
  public Axis getVerticalAxis() {
    return verticalAxis;
  }

  public Axis getDomainAxis() {
    return  getOrientation() == Orientation.HORIZONTAL ? verticalAxis : horizontalAxis;
  }
  
  public NumericAxis getRangeAxis() {
    return  (NumericAxis)(getOrientation() == Orientation.HORIZONTAL ? horizontalAxis : verticalAxis);
  }

  /**
   * {@link org.pentaho.chart.model.Plot#setOrientation(Orientation)}
   */
  public void setOrientation(Orientation orientation) {
    if (this.orientation != orientation) {
      Axis tmpAxis = verticalAxis;
      verticalAxis = horizontalAxis;
      horizontalAxis = tmpAxis;
    }
    super.setOrientation(orientation);
  }

  public Grid getGrid() {
    return grid;
  }

}
