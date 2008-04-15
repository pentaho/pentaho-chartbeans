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

import java.util.EnumSet;
import java.util.Set;

import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.css.styles.ChartSeriesType;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.AbstractChartPlugin;
import org.pentaho.experimental.chart.plugin.IChartPlugin;
import org.pentaho.experimental.chart.plugin.api.ChartResult;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine;
import org.pentaho.experimental.chart.plugin.jfreechart.outputs.JFreeChartOutput;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.util.messages.Messages;

/**
 * @author wseyler
 */
public class JFreeChartPlugin extends AbstractChartPlugin {
  final ChartFactoryEngine chartFactory = new JFreeChartFactoryEngine();
  CSSConstant currentChartType = ChartSeriesType.UNDEFINED;
  final static Set<IOutput.OutputTypes> supportedOutputs = EnumSet.of(IOutput.OutputTypes.DATA_TYPE_STREAM, IOutput.OutputTypes.FILE_TYPE_JPEG, IOutput.OutputTypes.FILE_TYPE_PNG);
  
  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.AbstractChartPlugin#renderChartDocument(org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.plugin.api.IOutput)
   * 
   * This method determines what type of chart to render and then calls the correct method in the ChartFactoryEngine.
   */
  public IOutput renderChartDocument(final ChartDocument chartDocument, final ChartTableModel data) {
    final ChartResult chartResult = validateChartDocument(chartDocument);
    IOutput output = new JFreeChartOutput();
    if (chartResult.getErrorCode() == IChartPlugin.RESULT_VALIDATED) {  // The superclass so now we'll render
      currentChartType = JFreeChartUtils.determineChartType(chartDocument);
      if (currentChartType == ChartSeriesType.UNDEFINED) {
        chartResult.setErrorCode(ERROR_INDETERMINATE_CHART_TYPE);
        chartResult.setDescription(Messages.getErrorString("JFreeChartPlugin.ERROR_0001_CHART_TYPE_INDETERMINABLE")); //$NON-NLS-1$
      }

      if (currentChartType == ChartSeriesType.BAR) {
        try {
          chartFactory.makeBarChart(data, chartDocument, output);
        } catch (Exception e) {
          chartResult.setErrorCode(RESULT_ERROR);
          chartResult.setDescription(e.getLocalizedMessage());
        }
      } else if (currentChartType == ChartSeriesType.LINE) {
        try {
          chartFactory.makeLineChart(data, chartDocument, output);
        } catch (Exception e) {
          chartResult.setErrorCode(RESULT_ERROR);
          chartResult.setDescription(e.getLocalizedMessage());
        }
      }

    }

    return output;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.AbstractChartPlugin#validateChartDocument(org.pentaho.experimental.chart.core.ChartDocument)
   */
  public ChartResult validateChartDocument(final ChartDocument chartDocument) {
    return super.validateChartDocument(chartDocument);
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.IChartPlugin#getSupportedOutputs()
   */
  public Set<IOutput.OutputTypes> getSupportedOutputs() {
    return supportedOutputs;
  }
}
