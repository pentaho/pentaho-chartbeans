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
 * Created 4/14/2008 
 * @author David Kincade 
 */
package org.pentaho.chart;

import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.chart.core.ChartDocument;

/**
 * The implementation of the ChartDefinition interface. This class
 * will hold the objects used to load the chart definition and the related data.
 * @author David Kincade
 */
public class ChartDefinitionImpl implements ChartDefinition {

  /**
   * The key used with the resource manager to store/retrieve the chart document
   */
  private ResourceKey chartKey;

  /**
   * The resource manager used to store/retrieve the chart document
   */
  private ResourceManager resourceManager;

  /**
   * The chart document which defines the chart
   */
  private ChartDocument chartDocument;

  /**
   * The data that will be used with the chart document to create the chart
   */
  private ChartData data;

  /**
   * Constructs a ChartDefinition with the specified data and the chart document
   * @param data the data for the chart (<code>null</code> is acceptable)
   * @param doc the chart document used in generating the chart
   * @throws IllegalArgumentException indicates the chart document passed is <code>null</code>
   */
  public ChartDefinitionImpl(final ChartData data, final ChartDocument doc) throws IllegalArgumentException {
    if (doc == null) {
      throw new IllegalArgumentException();
    }
    this.chartDocument = doc;
    this.data = data;
    this.chartKey = this.chartDocument.getResourceKey();
    this.resourceManager = this.chartDocument.getResourceManager();
    if (this.resourceManager == null) {
      this.resourceManager = new ResourceManager();
      this.resourceManager.registerDefaults();
    }
  }

  /**
   * Constructs a ChartDefinition with the specified chart document
   * @param doc
   * @throws IllegalArgumentException indicates the chart document passed is <code>null</code>
   */
  public ChartDefinitionImpl(final ChartDocument doc) throws IllegalArgumentException {
    this(null, doc);
  }

  /**
   * Constructs a ChartDefinition by loading the chart information from a resource manager using the specified key.
   * @param data the data for the chart (<code>null</code> is acceptable)
   * @param manager the resource manager from which the chart definition resource should be retrieved
   *   (<code>null</code> is acceptable)
   * @param key the key used to retrieve the chart definition resource from the resource manager
   * @throws IllegalArgumentException indicates the resource key provided is <code>null</code>
   */
  public ChartDefinitionImpl(final ChartData data, final ResourceManager manager, final ResourceKey key) throws IllegalArgumentException, ResourceException {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    this.data = data;
    this.chartKey = key;
    this.resourceManager = manager;
    if (this.resourceManager == null) {
      this.resourceManager = new ResourceManager();
      this.resourceManager.registerDefaults();
    }
    final Resource resource = this.resourceManager.create(this.chartKey, null, ChartDocument.class);
    this.chartDocument = (ChartDocument)resource.getResource();
  }

  /**
   *
   * @param manager
   * @param key
   */
  public ChartDefinitionImpl(final ResourceManager manager, final ResourceKey key) throws IllegalArgumentException, ResourceException {
    this(null, manager, key);
  }

  /**
   * Sets the chart data
   * @param data the chart data
   */
  public void setData(ChartData data) {
    this.data = data;
  }

  /**
   * Returns the chart data
   * @return the chart data
   */
  public ChartData getData() {
    return data;
  }

  /**
   * Returns the resource key which is used to store the chart in the resource manager
   * @return the resource key which is used to store the chart in the resource manager
   */
  public ResourceKey getChartResourceKey() {
    return chartKey;
  }

  /**
   * Returns the resource manager in which the chart document is stored
   * @return the resource manager in which the chart document is stored
   */
  public ResourceManager getChartResourceManager() {
    return resourceManager;
  }

  /**
   * Returns the chart document which was set or loaded from the resource manager
   * @return the chart document which was set or loaded from the resource manager
   */
  public ChartDocument getChartDocument() {
    return chartDocument;
  }
}
