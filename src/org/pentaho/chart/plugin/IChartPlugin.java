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


package org.pentaho.chart.plugin;

import java.util.Set;

import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.api.ChartResult;
import org.pentaho.chart.plugin.api.IOutput;

/**
 * @author wseyler
 */
public interface IChartPlugin {
  public static final int RESULT_VALIDATED = 0;             // All is well
  public static final int RESULT_ERROR = 1;                 // General error... see description field
  public static final int ERROR_MISSING_REQUIRED_DATA = 2;  // Some required item was not provided
  public static final int ERROR_INVALID_DEFINITION = 3;     // Item existed but the value didn't make sense
  public static final int ERROR_DUPLICATE_SINGLETON = 4;    // One item was expected but more than one was found
  public static final int ERROR_INDETERMINATE_CHART_TYPE = 5; // Couldn't figure out the chart type

  /**
   * Validates the current chart document.  AbstractChartPlugin implements the validations that are
   * NOT plugin specific.  The plugin should override and call the super before doing it's validation
   * 
   * @param chartDocument
   * @return
   */
  public ChartResult validateChartDocument(ChartDocument chartDocument);

  /**
   * Renders the chart based on the chartDocument and data.  Chart can be accessed via the output parameter
   * which gets returned from this method call
   * 
   * @param chartDocument
   * @param data
   * @return IOutput
   */
  public IOutput renderChartDocument(ChartDocumentContext chartDocumentContext, ChartTableModel data);

  /**
   * Returns a set of OutputTypes that this plugin can return (via the IOutput returned from renderChartDocument).
   * @return
   */
  public Set<IOutput.OutputTypes> getSupportedOutputs();
}
