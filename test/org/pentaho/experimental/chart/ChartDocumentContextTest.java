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
package org.pentaho.experimental.chart;

import junit.framework.TestCase;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;
import org.pentaho.reporting.libraries.css.dom.StyleReference;

/**
 * Tests for the <code>ChartDocumentContext</code> class
 *
 * @author David Kincade
 */
public class ChartDocumentContextTest extends TestCase {
  /**
   * Chart document used for testing
   */
  private ChartDocument chart;

  /**
   * Performs the ChartBoot before performing the tests
   *
   * @throws Exception
   */
  protected void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();

    // Create a chart document
    chart = new ChartXMLParser().parseChartDocument(getClass().getResource("test1.xml"));
  }

  /**
   * Tests the <code>createStyleReferences</code> method that searched the report definition
   * for style information and returns the information created from the style information.
   */
  public void testCreateStyleReferences() throws ResourceException {
    final ChartDocumentContext cdc = new ChartDocumentContext(chart);
    final ChartDocument chartDoc = cdc.getChartDocument();

    final StyleReference[] styleReferences = cdc.createStyleReferences(chartDoc);
    assertNotNull(styleReferences);
    assertEquals(4, styleReferences.length);

    assertEquals(StyleReference.INLINE, styleReferences[0].getType());
    assertTrue(-1 != styleReferences[0].getStyleContent().indexOf("width: 100px; color: BLUE"));

    assertEquals(StyleReference.LINK, styleReferences[1].getType());
    assertTrue(-1 != styleReferences[1].getStyleContent().indexOf("test1.css"));

    assertEquals(StyleReference.LINK, styleReferences[2].getType());
    assertTrue(-1 != styleReferences[2].getStyleContent().indexOf("test2.css"));

    assertEquals(StyleReference.INLINE, styleReferences[3].getType());
    assertTrue(-1 != styleReferences[3].getStyleContent().indexOf("width: 200"));

  }
}
