package org.pentaho.chart.plugin.openflashchart;

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
import ofc4j.model.elements.Element;
import ofc4j.model.elements.HorizontalBarChart;
import ofc4j.model.elements.LineChart;
import ofc4j.model.elements.PieChart;

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

  public Chart makePieChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    PieChart pieChart = new PieChart();
    pieChart.setAnimate(true);
    pieChart.setStartAngle(35);
    pieChart.setBorder(2);
    pieChart.setAlpha(0.6f);
    pieChart.addValues(2, 3);
    pieChart.addSlice(6.5f, "hello (6.5)");
    pieChart.setColours("#d01f3c", "#356aa0", "#C79810");
    pieChart.setTooltip("#val# of #total#<br>#percent# of 100%");
    
    Chart chart = new Chart("Pie Chart");
    chart.addElements(pieChart);
    return chart;
  }

  public Chart makeDialChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    Chart chartData = new Chart("The Title","font-size: 14px; font-family: Verdana; text-align: center;");
    return chartData;
  }
  
  public Chart makeBarChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) throws Exception {
    
    Element barChart = null;
    
    CSSValue orientation = getPlotOrientation(chartDocumentContext.getChartDocument());
    if (ChartOrientationStyle.HORIZONTAL.equals(orientation)) {
      HorizontalBarChart horizontalBarChart = new HorizontalBarChart();
      horizontalBarChart.addValues(9, 8, 7, 6, 5, 4, 3, 2, 1);
      barChart = horizontalBarChart;
    } else {
      BarChart verticalBarChart = new BarChart();
      verticalBarChart.addValues(9, 8, 7, 6, 5, 4, 3, 2, 1);
      barChart = verticalBarChart;
    }
    
    Chart chart = new Chart("Simple Bar Chart");
    chart.addElements(barChart);
    
    return chart;
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
    lineChart.setHaloSize(0).setWidth(2).setDotSize(4);

    for (float i = 0; i < 6.2; i += 0.2) {
      lineChart.addValues(Math.sin(i) * 1.9 + 10);
    }

    YAxis yAxis = new YAxis();
    yAxis.setRange(0, 15, 5);
    
    Chart chart = new Chart(new Date().toString());
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
