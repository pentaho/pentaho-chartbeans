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

package org.pentaho.chart.css.keys;

import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;

/**
 * Defines all the charting specific style keys
 *
 * @author David Kincade, Ravi Hasija
 */
public class ChartStyleKeys {

  /**
   * The chart type: bar, line, pie etc.
   */
  public static final StyleKey CHART_TYPE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-series-type", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The orientation of the charts: vertical or horizontal.
   */
  public static final StyleKey ORIENTATION = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-orientation", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The line style for line charts (solid, dashed, etc.)
   */
  public static final StyleKey LINE_STYLE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-line-style", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The visibility of the line
   */
  public static final StyleKey LINE_VISIBLE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-line-visible", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * The visibility of the markers
   */
  public static final StyleKey MARKER_VISIBLE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-marker-visible", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * Wether or not the marker is filled
   */
  public static final StyleKey MARKER_FILLED = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-marker-filled", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
 
  /**
   * fill color for the marker.  If "default" then it will use the series color
   */
  public static final StyleKey MARKER_FILL_COLOR = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-marker-fill-color", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * The shape of the marker (ie. Rectangle, Elipse, arc, etc..)
   */
  public static final StyleKey MARKER_SHAPE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-marker-shape", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * The the top and bottom boundary rectangle size
   */
  public static final StyleKey MARKER_WIDTH = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-marker-width", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The left and right boundary rectangle size 
   */
  public static final StyleKey MARKER_HEIGHT = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-marker-height", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The line width for line charts (thin, medium, thick, 5px, 0.1cm)
   */
  public static final StyleKey LINE_WIDTH = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-line-width", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The bar style for bar charts (bar, cylinder, stacked, etc.)
   */
  public static final StyleKey BAR_STYLE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-bar-style", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The area chart style for area charts (basic, stacked, xy, etc.)
   */
  public static final StyleKey AREA_STYLE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-area-style", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * Bar width specified in terms of percentage
   */
  public static final StyleKey BAR_MAX_WIDTH = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-bar-max-width", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The Drill-Through URL
   */
  public static final StyleKey DRILL_URL = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-drill-url-template", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The axis type dimention (auto, range, domain)
   */
  public static final StyleKey AXIS_DIMENSION = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-axis-type-dimension", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The axis position (primary, secondary)
   */
  public static final StyleKey AXIS_POSITION = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-axis-type-position", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The axis order (1, 2)
   */
  public static final StyleKey AXIS_ORDER = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-axis-type-order", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * Controls whether item label is visible or not
   */
  public static final StyleKey ITEM_LABEL_VISIBLE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-item-label-visible", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The label text with it's formatting information
   */
  public static final StyleKey ITEM_LABEL_TEXT = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-item-label-text", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The gradient type (for eg: none, VERTICAL, CENTER_VERTICAL, HORIZONTAL, CENTER_HORIZONTAL, POINTS)
   */
  public static final StyleKey GRADIENT_TYPE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-gradient-type", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The gradient color style
   */
  public static final StyleKey GRADIENT_COLOR = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-gradient-color", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * The gradient start position indicator (for eg: 1,1)
   */
  public static final StyleKey GRADIENT_START = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-gradient-start", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * The gradient end position indicator (eg: 12,10)
   */
  public static final StyleKey GRADIENT_END = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-gradient-end", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The scale for the plot for eg: a scale of 1000 means data will be divided by 1000
   */
  public static final StyleKey SCALE_NUM = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-scale", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The category margin for the axis
   */
  public static final StyleKey MARGIN_CATEGORY = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-axis-category-margin", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$
 
  /**
   * The lower margin for the axis
   */
  public static final StyleKey MARGIN_LOWER = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-axis-lower-margin", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The upper Margin for the axis
   */
  public static final StyleKey MARGIN_UPPER = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-axis-upper-margin", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * The item margin for the axis
   */
  public static final StyleKey MARGIN_ITEM = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-axis-item-margin", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * Axis location (primary/secondary)
   */
  public static final StyleKey AXIS_LOCATION = StyleKeyRegistry.getRegistry().
        createKey("-x-pentaho-chart-axis-location", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * Set the pie explode percent. It defaults to zero.
   */
  public static final StyleKey PIE_EXPLODE_PERCENT = StyleKeyRegistry.getRegistry().
        createKey("-x-pentaho-chart-pie-explode-percent", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * Set the pie explode percent. It defaults to zero.
   */
  public static final StyleKey PIE_START_ANGLE = StyleKeyRegistry.getRegistry().
        createKey("-x-pentaho-chart-pie-start-angle", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * Set the pie chart label location to be inside the chart. It defaults to no.
   */
  public static final StyleKey PIE_LABELS_INSIDE_CHART = StyleKeyRegistry.getRegistry().
        createKey("-x-pentaho-chart-pie-labels-inside-chart", false, false, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * Set the color of the first bar in waterfall chart.
   */
  public static final StyleKey FIRST_BAR_COLOR = StyleKeyRegistry.getRegistry().
        createKey("-x-pentaho-chart-first-bar-color", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * Set the color for the last bar paint in waterfall chart.
   */
  public static final StyleKey LAST_BAR_COLOR = StyleKeyRegistry.getRegistry().
        createKey("-x-pentaho-chart-last-bar-color", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * Set the color to the positive bar in waterfall chart.
   */
  public static final StyleKey POSITIVE_BAR_COLOR = StyleKeyRegistry.getRegistry().
        createKey("-x-pentaho-chart-positive-bar-color", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * Set the color to the negative bar in waterfall chart.
   */
  public static final StyleKey NEGATIVE_BAR_COLOR = StyleKeyRegistry.getRegistry().
        createKey("-x-pentaho-chart-negative-bar-color", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  /**
   * The multi style for bar charts (bar, cylinder, stacked, etc.)
   */
  public static final StyleKey MULTI_STYLE = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-multi-style", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$

  /**
   * Gradient.
   */
  public static final StyleKey GRADIENT = StyleKeyRegistry.getRegistry().
      createKey("-x-pentaho-chart-gradient", false, true, StyleKey.All_ELEMENTS); //$NON-NLS-1$
  
  private ChartStyleKeys() {
  }
}

