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
 * @created Feb 25, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin.jfreechart;

import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.plugin.AbstractChartPlugin;
import org.pentaho.experimental.chart.plugin.ChartResult;
import org.pentaho.experimental.chart.plugin.IOutput;

/**
 * @author wseyler
 *
 */
public class JFreeChartPlugin extends AbstractChartPlugin {
  
  public ChartResult renderChartDocument(ChartDocument chartDocument, IOutput output) {
    return super.renderChartDocument(chartDocument, output);
  }
  
  public ChartResult validateChartDocument(ChartDocument chartDocument) {
    return super.validateChartDocument(chartDocument);
  }
}
