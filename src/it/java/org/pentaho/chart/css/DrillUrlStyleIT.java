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


package org.pentaho.chart.css;

import junit.framework.TestCase;

import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSStringType;
import org.pentaho.reporting.libraries.css.values.CSSStringValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * Tests the -x-pentaho-chart-drill-url-template style
 */
public class DrillUrlStyleIT extends TestCase {
  protected void setUp() throws Exception {
    super.setUp();
    ChartBoot.getInstance().start();
  }

  public void testDrillUrl() throws Exception {
    // Get the chart
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("DrillUrlTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    ChartElement element = cd.getRootElement();
    assertNotNull(element);
    element = element.getFirstChildItem();
    assertNotNull(element);

    // Check the values
    final Object [] validResults = new Object[] {
      new CSSConstant("none"),
      new CSSConstant("none"),
      new CSSStringValue(CSSStringType.URI, "DrillUrlTest.xml"),
      new CSSStringValue(CSSStringType.URI, "DoesNotExist.file"),
    };
    int i = 0;
    while(element != null) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.DRILL_URL);
      System.out.println("expecting: "+validResults[i]+" : received: "+value);
      validResults[i].equals(value);
      assertEquals(validResults[i], value);
      element = element.getNextItem();
      ++i;
    }
  }
}
