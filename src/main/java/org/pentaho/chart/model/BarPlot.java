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

public class BarPlot extends TwoAxisPlot implements Serializable {
  private static final float DEFAULT_OPACITY = 0.85f;
  public enum BarPlotFlavor {PLAIN, THREED, GLASS, SKETCH, STACKED};
  
  BarPlotFlavor flavor = BarPlotFlavor.PLAIN;

  public BarPlot() {
    super(new Axis(), new NumericAxis());
  }

  public BarPlot(BarPlotFlavor flavor) {
    this();
    setOpacity(DEFAULT_OPACITY);
    setFlavor(flavor);
  }
  
  public BarPlotFlavor getFlavor() {
    return flavor;
  }

  /**
   * Set the visual appeal of the chart.
   * <p>
   * <table>
   *   <tr><th colspan="2">Possible values</th></tr>
   *   <tr><td>PLAIN</td><td>Standard two-dimensional chart</td></tr>
   *   <tr><td>THREED</td><td>Three-dimensional chart</td></tr>
   *   <tr><td>GLASS</td><td>Chart with glass components</td></tr>
   *   <tr><td>SKETCH</td><td>Chart that has been sketched</td></tr>
   *   <tr><td>STACKED</td><td>A stacked chart</td></tr>
   * </table>
   * </p>
   * @param flavor
   */
  public void setFlavor(BarPlotFlavor flavor) {
    this.flavor = flavor;
  }
}
