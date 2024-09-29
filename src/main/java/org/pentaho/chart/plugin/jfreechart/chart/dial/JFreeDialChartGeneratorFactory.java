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


package org.pentaho.chart.plugin.jfreechart.chart.dial;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

public class JFreeDialChartGeneratorFactory {

  public JFreeChart createChart(final ChartDocumentContext chartDocContext, final ChartTableModel data) {
    final JFreeDialChartGenerator dialChartGenerator = new JFreeDialChartGenerator();
    return dialChartGenerator.createChart(chartDocContext, data);
  }

}
