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


package org.pentaho.chart.plugin.jfreechart.chart.area;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.css.styles.ChartAreaStyle;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Creates basic/default area chart. Also, does some extra work like creating range axis etc.
 * </p> 
 * Author: Ravi Hasija
 * Date: May 16, 2008
 * Time: 12:45:07 PM
 */
public class JFreeDefaultAreaChartGenerator extends JFreeAreaChartGenerator {
  protected JFreeChart doCreateChart(ChartDocumentContext chartDocContext, ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, ChartAreaStyle.AREA);
    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    plot.setForegroundAlpha(0.5f);
    
    /*
     * NOTE: The chart object will be updated.
     */
    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
