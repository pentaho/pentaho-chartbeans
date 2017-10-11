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

package org.pentaho.chart;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.core.ChartSeriesDataLinkInfoFactory;
import org.pentaho.chart.core.parser.ChartXMLParser;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.StyledText;
import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;
import org.pentaho.chart.plugin.ChartProcessingException;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.chart.plugin.api.PersistenceException;
import org.pentaho.chart.plugin.api.IOutput.OutputTypes;
import org.pentaho.chart.plugin.jfreechart.JFreeChartPlugin;
import org.pentaho.chart.plugin.openflashchart.OpenFlashChartPlugin;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.color.ColorStyleKeys;
import org.pentaho.reporting.libraries.css.keys.font.FontStyleKeys;
import org.pentaho.reporting.libraries.css.resolver.StyleResolver;
import org.pentaho.reporting.libraries.css.resolver.impl.DefaultStyleResolver;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceKeyCreationException;

/**
 * API for generating charts
 *
 * @author David Kincade
 */
public class ChartFactory {
  private static final String UNIDENTIFIED = "Unable To Identify";

  private static List<IChartPlugin> chartPlugins = new ArrayList<IChartPlugin>();

  private ChartFactory() {
  }

  public static IChartPlugin getPlugin(String pluginId) {
    IChartPlugin plugin = null;
    for (IChartPlugin tmpPlugin : chartPlugins) {
      if (tmpPlugin.getPluginId().equals(pluginId)) {
        plugin = tmpPlugin;
      }
    }
    if ((plugin == null) && (chartPlugins.size() == 0)) {
      if (JFreeChartPlugin.PLUGIN_ID.equals(pluginId)) {
        plugin = new JFreeChartPlugin();
      } else if (OpenFlashChartPlugin.PLUGIN_ID.equals(pluginId)){
        plugin = new OpenFlashChartPlugin();
      }
    }
    return plugin;
  }

  /**
   *  This method is called from a platform system listener on startup,
   *  to initialize the available plugins from the chartbeans configuration file. 
   */
  public static void loadDefaultChartPlugins(List <IChartPlugin> plugins) {
	  chartPlugins = plugins;
  }


  /**
   * Creates a chart based on the chart definition
   * TODO: document / complete
   *
   * @param chartURL the URL of the chart definition
   * @throws ResourceException      indicates an error loading the chart resources
   * @throws InvalidChartDefinition indicates an error with chart definition
   */
  public static ChartDocumentContext generateChart(final URL chartURL) throws ResourceException {
    return ChartFactory.generateChart(chartURL, null);
  }

  public static ChartDocument getChartDocument(final URL chartURL) throws ResourceException {
    return getChartDocument(chartURL, true);
  }

  public static ChartDocument getChartDocument(final URL chartURL, boolean cascadeStyles) throws ResourceException {
    // Parse the chart
    final ChartXMLParser chartParser = new ChartXMLParser();
    final ChartDocument chart = chartParser.parseChartDocument(chartURL);

    if (cascadeStyles) {
      // Create a ChartDocumentContext
      final ChartDocumentContext cdc = new ChartDocumentContext(chart);

      // Resolve the style information
      ChartFactory.resolveStyles(chart, cdc);
    }

    return chart;
  }

  /**
   * Creats a chart based on the chart definition and the table model
   * TODO: document / complete
   *
   * @param chartURL the URL of the chart definition
   * @param tableModel the chart table model for this chart
   * @throws ResourceException      indicates an error loading the chart resources
   * @throws InvalidChartDefinition indicates an error with chart definition
   */
  public static ChartDocumentContext generateChart(final URL chartURL, final ChartTableModel tableModel)
      throws ResourceException {
    return generateChart(getChartDocument(chartURL), tableModel);
  }

  public static ChartDocumentContext generateChart(ChartDocument chart, final ChartTableModel tableModel)
      throws ResourceException {
    // Create a ChartDocumentContext
    final ChartDocumentContext cdc = new ChartDocumentContext(chart);
    // Link the series tags with the tabel model
    if (tableModel != null) {
      cdc.setDataLinkInfo(ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chart, tableModel));
    }
    // temporary
    return cdc;
  }

  /**
   * Returns the initialized <code>StyleResolver</code>.
   * NOTE: this method is protected for testing purposes only
   */
  protected static StyleResolver getStyleResolver(final ChartDocumentContext cdc) {
    final StyleResolver sr = new DefaultStyleResolver();
    sr.initialize(cdc);
    return sr;
  }

  /**
   * Resolves the style information for all the elements in the chart document
   *
   * @param chart the chart document to process
   * @param cdc   the chart document context used with the <code>StyleResolver</code>
   */
  protected static void resolveStyles(final ChartDocument chart, final ChartDocumentContext cdc) {
    // Get the style resolveer
    final StyleResolver sr = ChartFactory.getStyleResolver(cdc);

    // Resolve the style for all the nodes in the chart
    ChartElement element = chart.getRootElement();
    while (element != null) {
      // Resolve this element's style (if it hasn't been done before)
      if (element.isStyleResolved() == false) {
        sr.resolveStyle(element);
      }

      // Get the next element to process
      element = element.getNextDepthFirstItem();
    }
  }


  private static void setElementFont(ChartElement chartElement, String fontFamily, Integer fontSize,
      FontStyle fontStyle, FontWeight fontWeight) {
    LayoutStyle style = chartElement.getLayoutStyle();
    if (fontFamily != null) {
      style.setValue(FontStyleKeys.FONT_FAMILY, new CSSConstant(fontFamily));
    }
    if (fontSize != null) {
      style.setValue(FontStyleKeys.FONT_SIZE, CSSNumericValue.createValue(CSSNumericType.PX, fontSize));
    }
    if (fontStyle != null) {
      style.setValue(FontStyleKeys.FONT_STYLE, new CSSConstant(fontStyle.toString().toLowerCase()));
    }
    if (fontWeight != null) {
      style.setValue(FontStyleKeys.FONT_WEIGHT, new CSSConstant(fontWeight.toString().toLowerCase()));
    }
  }

  private static ChartElement createTextElement(String elementName, StyledText styledText) {
    ChartElement chartElement = null;
    if ((styledText != null) && (styledText.getText() != null)) {
      chartElement = new ChartElement();
      chartElement.setTagName(elementName);
      chartElement.setText(styledText.getText());
      LayoutStyle style = chartElement.getLayoutStyle();
      setElementFont(chartElement, styledText.getFontFamily(), styledText.getFontSize(), styledText.getFontStyle(),
          styledText.getFontWeight());
      if (styledText.getColor() != null) {
        style.setValue(ColorStyleKeys.COLOR, new CSSColorValue(new Color(styledText.getColor() & 0x00FFFFFF)));
      }
    }
    return chartElement;
  }

}
