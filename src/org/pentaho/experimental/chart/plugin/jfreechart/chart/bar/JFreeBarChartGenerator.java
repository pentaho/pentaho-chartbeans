package org.pentaho.experimental.chart.plugin.jfreechart.chart.bar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.JFreeChartGenerator;
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
    return chart;
  }
}
