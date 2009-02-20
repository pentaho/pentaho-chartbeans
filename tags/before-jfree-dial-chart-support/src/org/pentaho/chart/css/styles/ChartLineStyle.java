/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created 3/13/2008 
 * @author David Kincade 
 */
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

  private ChartLineStyle() {
  }
}
