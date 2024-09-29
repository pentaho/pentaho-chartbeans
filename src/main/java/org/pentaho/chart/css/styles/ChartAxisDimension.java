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
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSAutoValue;

/**
 * Defines the only valid values for the <code>-x-pentaho-chart-axis-type-dimension</code> style
 *
 * @author Ravi Hasija
 */
public class ChartAxisDimension {
  public static final CSSValue AUTO = CSSAutoValue.getInstance(); //$NON-NLS-1$
  public static final CSSConstant DOMAIN = new CSSConstant("domain"); //$NON-NLS-1$
  public static final CSSConstant RANGE = new CSSConstant("range"); //$NON-NLS-1$  

  private ChartAxisDimension() {
  }
}
