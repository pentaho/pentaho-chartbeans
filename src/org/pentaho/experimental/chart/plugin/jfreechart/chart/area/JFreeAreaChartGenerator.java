package org.pentaho.experimental.chart.plugin.jfreechart.chart.area;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.css.styles.ChartAreaStyle;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.JFreeChartGenerator;
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