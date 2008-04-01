package org.pentaho.experimental.chart.plugin.jfreechart;

import java.io.Serializable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;

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
  public void makeAreaChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeBarChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeBarChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception {
    String title = JFreeChartUtils.getTitle(chartDocument);
    String valueCategoryLabel = JFreeChartUtils.getValueCategoryLabel(chartDocument);
    String valueAxisLabel = JFreeChartUtils.getValueAxisLabel(chartDocument);
    PlotOrientation orientation = JFreeChartUtils.getPlotOrientation(chartDocument);
    boolean legend = JFreeChartUtils.getShowLegend(chartDocument);
    boolean toolTips = JFreeChartUtils.getShowToolTips(chartDocument);
    boolean urls = JFreeChartUtils.getShowUrls(chartDocument);
    JFreeChart chart = ChartFactory.createBarChart(title, valueCategoryLabel, valueAxisLabel, null, orientation, legend, toolTips, urls);

    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument, data);

    chart.getCategoryPlot().setDataset(JFreeChartUtils.createCategoryDataset(data));
    outHandler.setChart(chart);
    outHandler.persist();
  }


  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeBarLineChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeBarLineChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeBubbleChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeBubbleChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeDialChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeDialChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeDifferenceChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeDifferenceChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeLineChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeMultiPieChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeMultiPieChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makePieChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makePieChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeScatterPlotChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeScatterPlotChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeStepAreaChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeStepAreaChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeStepChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeStepChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeWaterfallChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public void makeWaterfallChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }


  
}
