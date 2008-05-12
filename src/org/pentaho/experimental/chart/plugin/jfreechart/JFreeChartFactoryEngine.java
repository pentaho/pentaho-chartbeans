package org.pentaho.experimental.chart.plugin.jfreechart;

import java.awt.Color;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.SortOrder;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.AxisSeriesLinkInfo;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.ChartSeriesDataLinkInfo;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartAxisLocationType;
import org.pentaho.experimental.chart.css.styles.ChartBarStyle;
import org.pentaho.experimental.chart.css.styles.ChartSeriesType;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.IChartPlugin;
import org.pentaho.experimental.chart.plugin.api.ChartResult;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.jfreechart.outputs.JFreeChartOutput;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.CylinderRenderer;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.messages.Messages;

public class JFreeChartFactoryEngine implements Serializable {
  
  private static final long serialVersionUID = -1079376910255750394L;

  public JFreeChartFactoryEngine(){
  }

  public IOutput makeChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext, final ChartResult chartResult) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final CSSConstant currentChartType = JFreeChartUtils.determineChartType(chartDocument);
    if (currentChartType == ChartSeriesType.UNDEFINED) {
      chartResult.setErrorCode(IChartPlugin.ERROR_INDETERMINATE_CHART_TYPE);
      chartResult.setDescription(Messages.getErrorString("JFreeChartPlugin.ERROR_0001_CHART_TYPE_INDETERMINABLE")); //$NON-NLS-1$
    }

    if (currentChartType == ChartSeriesType.BAR) {
      try {
        final JFreeChart chart = makeBarChart(data, chartDocumentContext);
        return new JFreeChartOutput(chart);
      } catch (Exception e) {
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.LINE) {
      try {
        return new JFreeChartOutput(makeLineChart(data, chartDocument));
      } catch (Exception e) {
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    }
  
    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeAreaChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeBarChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public JFreeChart makeBarChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext)
      throws Exception {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final String title = JFreeChartUtils.getTitle(chartDocument);
    final String valueCategoryLabel = JFreeChartUtils.getValueCategoryLabel(chartDocument);
    final String valueAxisLabel = JFreeChartUtils.getValueAxisLabel(chartDocument);
    final PlotOrientation orientation = JFreeChartUtils.getPlotOrientation(chartDocument);
    final boolean legend = JFreeChartUtils.getShowLegend(chartDocument);
    final boolean toolTips = JFreeChartUtils.getShowToolTips(chartDocument);
    final JFreeChart chart = createBarChartSubtype(chartDocumentContext, data, title, valueCategoryLabel, valueAxisLabel, orientation, legend, toolTips);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument, data);

    return chart;
  }


  /**
   * 
   * @param chartDocumentContext
   * @param axisId
   * @return
   */
  private Integer[] getColumnPositions(final ChartDocumentContext chartDocumentContext, final AxisSeriesLinkInfo axisSeriesLinkInfo, final Object axisId) {
    Integer[] columnPosArr = null;

    if (chartDocumentContext != null && axisSeriesLinkInfo != null && axisId != null) {
      final ChartSeriesDataLinkInfo seriesDataLinkInfo = chartDocumentContext.getDataLinkInfo();
      final ChartDocument chartDocument = chartDocumentContext.getChartDocument();

      if (chartDocument != null && seriesDataLinkInfo != null) {
        final ArrayList<ChartElement> seriesElementsList = axisSeriesLinkInfo.getSeriesElements(axisId);

          if (seriesElementsList != null) {
            final int size = seriesElementsList.size();
            final ArrayList<Integer> columnPosList = new ArrayList<Integer>();
            for (int i=0; i<size; i++) {
              final ChartElement seriesElement = seriesElementsList.get(i);
              final Integer columnPos = seriesDataLinkInfo.getColumnNum(seriesElement);
              columnPosList.add(columnPos);
            }
            final int listLength = columnPosList.size();
            columnPosArr = new Integer[listLength];
            System.arraycopy(columnPosList.toArray(),0, columnPosArr, 0, listLength);
            Arrays.sort(columnPosArr);
          }
        }
      }
      
    return columnPosArr;
  }
  
  /**
   * 
   * @param chartDocumentContext
   * @param data
   * @param title
   * @param valueCategoryLabel
   * @param valueAxisLabel
   * @param orientation
   * @param legend
   * @param toolTips
   * @return
   */
  private JFreeChart createBarChartSubtype(final ChartDocumentContext chartDocumentContext, final ChartTableModel data, final String title, final String valueCategoryLabel, final String valueAxisLabel, final PlotOrientation orientation, final boolean legend, final boolean toolTips) {
    boolean stacked = false;
    boolean stackedPct = false;
    boolean cylinder = false;
    boolean interval = false;
    boolean layered = false;
    boolean stacked100Pct = false;
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();

    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    //TODO: ask if we need to break out of this loop instead of going through the entire series.
    for (final ChartElement element : elements) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.BAR_STYLE);
      stacked |= value.equals(ChartBarStyle.STACKED);
      stackedPct |= value.equals(ChartBarStyle.STACK_PERCENT);
      cylinder |= value.equals(ChartBarStyle.CYLINDER);
      interval |= value.equals(ChartBarStyle.INTERVAL);
      layered |= value.equals(ChartBarStyle.LAYERED);
      stacked100Pct |= value.equals(ChartBarStyle.STACK_100_PERCENT);
    }
    
    final JFreeChart chart;
    // Note:  We'll handle url generator when we update the plot info
    if (stacked || stackedPct || stacked100Pct) {
      chart = ChartFactory.createStackedBarChart(title, valueAxisLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument, null), orientation, legend, toolTips, false);
      if (JFreeChartUtils.getIsStackedGrouped(chartDocument)) {
        final GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
        final KeyToGroupMap map = JFreeChartUtils.createKeyToGroupMap(chartDocument, data, chart.getCategoryPlot().getDataset());
        renderer.setSeriesToGroupMap(map);
//        SubCategoryAxis domainAxis = new SubCategoryAxis("Product / Month");
//        domainAxis.setCategoryMargin(0.05);
//        domainAxis.addSubCategory("North America / Canada");
//        domainAxis.addSubCategory("North America / USA");
//        domainAxis.addSubCategory("Asia / Russia");
//        domainAxis.addSubCategory("Asia / China");
//        domainAxis.addSubCategory("South America / Peru");
//        domainAxis.addSubCategory("South America / Brazil");
//        domainAxis.addSubCategory("Europe / Italy");
//        domainAxis.addSubCategory("Eurpoe / Germany");

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
//        plot.setDomainAxis(domainAxis);
        plot.setRenderer(renderer);
      }
      ((StackedBarRenderer)chart.getCategoryPlot().getRenderer()).setRenderAsPercentages(stackedPct || stacked100Pct);
      if (stacked100Pct) {
        final NumberAxis rangeAxis = (NumberAxis) chart.getCategoryPlot().getRangeAxis();
        rangeAxis.setNumberFormatOverride(NumberFormat.getPercentInstance());
      }
    } else {   
      if (cylinder) {
        chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument, null), orientation, legend, toolTips, false);
        final CylinderRenderer renderer = new CylinderRenderer();
        chart.getCategoryPlot().setRenderer(renderer);
      } else if (layered) { 
        chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument, null), orientation, legend, toolTips, false);
        final LayeredBarRenderer renderer = new LayeredBarRenderer();
        renderer.setDrawBarOutline(false);
        chart.getCategoryPlot().setRenderer(renderer);
        chart.getCategoryPlot().setRowRenderingOrder(SortOrder.DESCENDING);
      } else if (interval) {
        chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, JFreeChartUtils.createDefaultIntervalCategoryDataset(data, chartDocument), orientation, legend, toolTips, false);
        chart.getCategoryPlot().setRenderer(new IntervalBarRenderer());
      } else {
        chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument, null), orientation, legend, toolTips, false);
      }
    }

    /*
     * Assumption:
     * #1. User has to provide axis type for each axis (range/domain)
     * #2. User have to specify the axis id for each series
     * The code for handling multiple axis goes here.
     * 1. Create multiple datasets only if there are more than one range axis.
     * 2. Update certain axis attributes on the given plot.
     */
    final AxisSeriesLinkInfo axisSeriesLinkInfo = chartDocument.getAxisSeriesLinkInfo();
    final ArrayList<ChartElement> rangeAxisArrayList = axisSeriesLinkInfo.getRangeAxisElements();
    final int rangeAxisCount = rangeAxisArrayList.size();

    if (chart != null && rangeAxisCount > 0) {
      final CategoryPlot plot = (CategoryPlot)chart.getPlot();

      for (int i=0; i<rangeAxisCount; i++) {
        final ChartElement axisElement = rangeAxisArrayList.get(i);
        // If there is only one range axis then we do not need to create a new dataset (that uses certain column data)
        // Instead we just need to update certain attributes like label text, tick label color etc.
        if (rangeAxisCount > 1) {
          // Create new dataset since there are more than one range axis and get the data corresponding to
          // certain columns
          final DefaultCategoryDataset currDataset = getDatasetForAxis(chartDocumentContext, axisElement, axisSeriesLinkInfo, data);
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
    return chart;
  }


  /**
   * Returns custom dataset based on certain column positions. The column positions are retrieved by iterating
   * over series elements looking for a specific/given axis id.
   * @param chartDocumentContext
   * @param axisElement
   * @param axisSeriesLinkInfo
   * @param data
   * @return DefaultCategoryDataset that has information from specific column positions.
   */
  private DefaultCategoryDataset getDatasetForAxis(final ChartDocumentContext chartDocumentContext,
                                                   final ChartElement axisElement,
                                                   final AxisSeriesLinkInfo axisSeriesLinkInfo,
                                                   final ChartTableModel data) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    /*
     * First we get the column pos information for each range axis from the columns array list.
     * And then we create the default category dataset based on the columns positions retrieved above.
     */
    // Get current axis element's axis id.
    final Object axisID = axisElement.getAttribute("id");//$NON-NLS-1$
    // Get the column positions for current axis element by looking into each series for given axis id.
    final Integer[] columnPosArr = getColumnPositions(chartDocumentContext, axisSeriesLinkInfo, axisID);
    // Create custom dataset based on the given column positions.
    return JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument, columnPosArr);
  }

  /**
   * Creates a Range Axis
   * @param plot
   * @param axisElement
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
   * @param axisElement
   * @param valueAxis
   * @param labelType
   */
  private void setAxisColor(final ChartElement axisElement, final ValueAxis valueAxis, final String labelType) {
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
   * NOTE: Plot's range axis location is updated
   * @param plot
   * @param axisElement
   * @param axisCounter
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

  /**
   *
   * @param plot
   * @param index
   */
  private void setRenderer(final CategoryPlot plot, final int index ) {
    if (plot.getRenderer() instanceof GroupedStackedBarRenderer) {
    } else if (plot.getRenderer() instanceof CylinderRenderer) {
    } else if (plot.getRenderer() instanceof LayeredBarRenderer) {
    } else {
      final BarRenderer barRenderer = new BarRenderer();
      plot.setRenderer(index, barRenderer);
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  private JFreeChart makeLineChart(final ChartTableModel data, final ChartDocument chartDocument) {
    return null;
  }
  
}
