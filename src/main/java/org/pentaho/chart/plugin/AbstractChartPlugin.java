/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.chart.plugin;

import org.pentaho.chart.IChartLinkGenerator;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.data.IChartDataModel;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.plugin.api.ChartResult;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.util.messages.Messages;

/**
 * @author wseyler
 * 
 * This class is a Abstract implementation of the IChartPlugin.  As such it contains implementations
 * that should be common across all chart plugin implementations.
 */
public abstract class AbstractChartPlugin implements IChartPlugin {


  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.IChartPlugin#validateChartDocument(org.pentaho.chart.core.ChartDocument)
   */
  public ChartResult validateChartDocument(final ChartDocument chartDocument) {
    if (chartDocument != null) {
      // Validate the series
      final ChartElement[] seriesElement = chartDocument.getRootElement().findChildrenByName("series"); //$NON-NLS-1$
      if (seriesElement == null || seriesElement.length < 1) {
        return new ChartResult(IChartPlugin.ERROR_MISSING_REQUIRED_DATA, Messages.getErrorString("AbstractChartPlugin.ERROR_0001_NO_ELEMENT", "series"));  //$NON-NLS-1$//$NON-NLS-2$
      }
    }
    return new ChartResult();
  }

  public IOutput renderChartDocument(ChartModel chartModel, IChartDataModel data, IChartLinkGenerator linkGenerator) {
    throw new UnsupportedOperationException();
  }
}
