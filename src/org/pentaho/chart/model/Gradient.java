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

public class Gradient implements Serializable {
  Integer x1;
  Integer y1;
  Integer color1;
  Integer x2;
  Integer y2;
  Integer color2;
  
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
  
  public Integer getColor1() {
    return color1;
  }
  
  public void setColor1(Integer color1) {
    this.color1 = color1;
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
  
  public Integer getColor2() {
    return color2;
  }
  
  public void setColor2(Integer color2) {
    this.color2 = color2;
  }
}
