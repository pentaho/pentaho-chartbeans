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
package org.pentaho.experimental.chart;

import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.core.ChartDocument;

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
