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

package org.pentaho.chart.plugin.jfreechart.chart.area;

import java.awt.Font;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartUtils;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.styles.ChartAreaStyle;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.util.messages.Messages;

/**
 * Creates different area charts i.e. basic, stacked, xy etc.
 * </p>
 * Author: Ravi Hasija
 * Date: May 16, 2008
 * Time: 12:44:11 PM
 */
public abstract class JFreeAreaChartGenerator extends JFreeChartGenerator {
   private static final Log logger = LogFactory.getLog(JFreeAreaChartGenerator.class);
  /**
   * Creates the JFree chart based on the chart document, data provided and
   * type of chart expected.
   * </p>
   * @param chartDocContext  Chart documument context for the current chart.
   * @param data             Data for the current chart.
   * @param chartType        Area chart type (default, stacked, xy)
   * @return Returns the JFree chart object (created by the method)
   */
  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data,
                                final CSSConstant chartType) {
    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final String title = getTitle(chartDocument);
    final String valueCategoryLabel = getValueCategoryLabel(chartDocument);
    final String valueAxisLabel = getValueAxisLabel(chartDocument);
    final PlotOrientation orientation = getPlotOrientation(chartDocument);
    final boolean legend = getShowLegend(chartDocument);
    final boolean toolTips = getShowToolTips(chartDocument);

    final DefaultCategoryDataset categoryDataset = datasetGeneratorFactory.createDefaultCategoryDataset(chartDocContext, data);
    if (categoryDataset == null) {
      logger.error(Messages.getErrorString("JFreeChartFactoryEngine.ERROR_0001_DATASET_IS_NULL")); //$NON-NLS-1$
      return null;
    }

    JFreeChart chart = null;
    if (ChartAreaStyle.STACKED.equals(chartType)) {
      chart = ChartFactory.createStackedAreaChart(title, valueCategoryLabel, valueAxisLabel, categoryDataset, orientation, legend, toolTips, false);
    } else if (ChartAreaStyle.XY.equals(chartType)) {
    } else {
      chart = ChartFactory.createAreaChart(title, valueCategoryLabel, valueAxisLabel, categoryDataset, orientation, legend, toolTips, false);
    }

    if (chart != null) {
      final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
      final CategoryPlot categoryPlot = chart.getCategoryPlot();
      if (seriesElements != null && categoryPlot != null) {
        setSeriesAttributes(seriesElements, data, categoryPlot);
      }
    }
    
    return chart;
  }

  /**
    * Main method for setting ALL the series attributes.  This method is a stating
    * method for calling all the other helper methods.
    *
    * @param categoryPlot  - Plot for the current chart.
    * @param data          - Actual data.
    * @param seriesElements - Series elements from the chart.
    */  
  private void setSeriesAttributes(final ChartElement[] seriesElements,
                                   final ChartTableModel data,
                                   final CategoryPlot categoryPlot) {
    setSeriesItemLabel(categoryPlot, seriesElements, data);
    setSeriesPaint(categoryPlot, seriesElements, data);
  }
}
