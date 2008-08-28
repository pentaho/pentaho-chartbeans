package org.pentaho.experimental.chart.plugin.jfreechart;

import java.io.Serializable;

import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartSeriesType;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.IChartPlugin;
import org.pentaho.experimental.chart.plugin.api.ChartResult;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.area.JFreeAreaChartGeneratorFactory;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.bar.JFreeBarChartGeneratorFactory;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.line.JFreeLineChartGeneratorFactory;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.multi.JFreeMultiChartGeneratorFactory;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.pie.JFreePieChartGeneratorFactory;
import org.pentaho.experimental.chart.plugin.jfreechart.outputs.JFreeChartOutput;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.messages.Messages;

public class JFreeChartFactoryEngine implements Serializable {

  private static final long serialVersionUID = -1079376910255750394L;

  public JFreeChartFactoryEngine(){
  }

  public IOutput makeChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext, final ChartResult chartResult) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final CSSConstant currentChartType = determineChartType(chartDocument);
    if (currentChartType == ChartSeriesType.UNDEFINED) {
      chartResult.setErrorCode(IChartPlugin.ERROR_INDETERMINATE_CHART_TYPE);
      chartResult.setDescription(Messages.getErrorString("JFreeChartPlugin.ERROR_0001_CHART_TYPE_INDETERMINABLE")); //$NON-NLS-1$
    }

    if (currentChartType == ChartSeriesType.BAR) {
      try {
        final JFreeChart chart = makeBarChart(data, chartDocumentContext);
        return new JFreeChartOutput(chart);
      } catch (Exception e) {
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.LINE) {
      try {
        return new JFreeChartOutput(makeLineChart(data, chartDocumentContext));
      } catch (Exception e) {
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.AREA) {
      try {
        return new JFreeChartOutput((makeAreaChart(data, chartDocumentContext)));
      } catch (Exception e) {
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.PIE) {
      try {
        return new JFreeChartOutput((makePieChart(data, chartDocumentContext)));
      } catch (Exception e) {
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.MULTI) {
      try {
        return new JFreeChartOutput((makeMultiChart(data, chartDocumentContext)));
      } catch (Exception e) {
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public JFreeChart makeAreaChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createAreaChartSubtype(chartDocumentContext, data);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument);
    return chart;
  }

  private JFreeChart createAreaChartSubtype(final ChartDocumentContext chartDocumentContext, final ChartTableModel data) {
    final JFreeAreaChartGeneratorFactory chartFacEngine = new JFreeAreaChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public JFreeChart makePieChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    final JFreePieChartGeneratorFactory chartFacEngine = new JFreePieChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeBarChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public JFreeChart makeBarChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext)
      throws Exception {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createBarChartSubtype(chartDocumentContext, data);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument);

    return chart;
  }

  private JFreeChart createBarChartSubtype(final ChartDocumentContext chartDocumentContext, final ChartTableModel data) {
    final JFreeBarChartGeneratorFactory chartFacEngine = new JFreeBarChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public JFreeChart makeLineChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext)
      throws Exception {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createLineChartSubtype(chartDocumentContext, data);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument);
    
    return chart;
  }

 /**
   * @param chartDocumentContext
   * @param data
   * @return the newly created JFreeChart object
   */
  private JFreeChart createLineChartSubtype(ChartDocumentContext chartDocumentContext, ChartTableModel data) {
    final JFreeLineChartGeneratorFactory chartFacEngine = new JFreeLineChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.experimental.chart.data.ChartTableModel, org.pentaho.experimental.chart.core.ChartDocument, org.pentaho.experimental.chart.plugin.api.IOutput)
   */
  public JFreeChart makeMultiChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createMultiChartSubtype(chartDocumentContext, data);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument);
    return chart;
  }

  private JFreeChart createMultiChartSubtype(final ChartDocumentContext chartDocumentContext, final ChartTableModel data) {
    final JFreeMultiChartGeneratorFactory chartFacEngine = new JFreeMultiChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }
  /**
   * Determines what type of chart that should be rendered.  It is possible that this method
   * could somehow be moved up into the AbstractChartPlugin
   *
   * @param chartDocument that defines what type of chart to use
   * @return a ChartType that represents the type of chart the chartDocument is requesting.
   */
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
        } else if (value.equals(ChartSeriesType.MULTI)) {
          return ChartSeriesType.MULTI;
        }
      }
    }
    return ChartSeriesType.UNDEFINED;
  }
}
