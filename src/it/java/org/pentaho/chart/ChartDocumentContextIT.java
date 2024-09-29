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

import junit.framework.TestCase;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.parser.ChartXMLParser;
import org.pentaho.reporting.libraries.css.dom.StyleReference;

/**
 * Tests for the <code>ChartDocumentContext</code> class
 *
 * @author David Kincade
 */
public class ChartDocumentContextIT extends TestCase {
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
