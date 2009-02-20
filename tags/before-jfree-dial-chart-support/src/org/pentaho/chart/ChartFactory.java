/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created 2/7/2008 
 * @author David Kincade 
 */
package org.pentaho.chart;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.core.ChartSeriesDataLinkInfoFactory;
import org.pentaho.chart.core.parser.ChartXMLParser;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartAreaStyle;
import org.pentaho.chart.css.styles.ChartBarStyle;
import org.pentaho.chart.css.styles.ChartOrientationStyle;
import org.pentaho.chart.css.styles.ChartSeriesType;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.model.CategoricalAreaPlot;
import org.pentaho.chart.model.CategoricalBarPlot;
import org.pentaho.chart.model.CategoricalLinePlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.Graph;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.Plot;
import org.pentaho.chart.model.Series;
import org.pentaho.chart.model.PiePlot.Wedge;
import org.pentaho.chart.model.Plot.Orientation;
import org.pentaho.chart.plugin.ChartPluginFactory;
import org.pentaho.chart.plugin.ChartProcessingException;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.chart.plugin.api.PersistenceException;
import org.pentaho.chart.plugin.api.IOutput.OutputTypes;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.color.ColorStyleKeys;
import org.pentaho.reporting.libraries.css.resolver.StyleResolver;
import org.pentaho.reporting.libraries.css.resolver.impl.DefaultStyleResolver;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSStringType;
import org.pentaho.reporting.libraries.css.values.CSSStringValue;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceKeyCreationException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

/**
 * API for generating charts
 *
 * @author David Kincade
 */
public class ChartFactory {
  private static ResourceManager resourceManager = null;

  private ChartFactory() {
  }

  /**
   * Creats a chart based on the chart definition
   * TODO: document / complete
   *
   * @param chartURL the URL of the chart definition
   * @throws ResourceException      indicates an error loading the chart resources
   * @throws InvalidChartDefinition indicates an error with chart definition
   */
  public static ChartDocumentContext generateChart(final URL chartURL) throws ResourceException {
    return ChartFactory.generateChart(chartURL, null);
  }

  public static ChartDocument getChartDocument(final URL chartURL)  throws ResourceException {
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
  public static ChartDocumentContext generateChart(final URL chartURL, final ChartTableModel tableModel) throws ResourceException {
    return generateChart(getChartDocument(chartURL), tableModel);
  }

  public static ChartDocumentContext generateChart(ChartDocument chart, final ChartTableModel tableModel) throws ResourceException {
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
  private static final String ROW_NAME = "row-name"; //$NON-NLS-1$
  
  public static InputStream createChart(Object[][] queryResults, ChartModel chartModel, int width, int height, OutputTypes outputType) throws ChartProcessingException, SQLException, ResourceKeyCreationException, PersistenceException {
    int rangeColumnIndex = 0;
    int domainColumnIndex = -1;
    int categoryColumnIndex = -1;
    
    if ((queryResults.length > 0) && (queryResults[0].length > 1)) {
      domainColumnIndex = 1;
    }
    
    if ((queryResults.length > 0) && (queryResults[0].length > 2)) {
      categoryColumnIndex = 2;
    }
    return createChart(queryResults, rangeColumnIndex, domainColumnIndex, categoryColumnIndex, chartModel, width, height, outputType, null);
  }
  
  public static InputStream createChart(Object[][] queryResults, int rangeColumnIndex, int domainColumnIdx, int categoryColumnIdx, ChartModel chartModel, int width, int height, OutputTypes outputType, ChartThemeFactory chartThemeFactory) throws ChartProcessingException, SQLException, ResourceKeyCreationException, PersistenceException {
    ChartTableModel chartTableModel = createChartTableModel(queryResults, categoryColumnIdx, domainColumnIdx, rangeColumnIndex);

    ChartDocument themeDocument = null;
    if ((chartThemeFactory != null) && (chartModel.getTheme() != null)) {
      themeDocument = chartThemeFactory.getThemeDocument(chartModel.getTheme());
    }
    ChartDocument chartDocument = createChartDocument(chartModel, themeDocument);
    ChartDocumentContext chartDocumentContext = new ChartDocumentContext(chartDocument);

    IChartPlugin plugin = ChartPluginFactory.getInstance("org.pentaho.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    IOutput output = plugin.renderChartDocument(chartDocumentContext, chartTableModel);

    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ByteArrayInputStream inputStream = null;
    try {
      output.persistChart(outputStream, outputType, width, height);
      inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    } finally {
    }
    return inputStream;
  }
  
  public static ChartTableModel createChartTableModel(Object[][] queryResults, int categoryColumn, int domainColumn, int rangeColumn) {
    ChartTableModel chartTableModel = new ChartTableModel();
    
    SortedSet<String> categories = new TreeSet<String>();
    SortedSet<String> domains = new TreeSet<String>();

    for (int i = 0; i < queryResults.length; i++) {
      if (categoryColumn >= 0) {
        categories.add(queryResults[i][categoryColumn] != null ? queryResults[i][categoryColumn]
            .toString() : "null");
      }

      if (domainColumn >= 0) {
        domains.add(queryResults[i][domainColumn] != null ? queryResults[i][domainColumn]
            .toString() : "null");
      }
    }

    List<String> categoriesList = new ArrayList<String>(categories);
    List<String> domainsList = new ArrayList<String>(domains);

    if (categoriesList.size() == 0) {
      categoriesList.add("dummyCategory");
    }

    if (domainsList.size() == 0) {
      domainsList.add("dummyDomain");
    }

    Number[][] chartData = new Number[domainsList.size()][categoriesList.size()];

    for (int i = 0; i < queryResults.length; i++) {
      String domainName = null;
      if (domainColumn != -1) {
        domainName = (queryResults[i][domainColumn] != null ? queryResults[i][domainColumn]
            .toString() : "null");
      } else {
        domainName = "dummyDomain";
      }
      int chartDataRow = domainsList.indexOf(domainName.toString());

      String categoryName = null;
      if (categoryColumn != -1) {
        categoryName = (queryResults[i][categoryColumn] != null ? queryResults[i][categoryColumn]
            .toString() : "null");
      } else {
        categoryName = "dummyCategory";
      }
      int chartDataColumn = categoriesList.indexOf(categoryName.toString());

      if (chartData[chartDataRow][chartDataColumn] == null) {
        chartData[chartDataRow][chartDataColumn] = (Number) queryResults[i][rangeColumn];
      } else {
        chartData[chartDataRow][chartDataColumn] = chartData[chartDataRow][chartDataColumn].doubleValue()
            + ((Number) queryResults[i][rangeColumn]).doubleValue();
      }
    }

    chartTableModel.setData(chartData);
    for (int i = 0; i < categoriesList.size(); i++) {
      chartTableModel.setColumnName(i, categoriesList.get(i));
    }

    for (int i = 0; i < domainsList.size(); i++) {
      chartTableModel.setRowMetadata(i, ROW_NAME, domainsList.get(i));
      chartTableModel.setRowName(i, domainsList.get(i));
    }
    return chartTableModel;
  }
  
  private static ChartDocument createChartDocument(ChartModel chartModel, ChartDocument themeDocument) {
    ChartElement rootElement = new ChartElement();
    rootElement.setTagName(ChartElement.TAG_NAME_CHART);

    ChartElement chartElement = new ChartElement();
    chartElement.setTagName(ChartElement.TAG_NAME_TITLE);
    chartElement.setText(chartModel.getTitle() != null ? chartModel.getTitle() : "");
    rootElement.addChildElement(chartElement);

    ChartElement[] seriesThemes = null;
    if (themeDocument != null) {
      seriesThemes = themeDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    } else {
      seriesThemes = new ChartElement[0];
    }

    Plot plot = chartModel.getPlot();
    if (plot instanceof Graph) {
      Graph categoricalPlot = (Graph) plot;

      chartElement = new ChartElement();
      chartElement.setTagName("rangeLabel");
      chartElement.setText(categoricalPlot.getValueAxisLabel() != null ? categoricalPlot.getValueAxisLabel() : "");
      rootElement.addChildElement(chartElement);

      chartElement = new ChartElement();
      chartElement.setTagName("domainLabel");
      chartElement
          .setText(categoricalPlot.getCategoryAxisLabel() != null ? categoricalPlot.getCategoryAxisLabel() : "");
      rootElement.addChildElement(chartElement);

      for (int i = 0; i < categoricalPlot.getSeries().size(); i++) {
        LayoutStyle themeStyle = (i < seriesThemes.length ? seriesThemes[i].getLayoutStyle() : null);
        if (categoricalPlot instanceof CategoricalBarPlot) {
          rootElement.addChildElement(createBarPlotSeriesElement(categoricalPlot.getSeries().get(i), i, themeStyle));
        } else if (categoricalPlot instanceof CategoricalLinePlot) {
          rootElement.addChildElement(createLinePlotSeriesElement(categoricalPlot.getSeries().get(i), i, themeStyle));
        } else if (categoricalPlot instanceof CategoricalAreaPlot) {
          rootElement.addChildElement(createAreaPlotSeriesElement(categoricalPlot.getSeries().get(i), i, themeStyle));
        }
      }

      chartElement = new ChartElement();
      chartElement.setTagName(ChartElement.TAG_NAME_PLOT);
      CSSConstant chartOrientationStyle = categoricalPlot.getOrientation() == Orientation.HORIZONTAL ? ChartOrientationStyle.HORIZONTAL
          : ChartOrientationStyle.VERTICAL;
      chartElement.getLayoutStyle().setValue(ChartStyleKeys.ORIENTATION, chartOrientationStyle);
      chartElement.getLayoutStyle().setValue(ChartStyleKeys.DRILL_URL,
          new CSSStringValue(CSSStringType.URI, "http://localhost:8080/Pentaho/JPivot"));
      chartElement.getLayoutStyle().setValue(ChartStyleKeys.SCALE_NUM,
          CSSNumericValue.createValue(CSSNumericType.NUMBER, 1));
      rootElement.addChildElement(chartElement);
    } else if (plot instanceof PiePlot) {
      PiePlot piePlot = (PiePlot) plot;
      for (int i = 0; i < piePlot.getWedges().size(); i++) {
        LayoutStyle themeStyle = (i < seriesThemes.length ? seriesThemes[i].getLayoutStyle() : null);
        rootElement.addChildElement(createPieSeriesElement(piePlot.getWedges().get(i), i, themeStyle));
      }

      chartElement = new ChartElement();
      chartElement.setTagName(ChartElement.TAG_NAME_PLOT);
      chartElement.getLayoutStyle().setValue(ChartStyleKeys.DRILL_URL,
          new CSSStringValue(CSSStringType.URI, "http://localhost:8080/Pentaho/JPivot"));
      chartElement.getLayoutStyle().setValue(ChartStyleKeys.SCALE_NUM,
          CSSNumericValue.createValue(CSSNumericType.NUMBER, 1));

      ChartElement datasetElement = new ChartElement();
      datasetElement.setTagName("dataset");
      datasetElement.setAttribute("type", "pie");

      chartElement.addChildElement(datasetElement);
      rootElement.addChildElement(chartElement);
    }

    ChartDocument chartDocument = new ChartDocument(rootElement);
    ResourceManager resourceManager = new ResourceManager();
    resourceManager.registerDefaults();
    chartDocument.setResourceManager(resourceManager);
    return chartDocument;
  }
  
  private static ChartElement createPieSeriesElement(Wedge wedge, int seriesIndex, LayoutStyle themeStyle) {
    ChartElement chartElement = new ChartElement();
    chartElement.setTagName(ChartElement.TAG_NAME_SERIES);
    Color color = themeStyle != null ? (Color) themeStyle.getValue(ColorStyleKeys.COLOR) : null;
    if (color != null) {
      chartElement.getLayoutStyle().setValue(ColorStyleKeys.COLOR, new CSSColorValue(color));
    }
    chartElement.getLayoutStyle().setValue(ChartStyleKeys.CHART_TYPE, ChartSeriesType.PIE);
    return chartElement;
  }

  private static ChartElement createBarPlotSeriesElement(Series series, int seriesIndex, LayoutStyle themeStyle) {
    ChartElement chartElement = new ChartElement();
    chartElement.setTagName(ChartElement.TAG_NAME_SERIES);
    chartElement.setAttribute(ChartElement.COLUMN_POSITION, seriesIndex);
    Color color = themeStyle != null ? (Color) themeStyle.getValue(ColorStyleKeys.COLOR) : null;
    if (color != null) {
      chartElement.getLayoutStyle().setValue(ColorStyleKeys.COLOR, new CSSColorValue(color));
    }
    chartElement.getLayoutStyle().setValue(ChartStyleKeys.CHART_TYPE, ChartSeriesType.BAR);
    chartElement.getLayoutStyle().setValue(ChartStyleKeys.BAR_STYLE, ChartBarStyle.BAR);
    chartElement.getLayoutStyle().setValue(ChartStyleKeys.BAR_MAX_WIDTH,
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 10));
    return chartElement;
  }

  private static ChartElement createLinePlotSeriesElement(Series series, int seriesIndex, LayoutStyle themeStyle) {
    ChartElement chartElement = new ChartElement();
    chartElement.setTagName(ChartElement.TAG_NAME_SERIES);
    Color color = themeStyle != null ? (Color) themeStyle.getValue(ColorStyleKeys.COLOR) : null;
    if (color != null) {
      chartElement.getLayoutStyle().setValue(ColorStyleKeys.COLOR, new CSSColorValue(color));
    }
    chartElement.getLayoutStyle().setValue(ChartStyleKeys.CHART_TYPE, ChartSeriesType.LINE);
    chartElement.getLayoutStyle()
        .setValue(ChartStyleKeys.LINE_WIDTH, CSSNumericValue.createValue(CSSNumericType.PX, 1));
    return chartElement;
  }

  private static ChartElement createAreaPlotSeriesElement(Series series, int seriesIndex, LayoutStyle themeStyle) {
    ChartElement chartElement = new ChartElement();
    chartElement.setTagName(ChartElement.TAG_NAME_SERIES);
    Color color = themeStyle != null ? (Color) themeStyle.getValue(ColorStyleKeys.COLOR) : null;
    if (color != null) {
      chartElement.getLayoutStyle().setValue(ColorStyleKeys.COLOR, new CSSColorValue(color));
    }
    chartElement.getLayoutStyle().setValue(ChartStyleKeys.CHART_TYPE, ChartSeriesType.AREA);
    chartElement.getLayoutStyle().setValue(ChartStyleKeys.AREA_STYLE, ChartAreaStyle.AREA);
    return chartElement;
  }
}
