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
 * Defines the only valid values for the <code>-x-pentaho-chart-area-style</code> style
 *
 * @author Ravi Hasija
 */
public class ChartAreaStyle {
  public static final CSSConstant AREA = new CSSConstant("area"); //$NON-NLS-1$
  public static final CSSConstant STACKED = new CSSConstant("stacked"); //$NON-NLS-1$
  public static final CSSConstant XY = new CSSConstant("xy"); //$NON-NLS-1$

  private ChartAreaStyle() {
  }
}
