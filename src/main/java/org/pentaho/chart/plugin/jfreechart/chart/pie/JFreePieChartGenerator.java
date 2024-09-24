/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.plugin.jfreechart.chart.pie;

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
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartItemLabelVisibleType;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSStringValue;
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

  protected JFreeChart doCreateChart(final ChartDocumentContext chartDocContext, 
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
    final ChartElement plotElement =  chartDocument.getPlotElement();
    setLabelPlacingInsideChart(piePlot, plotElement);
    setSeriesAttributes(piePlot, chartDocument, data);    
    setStartAngle(piePlot, chartDocument.getPlotElement());
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
    generateSectionLabels(piePlot, seriesElements);
  }

  private void setSeriesPaint(final PiePlot plot,
                              final ChartElement[] seriesElements,
                              final ChartTableModel data) {
    final int length = seriesElements.length;
    for (int i = 0; i < length; i++) {
      final ChartElement seriesElement = seriesElements[i];
     final Paint paint = getPaintFromSeries(seriesElement);
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
   * @param seriesElements - Array of all the series elements defined in the chart definition document.
   */
  private void generateSectionLabels(final PiePlot piePlot,
                                     final ChartElement[] seriesElements) {

    final int length = seriesElements.length;
    PieSectionLabelGenerator generator = null;
    for (int i = 0; i < length; i++) {
      final ChartElement seriesElement = seriesElements[i];
      final LayoutStyle layoutStyle = seriesElement.getLayoutStyle();
      /*
       * NOTE: The message format can have following codes: {0} {1} {2} {3}.
       * For eg: {1} = {2}, {1} val {2}, {1} =>> {3} etc are okay.
       *      : {1xyz} {2  } is not okay.
       * Anything outside of it would not be formatted correctlt by StandardPieSectionLabelGenerator
       */
      CSSValue cssValue = layoutStyle.getValue(ChartStyleKeys.ITEM_LABEL_TEXT);
      final String messageFormat = cssValue != null ? ((CSSStringValue)cssValue).getValue() : null;

      if (messageFormat != null) {
        generator = new StandardPieSectionLabelGenerator(messageFormat, new DecimalFormat("0.00"), new DecimalFormat("0.00%"));//$NON-NLS-1$ //$NON-NLS-2$
        break;
      } else {
        generator = new StandardPieSectionLabelGenerator();
      }
    }
    if (generator != null) {
      piePlot.setLabelGenerator(generator);
    }
  }

  /**
   * Places the label inside or outside the chart.
   * <p/> 
   * @param piePlot -- PiePlot for the current Pie Chart.
   * @param plotElement -- Plot Element from the current chart document.
   */
  private void setLabelPlacingInsideChart(final PiePlot piePlot,
                                          final ChartElement plotElement) {
    final LayoutStyle layoutStyle = plotElement.getLayoutStyle();
    final CSSValue inside = layoutStyle.getValue(ChartStyleKeys.PIE_LABELS_INSIDE_CHART);
    if (ChartItemLabelVisibleType.VISIBLE.equals(inside)) {
      piePlot.setSimpleLabels(true);
    }
  }

  private void setStartAngle(final PiePlot piePlot, ChartElement plotElement) {
    final LayoutStyle layoutStyle = plotElement.getLayoutStyle();
    final CSSValue startAngle = layoutStyle.getValue(ChartStyleKeys.PIE_START_ANGLE);
    if (startAngle instanceof CSSNumericValue) {
      piePlot.setStartAngle(((CSSNumericValue)startAngle).getValue());
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
      if (pieExplodePercent != null) {
        String percentStr = pieExplodePercent.getCSSText();
        percentStr = percentStr.substring(0, percentStr.indexOf("%")); //$NON-NLS-1$
        double percent;
        try {
          percent = Double.parseDouble(percentStr)/100;
        } catch (NumberFormatException ne) {
          logger.warn(Messages.getString("JFreePieChartGenerator.WARN_EXPLODE_PERCENT_NOT_DEFINED_CORRECTLY", percentStr, null)); //$NON-NLS-1$
          //Setting the default to 10%
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
  }
} // Class ends here 
