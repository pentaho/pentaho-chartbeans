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
 * Created 4/16/2008 
 * @author David Kincade 
 */
package org.pentaho.experimental.chart;

import junit.framework.TestCase;
import org.jfree.resourceloader.Resource;
import org.jfree.resourceloader.ResourceException;
import org.jfree.resourceloader.ResourceKey;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.data.ChartTableModel;

/**
 * Tests for the <code>ChartDefinitionImpl class
 * @author David Kincade
 */
public class ChartDefinitionImplTest extends TestCase {
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
    assertNull(def1.getChartResourceKey());
    assertNotNull(def1.getChartResourceManager());
    assertNull(def1.getData());
    assertEquals(testDoc, def1.getChartDocument());

    // Create the chart definition from the chart document and the test data
    final ChartDefinition def2 = ChartDefinitionFactory.createChartDefinition(chartData, testDoc);
    assertNotNull(def2);
    assertNull(def2.getChartResourceKey());
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
