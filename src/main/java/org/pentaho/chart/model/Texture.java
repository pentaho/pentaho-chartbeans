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

public class Texture implements Serializable {
  String imageLocation;
  Integer x1;
  Integer y1;
  Integer x2;
  Integer y2;
  public String getImageLocation() {
    return imageLocation;
  }
  public void setImageLocation(String imageLocation) {
    this.imageLocation = imageLocation;
  }
  public Integer getX1() {
    return x1;
  }
  public void setX1(Integer x1) {
    this.x1 = x1;
  }
  public Integer getY1() {
    return y1;
  }
  public void setY1(Integer y1) {
    this.y1 = y1;
  }
  public Integer getX2() {
    return x2;
  }
  public void setX2(Integer x2) {
    this.x2 = x2;
  }
  public Integer getY2() {
    return y2;
  }
  public void setY2(Integer y2) {
    this.y2 = y2;
  }
  
  
}
