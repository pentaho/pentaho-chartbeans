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
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.AbstractChartPlugin;
import org.pentaho.experimental.chart.plugin.IChartPlugin;
import org.pentaho.experimental.chart.plugin.api.ChartResult;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.messages.Messages;

/**
 * @author wseyler
 */
public class JFreeChartPlugin extends AbstractChartPlugin {
  public enum ChartTypes {
    UNDETERMINED, AREA, BAR, BAR_LINE, BUBBLE, DIAL, DIFFERENCE, LINE, MULTI_PIE, PIE, SCATTER, STEP_AREA, STEP, WATERFALL
  }

  ChartTypes currentChartType = ChartTypes.UNDETERMINED;

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.AbstractChartPlugin#renderChartDocument(org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.plugin.api.IOutput)
   * 
   * This method determines what type of chart to render and then calls the correct method in the ChartFactoryEngine.
   */
  public ChartResult renderChartDocument(ChartDocument chartDocument, ChartTableModel data, IOutput output) {
    ChartResult chartResult = super.renderChartDocument(chartDocument, data, output);

    if (chartResult.getErrorCode() == IChartPlugin.RESULT_VALIDATED) {  // The superclass so now we'll render
      currentChartType = determineChartType(chartDocument);
      if (currentChartType == ChartTypes.UNDETERMINED) {
        chartResult.setErrorCode(ERROR_INDETERMINATE_CHART_TYPE);
        chartResult.setDescription(Messages.getErrorString("JFreeChartPlugin.ERROR_0001_CHART_TYPE_INDETERMINABLE")); //$NON-NLS-1$
      }

      ChartFactoryEngine chartFactory = new JFreeChartFactoryEngine();
      if (currentChartType == ChartTypes.BAR) {
        try {
          chartFactory.makeBarChart(data, chartDocument, output);
        } catch (Exception e) {
          chartResult.setErrorCode(RESULT_ERROR);
          chartResult.setDescription(e.getLocalizedMessage());
        }
      } else if (currentChartType == ChartTypes.LINE) {
        try {
          chartFactory.makeLineChart(data, chartDocument, output);
        } catch (Exception e) {
          chartResult.setErrorCode(RESULT_ERROR);
          chartResult.setDescription(e.getLocalizedMessage());
        }
      }

    }

    return chartResult;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.AbstractChartPlugin#validateChartDocument(org.pentaho.experimental.chart.core.ChartDocument)
   */
  public ChartResult validateChartDocument(ChartDocument chartDocument) {
    return super.validateChartDocument(chartDocument);
  }

  /**
   * This method uses the chartDocument to determine what type of chart that should be rendered.  It is possible that this method
   * could somehow be moved up into the AbstractChartPlugin
   * 
   * @param chartDocument
   * @return a ChartType that represents the type of chart the chartDocument is requesting.
   */
  public ChartTypes determineChartType(ChartDocument chartDocument) {
    ChartElement[] elements = chartDocument.getRootElement().findChildrenByName("series");
    for (ChartElement element : elements) {
      CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.CHART_TYPE);
      if (value != null) {
        if (value.getCSSText().equalsIgnoreCase("bar")) {
          return ChartTypes.BAR;
        } else if (value.getCSSText().equals("line")) {
          return ChartTypes.LINE;
        }
      }
    }
    return ChartTypes.UNDETERMINED;
  }

}
