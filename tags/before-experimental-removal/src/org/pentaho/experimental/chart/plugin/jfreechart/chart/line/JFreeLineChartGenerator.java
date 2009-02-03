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


package org.pentaho.experimental.chart.plugin.jfreechart.chart.line;

import java.awt.BasicStroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartMarkerVisibleType;
import org.pentaho.experimental.chart.css.styles.ChartLineVisibleType;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.ChartMarkerFilledType;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.ShapeFactory;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.StrokeFactory;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * @author wseyler
 *
 */
public class JFreeLineChartGenerator extends JFreeChartGenerator {

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.jfreechart.chart.IJFreeChartGenerator#createChart(org.pentaho.experimental.chart.ChartDocumentContext, org.pentaho.experimental.chart.data.ChartTableModel)
   */
  public JFreeChart createChart(final ChartDocumentContext chartDocContext, final ChartTableModel data) {
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

  private void setSeriesAttributes(final ChartElement[] seriesElements,
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