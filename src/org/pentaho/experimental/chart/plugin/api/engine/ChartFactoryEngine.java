package org.pentaho.experimental.chart.plugin.api.engine;

import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.IOutput;

public interface ChartFactoryEngine {
  
  public ChartFactoryEngine getInstance();
  
  public void makeBarChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeLineChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeAreaChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makePieChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeMultiPieChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeScatterPlotChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeDifferenceChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeStepChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeStepAreaChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeWaterfallChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeDialChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeBubbleChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);
  public void makeBarLineChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler);

}
