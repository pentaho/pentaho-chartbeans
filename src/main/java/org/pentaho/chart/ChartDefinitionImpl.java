/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

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
