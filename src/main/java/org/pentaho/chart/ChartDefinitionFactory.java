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
