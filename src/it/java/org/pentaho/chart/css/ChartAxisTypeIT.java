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
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartAxisDimension;
import org.pentaho.chart.css.styles.ChartAxisPosition;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class ChartAxisTypeIT extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testChartAxisType() throws IllegalStateException, ResourceException {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartAxisTypeTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSValue[][] passValues = new CSSValue[][] {
        new CSSValue[] {ChartAxisDimension.AUTO, ChartAxisPosition.PRIMARY, new CSSConstant("1")}, //$NON-NLS-1$
        new CSSValue[] {ChartAxisDimension.AUTO, ChartAxisPosition.SECONDARY, new CSSConstant("2")}, //$NON-NLS-1$
        new CSSValue[] {ChartAxisDimension.DOMAIN, ChartAxisPosition.PRIMARY, new CSSConstant("3")}, //$NON-NLS-1$
        new CSSValue[] {ChartAxisDimension.DOMAIN, ChartAxisPosition.SECONDARY, new CSSConstant("4")}, //$NON-NLS-1$
        new CSSValue[] {ChartAxisDimension.RANGE, ChartAxisPosition.PRIMARY, new CSSConstant("5")}, //$NON-NLS-1$
        new CSSValue[] {ChartAxisDimension.RANGE, ChartAxisPosition.SECONDARY, new CSSConstant("6")}, //$NON-NLS-1$
        
        new CSSValue[] {ChartAxisDimension.AUTO, ChartAxisPosition.PRIMARY, new CSSConstant("1")}, //$NON-NLS-1$        
        new CSSValue[] {ChartAxisDimension.AUTO, ChartAxisPosition.PRIMARY, new CSSConstant("1")}, //$NON-NLS-1$        
        new CSSValue[] {ChartAxisDimension.AUTO, ChartAxisPosition.PRIMARY, new CSSConstant("1")}, //$NON-NLS-1$        
        new CSSValue[] {ChartAxisDimension.AUTO, ChartAxisPosition.PRIMARY, new CSSConstant("1")}, //$NON-NLS-1$
        new CSSValue[] {ChartAxisDimension.AUTO, ChartAxisPosition.PRIMARY, new CSSConstant("1")}, //$NON-NLS-1$
    };
    
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.print("Counter#: " + counter + " Expecting: " + passValues[counter][0] + "," + passValues[counter][1] + "," + passValues[counter][2]); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
      System.out.println(" -- Got: " + layoutStyle.getValue(ChartStyleKeys.AXIS_DIMENSION) + "," + layoutStyle.getValue(ChartStyleKeys.AXIS_POSITION) + "," + layoutStyle.getValue(ChartStyleKeys.AXIS_ORDER).getCSSText()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      assertEquals("Counter# " + counter + " DIMENSION: ", passValues[counter][0], layoutStyle.getValue(ChartStyleKeys.AXIS_DIMENSION)); //$NON-NLS-1$ //$NON-NLS-2$ 
      assertEquals("Counter# " + counter + " POSITION: ", passValues[counter][1], layoutStyle.getValue(ChartStyleKeys.AXIS_POSITION)); //$NON-NLS-1$ //$NON-NLS-2$ 
      assertEquals("Counter# " + counter + " ORDER: ", passValues[counter][2].getCSSText(), layoutStyle.getValue(ChartStyleKeys.AXIS_ORDER).getCSSText()); //$NON-NLS-1$ //$NON-NLS-2$ 
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
}
