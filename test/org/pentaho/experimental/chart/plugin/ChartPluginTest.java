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
 * @created Feb 28, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin;

import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin;
import org.pentaho.experimental.chart.plugin.jfreechart.outputs.JFreeChartOutput;

import junit.framework.TestCase;

/**
 * @author wseyler
 *
 */
public class ChartPluginTest extends TestCase {
  protected void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();   
  }

  public void testFactory() {
    IChartPlugin plugin = ChartPluginFactory.getChartPlugin("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    assertTrue(plugin instanceof AbstractChartPlugin);
    assertTrue(plugin instanceof JFreeChartPlugin);
    
    plugin = ChartPluginFactory.getChartPlugin();
    assertTrue(plugin instanceof AbstractChartPlugin);
    assertTrue(plugin instanceof JFreeChartPlugin);
    
    IOutput output = ChartPluginFactory.getChartOutput();
    assertNotNull(output);
    assertTrue(output instanceof JFreeChartOutput);
  }
}
