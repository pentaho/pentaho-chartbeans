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
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;

public class PieExplodePercentStyleIT extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }

  public void testPieExplodePercentStyle() throws Exception {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("PieExplodePercentStyleTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSNumericValue[] passValues = new CSSNumericValue[]{
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 0), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 100), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 75), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 5), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 0), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 0), //$NON-NLS-1$
    };

    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();

    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.println("Expected: "+passValues[counter]+" - Got: "+layoutStyle.getValue(ChartStyleKeys.PIE_EXPLODE_PERCENT)); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(passValues[counter++].getCSSText(), layoutStyle.getValue(ChartStyleKeys.PIE_EXPLODE_PERCENT).getCSSText());
      child = child.getNextItem();
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
}
