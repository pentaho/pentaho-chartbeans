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


package org.pentaho.chart.plugin.openflashchart;

import java.util.EnumSet;
import java.util.Set;

import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.IChartLinkGenerator;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.data.IChartDataModel;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.plugin.AbstractChartPlugin;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.ChartResult;
import org.pentaho.chart.plugin.api.IOutput;

/**
 * @author arodriguez
 */
public class OpenFlashChartPlugin extends AbstractChartPlugin
{
  public static final String PLUGIN_ID = "OpenFlashChart";
  
  private OpenFlashChartFactoryEngine chartFactory;
  
  private static final Set<IOutput.OutputTypes> supportedOutputs =
      EnumSet.of(IOutput.OutputTypes.DATA_TYPE_STREAM);

  public OpenFlashChartPlugin()
  {
    chartFactory = new OpenFlashChartFactoryEngine();
  }

  public IOutput renderChartDocument(ChartModel chartModel, IChartDataModel chartTableModel) {
    return renderChartDocument(chartModel, chartTableModel, null);
  }
  
  public IOutput renderChartDocument(ChartModel chartModel, IChartDataModel chartTableModel, IChartLinkGenerator chartLinkGenerator) {
    return chartFactory.makeChart(chartModel, chartTableModel, chartLinkGenerator);
  }
  
  /* (non-Javadoc)
  * @see org.pentaho.chart.plugin.AbstractChartPlugin#renderChartDocument(org.pentaho.chart.core.ChartDocument, org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.plugin.api.IOutput)
  *
  * This method determines what type of chart to render and then calls the correct method in the ChartFactoryEngine.
  */
  public IOutput renderChartDocument(final ChartDocumentContext chartDocumentContext, final ChartTableModel data)
  {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final ChartResult chartResult = validateChartDocument(chartDocument);
    if (chartResult.getErrorCode() == IChartPlugin.RESULT_VALIDATED)
    {  
      return chartFactory.makeChart(data, chartDocumentContext, chartResult);
    }

    return null;
  }


  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.IChartPlugin#getSupportedOutputs()
   */
  public Set<IOutput.OutputTypes> getSupportedOutputs()
  {
    return supportedOutputs;
  }
  
  public String getPluginId() {
    return PLUGIN_ID;
  }

  protected OpenFlashChartFactoryEngine getChartFactory() {
    return chartFactory;
  }

  protected void setChartFactory(OpenFlashChartFactoryEngine chartFactory) {
    this.chartFactory = chartFactory;
  }
}
