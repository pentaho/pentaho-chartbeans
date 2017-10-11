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
