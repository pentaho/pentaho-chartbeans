package org.pentaho.chart.plugin.jfreechart;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.pentaho.chart.api.engine.Chart;
import org.pentaho.chart.api.engine.ChartFactory;
import org.pentaho.chart.api.engine.Data;
import org.pentaho.chart.api.engine.Output;

public class JFreeChartFactory implements ChartFactory, Serializable {
  
  private static final long serialVersionUID = -1079376910255750394L;

  public JFreeChartFactory(){
  }
  
  public ChartFactory getInstance() {
    return this;
  }

  public void makeAreaChart(Data data, Map styles, Output outHandler) {
    // TODO Auto-generated method stub

  }

  public void makeBarChart(Data data, Map styles, Output outHandler) {
    // TODO Auto-generated method stub

  }

  public void makeBarLineChart(Data data, Map styles, Output outHandler) {

    Chart chartBean = new CombinationChartBean();
    
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

  public void makeBubbleChart(Data data, Map styles, Output outHandler) {

  }

  public void makeDialChart(Data data, Map styles, Output outHandler) {

  }

  public void makeDifferenceChart(Data data, Map styles, Output outHandler) {

  }

  public void makeLineChart(Data data, Map styles, Output outHandler) {

  }

  public void makeMultiPieChart(Data data, Map styles, Output outHandler) {

  }

  public void makePieChart(Data data, Map styles, Output outHandler) {

  }

  public void makeScatterPlotChart(Data data, Map styles, Output outHandler) {

  }

  public void makeStepAreaChart(Data data, Map styles, Output outHandler) {

  }

  public void makeStepChart(Data data, Map styles, Output outHandler) {

  }

  public void makeWaterfallChart(Data data, Map styles, Output outHandler) {

  }

}
