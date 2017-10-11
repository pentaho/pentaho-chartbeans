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

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.bar.JFreeBarChartGenerator;
import org.pentaho.chart.plugin.jfreechart.chart.bar.JFreeBarChartTypes;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;

/**
 * Created stacked bar chart.
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 4:10:48 PM
 */
public class JFreeStackedBarChartGenerator extends JFreeBarChartGenerator {

  /**
   * Creates stacked bar chart and creates range axis for it.
   * </p>
   * @param chartDocContext Current chart's document context
   * @param data Current chart data
   * @return Returns JFree layered bar chart.
   */
  protected JFreeChart doCreateChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, JFreeBarChartTypes.STACKED);

    /*
     * Doing all the render stuff and then off to create range axis.
     * NOTE: The chart object will be updated.
     */
    final ChartDocument chartDocument = chartDocContext.getChartDocument();  
    if (JFreeChartUtils.getIsStackedGrouped(chartDocument)) {
      final GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
      final KeyToGroupMap map = JFreeChartUtils.createKeyToGroupMap(chartDocument, data, chart.getCategoryPlot().getDataset());
      renderer.setSeriesToGroupMap(map);

      final CategoryPlot plot = (CategoryPlot) chart.getPlot();
      plot.setRenderer(renderer);
    }

    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }
}
