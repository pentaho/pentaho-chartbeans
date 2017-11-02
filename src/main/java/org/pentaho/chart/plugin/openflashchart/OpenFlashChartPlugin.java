/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

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
