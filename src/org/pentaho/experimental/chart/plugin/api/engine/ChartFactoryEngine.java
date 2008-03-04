package org.pentaho.experimental.chart.plugin.api.engine;

import java.util.Map;

import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.IOutput;

public interface ChartFactoryEngine {
  
  public ChartFactoryEngine getInstance();
  
  public void makeBarChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeLineChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeAreaChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makePieChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeMultiPieChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeScatterPlotChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeDifferenceChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeStepChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeStepAreaChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeWaterfallChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeDialChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeBubbleChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);
  public void makeBarLineChart(ChartTableModel data, Map <String,?> styles, IOutput outHandler);

}
