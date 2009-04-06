/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created 4/14/2008 
 * @author David Kincade 
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
