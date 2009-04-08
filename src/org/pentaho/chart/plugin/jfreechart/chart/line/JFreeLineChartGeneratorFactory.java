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
 * @created May 21, 2008 
 * @author wseyler
 */


package org.pentaho.chart.plugin.jfreechart.chart.line;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartLineStyle;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * @author wseyler
 *
 */
public class JFreeLineChartGeneratorFactory {

  /**
   * @param chartDocumentContext
   * @param data
   * @return
   */
  public JFreeChart createChart(ChartDocumentContext chartDocumentContext, ChartTableModel data) {
    boolean threeD = false;

    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    for (final ChartElement element : elements) {
      CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.LINE_STYLE);
      threeD |= ChartLineStyle.THREE_D.equals(value);
      break;
    }

    final JFreeLineChartGenerator lineChartGenerator;

    if (threeD) {
      lineChartGenerator = new JFree3DLineChartGenerator();
    } else {
      lineChartGenerator = new JFreeDefaultLineChartGenerator();
    }

    return lineChartGenerator.createChart(chartDocumentContext, data);
  }

}
