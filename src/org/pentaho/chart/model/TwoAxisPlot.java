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
