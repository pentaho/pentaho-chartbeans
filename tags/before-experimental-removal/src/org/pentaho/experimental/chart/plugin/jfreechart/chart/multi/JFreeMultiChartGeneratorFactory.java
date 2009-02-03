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


package org.pentaho.experimental.chart.plugin.jfreechart.chart.multi;

import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.data.ChartTableModel;

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
