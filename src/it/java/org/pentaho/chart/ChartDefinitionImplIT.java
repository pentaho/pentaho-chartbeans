/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart;

import junit.framework.TestCase;
import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.chart.ChartDefinition;
import org.pentaho.chart.ChartDefinitionFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Tests for the <code>ChartDefinitionImpl class
 * @author David Kincade
 */
public class ChartDefinitionImplIT extends TestCase {
  public void testChartDefinitionImpl() throws ResourceException {
    // Setup data used with the test
    final ResourceManager testManager = new ResourceManager();
    testManager.registerDefaults();
    final Resource chartResource = testManager.createDirectly(this.getClass().getResource("ChartDefinitionImplTest.xml"), ChartDocument.class);
    final ResourceKey testKey = chartResource.getSource();
    final ChartDocument testDoc = (ChartDocument)chartResource.getResource();
    final ChartTableModel chartData = new ChartTableModel();

    // Create the chart definition from the chart document
    final ChartDefinition def1 = ChartDefinitionFactory.createChartDefinition(testDoc);
    assertNotNull(def1);
    assertNotNull(def1.getChartResourceKey());
    assertNotNull(def1.getChartResourceManager());
    assertNull(def1.getData());
    assertEquals(testDoc, def1.getChartDocument());

    // Create the chart definition from the chart document and the test data
    final ChartDefinition def2 = ChartDefinitionFactory.createChartDefinition(chartData, testDoc);
    assertNotNull(def2);
    assertNotNull(def2.getChartResourceKey());
    assertNotNull(def2.getChartResourceManager());
    assertEquals(chartData, def2.getData());
    assertEquals(testDoc, def1.getChartDocument());

    // Create the chart definition from the chart resource key
    final ChartDefinition def3 = ChartDefinitionFactory.createChartDefinition(testKey);
    assertNotNull(def3);
    assertEquals(testKey, def3.getChartResourceKey());
    assertNotNull(def3.getChartResourceManager());
    assertNotNull(def3.getChartDocument());
    assertNull(def3.getData());

    // Create the chart definition from the chart resource key and resource manager
    final ChartDefinition def4 = ChartDefinitionFactory.createChartDefinition(testKey, testManager);
    assertNotNull(def4);
    assertEquals(testKey, def4.getChartResourceKey());
    assertEquals(testManager, def4.getChartResourceManager());
    assertNotNull(def3.getChartDocument());
    assertNull(def3.getData());

    // FAIL: create the chart with a null key
    try {
      ChartDefinitionFactory.createChartDefinition((ResourceKey)null);
      fail("Should not accept a null key");
    } catch (IllegalArgumentException iae) {
      // success
    }

    // FAIL: create the chart with a null key and specifying the resource manager
    try {
      ChartDefinitionFactory.createChartDefinition((ResourceKey)null, testManager);
      fail("Should not accept a null key");
    } catch (IllegalArgumentException iae) {
      // success
    }

    // FAIL: create the chart with a null chart definition
    try {
      ChartDefinitionFactory.createChartDefinition((ChartDocument)null);
      fail("Should not accept a null chart definition");
    } catch (IllegalArgumentException iae) {
      // success
    }
  }
}
