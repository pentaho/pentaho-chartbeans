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
 * Defines the only valid values for the <code>-x-pentaho-chart-gradient-type</code> style
 *
 * @author Ravi Hasija
 */
public class ChartGradientType {
  public static final CSSConstant NONE = new CSSConstant("none"); //$NON-NLS-1$
  public static final CSSConstant VERTICAL = new CSSConstant("vertical"); //$NON-NLS-1$
  public static final CSSConstant HORIZONTAL = new CSSConstant("horizontal"); //$NON-NLS-1$
  public static final CSSConstant CENTER_HORIZONTAL = new CSSConstant("center-horizontal"); //$NON-NLS-1$
  public static final CSSConstant CENTER_VERTICAL = new CSSConstant("center-vertical"); //$NON-NLS-1$
  public static final CSSConstant POINTS = new CSSConstant("points"); //$NON-NLS-1$  

  private ChartGradientType() {
  }
}
