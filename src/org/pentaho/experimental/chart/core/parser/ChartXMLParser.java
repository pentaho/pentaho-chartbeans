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
 * Created 2/11/2008 
 * @author David Kincade 
 */
package org.pentaho.experimental.chart.core.parser;

import org.pentaho.experimental.chart.core.ChartDocument;
import org.jfree.resourceloader.ResourceException;
import org.jfree.resourceloader.Resource;
import org.jfree.resourceloader.ResourceManager;
import org.jfree.resourceloader.ResourceKey;

import java.net.URL;

/**
 * Parses the chart XML document into the internal representation of the chart document.
 * @author David Kincade
 */
public class ChartXMLParser {
  /**
   * The resource manager used to parse chart definitions
   */
  private final ResourceManager resourceManager;

  /**
   * Initializes the chart parser by creating a default <code>ResourceManager</code>
   */
  public ChartXMLParser() {
    resourceManager = new ResourceManager();
    resourceManager.registerDefaults();
  }

  /**
   * Initializes the chart parser with a <code>ResourceManager</code>
   * @param resourceManager the <code>ResourceManager</code> to use while parsing a chart definition
   */
  public ChartXMLParser(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  /**
   * Parses the chart definition file into a <code>ChartDocument</code>.
   * NOTE: this method is protected for unit testing
   *
   * @param chartDefinitionURL the location of the chart definition file
   * @return a <code>ChartDocument</code> that was created from the chart definition
   * @throws org.jfree.resourceloader.ResourceException indeicates an error occurred loading the chart defintion
   */
  public ChartDocument parseChartDocument(URL chartDefinitionURL) throws ResourceException {
    Resource res = resourceManager.createDirectly(chartDefinitionURL, ChartDocument.class);
    ResourceKey key = res.getSource();
    ChartDocument chart = (ChartDocument) res.getResource();
    chart.setResourceManager(resourceManager);
    chart.setResourceKey(key);
    return chart;
  }

  /**
   * Returns the <code>ResourceManager</code> used by this instance of the chart parser
   */
  public ResourceManager getResourceManager() {
    return resourceManager;
  }
}
