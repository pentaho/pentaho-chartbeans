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


package org.pentaho.chart.plugin.jfreechart;

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
 * @author wseyler
 */
public class JFreeChartPlugin extends AbstractChartPlugin
{
  public static final String PLUGIN_ID = "JFreeChart";
  private final JFreeChartFactoryEngine chartFactory;
  private static final Set<IOutput.OutputTypes> supportedOutputs =
      EnumSet.of(IOutput.OutputTypes.FILE_TYPE_JPEG, IOutput.OutputTypes.FILE_TYPE_PNG);

  public JFreeChartPlugin()
  {
    chartFactory = new JFreeChartFactoryEngine();
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
//    IOutput output = new JFreeChartOutput();
    if (chartResult.getErrorCode() == IChartPlugin.RESULT_VALIDATED)
    {  // The superclass so now we'll render
      return chartFactory.makeChart(data, chartDocumentContext, chartResult);
    }

    return null;
  }

  public String getPluginId() {
    return PLUGIN_ID;
  }

  public IOutput renderChartDocument(ChartModel chartModel, final IChartDataModel data)
  {
    return renderChartDocument(chartModel, data, null);
  }
  
  public IOutput renderChartDocument(ChartModel chartModel, final IChartDataModel data, IChartLinkGenerator linkGenerator)
  {
    return chartFactory.makeChart(chartModel, data, linkGenerator);
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.AbstractChartPlugin#validateChartDocument(org.pentaho.chart.core.ChartDocument)
   */
  public ChartResult validateChartDocument(final ChartDocument chartDocument)
  {
    return super.validateChartDocument(chartDocument);
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.IChartPlugin#getSupportedOutputs()
   */
  public Set<IOutput.OutputTypes> getSupportedOutputs()
  {
    return supportedOutputs;
  }
}
