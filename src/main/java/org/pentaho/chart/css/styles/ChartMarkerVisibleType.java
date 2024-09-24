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
 * Defines the only valid values for the <code>-x-pentaho-chart-marker-visible</code> style
 *
 * @author William Seyler
 */
public class ChartMarkerVisibleType {
  public static final CSSConstant HIDDEN = new CSSConstant("hidden"); //$NON-NLS-1$
  public static final CSSConstant VISIBLE = new CSSConstant("visible"); //$NON-NLS-1$

  private ChartMarkerVisibleType() {
  }
}
