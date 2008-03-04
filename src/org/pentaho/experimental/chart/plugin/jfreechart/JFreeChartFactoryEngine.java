package org.pentaho.experimental.chart.plugin.jfreechart;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.PersistenceException;
import org.pentaho.experimental.chart.plugin.api.engine.Chart;
import org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine;
import org.pentaho.experimental.chart.plugin.jfreechart.beans.BarChartBean;
import org.pentaho.experimental.chart.plugin.jfreechart.beans.CombinationChartBean;

public class JFreeChartFactoryEngine implements ChartFactoryEngine, Serializable {
  
  private static final long serialVersionUID = -1079376910255750394L;

  public JFreeChartFactoryEngine(){
  }
  
  public ChartFactoryEngine getInstance() {
    return this;
  }

  public void makeAreaChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {
    // TODO Auto-generated method stub

  }

  public void makeBarChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {
    BarChartBean chartBean = new BarChartBean();
    chartBean.createDefaultChart();
    
    JFreeChart aChart = chartBean.getChart();
    try {
      BeanUtils.populate(chartBean, styles);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    DefaultCategoryDataset[] categoryDataSet = createCategoryDataset(data);
    chartBean.setData(categoryDataSet);

    outHandler.setChart(chartBean);
    try {
      outHandler.persist();
    } catch (PersistenceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void makeBarLineChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

    Chart chartBean = new CombinationChartBean();
    chartBean.createDefaultChart();
    
    // TODO: figure out data loading
    
    try {
      BeanUtils.populate(chartBean, styles);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    
    outHandler.setChart(chartBean);
    
    // TODO: figure out output handling
  }

  public void makeBubbleChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeDialChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeDifferenceChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeLineChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeMultiPieChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makePieChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeScatterPlotChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeStepAreaChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeStepChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeWaterfallChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler) {

  }

  /**
   * @return
   */
  private DefaultCategoryDataset[] createCategoryDataset(ChartTableModel data) {
    DefaultCategoryDataset[] value = new DefaultCategoryDataset[1];
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for(int row=0; row<data.getRowCount(); row++) {
      for(int column=0; column<data.getColumnCount(); column++) {
        dataset.setValue((Number) data.getValueAt(row, column), row, column);
      }
    }
    
    value[0] = dataset;
    
    return value;
  }


}
