/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.chart.css.styles;

import org.pentaho.reporting.libraries.css.values.CSSConstant;

/**
 * Defines the only valid values for the <code>-x-pentaho-chart-axis-type-position</code> style
 *
 * @author Ravi Hasija
 */
public class ChartAxisPosition {
  public static final CSSConstant PRIMARY = new CSSConstant("primary"); //$NON-NLS-1$
  public static final CSSConstant SECONDARY = new CSSConstant("secondary"); //$NON-NLS-1$    

  private ChartAxisPosition() {
  }
}
