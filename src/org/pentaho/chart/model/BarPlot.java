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
