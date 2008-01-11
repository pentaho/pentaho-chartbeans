package org.pentaho.chart.engine;

import java.util.Map;

import org.pentaho.chart.api.engine.ChartFactory;
import org.pentaho.chart.api.engine.Data;
import org.pentaho.chart.api.engine.Output;
import org.pentaho.chart.plugin.jfreechart.JFreeChartFactory;

public class ChartUtil {
  
  // TODO: 
  // This singleton needs to be loaded from a configuration ...
  private static final ChartFactory CHART_FACTORY = new JFreeChartFactory();

  
  public static void makeAreaChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeAreaChart(data, styles, outHandler);
  }

  public static void makeBarChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeBarChart(data, styles, outHandler);
  }

  public static void makeBarLineChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeBarLineChart(data, styles, outHandler);
  }

  public static void makeBubbleChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeBubbleChart(data, styles, outHandler);
  }

  public static void makeDialChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeDialChart(data, styles, outHandler);
  }

  public static void makeDifferenceChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeDifferenceChart(data, styles, outHandler);
  }

  public static void makeLineChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeLineChart(data, styles, outHandler);
  }

  public static void makeMultiPieChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeMultiPieChart(data, styles, outHandler);
  }

  public static void makePieChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makePieChart(data, styles, outHandler);
  }

  public static void makeScatterPlotChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeScatterPlotChart(data, styles, outHandler);
  }

  public static void makeStepAreaChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeStepAreaChart(data, styles, outHandler);
  }

  public static void makeStepChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeStepChart(data, styles, outHandler);
  }

  public static void makeWaterfallChart(Data data, Map <String,?> styles, Output outHandler) {
    CHART_FACTORY.getInstance().makeWaterfallChart(data, styles, outHandler);
  }

}
