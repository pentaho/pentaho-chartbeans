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


package org.pentaho.chart.plugin;

import java.util.Set;

import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.IChartLinkGenerator;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.data.IChartDataModel;
import org.pentaho.chart.model.ChartModel;
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

  public String getPluginId();
  
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

  public IOutput renderChartDocument(ChartModel chartModel, IChartDataModel data);
  
  public IOutput renderChartDocument(ChartModel chartModel, IChartDataModel data, IChartLinkGenerator linkGenerator);
  /**
   * Returns a set of OutputTypes that this plugin can return (via the IOutput returned from renderChartDocument).
   * @return
   */
  public Set<IOutput.OutputTypes> getSupportedOutputs();
}
