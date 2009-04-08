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
