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


package org.pentaho.chart.plugin.jfreechart.chart.multi;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

/**
 * @author Stephen Halili
 *
 */
public class JFreeMultiChartGeneratorFactory {

  /**
   * @param chartDocumentContext
   * @param data
   * @return
   */
  public JFreeChart createChart(final ChartDocumentContext chartDocumentContext, 
                                final ChartTableModel data) {
    JFreeMultiChartGenerator multiChartGenerator = new JFreeDefaultMultiChartGenerator();
 
    return multiChartGenerator.createChart(chartDocumentContext, data);
  }

}
