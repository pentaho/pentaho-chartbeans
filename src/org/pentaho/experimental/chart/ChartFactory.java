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

import org.jfree.resourceloader.ResourceException;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.reporting.libraries.css.resolver.StyleResolver;
import org.pentaho.reporting.libraries.css.resolver.impl.DefaultStyleResolver;
import org.pentaho.reporting.libraries.css.dom.StyleReference;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;

import java.net.URL;

/**
 * API for generating charts
 *
 * @author David Kincade
 */
public class ChartFactory {
  /**
   * Creats a chart based on the chart definition
   * TODO: document / complete
   *
   * @param chartURL the URL of the chart definition
   * @throws ResourceException indicates an error loading the chart resources
   * @throws InvalidChartDefinition indicates an error with chart definition
   */
  public static void generateChart(URL chartURL) throws ResourceException {
    // Create a ResourceManager
    ResourceManager resourceManager = new ResourceManager();
    resourceManager.registerDefaults();

    // Parse the chart
    ChartXMLParser chartParser = new ChartXMLParser(resourceManager);
    ChartDocument cd = chartParser.parseChartDocument(chartURL);

    // Create a ChartDocumentContext
    ChartDocumentContext cdc = new ChartDocumentContext(resourceManager, cd);

    // Get the style sheet information
    StyleReference[] styleReferences = cdc.getStyleReferences();

    // Resolve the style information 
    StyleResolver sr = new DefaultStyleResolver();
    sr.initialize(cdc);
  }
}
