package org.pentaho.experimental.chart.plugin.api.engine;

import java.util.Map;

import org.pentaho.experimental.chart.plugin.api.IOutput;

public interface ChartFactoryEngine {
  
  public ChartFactoryEngine getInstance();
  
  public void makeBarChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeLineChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeAreaChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makePieChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeMultiPieChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeScatterPlotChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeDifferenceChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeStepChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeStepAreaChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeWaterfallChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeDialChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeBubbleChart(Data data, Map <String,?> styles, IOutput outHandler);
  public void makeBarLineChart(Data data, Map <String,?> styles, IOutput outHandler);

}
