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


package org.pentaho.chart.plugin.jfreechart.chart.line;

import java.awt.BasicStroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
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
public class JFree3DLineChartGenerator extends JFreeLineChartGenerator {

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
    categoryPlot.setRenderer(new LineRenderer3D());
    
    final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    if (categoryPlot != null && seriesElements != null) {
      setSeriesAttributes(seriesElements, data, categoryPlot);
    }

    
    return chart;
  }

}
