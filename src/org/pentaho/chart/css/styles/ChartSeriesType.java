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

//  public ChartSeriesType() {
//  }
}
