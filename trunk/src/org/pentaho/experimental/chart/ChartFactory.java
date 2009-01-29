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
package org.pentaho.experimental.chart;

import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.ChartSeriesDataLinkInfoFactory;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.resolver.StyleResolver;
import org.pentaho.reporting.libraries.css.resolver.impl.DefaultStyleResolver;

import java.net.URL;

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
    final ChartDocument chart = getChartDocument(chartURL);
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
}
