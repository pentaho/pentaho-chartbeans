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

package org.pentaho.chart.data;

import org.pentaho.chart.model.Plot.Orientation;

public class XYDataPoint {

  Number domainValue;
  Number rangeValue;
  
  public XYDataPoint() {
    
  }
  
  public XYDataPoint(Number domainValue, Number rangeValue) {
    this.domainValue = domainValue;
    this.rangeValue = rangeValue;
  }
  
  public Number getX(Orientation chartOrientation) {
    return chartOrientation == Orientation.HORIZONTAL ? getRangeValue() : getDomainValue();
  }

  public void setX(Orientation chartOrientation, Number x) {
    if (chartOrientation == Orientation.HORIZONTAL) {
      setRangeValue((Number)x);
    } else {
      setDomainValue(x);
    }
  }

  public Number getY(Orientation chartOrientation) {
    return chartOrientation == Orientation.HORIZONTAL ? getDomainValue() : getRangeValue();
  }

  public void setY(Orientation chartOrientation, Number y) {
    if (chartOrientation == Orientation.HORIZONTAL) {
      setDomainValue(y);
    } else {
      setRangeValue((Number)y);
    }
  }
  
  public Number getX() {
    return getX(Orientation.VERTICAL);
  }

  public void setX(Number x) {
    setX(Orientation.VERTICAL, x);
  }

  public Number getY() {
    return getY(Orientation.VERTICAL);
  }

  public void setY(Number y) {
    setY(Orientation.VERTICAL, y);
  }
  
  public void setDomainValue(Number value) {
    domainValue = value;
  }
  
  public void setRangeValue(Number value) {
    rangeValue = value;
  }
  
  public Number getDomainValue() {
    return domainValue;
  }
  
  public Number getRangeValue() {
    return rangeValue;
  }
}
