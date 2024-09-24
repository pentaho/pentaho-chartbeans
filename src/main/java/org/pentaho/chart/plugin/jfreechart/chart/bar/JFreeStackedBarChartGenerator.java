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
