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
