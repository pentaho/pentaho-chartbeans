/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.chart;

import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.chart.core.ChartDocument;

/**
 * @author David Kincade
 */
public interface ChartDefinition {
  public void setData(ChartData data);

  public ChartData getData();

  public ResourceKey getChartResourceKey();

  public ResourceManager getChartResourceManager();

  public ChartDocument getChartDocument(); 
}
