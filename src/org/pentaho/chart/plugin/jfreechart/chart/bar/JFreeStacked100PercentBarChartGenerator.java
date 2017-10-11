/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.plugin.jfreechart.chart.bar;

import java.text.NumberFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Creates Stacked 100 Percent bar chart.
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 5:08:48 PM
 */
public class JFreeStacked100PercentBarChartGenerator extends JFreeStackedBarChartGenerator {

  /**
   * Creates stacked 100 percent bar chart and creates range axis for it.
   * </p>
   * @param chartDocContext Current chart's document context.
   * @param data Current chart data.
   * @return Returns JFree stacked 100 percent bar chart.
   */
  public JFreeChart createChart(final ChartDocumentContext chartDocContext, final ChartTableModel data) {
    final JFreeChart chart = super.createChart(chartDocContext, data);

    /*
     * Doing all the render stuff and then off to create range axis.
     * NOTE: The chart object will be updated.
     */
    ((StackedBarRenderer)chart.getCategoryPlot().getRenderer()).setRenderAsPercentages(true);
    final NumberAxis rangeAxis = (NumberAxis) chart.getCategoryPlot().getRangeAxis();
    rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());

    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
