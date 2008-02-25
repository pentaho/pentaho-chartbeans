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
 * @created Feb 14, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin;

import org.pentaho.experimental.chart.core.ChartDocument;

/**
 * @author wseyler
 *
 */
public interface IChartPlugin {
  public static final int RESULT_VALIDATED = 0;             // All is well
  public static final int RESULT_ERROR = 1;                 // General error... see description field
  public static final int ERROR_MISSING_REQUIRED_DATA = 2;  // Some required item was not provided
  public static final int ERROR_INVALID_DATA = 3;           // Item existed but the value didn't make sense
  public static final int ERROR_DUPLICATE_SINGLETON = 4;    // One item was expected but more than one was found
  
  public ChartResult validateChartDocument(ChartDocument chartDocument);
  public ChartResult renderChartDocument(ChartDocument chartDocument, IOutput output);
}
