package org.pentaho.experimental.chart.plugin.jfreechart.chart.bar;

import java.awt.BasicStroke;
import java.awt.Color;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.StrokeFactory;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.messages.Messages;

/**
 * Generates JFreeChart object that is bar chart type.
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 1:09:34 PM
 */
public abstract class JFreeBarChartGenerator extends JFreeChartGenerator {
  private static final Log logger = LogFactory.getLog(JFreeBarChartGenerator.class);

  /**
   * Creates the JFree chart based on the chart document, data provided and
   * type of chart expected.
   * </p>
   * @param chartDocContext  Chart documument context for the current chart.
   * @param data             Data for the current chart.
   * @param chartType        Bar chart type (interval, stacked, default/basic)
   * @return Returns the JFree chart object (created by the method)
   */
  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data,
                                final String chartType) {
    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final String title = getTitle(chartDocument);
    final String valueCategoryLabel = getValueCategoryLabel(chartDocument);
    final String valueAxisLabel = getValueAxisLabel(chartDocument);
    final PlotOrientation orientation = getPlotOrientation(chartDocument);
    final boolean legend = getShowLegend(chartDocument);
    final boolean toolTips = getShowToolTips(chartDocument);

    DefaultCategoryDataset categoryDataset = null;
    final DefaultIntervalCategoryDataset intervalCategoryDataset = null;

    if (JFreeBarChartTypes.INTERVAL.equalsIgnoreCase(chartType)) {
      logger.error(Messages.getErrorString("JFreeBarChartGenerator.INFO_INTERVAL_CHART_NOT_SUPPORTED")); //$NON-NLS-1$
    } else {
      categoryDataset = datasetGeneratorFactory.createDefaultCategoryDataset(chartDocContext, data);
      if (categoryDataset == null) {
        logger.error(Messages.getErrorString("JFreeChartFactoryEngine.ERROR_0001_DATASET_IS_NULL")); //$NON-NLS-1$
        return null;
      }
    }

    final JFreeChart chart;
    if (JFreeBarChartTypes.STACKED.equalsIgnoreCase(chartType)) {
      chart = ChartFactory.createStackedBarChart(title, valueCategoryLabel, valueAxisLabel, categoryDataset, orientation, legend, toolTips, false);
    } else if (JFreeBarChartTypes.INTERVAL.equalsIgnoreCase(chartType)) {
      chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, intervalCategoryDataset, orientation, legend, toolTips, false);
    } else {
      chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, categoryDataset, orientation, legend, toolTips, false);
    }

    final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    final CategoryPlot categoryPlot = chart.getCategoryPlot();
    if (seriesElements != null && categoryPlot != null) {
      setSeriesAttributes(seriesElements, data, categoryPlot);  
    }

    return chart;
  }

  /**
   * Sets the series specific attributes for the current chart object.
   * </p>
   * @param seriesElements -- Series elements from the current chart definition.
   * @param data           -- Actual data.
   * @param categoryPlot   -- The category plot from the current chart object. Category plot will be updated.
   */
  private void setSeriesAttributes(final ChartElement[] seriesElements,
                                   final ChartTableModel data,
                                   final CategoryPlot categoryPlot) {
    setSeriesItemLabel(categoryPlot, seriesElements, data);
    setSeriesPaint(categoryPlot, seriesElements, data);
    setSeriesBarOutline(categoryPlot, seriesElements);
  }

 /**
   * Paints the series border/outline.
   *
   * @param categoryPlot   The plot that has the renderer object
   * @param seriesElements The series elements from the chart document
   */
  private static void setSeriesBarOutline(final CategoryPlot categoryPlot, final ChartElement[] seriesElements) {
    final int length = seriesElements.length;
    final StrokeFactory strokeFacObj = StrokeFactory.getInstance();
    for (int i = 0; i < length; i++) {
      final ChartElement currElement = seriesElements[i];

      if (categoryPlot.getRenderer() instanceof BarRenderer) {
        final BarRenderer barRender = (BarRenderer) categoryPlot.getRenderer();
        final BasicStroke borderStyleStroke = strokeFacObj.getBorderStroke(currElement);
        if (borderStyleStroke != null) {
          final CSSValue borderColorValue = currElement.getLayoutStyle().getValue(BorderStyleKeys.BORDER_TOP_COLOR);
          final Color borderColor = JFreeChartUtils.getColorFromCSSValue(borderColorValue);
          if (borderColor != null) {
            barRender.setSeriesOutlinePaint(i, borderColor, true);
          }
          barRender.setSeriesOutlineStroke(i, borderStyleStroke, true);
          barRender.setDrawBarOutline(true);
        }
      }
    }
  }
}
