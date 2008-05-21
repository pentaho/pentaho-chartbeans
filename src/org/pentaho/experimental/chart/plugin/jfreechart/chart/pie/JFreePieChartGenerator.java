package org.pentaho.experimental.chart.plugin.jfreechart.chart.pie;

import java.awt.Font;
import java.awt.Paint;
import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.messages.Messages;

/**
 * Generates Pie Charts based on the configuration/options defined in the chart document.
 * </p>
 * Author: Ravi Hasija
 * Date: May 20, 2008
 * Time: 4:09:52 PM
 */
public class JFreePieChartGenerator extends JFreeChartGenerator {
  private static final Log logger = LogFactory.getLog(JFreePieChartGenerator.class);

  public JFreeChart createChart(final ChartDocumentContext chartDocContext, 
                                final ChartTableModel data) {
    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final String title = getTitle(chartDocument);
    final boolean legend = getShowLegend(chartDocument);
    final boolean toolTips = getShowToolTips(chartDocument);

    final DefaultPieDataset defaultPieDataset = datasetGeneratorFactory.createDefaultPieDataset(chartDocContext, data);
    if (defaultPieDataset == null) {
      logger.error(Messages.getErrorString("JFreeChartFactoryEngine.ERROR_0001_DATASET_IS_NULL")); //$NON-NLS-1$
      return null;
    }

    final JFreeChart chart = ChartFactory.createPieChart(title, defaultPieDataset, legend, toolTips, false);
    final PiePlot piePlot = (PiePlot) chart.getPlot();
    setPlotAttributes(piePlot, chartDocument, data);
    return chart;
  }

  /**
   * Set Plot attributes for the Pie Chart
   * </p>
   * @param piePlot        The pie plot retrieved from the chart object.
   * @param chartDocument  Chart document for the current chart definition.
   * @param data           Actual data.
   */
  private void setPlotAttributes(final PiePlot piePlot,
                                 final ChartDocument chartDocument,
                                 final ChartTableModel data) {
    piePlot.setNoDataMessage("No data available"); //$NON-NLS-1$
    piePlot.setCircular(false);
    piePlot.setLabelGap(0.02);
    generateSectionLabels(piePlot,3);
    setLabelPlacingInsideChart(piePlot, true);
    setSeriesAttributes(piePlot, chartDocument, data);    
  }

  /**
   * Main method for setting ALL the series attributes for the pie chart.
   * This method is a starting method for calling all the other helper methods.
   *
   * @param piePlot       - PiePlot for the current Pie Chart.
   * @param chartDocument - ChartDocument that defines what the series should look like.
   * @param data          - Actual data.
   */
  private void setSeriesAttributes(final PiePlot piePlot, final ChartDocument chartDocument, final ChartTableModel data) {
    final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    setSeriesPaint(piePlot, seriesElements, data);
    setLabelFont(piePlot, seriesElements);
    setExplode(piePlot, seriesElements, data);
  }

  private void setSeriesPaint(final PiePlot plot,
                              final ChartElement[] seriesElements,
                              final ChartTableModel data) {
    final int length = seriesElements.length;
    for (int i = 0; i < length; i++) {
      final ChartElement seriesElement = seriesElements[i];
     final Paint paint = JFreeChartUtils.getPaintFromSeries(seriesElement);
      if (paint != null) {
        final Comparable rowName = data.getRowName(i);
        if (rowName != null) {
          plot.setSectionPaint(rowName, paint);
        }
      }
    }
  }

  /**
   * Set the font for the labels on the chart.
   * </p>
   * @param piePlot        - PiePlot for the current Pie Chart.
   * @param seriesElements - Array of all the series elements defined in the chart definition document.
   */
  private void setLabelFont(final PiePlot piePlot,
                            final ChartElement[] seriesElements) {
    final int length = seriesElements.length;
    for (int i = 0; i < length; i++) {
      final ChartElement seriesElement = seriesElements[i];
      final Font font = JFreeChartUtils.getFont(seriesElement);
      if (font != null) {
        piePlot.setLabelFont(font);
        break;
      }
    }
  }

  /**
   * Generates the labels for the chart along with the formatting.
   * </p>
   * @param piePlot -- PiePlot for the current Pie Chart.
   * @param style   -- Defines the formatting for the labels.
   */
  private void generateSectionLabels(final PiePlot piePlot,
                                     final int style) {

    final PieSectionLabelGenerator generator;
    if (style == 1) {
      generator = new StandardPieSectionLabelGenerator("{1}"); //$NON-NLS-1$ 
    } else if (style == 2) {
      generator = new StandardPieSectionLabelGenerator("{2}", new DecimalFormat("0"), new DecimalFormat("0.00%")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    } else if (style == 3) {
      generator = new StandardPieSectionLabelGenerator("{1} = {2}", new DecimalFormat("0.00"), new DecimalFormat("0.00%")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    } else {
      generator = new StandardPieSectionLabelGenerator();
    }
    piePlot.setLabelGenerator(generator);
  }

  /**
   * Places the label inside or outside the chart.
   * <p/> 
   * @param piePlot -- PiePlot for the current Pie Chart.
   * @param inside  -- true if the label needs to be placed inside the chart, false otherwise.
   */
  private void setLabelPlacingInsideChart(final PiePlot piePlot,
                                          final boolean inside) {
    if (inside) {
      piePlot.setSimpleLabels(true);
    }
  }

  /**
   * Sets the explode percent for each key/section in the plot.
   * </p>
   * @param piePlot        -- PiePlot for the current Pie Chart.
   * @param seriesElements -- Array of series elements.
   * @param data           -- Actual data. 
   */
  private void setExplode(final PiePlot piePlot,
                          final ChartElement[] seriesElements,
                          final ChartTableModel data) {
    final int length = seriesElements.length;
    for (int i = 0; i < length; i++) {
      final ChartElement seriesElement = seriesElements[i];
      final LayoutStyle layoutStyle = seriesElement.getLayoutStyle();
      final CSSValue pieExplodePercent = layoutStyle.getValue(ChartStyleKeys.PIE_EXPLODE_PERCENT);
      String percentStr = pieExplodePercent.getCSSText();
      percentStr = percentStr.substring(0, percentStr.indexOf("%")); //$NON-NLS-1$
      double percent;
      try {
        percent = Double.parseDouble(percentStr)/100;
      } catch (NumberFormatException ne) {
        logger.warn(Messages.getString("JFreePieChartGenerator.WARN_EXPLODE_PERCENT_NOT_DEFINED_CORRECTLY", percentStr, null)); //$NON-NLS-1$
        //Setting the default to 10D
        percent = 0.1D;
      }

      if (percent > 0) {
        final int rowPos = JFreeChartUtils.getSeriesRow(seriesElement, i);
        final Comparable key = data.getRowName(rowPos);
        if (key != null) {
          piePlot.setExplodePercent(key, percent);
        }
      }
    }
  }
} // Class ends here 
