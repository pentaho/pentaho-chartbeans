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

package org.pentaho.chart;

import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.chart.core.ChartDocument;

/**
 * The definition of a chart that has been created by the charting api.
 * @author David Kincade
 */
public class ChartDefinitionFactory {
  public static ChartDefinition createChartDefinition(ChartDocument doc) {
    return new ChartDefinitionImpl(doc);
  }

  public static ChartDefinition createChartDefinition(ResourceKey chartKey) throws ResourceException {
    return new ChartDefinitionImpl(null, chartKey);
  }

  public static ChartDefinition createChartDefinition(ResourceKey chartKey, ResourceManager resourceManager) throws ResourceException {
    return new ChartDefinitionImpl(resourceManager, chartKey);
  }

  public static ChartDefinition createChartDefinition(ChartData data, ChartDocument doc) {
    return new ChartDefinitionImpl(data, doc);
  }

  public static ChartDefinition createChartDefinition(ChartData data, ResourceKey chartKey) throws ResourceException {
    return new ChartDefinitionImpl(data, null, chartKey);
  }

  public static ChartDefinition createChartDefinition(ChartData data, ResourceKey chartKey, ResourceManager resourceManager) throws ResourceException {
    return new ChartDefinitionImpl(data, resourceManager, chartKey);
  }
}
