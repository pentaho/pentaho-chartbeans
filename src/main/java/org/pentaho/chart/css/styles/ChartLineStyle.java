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
 * Defines the only valid values for the <code>-x-pentaho-chart-line-style</code> style
 *
 * @author David Kincade
 */
public class ChartLineStyle {
  public static final CSSConstant SOLID = new CSSConstant("solid");
  public static final CSSConstant DOTTED = new CSSConstant("dotted");
  public static final CSSConstant DASHED = new CSSConstant("dashed");
  public static final CSSConstant DOT_DASH = new CSSConstant("dot-dash");
  public static final CSSConstant DOT_DOT_DASH = new CSSConstant("dot-dot-dash");
  public static final CSSConstant THREE_D = new CSSConstant("three_d"); //$NON-NLS-1$

  private ChartLineStyle() {
  }
}
