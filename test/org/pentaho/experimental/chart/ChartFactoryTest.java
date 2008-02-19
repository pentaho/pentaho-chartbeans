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

import junit.framework.TestCase;
import org.jfree.resourceloader.ResourceException;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.resolver.StyleResolver;

/**
 * Unit tests for the ChartFactory class.
 *
 * @author David Kincade
 */
public class ChartFactoryTest extends TestCase {
  /**
   * Performs the ChartBoot before performing the tests
   *
   * @throws Exception
   */
  protected void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }

  /**
   * Tests the loading and consolidation of the style sheet information. This
   * test will load the sample report which has the following style sheet information
   * in the following order:
   * <pre>
   *   <stylesheet>
   *     .test1 { width: 100px; color: BLUE }
   *   </stylesheet>
   * <p/>
   *   <stylesheet href="test1.css" /> <!-- contains .test1 { border: SOLID } -->
   * <p/>
   *   <stylesheet href="test2.css" >  <!-- contains .test1 { color: RED }  .test2 { width: 150px; color: BLACK } -->
   *     .test2 { width: 200 px }
   *   </stylesheet>
   * </pre>
   */
  public void testLoadStyleSheet() throws ResourceException {
    ChartFactory.generateChart(getClass().getResource("test1.xml"));
  }

  /**
   * Tests the style resolution to make sure the <code>StyleResolver</code> class is initialized correctly
   */
  public void testStyleResolver() throws ResourceException {
    // Create the chart for testing
    ChartDocument cd = new ChartXMLParser().parseChartDocument(this.getClass().getResource("style_test.xml"));
    ChartDocumentContext cdc = new ChartDocumentContext(cd);

    // Get the initialized style resolver
    StyleResolver sr = ChartFactory.getStyleResolver(cdc);
    assertNotNull(sr);

    // The 1st element of the chart should have no style information
    ChartElement element = cd.getRootElement();
    sr.resolveStyle(element);
    LayoutStyle styleRule = element.getLayoutStyle();
    assertNotNull(styleRule);
  }
}
