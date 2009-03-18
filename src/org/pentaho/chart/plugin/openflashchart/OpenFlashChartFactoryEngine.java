package org.pentaho.chart.plugin.openflashchart;

import java.awt.Color;
import java.awt.Paint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ofc4j.model.Chart;
import ofc4j.model.axis.XAxis;
import ofc4j.model.axis.YAxis;
import ofc4j.model.axis.Label.Rotation;
import ofc4j.model.elements.AreaHollowChart;
import ofc4j.model.elements.BarChart;
import ofc4j.model.elements.HorizontalBarChart;
import ofc4j.model.elements.LineChart;
import ofc4j.model.elements.PieChart;
import ofc4j.model.elements.HorizontalBarChart.Bar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartOrientationStyle;
import org.pentaho.chart.css.styles.ChartSeriesType;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.ChartResult;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.chart.plugin.openflashchart.outputs.OpenFlashChartOutput;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.color.ColorStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.messages.Messages;



public class OpenFlashChartFactoryEngine implements Serializable {

  private static final Log logger = LogFactory.getLog(OpenFlashChartFactoryEngine.class);
  
  private static final long serialVersionUID = -1079376910255750394L;

  public OpenFlashChartFactoryEngine(){
  }

  public IOutput makeChart(ChartTableModel data, ChartDocumentContext chartDocumentContext, ChartResult chartResult) {
    final CSSConstant currentChartType = determineChartType(chartDocumentContext.getChartDocument());
    IOutput chartOutput = null;
    try {
      if (currentChartType == ChartSeriesType.BAR) {
        chartOutput = new OpenFlashChartOutput(makeBarChart(data, chartDocumentContext));
      } else if (currentChartType == ChartSeriesType.LINE) {
        chartOutput = new OpenFlashChartOutput(makeLineChart(data, chartDocumentContext));
      } else if (currentChartType == ChartSeriesType.AREA) {
        chartOutput = new OpenFlashChartOutput((makeAreaChart(data, chartDocumentContext)));
      } else if (currentChartType == ChartSeriesType.PIE) {
        chartOutput = new OpenFlashChartOutput((makePieChart(data, chartDocumentContext)));
      } else if (currentChartType == ChartSeriesType.DIAL) {
        chartOutput = new OpenFlashChartOutput((makeDialChart(data, chartDocumentContext)));
      } else {
        chartResult.setErrorCode(IChartPlugin.ERROR_INDETERMINATE_CHART_TYPE);
        chartResult.setDescription(Messages.getErrorString("JFreeChartPlugin.ERROR_0001_CHART_TYPE_INDETERMINABLE")); //$NON-NLS-1$
      }
    } catch (Exception e) {
      chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
      chartResult.setDescription(e.getLocalizedMessage());
    }
    return chartOutput;
  }

  
  public Chart makeAreaChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    List<Number> list = new ArrayList<Number>(30);
    for (float i = 0; i < 6.2; i += 0.2) {
      list.add(new Float(Math.sin(i) * 1.9));
    }
    
    AreaHollowChart areaChart = new AreaHollowChart();
    areaChart.addValues(list);
    areaChart.setWidth(1);
        
    XAxis xaxis = new XAxis();
    xaxis.setSteps(2);
    xaxis.getLabels().setSteps(4);
    xaxis.getLabels().setRotation(Rotation.VERTICAL);

    YAxis yaxis = new YAxis();
    yaxis.setRange(-2, 2, 2);
    yaxis.setOffset(false);

    Chart chart = new Chart("Area Chart");
    chart.addElements(areaChart);
    chart.setYAxis(yaxis);
    chart.setXAxis(xaxis);
    return chart;
  }

  public Chart makePieChart(final ChartTableModel chartTableModel, final ChartDocumentContext chartDocumentContext) {
    ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    
    PieChart pieChart = new PieChart();
    pieChart.setAnimate(false);
    pieChart.setStartAngle(35);
    pieChart.setBorder(2);
    pieChart.setTooltip("#val# of #total#<br>#percent# of 100%");
    
    ArrayList<Number> values = new ArrayList<Number>();
    for (int row = 0; row < chartTableModel.getRowCount(); row++) {
      Number value = (Number)chartTableModel.getValueAt(row, 0);
      values.add(value == null ? 0 : value);
    }
    pieChart.addValues(values);
    
    ArrayList<String> colors = new ArrayList<String>();    
    for (ChartElement seriesElement : chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES)) {
      LayoutStyle layoutStyle = seriesElement.getLayoutStyle();
      Paint color = (layoutStyle != null ? (Paint)layoutStyle.getValue(ColorStyleKeys.COLOR) : null);
      if (color instanceof Color) {
        colors.add("#" + Integer.toHexString(0x00FFFFFF & ((Color)color).getRGB()));
      }
    }
    pieChart.setColours(colors);
    
    String chartTitle = getChartTitle(chartDocument);    
    Chart chart = (chartTitle != null ? new Chart(chartTitle) : new Chart());
    chart.setBackgroundColour("#FFFFFF");
    chart.addElements(pieChart);
    return chart;
  }

  public Chart makeDialChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    Chart chartData = new Chart("The Title","font-size: 14px; font-family: Verdana; text-align: center;");
    return chartData;
  }
  
  public Chart makeBarChart(final ChartTableModel chartTableModel, final ChartDocumentContext chartDocumentContext) throws Exception {
    
    ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    
    String chartTitle = getChartTitle(chartDocument);    
    Chart chart = (chartTitle != null ? new Chart(chartTitle) : new Chart());
    chart.setBackgroundColour("#FFFFFF");
    
    CSSValue orientation = getPlotOrientation(chartDocumentContext.getChartDocument());
    if (ChartOrientationStyle.HORIZONTAL.equals(orientation)) {
      ArrayList<String> categories = new ArrayList<String>();
      for (int i = 0; i < chartTableModel.getColumnCount(); i++) {
        categories.add(chartTableModel.getColumnName(i));
      }
      if (categories.size() > 0) {
        YAxis ya = new YAxis();
        ya.setLabels(categories.toArray(new String[0]));
        ya.setMax(categories.size());
        chart.setYAxis(ya);        
      }
      
      final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
      
      Number maxValue = null;
      Number minValue = null;
      for (int row = 0; row < chartTableModel.getRowCount(); row++) {
        HorizontalBarChart horizontalBarChart = new HorizontalBarChart();
        horizontalBarChart.setText(chartTableModel.getRowName(row));
        horizontalBarChart.setTooltip("$#val#");
        if ((seriesElements != null) && (seriesElements.length > row)) {
          LayoutStyle layoutStyle = seriesElements[row].getLayoutStyle();
          Paint color = (layoutStyle != null ? (Paint)layoutStyle.getValue(ColorStyleKeys.COLOR) : null);
          if (color instanceof Color) {
            horizontalBarChart.setColour("#" + Integer.toHexString(0x00FFFFFF & ((Color)color).getRGB()));
          }
        }
        ArrayList<Number> values = new ArrayList<Number>();
        for (int column = 0; column < chartTableModel.getColumnCount(); column++) {
          Number value = (Number)chartTableModel.getValueAt(row, column);
          if (maxValue == null) {
            maxValue = value;
          } else if (value != null) {
            maxValue = Math.max(maxValue.doubleValue(), value.doubleValue());
          }
          if (minValue == null) {
            minValue = value;
          } else if (value != null) {
            minValue = Math.min(minValue.doubleValue(), value.doubleValue());
          }
          values.add(value == null ? 0 : value);
        }

        horizontalBarChart.addValues(values.toArray(new Number[0]));
        chart.addElements(horizontalBarChart);
      }
      
      if ((maxValue != null) && (minValue != null)) {
        int exponent = Integer.toString(Math.abs(maxValue.intValue())).length() - 1;
        
        XAxis xa = new XAxis();
        int stepSize = (int)(((long)(maxValue.intValue() / Math.pow(10, exponent))) * Math.pow(10, exponent - 1)) * 2;
        xa.setSteps(stepSize);
        
        xa.setMax((int)(maxValue.doubleValue() - (maxValue.doubleValue() % stepSize)) + stepSize);
        chart.setXAxis(xa);
      }
    } else {
     
      ArrayList<String> categories = new ArrayList<String>();
      for (int i = 0; i < chartTableModel.getColumnCount(); i++) {
        categories.add(chartTableModel.getColumnName(i));
      }
      if (categories.size() > 0) {
        XAxis xa = new XAxis();
        xa.setLabels(categories);
        xa.setMax(categories.size());
        chart.setXAxis(xa);        
      }
      
      final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
      
      Number maxValue = null;
      Number minValue = null;
      for (int row = 0; row < chartTableModel.getRowCount(); row++) {
        BarChart verticalBarChart = new BarChart();
        verticalBarChart.setText(chartTableModel.getRowName(row));
        verticalBarChart.setTooltip("$#val#");
        if ((seriesElements != null) && (seriesElements.length > row)) {
          LayoutStyle layoutStyle = seriesElements[row].getLayoutStyle();
          Paint color = (layoutStyle != null ? (Paint)layoutStyle.getValue(ColorStyleKeys.COLOR) : null);
          if (color instanceof Color) {
            verticalBarChart.setColour("#" + Integer.toHexString(0x00FFFFFF & ((Color)color).getRGB()));
          }
        }
        ArrayList<Number> values = new ArrayList<Number>();
        for (int column = 0; column < chartTableModel.getColumnCount(); column++) {
          Number value = (Number)chartTableModel.getValueAt(row, column);
          if (maxValue == null) {
            maxValue = value;
          } else if (value != null) {
            maxValue = Math.max(maxValue.doubleValue(), value.doubleValue());
          }
          if (minValue == null) {
            minValue = value;
          } else if (value != null) {
            minValue = Math.min(minValue.doubleValue(), value.doubleValue());
          }
          values.add(value == null ? 0 : value);
        }
        
        verticalBarChart.addValues(values);
        chart.addElements(verticalBarChart);
      }
      
      if ((maxValue != null) && (minValue != null)) {
        int exponent = Integer.toString(Math.abs(maxValue.intValue())).length() - 1;
        
        YAxis ya = new YAxis();
        int stepSize = (int)(((long)(maxValue.intValue() / Math.pow(10, exponent))) * Math.pow(10, exponent - 1));
        ya.setSteps(stepSize);
        
        ya.setMax((int)(maxValue.doubleValue() - (maxValue.doubleValue() % stepSize)) + stepSize);
        chart.setYAxis(ya);
      }
    }
    
    return chart;
  }

  private String getChartTitle(ChartDocument chartDocument) {
    final ChartElement[] children = chartDocument.getRootElement().findChildrenByName("title"); //$NON-NLS-1$
    if (children != null && children.length > 0) {
      return children[0].getText();
    }
    return null;
  }
  
  private CSSValue getPlotOrientation(final ChartDocument chartDocument) {
    CSSValue plotOrient = null;
    final ChartElement plotElement = chartDocument.getPlotElement();

    if (plotElement != null) {
      final LayoutStyle layoutStyle = plotElement.getLayoutStyle();
      plotOrient = layoutStyle.getValue(ChartStyleKeys.ORIENTATION);
    }
    
    return plotOrient;
  }
  
  public Chart makeLineChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {

    LineChart lineChart = new LineChart(LineChart.Style.DOT);
    lineChart.setHaloSize(0);
    lineChart.setWidth(2);
    lineChart.setDotSize(4);

    for (float i = 0; i < 6.2; i += 0.2) {
      lineChart.addValues(Math.sin(i) * 1.9 + 10);
    }

    YAxis yAxis = new YAxis();
    yAxis.setRange(0, 15, 5);
    
    Chart chart = new Chart(new Date().toString());
    chart.setBackgroundColour("#FFFFFF");
    chart.setYAxis(yAxis);
    chart.addElements(lineChart);
    
    return chart;
  }

  public CSSConstant determineChartType(final ChartDocument chartDocument) {
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    for (final ChartElement element : elements) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.CHART_TYPE);
      if (value != null) {
        if (value.equals(ChartSeriesType.BAR)) {
          return ChartSeriesType.BAR;
        } else if (value.equals(ChartSeriesType.LINE)) {
          return ChartSeriesType.LINE;
        } else if (value.equals(ChartSeriesType.AREA)) {
          return ChartSeriesType.AREA;
        } else if (value.equals(ChartSeriesType.PIE)) {
          return ChartSeriesType.PIE;
        }else if (value.equals(ChartSeriesType.DIAL)) {
          return ChartSeriesType.DIAL;
        }
      }
    }
    return ChartSeriesType.UNDEFINED;
  }
}
