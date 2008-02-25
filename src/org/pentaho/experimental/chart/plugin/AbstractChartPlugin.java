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
 * @created Feb 25, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin;

import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;

/**
 * @author wseyler
 *
 */
public abstract class AbstractChartPlugin implements IChartPlugin {

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.IChartPlugin#renderChartDocument(org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.IOutput)
   */
  public ChartResult renderChartDocument(ChartDocument chartDocument, IOutput output) {
    ChartResult validated = validateChartDocument(chartDocument);
    return validated;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.IChartPlugin#validateChartDocument(org.pentaho.experimental.chart.core.ChartDocument)
   */
  public ChartResult validateChartDocument(ChartDocument chartDocument) {
    
    // Validate the plot
    ChartElement[] plotElement = chartDocument.getRootElement().findChildrenByName("plot");
    if (plotElement == null || plotElement.length < 1) {
      return new ChartResult(ERROR_MISSING_REQUIRED_DATA, "plot", "Missing the plot element");
    }
    
    // Validate the axis
    ChartElement[] axisElement = chartDocument.getRootElement().findChildrenByName("axis");
    if (axisElement == null || axisElement.length < 1) {
      return new ChartResult(ERROR_MISSING_REQUIRED_DATA, "axis", "Missing the axis element");
    }
    
    // Validate the series
    ChartElement[] seriesElement = chartDocument.getRootElement().findChildrenByName("series");
    if (seriesElement == null || seriesElement.length < 1) {
      return new ChartResult(ERROR_MISSING_REQUIRED_DATA, "series", "Missing the series element");
    }

    return new ChartResult();
  }

}
