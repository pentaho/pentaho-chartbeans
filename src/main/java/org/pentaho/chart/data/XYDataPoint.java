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
