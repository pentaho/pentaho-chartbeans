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
import org.jfree.chart.plot.CategoryPlot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.css.styles.ChartMultiStyle;
import org.pentaho.chart.data.ChartTableModel;

/**
 * @author Stephen Halili
 *
 */
public class JFreeDefaultMultiChartGenerator extends JFreeMultiChartGenerator {
  protected JFreeChart doCreateChart(ChartDocumentContext chartDocContext, ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, ChartMultiStyle.MULTI);
    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    plot.setForegroundAlpha(0.5f);
    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
