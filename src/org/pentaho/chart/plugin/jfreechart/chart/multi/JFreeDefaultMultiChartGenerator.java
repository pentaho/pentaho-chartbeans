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
