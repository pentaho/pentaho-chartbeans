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

package org.pentaho.chart.plugin.jfreechart.chart;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Implement this interface to generate any kind of JFreeChart chart eg: bar, line, area etc...
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 1:08:16 PM
 */
public interface IJFreeChartGenerator {
  public JFreeChart createChart(final ChartDocumentContext chartDocContext, final ChartTableModel data);
}
