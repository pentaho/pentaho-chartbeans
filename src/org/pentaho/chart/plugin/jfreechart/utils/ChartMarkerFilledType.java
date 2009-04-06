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
 * @created May 22, 2008 
 * @author wseyler
 */


package org.pentaho.chart.plugin.jfreechart.utils;

import org.pentaho.reporting.libraries.css.values.CSSConstant;

/**
 * @author wseyler
 *
 */
public class ChartMarkerFilledType {
  public static final CSSConstant EMPTY = new CSSConstant("empty"); //$NON-NLS-1$
  public static final CSSConstant FILLED = new CSSConstant("filled"); //$NON-NLS-1$

  private ChartMarkerFilledType() {
  }
}
