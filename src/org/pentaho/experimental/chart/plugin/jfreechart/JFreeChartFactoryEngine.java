package org.pentaho.experimental.chart.plugin.jfreechart;

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
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.CylinderRenderer;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class JFreeChartFactoryEngine implements ChartFactoryEngine, Serializable {
  
  private static final long serialVersionUID = -1079376910255750394L;
  //TODO: Remove this at the earliest
  private static final String RANGE="range";

  public JFreeChartFactoryEngine(){
  }
  
  public ChartFactoryEngine getInstance() {
    return this;
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
  public void makeBarChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext, final IOutput outHandler) throws Exception {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final String title = JFreeChartUtils.getTitle(chartDocument);
    final String valueCategoryLabel = JFreeChartUtils.getValueCategoryLabel(chartDocument);
    final String valueAxisLabel = JFreeChartUtils.getValueAxisLabel(chartDocument);
    final PlotOrientation orientation = JFreeChartUtils.getPlotOrientation(chartDocument);
    final boolean legend = JFreeChartUtils.getShowLegend(chartDocument);
    final boolean toolTips = JFreeChartUtils.getShowToolTips(chartDocument);
    final JFreeChart chart = createBarChartSubtype(chartDocumentContext, data, title, valueCategoryLabel, valueAxisLabel, orientation, legend, toolTips);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument, data);

    outHandler.setChart(chart);
  }


  /**
   * 
   * @param chartDocumentContext
   * @param axisId
   * @return
   */
  private Integer[] getColumnPositions(ChartDocumentContext chartDocumentContext, AxisSeriesLinkInfo axisSeriesLinkInfo, Object axisId) {
    Integer[] columnPosArr = null;

    if (chartDocumentContext != null && axisSeriesLinkInfo != null && axisId != null) {
      final ChartSeriesDataLinkInfo seriesDataLinkInfo = chartDocumentContext.getDataLinkInfo();
      final ChartDocument chartDocument = chartDocumentContext.getChartDocument();

      if (chartDocument != null && seriesDataLinkInfo != null) {
        final ArrayList<ChartElement> seriesElementsList =
                (ArrayList<ChartElement>)axisSeriesLinkInfo.getSeriesElements(axisId);

          if (seriesElementsList != null) {
            final int size = seriesElementsList.size();
            final ArrayList<Integer> columnPosList = new ArrayList<Integer>();
            for (int i=0; i<size; i++) {
              final ChartElement seriesElement = seriesElementsList.get(i);
              final Integer columnPos = seriesDataLinkInfo.getColumnNum(seriesElement);
              columnPosList.add(columnPos);
            }
            System.out.println(" SWSWD " + columnPosList.toArray().getClass().getName());
            final int listLength = columnPosList.size();
            columnPosArr = new Integer[listLength];
            System.arraycopy(columnPosList.toArray(),0, columnPosArr, 0, listLength);
            //columnPosArr = (Integer[])columnPosList.toArray();
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
      stacked = value.equals(ChartBarStyle.STACKED) ? true : stacked;
      stackedPct = value.equals(ChartBarStyle.STACK_PERCENT) ? true : stackedPct;
      cylinder = value.equals(ChartBarStyle.CYLINDER) ? true : cylinder;
      interval = value.equals(ChartBarStyle.INTERVAL) ? true : interval;
      layered = value.equals(ChartBarStyle.LAYERED) ? true : layered;
      stacked100Pct = value.equals(ChartBarStyle.STACK_100_PERCENT) ? true : stacked100Pct;
    }
    
    JFreeChart chart = null;
    // Note:  We'll handle url generator when we update the plot info
    if (stacked || stackedPct || stacked100Pct) {
      chart = ChartFactory.createStackedBarChart(title, valueAxisLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument, null), orientation, legend, toolTips, false);
      if (JFreeChartUtils.getIsStackedGrouped(chartDocument)) {
        GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
        KeyToGroupMap map = JFreeChartUtils.createKeyToGroupMap(chartDocument, data);
        renderer.setSeriesToGroupMap(map); 
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
     * #1. User has to provide range axis type
     * #2. User have to specify the axis id for each series
     * The code for handling multiple axis goes here.
     * 1. Create multiple datasets only if there are more than one range axis
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
        // Instead we just need to update certain attributes
        if (rangeAxisCount > 1) {
          // Create new dataset since there are more than one range axis and get the data corresponding to
          // certain columns
          final DefaultCategoryDataset currDataset = getDatasetForAxis(chartDocumentContext, axisElement, axisSeriesLinkInfo, data);
          plot.setDataset(i, currDataset);
          plot.mapDatasetToRangeAxis(i, i);
        }

        final ValueAxis valueAxis = createRangeAxis(plot, axisElement, i);
        plot.setRangeAxis(i, valueAxis);

        setRangeAxisLocation(plot, axisElement, i);
        setRenderer(plot, i);
      }
    }

    return chart;
  }


  /**
   * 
   * @param chartDocumentContext
   * @param axisElement
   * @param axisSeriesLinkInfo
   * @param data
   * @return
   */
  private DefaultCategoryDataset getDatasetForAxis(final ChartDocumentContext chartDocumentContext,
                                                   final ChartElement axisElement,
                                                   final AxisSeriesLinkInfo axisSeriesLinkInfo,
                                                   final ChartTableModel data) {
    /*
    First we get the information that for each range axis we get the columns array list.
     */
    final Object axisID = axisElement.getAttribute("id");
    final Integer[] columnPosArr = getColumnPositions(chartDocumentContext, axisSeriesLinkInfo, axisID);
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();

    final DefaultCategoryDataset currDataset = JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument, columnPosArr);
    return currDataset;

  }

  /**
   *
   * @param plot
   * @param axisElement
   * @param rangeAxisCounter
   * @return
   */
  private ValueAxis createRangeAxis(final CategoryPlot plot,
                                    final ChartElement axisElement,
                                    final int rangeAxisCounter) {
    final String axisLabel = (String)axisElement.getAttribute("label");
    final ValueAxis valueAxis;
    if (axisLabel != null) {
      valueAxis = new NumberAxis(axisLabel);
    } else {
      valueAxis = new NumberAxis();
    }
    return valueAxis;
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
  private void setRenderer(CategoryPlot plot, final int index ) {
    if (plot.getRenderer() instanceof GroupedStackedBarRenderer) {
    } else if (plot.getRenderer() instanceof CylinderRenderer) {
    } else if (plot.getRenderer() instanceof LayeredBarRenderer) {
    } else {
      final BarRenderer barRenderer = new BarRenderer();
      plot.setRenderer(index, barRenderer);
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeBarLineChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeBarLineChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeBubbleChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeBubbleChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeDialChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeDialChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeDifferenceChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeDifferenceChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeLineChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeMultiPieChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeMultiPieChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makePieChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makePieChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeScatterPlotChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeScatterPlotChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeStepAreaChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeStepAreaChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeStepChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeStepChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeWaterfallChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeWaterfallChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) {

  }


  
}
