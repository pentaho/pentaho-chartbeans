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

package org.pentaho.chart.plugin.jfreechart.chart.multi;

import java.awt.BasicStroke;
import java.awt.Color;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartLineVisibleType;
import org.pentaho.chart.css.styles.ChartMarkerVisibleType;
import org.pentaho.chart.css.styles.ChartMultiStyle;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.chart.plugin.jfreechart.utils.ChartMarkerFilledType;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.chart.plugin.jfreechart.utils.ShapeFactory;
import org.pentaho.chart.plugin.jfreechart.utils.StrokeFactory;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.messages.Messages;

/**
 * Creates multi chart (area, bar and line combined into one chart or ).
 * Author: Stephen Halili
 */

public abstract class JFreeMultiChartGenerator extends JFreeChartGenerator {
  private static final Log logger = LogFactory.getLog(JFreeMultiChartGenerator.class);

  public JFreeChart createChart(final ChartDocumentContext chartDocContext, final ChartTableModel data,
      final CSSConstant chartType) {
    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final String title = getTitle(chartDocument);
    final String valueCategoryLabel = getValueCategoryLabel(chartDocument);
    final String valueAxisLabel = getValueAxisLabel(chartDocument);
    final PlotOrientation orientation = getPlotOrientation(chartDocument);
    final boolean legend = getShowLegend(chartDocument);
    final boolean toolTips = getShowToolTips(chartDocument);

    final DefaultCategoryDataset categoryDataset = datasetGeneratorFactory.createDefaultCategoryDataset(
        chartDocContext, data);
    if (categoryDataset == null) {
      logger.error(Messages.getErrorString("JFreeChartFactoryEngine.ERROR_0001_DATASET_IS_NULL")); //$NON-NLS-1$
      return null;
    }

    JFreeChart chart = null;
    if (ChartMultiStyle.MULTI.equals(chartType)) {

      chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, categoryDataset, orientation,
          legend, toolTips, true);

      CategoryPlot plot = (CategoryPlot) chart.getPlot();

      DefaultCategoryDataset dataset1 = categoryDataset;
      plot.setDataset(1, dataset1);

      DefaultCategoryDataset dataset2 = categoryDataset;
      plot.setDataset(2, dataset2);
      
      LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
      renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
      plot.setRenderer(1, renderer1);
      plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
      
      AreaRenderer renderer2 = new AreaRenderer();
      renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
      plot.setRenderer(2, renderer2);
      plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
      
    } 
    
    final CategoryPlot categoryPlot = chart.getCategoryPlot();
    final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(
        ChartElement.TAG_NAME_SERIES);
    if (categoryPlot != null && seriesElements != null) {
      setSeriesAttributes(seriesElements, data, categoryPlot);
    }
    
    return chart;
  }

  private void setSeriesAttributes(final ChartElement[] seriesElements, final ChartTableModel data,
      final CategoryPlot categoryPlot) {
    setSeriesItemLabel(categoryPlot, seriesElements, data);
    setSeriesPaint(categoryPlot, seriesElements, data);
    setSeriesMarkerStyles(categoryPlot, seriesElements);
    setSeriesLineStyles(categoryPlot, seriesElements);
    setSeriesBarOutline(categoryPlot, seriesElements, data);
  }

  /**
   * @param categoryPlot
   * @param seriesElements
   * 
   * Set the line marker attributes
   */
  private static void setSeriesMarkerStyles(CategoryPlot categoryPlot, ChartElement[] seriesElements) {
    final int length = seriesElements.length;
    final ShapeFactory shapeFacObj = ShapeFactory.getInstance();
    for (int i = 0; i < length; i++) {
      final ChartElement currElement = seriesElements[i];

      if (categoryPlot.getRenderer() instanceof LineAndShapeRenderer) {
        final LineAndShapeRenderer lineAndShapeRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
        lineAndShapeRenderer.setSeriesShapesVisible(i, isMarkerVisible(currElement));
        lineAndShapeRenderer.setSeriesShape(i, shapeFacObj.getShape(currElement));
        lineAndShapeRenderer.setSeriesShapesFilled(i, isShapeFilled(currElement));
      }
    }
  }

  /**
   * @param currElement
   * @return
   */
  private static boolean isMarkerVisible(ChartElement currElement) {
    final CSSValue visibleStr = currElement.getLayoutStyle().getValue(ChartStyleKeys.MARKER_VISIBLE);
    return ChartMarkerVisibleType.VISIBLE.equals(visibleStr);
  }

  /**
   * @param currElement
   * @return
   */
  private static boolean isShapeFilled(ChartElement currElement) {
    final CSSValue filledStr = currElement.getLayoutStyle().getValue(ChartStyleKeys.MARKER_FILLED);
    return ChartMarkerFilledType.FILLED.equals(filledStr);
  }

  /**
   * Sets the line width for each of the series that is defined as a lineRenderer
   * </p>
   * @param categoryPlot   -- The category plot from the current chart object. Category plot will be updated.
   * @param seriesElements -- Series elements from the current chart definition.
   */
  public static void setSeriesLineStyles(final CategoryPlot categoryPlot, final ChartElement[] seriesElements) {
    final int length = seriesElements.length;
    final StrokeFactory strokeFacObj = StrokeFactory.getInstance();
    for (int i = 0; i < length; i++) {
      final ChartElement currElement = seriesElements[i];

      if (categoryPlot.getRenderer() instanceof LineAndShapeRenderer) {
        final LineAndShapeRenderer lineAndShapeRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
        final CSSValue visibleStr = currElement.getLayoutStyle().getValue(ChartStyleKeys.LINE_VISIBLE);
        lineAndShapeRenderer.setSeriesLinesVisible(i, ChartLineVisibleType.VISIBLE.equals(visibleStr));
        final BasicStroke lineStyleStroke = strokeFacObj.getLineStroke(currElement);
        if (lineStyleStroke != null) {
          lineAndShapeRenderer.setSeriesStroke(i, lineStyleStroke);
        }
      }
    }
  }

  /**
   * Paints the series border/outline.
   *
   * @param categoryPlot   The plot that has the renderer object
   * @param seriesElements The series elements from the chart document
   */
  private static void setSeriesBarOutline(final CategoryPlot categoryPlot, final ChartElement[] seriesElements,
      ChartTableModel data) {
    final int length = seriesElements.length;
    final StrokeFactory strokeFacObj = StrokeFactory.getInstance();
    for (int i = 0; i < length; i++) {
      final ChartElement currElement = seriesElements[i];
      final int column = JFreeChartUtils.getSeriesColumn(currElement, data, i);
      if (categoryPlot.getRenderer() instanceof BarRenderer) {
        final BarRenderer barRender = (BarRenderer) categoryPlot.getRenderer();
        final BasicStroke borderStyleStroke = strokeFacObj.getBorderStroke(currElement);
        if (borderStyleStroke != null) {
          final CSSValue borderColorValue = currElement.getLayoutStyle().getValue(BorderStyleKeys.BORDER_TOP_COLOR);
          final Color borderColor = JFreeChartUtils.getColorFromCSSValue(borderColorValue);
          if (borderColor != null) {
            barRender.setSeriesOutlinePaint(column, borderColor, true);
          }
          barRender.setSeriesOutlineStroke(column, borderStyleStroke, true);
          barRender.setDrawBarOutline(true);
        }
      }
    }
  }
}
