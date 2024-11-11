/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


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
