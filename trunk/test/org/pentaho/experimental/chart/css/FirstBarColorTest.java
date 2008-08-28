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

public class FirstBarColorTest extends TestCase
{

  @Override
  protected void setUp() throws Exception
  {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }

  public void testFirstBarColorStyle() throws Exception
  {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("FirstBarColorTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSValue[] passValues = new CSSValue[]{
        new CSSColorValue(0,128,0), // green in CSS/HTML is different from the Green in Java
        new CSSColorValue(Color.RED),
        new CSSColorValue(Color.BLUE),
    };

    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();

    while (child != null)
    {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.println("Expected: " + passValues[counter] + " - Got: " + layoutStyle.getValue(ChartStyleKeys.FIRST_BAR_COLOR)); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(passValues[counter++], layoutStyle.getValue(ChartStyleKeys.FIRST_BAR_COLOR));
      child = child.getNextItem();
    }

    if (counter < lenArray - 1)
    {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }


}
