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
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.ChartResult;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;

/**
 * @author wseyler
 */
public class PluginTest extends TestCase {
//  private static final Object[][] dataArray = {{10, 5,  15, "North America",  "Canada",   "East"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {11, 17, 38, "North America",  "Canada",   "West"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {12, 14, 55, "North America",  "USA",      "East"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {42, 35, 51, "North America",  "USA",      "West"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {41, 28, 19, "Asia",           "Russia",   "East"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {43, 29, 50, "Asia",           "Russia",   "West"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {49, 3,  4,  "Asia",           "China",    "East"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {28, 2,  0,  "Asia",           "China",    "West"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {31, 36, 13, "South America",  "Brazil",   "East"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$ 
//                                               {22, 33, 44, "South America",  "Brazil",   "West"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {34, 18, 31, "South America",  "Peru",     "East"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {41, 23, 8,  "South America",  "Peru",     "West"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {6,  34, 7,  "Europe",         "Italy",    "East"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {12, 44, 45, "Europe",         "Italy",    "West"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$ 
//                                               {31, 46, 23, "Europe",         "Germany",  "East"},  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
//                                               {47, 33, 51, "Europe",         "Germany",  "West"}}; //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$

  private static final String MAP_EXTENSION = ".map"; //$NON-NLS-1$
  private static final String PNG_SUFFIX = ".png"; //$NON-NLS-1$
  private static final String JPG_SUFFIX = ".jpeg"; //$NON-NLS-1$
  private static final String TEST_FILE_PATH = "test/test-output/TestChart"; //$NON-NLS-1$
  private static final String ROW_NAME = "row-name"; //$NON-NLS-1$
  //private static int CHART_COUNT = 0;

  protected void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }

  public void testValidate() throws Exception {
    final ChartXMLParser chartParser = new ChartXMLParser();
    final URL chartXmlDocument = this.getClass().getResource("PluginTest2a.xml"); //$NON-NLS-1$
    final ChartDocument chartDocument = chartParser.parseChartDocument(chartXmlDocument);
    if (chartDocument == null) {
      fail("A null document should never be returned"); //$NON-NLS-1$
    }

    final IChartPlugin chartPlugin = ChartPluginFactory.getInstance();
    final ChartResult result = chartPlugin.validateChartDocument(chartDocument);
    assertEquals(result.getErrorCode(), IChartPlugin.RESULT_VALIDATED);
  }

  private void testRenderAsPng(final String fileName,
                               final ChartTableModel data) throws Exception {
    final IChartPlugin plugin = ChartPluginFactory.getInstance("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    // At this point we have an output of the correct type
    // Now we can manipulate it to meet our needs so that we get the correct
    // output location and type.

    String chartFileName = TEST_FILE_PATH + fileName.substring(0, fileName.indexOf('.')) + PNG_SUFFIX;

    // Load / parse the chart document
    final URL chartURL = this.getClass().getResource(fileName);
    final ChartDocumentContext cdc = ChartFactory.generateChart(chartURL, data);
    assertNotNull(cdc);
    assertNotNull(cdc.getChartDocument());
    assertNotNull(cdc.getDataLinkInfo());

    // Render and save the plot
    IOutput output = plugin.renderChartDocument(cdc, data);
    OutputUtils.persistChart(output, chartFileName, IOutput.OutputTypes.FILE_TYPE_PNG);
    final File chartFile = new File(chartFileName);
    assertTrue(chartFile.exists());
    assertTrue(chartFile.length() > 5000);
    if (JFreeChartUtils.getShowUrls(cdc.getChartDocument())) {
      OutputUtils.persistMap(output, chartFileName + MAP_EXTENSION);
      final File mapFile = new File(chartFileName + MAP_EXTENSION);
      assertTrue(mapFile.exists());
      assertTrue(mapFile.length() > 100);
    }
  }

  private void testRenderAsJpeg(final String fileName,
                                  final ChartTableModel data) throws Exception {
    final IChartPlugin plugin = ChartPluginFactory.getInstance("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    // At this point we have an output of the correct type
    // Now we can manipulate it to meet our needs so that we get the correct
    // output location and type.

    // Now lets create some data
    String chartFileName = TEST_FILE_PATH + fileName.substring(0, fileName.indexOf('.')) + JPG_SUFFIX;

    // Load / parse the chart document
    final URL chartURL = this.getClass().getResource(fileName);
    final ChartDocumentContext cdc = ChartFactory.generateChart(chartURL, data);
    assertNotNull(cdc);
    assertNotNull(cdc.getChartDocument());
    assertNotNull(cdc.getDataLinkInfo());

    // Render and save the plot
    IOutput output = plugin.renderChartDocument(cdc, data);
    OutputUtils.persistChart(output, chartFileName, IOutput.OutputTypes.FILE_TYPE_JPEG);
    final File chartFile = new File(chartFileName);
    assertTrue(chartFile.exists());
    assertTrue(chartFile.length() > 5000);
    if (JFreeChartUtils.getShowUrls(cdc.getChartDocument())) {
      OutputUtils.persistMap(output, chartFileName + MAP_EXTENSION);
      final File mapFile = new File(chartFileName + MAP_EXTENSION);
      assertTrue(mapFile.exists());
      assertTrue(mapFile.length() > 100);
    }
  }

  private void testRenderAsPngStream(final String fileName,
                                       final ChartTableModel data) throws Exception {
    final IChartPlugin plugin = ChartPluginFactory.getInstance("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
     // At this point we have an output of the correct type
    // Now we can manipulate it to meet our needs so that we get the correct
    // output location and type.

    // Load / parse the chart document
    final URL chartURL = this.getClass().getResource(fileName);
    final ChartDocumentContext cdc = ChartFactory.generateChart(chartURL, data);
    assertNotNull(cdc);
    assertNotNull(cdc.getChartDocument());
    assertNotNull(cdc.getDataLinkInfo());

    // Render and save the plot
    IOutput output = plugin.renderChartDocument(cdc, data);

    final ByteArrayOutputStream newOutputStream = (ByteArrayOutputStream) output.persistChart(new ByteArrayOutputStream(), IOutput.OutputTypes.FILE_TYPE_PNG);
    assertTrue(newOutputStream.toByteArray().length > 5000);

  }

  private void testRenderAsJpegStream(final String fileName,
                                      final ChartTableModel data) throws Exception {
    final IChartPlugin plugin = ChartPluginFactory.getInstance("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$
    // At this point we have an output of the correct type
    // Now we can manipulate it to meet our needs so that we get the correct
    // output location and type.
    // Load / parse the chart document
    final URL chartURL = this.getClass().getResource(fileName);
    final ChartDocumentContext cdc = ChartFactory.generateChart(chartURL, data);
    assertNotNull(cdc);
    assertNotNull(cdc.getChartDocument());
    assertNotNull(cdc.getDataLinkInfo());

    // Render and save the plot
    ByteArrayOutputStream os = (ByteArrayOutputStream) plugin.renderChartDocument(cdc, data).persistChart(new ByteArrayOutputStream(), IOutput.OutputTypes.FILE_TYPE_JPEG);

    assertTrue(os.toByteArray().length > 5000);
  }

  private static ChartTableModel createChartTableModel(final Object[][] dataArray) {
    final ChartTableModel data = new ChartTableModel();
    data.setData(dataArray);
    data.setColumnName(0, "budget"); //$NON-NLS-1$
    data.setColumnName(1, "sales"); //$NON-NLS-1$
    data.setColumnName(2, "forecast"); //$NON-NLS-1$
//    data.setColumnName(3, "continent"); //$NON-NLS-1$
//    data.setColumnName(4, "country"); //$NON-NLS-1$
//    data.setColumnName(5, "territory"); //$NON-NLS-1$
    
    data.setRowMetadata(0, ROW_NAME, "Jan"); //$NON-NLS-1$
    data.setRowMetadata(1, ROW_NAME, "Feb"); //$NON-NLS-1$
    data.setRowMetadata(2, ROW_NAME, "Mar"); //$NON-NLS-1$

//    data.setRowMetadata(0, ROW_NAME, "Jan 05"); //$NON-NLS-1$
//    data.setRowMetadata(1, ROW_NAME, "Feb 05"); //$NON-NLS-1$
//    data.setRowMetadata(2, ROW_NAME, "Mar 05"); //$NON-NLS-1$
//    data.setRowMetadata(3, ROW_NAME, "APR 05"); //$NON-NLS-1$
//    data.setRowMetadata(4, ROW_NAME, "May 05"); //$NON-NLS-1$
//    data.setRowMetadata(5, ROW_NAME, "Jun 05"); //$NON-NLS-1$
//    data.setRowMetadata(6, ROW_NAME, "Jul 05"); //$NON-NLS-1$
//    data.setRowMetadata(7, ROW_NAME, "Aug 05"); //$NON-NLS-1$
//    data.setRowMetadata(8, ROW_NAME, "Sep 05"); //$NON-NLS-1$
//    data.setRowMetadata(9, ROW_NAME, "Oct 05"); //$NON-NLS-1$
//    data.setRowMetadata(10, ROW_NAME, "Nov 05"); //$NON-NLS-1$
//    data.setRowMetadata(11, ROW_NAME, "Dec 05"); //$NON-NLS-1$
//    data.setRowMetadata(12, ROW_NAME, "Jan 06"); //$NON-NLS-1$
//    data.setRowMetadata(13, ROW_NAME, "Feb 06"); //$NON-NLS-1$
//    data.setRowMetadata(14, ROW_NAME, "Mar 06"); //$NON-NLS-1$
//    data.setRowMetadata(15, ROW_NAME, "Apr 06"); //$NON-NLS-1$

    return data;
  }


  public void testAreaChart() {
    Object[][] dataArray = {{75.55, 85.11, 90.22, "East"}, //$NON-NLS-1$
                            {70.33, 80.44, 85.55, "West"}, //$NON-NLS-1$
                            {60.66, 70.77, 80.88, "Central"}};//$NON-NLS-1$

    final ChartTableModel data = createChartTableModel(dataArray);

    final String[] fileNames = {
        "PluginTest13a.xml", //$NON-NLS-1$
        "PluginTest13b.xml", //$NON-NLS-1$
        "PluginTest13c.xml", //$NON-NLS-1$
         "PluginTest13d.xml", //$NON-NLS-1$
    };

    runTests(fileNames, data);
  }

  public void testBarAndLineChart() {

    final Object[][] dataArray = {{5.55, 10.11, 20.22, "East"}, //$NON-NLS-1$
                                   {30.33, 40.44, 50.55, "West"}, //$NON-NLS-1$
                                   {60.66, 70.77, 80.88, "Central"}};//$NON-NLS-1$
    final ChartTableModel data = createChartTableModel(dataArray);

    final String[] fileNames = new String[] {
      "PluginTest1a.xml", //$NON-NLS-1$
      "PluginTest1b.xml", //$NON-NLS-1$
      "PluginTest1c.xml", //$NON-NLS-1$
      "PluginTest2a.xml", //$NON-NLS-1$
      "PluginTest2b.xml", //$NON-NLS-1$
      "PluginTest2c.xml", //$NON-NLS-1$
      "PluginTest2d.xml", //$NON-NLS-1$
      "PluginTest2e.xml", //$NON-NLS-1$
//      "PluginTest2f.xml", //$NON-NLS-1$
      "PluginTest3.xml", //$NON-NLS-1$
      "PluginTest4.xml", //$NON-NLS-1$
      "PluginTest5.xml", //$NON-NLS-1$
      "PluginTest6.xml", //$NON-NLS-1$
      "PluginTest7.xml", //$NON-NLS-1$
      "PluginTest8a.xml", //$NON-NLS-1$
      "PluginTest8b.xml", //$NON-NLS-1$
      "PluginTest9.xml", //$NON-NLS-1$
      "PluginTest10a.xml", //$NON-NLS-1$
      "PluginTest11a.xml", //$NON-NLS-1$
      "PluginTest11b.xml", //$NON-NLS-1$
      "PluginTest11c.xml", //$NON-NLS-1$
      "PluginTest11d.xml", //$NON-NLS-1$
      "PluginTest12a.xml", //$NON-NLS-1$
      "PluginTest12b.xml", //$NON-NLS-1$
      "PluginTest12c.xml", //$NON-NLS-1$
    };
    
    runTests(fileNames, data);
  }

  private void runTests(final String[] fileNames, final ChartTableModel data) {
    for (int i = 0; i < fileNames.length; i++) {
      try {
        testRenderAsJpeg(fileNames[i], data);
      } catch (Exception e) {
        e.printStackTrace();
        fail("Failed parsing "+fileNames[i]+ " file in method testRenderAsJpeg"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      try {
        testRenderAsJpegStream(fileNames[i], data);
      } catch (Exception e) {
        e.printStackTrace();
        fail("Failed parsing "+fileNames[i]+ " file in method testRenderAsJpegStream"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      try {
        testRenderAsPng(fileNames[i], data);
      } catch (Exception e) {
        e.printStackTrace();
        fail("Failed parsing "+fileNames[i]+ " file in method testRenderAsPng"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      try {
        testRenderAsPngStream(fileNames[i], data);
      } catch (Exception e) {
        e.printStackTrace();
        fail("Failed parsing "+fileNames[i]+ " file in method testRenderAsPngStream"); //$NON-NLS-1$ //$NON-NLS-2$
      }
    }
  }

} //Class ends

