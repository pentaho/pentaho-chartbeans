package org.pentaho.experimental.chart.plugin.jfreechart;

import java.io.Serializable;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.util.SortOrder;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartBarStyle;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.CylinderRenderer;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class JFreeChartFactoryEngine implements ChartFactoryEngine, Serializable {
  
  private static final long serialVersionUID = -1079376910255750394L;

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
  public void makeBarChart(final ChartTableModel data, final ChartDocument chartDocument, final IOutput outHandler) throws Exception {
    final String title = JFreeChartUtils.getTitle(chartDocument);
    final String valueCategoryLabel = JFreeChartUtils.getValueCategoryLabel(chartDocument);
    final String valueAxisLabel = JFreeChartUtils.getValueAxisLabel(chartDocument);
    final PlotOrientation orientation = JFreeChartUtils.getPlotOrientation(chartDocument);
    final boolean legend = JFreeChartUtils.getShowLegend(chartDocument);
    final boolean toolTips = JFreeChartUtils.getShowToolTips(chartDocument);
    final JFreeChart chart = createBarChartSubtype(chartDocument, data, title, valueCategoryLabel, valueAxisLabel, orientation, legend, toolTips);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument, data);

    outHandler.setChart(chart);
  }
  
  private JFreeChart createBarChartSubtype(final ChartDocument chartDocument, final ChartTableModel data, final String title, final String valueCategoryLabel, final String valueAxisLabel, final PlotOrientation orientation, final boolean legend, final boolean toolTips) {
    boolean stacked = false;
    boolean stackedPct = false;
    boolean cylinder = false;
    boolean interval = false;
    boolean layered = false;
    boolean stacked100Pct = false;

    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
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
      chart = ChartFactory.createStackedBarChart(title, valueAxisLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument), orientation, legend, toolTips, false);
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
        chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument), orientation, legend, toolTips, false);
        final CylinderRenderer renderer = new CylinderRenderer();
        chart.getCategoryPlot().setRenderer(renderer);
      } else if (layered) { 
        chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument), orientation, legend, toolTips, false);
        final LayeredBarRenderer renderer = new LayeredBarRenderer();
        renderer.setDrawBarOutline(false);
        chart.getCategoryPlot().setRenderer(renderer);
        chart.getCategoryPlot().setRowRenderingOrder(SortOrder.DESCENDING);
      } else if (interval) {
        chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, JFreeChartUtils.createDefaultIntervalCategoryDataset(data, chartDocument), orientation, legend, toolTips, false);
        chart.getCategoryPlot().setRenderer(new IntervalBarRenderer());
      } else {
        chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, JFreeChartUtils.createDefaultCategoryDataset(data, chartDocument), orientation, legend, toolTips, false);
      }
    }
    return chart;
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
