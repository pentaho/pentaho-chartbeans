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
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.ChartResult;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.util.messages.Messages;

/**
 * @author wseyler
 * 
 * This class is a Abstract implementation of the IChartPlugin.  As such it contains implementations
 * that should be common across all chart plugin implementations.
 */
public abstract class AbstractChartPlugin implements IChartPlugin {
  protected IChartCallback callback = null;

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.IChartPlugin#renderChartDocument(org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.IOutput)
   */
  public ChartResult renderChartDocument(final ChartDocument chartDocument, final ChartTableModel data, final IOutput output) {
    return validateChartDocument(chartDocument);
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.IChartPlugin#validateChartDocument(org.pentaho.experimental.chart.core.ChartDocument)
   */
  public ChartResult validateChartDocument(final ChartDocument chartDocument) {

    // Validate the series
    final ChartElement[] seriesElement = chartDocument.getRootElement().findChildrenByName("series"); //$NON-NLS-1$
    if (seriesElement == null || seriesElement.length < 1) {
      return new ChartResult(IChartPlugin.ERROR_MISSING_REQUIRED_DATA, Messages.getErrorString("AbstractChartPlugin.ERROR_0001_NO_ELEMENT", "series"));  //$NON-NLS-1$//$NON-NLS-2$
    }

    return new ChartResult();
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.IChartPlugin#setChartCallback(org.pentaho.experimental.chart.plugin.IChartCallback)
   */
  public void setChartCallback(final IChartCallback callback) {
    this.callback = callback;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.IChartPlugin#getChartCallback()
   */
  public IChartCallback getChartCallback() {
    return callback;
  }
}
