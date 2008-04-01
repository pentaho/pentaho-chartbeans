package org.pentaho.experimental.chart.plugin.api.engine;

import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.IOutput;

/**
 * @author wseyler
 * 
 * This class contains all the possible charts that can be created by an implementing plugin.
 */

public interface ChartFactoryEngine {

  public ChartFactoryEngine getInstance();

  public void makeBarChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeLineChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeAreaChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makePieChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeMultiPieChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeScatterPlotChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeDifferenceChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeStepChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeStepAreaChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeWaterfallChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeDialChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeBubbleChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

  public void makeBarLineChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) throws Exception;

}
