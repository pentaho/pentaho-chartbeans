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

package org.pentaho.chart.plugin.jfreechart.chart.line;

import java.awt.BasicStroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartLineVisibleType;
import org.pentaho.chart.css.styles.ChartMarkerVisibleType;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.chart.plugin.jfreechart.utils.ChartMarkerFilledType;
import org.pentaho.chart.plugin.jfreechart.utils.ShapeFactory;
import org.pentaho.chart.plugin.jfreechart.utils.StrokeFactory;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * @author wseyler
 *
 */
public class JFreeLineChartGenerator extends JFreeChartGenerator {

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.jfreechart.chart.IJFreeChartGenerator#createChart(org.pentaho.chart.ChartDocumentContext, org.pentaho.chart.data.ChartTableModel)
   */
  protected JFreeChart doCreateChart(final ChartDocumentContext chartDocContext, final ChartTableModel data) {
    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final String title = getTitle(chartDocument);
    final String valueCategoryLabel = getValueCategoryLabel(chartDocument);
    final String valueAxisLabel = getValueAxisLabel(chartDocument);
    final PlotOrientation orientation = getPlotOrientation(chartDocument);
    final boolean legend = getShowLegend(chartDocument);
    final boolean toolTips = getShowToolTips(chartDocument);

    final DefaultCategoryDataset categoryDataset = datasetGeneratorFactory.createDefaultCategoryDataset(chartDocContext, data);
    
    final JFreeChart chart = ChartFactory.createLineChart(title, valueCategoryLabel, valueAxisLabel, categoryDataset,
                                                          orientation, legend, toolTips, toolTips);


    final CategoryPlot categoryPlot = chart.getCategoryPlot();
    final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    if (categoryPlot != null && seriesElements != null) {
      setSeriesAttributes(seriesElements, data, categoryPlot);
    }

    
    return chart;
  }

  protected void setSeriesAttributes(final ChartElement[] seriesElements,
                                   final ChartTableModel data,
                                   final CategoryPlot categoryPlot) {
    setSeriesItemLabel(categoryPlot, seriesElements, data);
    setSeriesPaint(categoryPlot, seriesElements, data);
    setSeriesMarkerStyles(categoryPlot, seriesElements);
    setSeriesLineStyles(categoryPlot, seriesElements);
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
  public static void setSeriesLineStyles(final CategoryPlot categoryPlot,
                                         final ChartElement[] seriesElements) {
    final int length = seriesElements.length;
    final StrokeFactory strokeFacObj = StrokeFactory.getInstance();
    for (int i = 0; i < length; i++) {
      final ChartElement currElement = seriesElements[i];

      if (categoryPlot.getRenderer() instanceof LineAndShapeRenderer) {
        final LineAndShapeRenderer lineAndShapeRenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
        final CSSValue visibleStr = currElement.getLayoutStyle().getValue(ChartStyleKeys.LINE_VISIBLE);
        lineAndShapeRenderer.setSeriesLinesVisible(i, !ChartLineVisibleType.HIDDEN.equals(visibleStr));
        final BasicStroke lineStyleStroke = strokeFacObj.getLineStroke(currElement);
        if (lineStyleStroke != null) {
          lineAndShapeRenderer.setSeriesStroke(i, lineStyleStroke);
        }
      }
    }
  }
}
