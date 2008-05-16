package org.pentaho.experimental.chart.plugin.jfreechart.chart;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.AxisSeriesLinkInfo;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartAxisLocationType;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.dataset.DatasetGeneratorFactory;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.CylinderRenderer;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
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
  private final DatasetGeneratorFactory datasetGeneratorFactory;

  protected JFreeBarChartGenerator() {
    datasetGeneratorFactory = new DatasetGeneratorFactory();
  }

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

  /**
   * Create range axis for the current chart and update the chart object with it.
   * </p>
   * @param chartDocContext  Chart documument context for the current chart.
   * @param data             Data for the current chart.
   * @param chart            The chart object to be updated with range axis info.
   */
  public void createRangeAxis(final ChartDocumentContext chartDocContext,
                              final ChartTableModel data,
                              final JFreeChart chart) {
    /*
     * Assumption:
     * #1. User has to provide axis type for each axis (range/domain)
     * #2. User have to specify the axis id for each series
     * The code for handling multiple axis goes here.
     * 1. Create multiple datasets only if there are more than one range axis.
     * 2. Update certain axis attributes on the given plot.
     */
    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final AxisSeriesLinkInfo axisSeriesLinkInfo = chartDocument.getAxisSeriesLinkInfo();
    final ArrayList<ChartElement> rangeAxisArrayList = axisSeriesLinkInfo.getRangeAxisElements();
    final int rangeAxisCount = rangeAxisArrayList.size();

    if (chart != null && rangeAxisCount > 0) {
      final CategoryPlot plot = (CategoryPlot)chart.getPlot();

      for (int i=0; i<rangeAxisCount; i++) {
        final ChartElement axisElement = rangeAxisArrayList.get(i);
        // If there is only one range axis then we do not need to create a new jfreeDataset (that uses certain column data)
        // Instead we just need to update certain attributes like label text, tick label color etc.
        if (rangeAxisCount > 1) {
          // Create new jfreeDataset since there are more than one range axis and get the data corresponding to
          // certain columns
          final Integer[] columnPosArr = getColumPositions(chartDocContext,axisElement, axisSeriesLinkInfo);
          final DefaultCategoryDataset currDataset = datasetGeneratorFactory.createDefaultCategoryDataset(chartDocContext, data, columnPosArr);
          plot.setDataset(i, currDataset);
          plot.mapDatasetToRangeAxis(i, i);
        }

        final ValueAxis valueAxis = createRangeAxis(axisElement);
        if(valueAxis != null) {
          plot.setRangeAxis(i, valueAxis);
          setAxisColor(axisElement, valueAxis, ChartElement.TAG_NAME_LABEL);
          setAxisColor(axisElement, valueAxis, ChartElement.TAG_NAME_TICK_LABEL);
          setRangeAxisLocation(plot, axisElement, i);
          setRenderer(plot, i);
        }
      }
    }
  }

  /**
   * Set the renderer for the chart.
   * </p>
   * @param plot   Plot element from the current chart document.
   * @param index  Set the renderer for the index.
   */
  //TODO: Look into this method for remaining implementations.
  private void setRenderer(final CategoryPlot plot,
                           final int index ) {
    if (plot.getRenderer() instanceof GroupedStackedBarRenderer) {
    } else if (plot.getRenderer() instanceof CylinderRenderer) {
    } else if (plot.getRenderer() instanceof LayeredBarRenderer) {
    } else {
      final BarRenderer barRenderer = new BarRenderer();
      plot.setRenderer(index, barRenderer);
    }
}

  /**
   * Creates a Range Axis
   * @param axisElement -- Current axis element
   * @return Returns the new range axis.
   */
  private ValueAxis createRangeAxis(final ChartElement axisElement) {
    final String axisLabel = (String)axisElement.getAttribute("label");//$NON-NLS-1$
    final ValueAxis valueAxis;
    if (axisLabel != null) {
      valueAxis = new NumberAxis(axisLabel);
    } else {
      valueAxis = new NumberAxis();
    }
    return valueAxis;
  }

  /**
   * Sets the axis label and tick label color.
   * </p>
   * @param axisElement Current axis element from the chart document
   * @param valueAxis   Current value axis. NOTE: This object will be updated in this method.
   * @param labelType   Tag name label or tag name tick label.
   */
  private void setAxisColor(final ChartElement axisElement,
                            final ValueAxis valueAxis,
                            final String labelType) {
    final ChartElement [] labelElements = axisElement.findChildrenByName(labelType);
    if (labelElements != null && labelElements.length > 0) {
      final CSSValue colorCSSValue = labelElements[0].getLayoutStyle().getValue(ChartStyleKeys.CSS_COLOR);
      final Color axisLabelColor = JFreeChartUtils.getColorFromCSSValue(colorCSSValue);
      if (axisLabelColor != null) {
        if (ChartElement.TAG_NAME_LABEL.equalsIgnoreCase(labelType)) {
          valueAxis.setLabelPaint(axisLabelColor);
        } else if (ChartElement.TAG_NAME_TICK_LABEL.equalsIgnoreCase(labelType)) {
          valueAxis.setTickLabelPaint(axisLabelColor);
        }
      }
    }
  }

  /**
   * Sets the current range axis location based on axis location style key (specified in the chart document).
   *
   * NOTE: Plot's range axis location is updated
   *
   * @param plot        Plot for the current chart
   * @param axisElement Current axis element being proccessed
   * @param axisCounter Set the location and tie it to a index.
   */
  private void setRangeAxisLocation (final CategoryPlot plot,
                                     final ChartElement axisElement,
                                     final int axisCounter) {
    final CSSValue cssValue = axisElement.getLayoutStyle().getValue(ChartStyleKeys.AXIS_LOCATION);
    final String side = cssValue.getCSSText();
    if (side != null && (cssValue.equals(ChartAxisLocationType.PRIMARY))){
      plot.setRangeAxisLocation(axisCounter, AxisLocation.BOTTOM_OR_LEFT);
    } else {
      plot.setRangeAxisLocation(axisCounter, AxisLocation.TOP_OR_RIGHT);
    }
  }
}
