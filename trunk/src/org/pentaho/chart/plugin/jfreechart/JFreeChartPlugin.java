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


package org.pentaho.chart.plugin.jfreechart;

import java.util.EnumSet;
import java.util.Set;

import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.AbstractChartPlugin;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.ChartResult;
import org.pentaho.chart.plugin.api.IOutput;

/**
 * @author wseyler
 */
public class JFreeChartPlugin extends AbstractChartPlugin
{
  private final JFreeChartFactoryEngine chartFactory;
  private static final Set<IOutput.OutputTypes> supportedOutputs =
      EnumSet.of(IOutput.OutputTypes.FILE_TYPE_JPEG, IOutput.OutputTypes.FILE_TYPE_PNG);

  public JFreeChartPlugin()
  {
    chartFactory = new JFreeChartFactoryEngine();
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
//    IOutput output = new JFreeChartOutput();
    if (chartResult.getErrorCode() == IChartPlugin.RESULT_VALIDATED)
    {  // The superclass so now we'll render
      return chartFactory.makeChart(data, chartDocumentContext, chartResult);
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.AbstractChartPlugin#validateChartDocument(org.pentaho.chart.core.ChartDocument)
   */
  public ChartResult validateChartDocument(final ChartDocument chartDocument)
  {
    return super.validateChartDocument(chartDocument);
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.IChartPlugin#getSupportedOutputs()
   */
  public Set<IOutput.OutputTypes> getSupportedOutputs()
  {
    return supportedOutputs;
  }
}
