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


package org.pentaho.chart.css.styles;

import org.pentaho.reporting.libraries.css.values.CSSConstant;

/**
 * Defines the only valid values for the <code>-x-pentaho-chart-bar-style</code> style
 *
 * @author Ravi Hasija
 */
public class ChartBarStyle {
  public static final CSSConstant BAR = new CSSConstant("bar"); //$NON-NLS-1$
  public static final CSSConstant CYLINDER = new CSSConstant("cylinder"); //$NON-NLS-1$
  public static final CSSConstant INTERVAL = new CSSConstant("interval"); //$NON-NLS-1$
  public static final CSSConstant STACKED = new CSSConstant("stacked"); //$NON-NLS-1$
  public static final CSSConstant LAYERED = new CSSConstant("layered"); //$NON-NLS-1$
  public static final CSSConstant STACK_PERCENT = new CSSConstant("stack-percent"); //$NON-NLS-1$
  public static final CSSConstant STACK_100_PERCENT = new CSSConstant("stack-100percent"); //$NON-NLS-1$
  public static final CSSConstant WATERFALL = new CSSConstant("waterfall"); //$NON-NLS-1$
  public static final CSSConstant THREED = new CSSConstant("threed"); //$NON-NLS-1$
  
  private ChartBarStyle() {
  }
}
