/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package org.pentaho.experimental.chart.css;

import java.awt.Color;

import junit.framework.TestCase;

import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSFunctionValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;

public class LastBarColorTest extends TestCase {

  @Override
  protected void setUp() throws Exception {    
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testLastBarColorStyle() throws Exception {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("LastBarColorTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSValue[] passValues = new CSSValue[]{
        new CSSColorValue(Color.RED),
        new CSSColorValue(Color.BLUE),
        new CSSColorValue(0, 255, 0)
    };
    
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      final CSSValue value = layoutStyle.getValue(ChartStyleKeys.LAST_BAR_COLOR);
      System.out.println("Expected: "+passValues[counter]+" - Got: "+ value); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(passValues[counter++], value);
      child = child.getNextItem();
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
  
  
}
