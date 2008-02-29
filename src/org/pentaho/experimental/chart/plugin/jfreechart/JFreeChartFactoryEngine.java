package org.pentaho.experimental.chart.plugin.jfreechart;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.engine.Chart;
import org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine;
import org.pentaho.experimental.chart.plugin.api.engine.Data;
import org.pentaho.experimental.chart.plugin.jfreechart.beans.BarChartBean;
import org.pentaho.experimental.chart.plugin.jfreechart.beans.CombinationChartBean;

public class JFreeChartFactoryEngine implements ChartFactoryEngine, Serializable {
  
  private static final long serialVersionUID = -1079376910255750394L;

  public JFreeChartFactoryEngine(){
  }
  
  public ChartFactoryEngine getInstance() {
    return this;
  }

  public void makeAreaChart(Data data, Map <String,?> styles, IOutput outHandler) {
    // TODO Auto-generated method stub

  }

  public void makeBarChart(Data data, Map <String,?> styles, IOutput outHandler) {
    BarChartBean chartBean = new BarChartBean();
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

  public void makeBarLineChart(Data data, Map <String,?> styles, IOutput outHandler) {

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

  public void makeBubbleChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeDialChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeDifferenceChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeLineChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeMultiPieChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makePieChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeScatterPlotChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeStepAreaChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeStepChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

  public void makeWaterfallChart(Data data, Map <String,?> styles, IOutput outHandler) {

  }

}
