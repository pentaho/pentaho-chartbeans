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
 * @created Feb 25, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.ChartResult;
import org.pentaho.experimental.chart.plugin.api.IOutput;

/**
 * @author wseyler
 *
 */
public class PluginTest extends TestCase {
  private static final Object[][] dataArray = {{30, 20, 17}, {20, 40, 35}, {46, 35, 86}};
  
  private static final String PNG_SUFFIX = ".png"; //$NON-NLS-1$
  private static final String JPG_SUFFIX = ".jpeg"; //$NON-NLS-1$
  private static final String TEST_FILE_PATH = "test/test-output/TestChart"; //$NON-NLS-1$
  
  protected void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();   
  }
  
  public void testValidate() throws Exception {
    ChartXMLParser chartParser = new ChartXMLParser();
    URL chartXmlDocument = this.getClass().getResource("PluginTest.xml"); //$NON-NLS-1$
    ChartDocument chartDocument = chartParser.parseChartDocument(chartXmlDocument);
    if (chartDocument == null) {
      fail("A null document should never be returned"); //$NON-NLS-1$
    }
    
    IChartPlugin chartPlugin = ChartPluginFactory.getChartPlugin();
    ChartResult result = chartPlugin.validateChartDocument(chartDocument);
    assertEquals(result.getErrorCode(), IChartPlugin.RESULT_VALIDATED);
  }
  
  public void testRenderAsPng() throws Exception {
    IChartPlugin plugin = ChartPluginFactory.getChartPlugin("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    IOutput output = ChartPluginFactory.getChartOutput();
    // At this point we have an output of the correct type
    // Now we can manipulate it to meet our needs so that we get the correct
    // output location and type.
    
    // Now get the chart definition
    ChartXMLParser chartParser = new ChartXMLParser();
    URL chartXmlDocument = this.getClass().getResource("PluginTest.xml"); //$NON-NLS-1$
    ChartDocument chartDocument = chartParser.parseChartDocument(chartXmlDocument);
    if (chartDocument == null) {
      fail("A null document should never be returned"); //$NON-NLS-1$
    }
    
    // Now lets create some data
    ChartTableModel data = new ChartTableModel();
    data.setData(dataArray);
    output.setFilename(TEST_FILE_PATH + PNG_SUFFIX);
    output.setFileType(IOutput.FILE_TYPE_PNG);
    
    // Render and save the plot
    plugin.renderChartDocument(chartDocument, data, output);
    File chartFile = new File(output.getFilename());
    assertTrue(chartFile.exists());
    assertTrue(chartFile.length() > 5000);
  }
  
  public void testRenderAsJpeg() throws Exception {
    IChartPlugin plugin = ChartPluginFactory.getChartPlugin("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    IOutput output = ChartPluginFactory.getChartOutput();
    // At this point we have an output of the correct type
    // Now we can manipulate it to meet our needs so that we get the correct
    // output location and type.
    
    // Now get the chart definition
    ChartXMLParser chartParser = new ChartXMLParser();
    URL chartXmlDocument = this.getClass().getResource("PluginTest.xml"); //$NON-NLS-1$
    ChartDocument chartDocument = chartParser.parseChartDocument(chartXmlDocument);
    if (chartDocument == null) {
      fail("A null document should never be returned"); //$NON-NLS-1$
    }
    
    // Now lets create some data
    ChartTableModel data = new ChartTableModel();
    data.setData(dataArray);
    output.setFilename(TEST_FILE_PATH + JPG_SUFFIX);
    output.setFileType(IOutput.FILE_TYPE_JPEG);
    
    // Render and save the plot
    plugin.renderChartDocument(chartDocument, data, output);
    File chartFile = new File(output.getFilename());
    assertTrue(chartFile.exists());
    assertTrue(chartFile.length() > 5000);
  }
  
  public void testRenderAsPngStream() throws Exception {
    IChartPlugin plugin = ChartPluginFactory.getChartPlugin("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    IOutput output = ChartPluginFactory.getChartOutput();
    // At this point we have an output of the correct type
    // Now we can manipulate it to meet our needs so that we get the correct
    // output location and type.
    
    // Now get the chart definition
    ChartXMLParser chartParser = new ChartXMLParser();
    URL chartXmlDocument = this.getClass().getResource("PluginTest.xml"); //$NON-NLS-1$
    ChartDocument chartDocument = chartParser.parseChartDocument(chartXmlDocument);
    if (chartDocument == null) {
      fail("A null document should never be returned"); //$NON-NLS-1$
    }
    
    // Now lets create some data
    ChartTableModel data = new ChartTableModel();
    data.setData(dataArray);
    output.setFileType(IOutput.FILE_TYPE_PNG);
    
    // Render and save the plot
    plugin.renderChartDocument(chartDocument, data, output);
    
    ByteArrayOutputStream newOutputStream = (ByteArrayOutputStream) output.getChartAsStream();
    assertTrue(newOutputStream.toByteArray().length > 5000);
    
  }
  
  public void testRenderAsJpegStream() throws Exception {
    IChartPlugin plugin = ChartPluginFactory.getChartPlugin("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    IOutput output = ChartPluginFactory.getChartOutput();
    // At this point we have an output of the correct type
    // Now we can manipulate it to meet our needs so that we get the correct
    // output location and type.
    
    // Now get the chart definition
    ChartXMLParser chartParser = new ChartXMLParser();
    URL chartXmlDocument = this.getClass().getResource("PluginTest.xml"); //$NON-NLS-1$
    ChartDocument chartDocument = chartParser.parseChartDocument(chartXmlDocument);
    if (chartDocument == null) {
      fail("A null document should never be returned"); //$NON-NLS-1$
    }
    
    // Now lets create some data
    ChartTableModel data = new ChartTableModel();
    data.setData(dataArray);
    output.setFileType(IOutput.FILE_TYPE_JPEG);
    
    // Render and save the plot
    plugin.renderChartDocument(chartDocument, data, output);
    
    ByteArrayOutputStream newOutputStream = (ByteArrayOutputStream) output.getChartAsStream();
    assertTrue(newOutputStream.toByteArray().length > 5000);
    
  }

}
