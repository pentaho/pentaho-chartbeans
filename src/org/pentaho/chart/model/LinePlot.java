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

import org.pentaho.chart.model.CssStyle.LineStyle;

public class LinePlot extends TwoAxisPlot implements Serializable {

  public enum LinePlotFlavor {PLAIN, THREED, DOT, DASH, DASHDOT, DASHDOTDOT};
  
  LinePlotFlavor flavor = LinePlotFlavor.PLAIN;

  public LinePlot() {
    super(new Axis(), new NumericAxis());
  }

  public LinePlot(LinePlotFlavor flavor) {
    this();
    setFlavor(flavor);
  }
  
  public LinePlotFlavor getFlavor() {
    return flavor;
  }

  /**
   * Set the visual appeal of the chart.
   * <p>
   * <table>
   *   <tr><th colspan="2">Possible values</th></tr>
   *   <tr><td>PLAIN</td><td>Standard chart</td></tr>
   *   <tr><td>THREED</td><td>Three-dimensional chart</td></tr>
   *   <tr><td>DOT</td><td>Lines made of dots</td></tr>
   *   <tr><td>DASH</td><td>Lines made of dashes</td></tr>
   *   <tr><td>DASHDOT</td><td>Lines made of a dash then a dot, repeated</td></tr>
   *   <tr><td>DASHDOTDOT</td><td>Lines made of a dash a dot and another dot, repeated</td></tr>
   * </table>
   * </p>
   * 
   * @param flavor
   */
  public void setFlavor(LinePlotFlavor flavor) {
    this.flavor = flavor;
  }
  
  /**
   * The width of the lines that represent data in pixels.
   * @param width
   */
  public void setLineWidth(Integer width) {
    style.setBorderWidth(width);
  }
  
  public Integer getLineWidth() {
    return style.getBorderWidth();
  }
}
