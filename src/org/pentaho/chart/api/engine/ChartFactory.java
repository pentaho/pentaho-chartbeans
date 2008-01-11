package org.pentaho.chart.api.engine;

import java.util.Map;

public interface ChartFactory {
  
  public ChartFactory getInstance();
  
  public void makeBarChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeLineChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeAreaChart(Data data, Map <String,?> styles, Output outHandler);
  public void makePieChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeMultiPieChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeScatterPlotChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeDifferenceChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeStepChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeStepAreaChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeWaterfallChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeDialChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeBubbleChart(Data data, Map <String,?> styles, Output outHandler);
  public void makeBarLineChart(Data data, Map <String,?> styles, Output outHandler);

}
