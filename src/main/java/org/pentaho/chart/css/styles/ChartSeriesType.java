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


package org.pentaho.chart.css.styles;

import org.pentaho.reporting.libraries.css.values.CSSConstant;

/**
 * Defines the only valid values for the <code>-x-pentaho-chart-series-type</code> style
 *
 * @author Ravi Hasija
 */
public class ChartSeriesType {
  public static final CSSConstant UNDEFINED = new CSSConstant("undefined"); //$NON-NLS-1$
  public static final CSSConstant BAR = new CSSConstant("bar"); //$NON-NLS-1$
  public static final CSSConstant PIE = new CSSConstant("pie"); //$NON-NLS-1$
  public static final CSSConstant LINE = new CSSConstant("line"); //$NON-NLS-1$
  public static final CSSConstant AREA = new CSSConstant("area"); //$NON-NLS-1$
  public static final CSSConstant WATERFALL = new CSSConstant ("waterfall"); //$NON-NLS-1$
  public static final CSSConstant MULTI = new CSSConstant ("multi");  //$NON-NLS-1$
  public static final CSSConstant DIAL = new CSSConstant ("dial");  //$NON-NLS-1$

//  public ChartSeriesType() {
//  }
}
