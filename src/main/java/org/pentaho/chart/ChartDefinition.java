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
