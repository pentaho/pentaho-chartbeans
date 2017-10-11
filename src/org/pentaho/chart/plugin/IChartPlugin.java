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
