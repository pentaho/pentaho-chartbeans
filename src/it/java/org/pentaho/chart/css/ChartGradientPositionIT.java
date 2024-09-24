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
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;

public class ChartGradientPositionIT extends TestCase {


  @Override
  protected void setUp() throws Exception {    
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testGradientStartPosition()
  throws Exception {
    final CSSValuePair[] passValues = new CSSValuePair[] {
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,0), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,0) 
          ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,1), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,10) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,0.5), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,99.90) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,0), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,1) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,0), 
                           CSSNumericValue.createValue(CSSNumericType.NUMBER,0) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,0), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,0) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,0), 
                            CSSNumericValue.createValue(CSSNumericType.NUMBER,0) 
                          ),
    };

    testPosition("ChartGradientStartTest.xml", ChartStyleKeys.GRADIENT_START, passValues); //$NON-NLS-1$
  }

  public void testGradientEndPosition()
  throws Exception {
    final CSSValuePair[] passValues = new CSSValuePair[] {
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,1), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,1) 
          ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,1), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,10) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,0.5), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,99.90) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,0), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,1) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,1), 
                           CSSNumericValue.createValue(CSSNumericType.NUMBER,1) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,1), 
                          CSSNumericValue.createValue(CSSNumericType.NUMBER,1) 
                        ),
        new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,1), 
                            CSSNumericValue.createValue(CSSNumericType.NUMBER,1) 
                          ),
    };
    testPosition("ChartGradientEndTest.xml", ChartStyleKeys.GRADIENT_END, passValues); //$NON-NLS-1$
  }
  
  private void testPosition(final String xmlFileName, final StyleKey currTestStyleKey, final CSSValuePair[] passValues)
  throws Exception {
    System.out.println("----------Start Testing :--> " + currTestStyleKey + "--------------" ); //$NON-NLS-1$ //$NON-NLS-2$
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource(xmlFileName));
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);
  
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      
      final CSSValuePair valuePair = (CSSValuePair) layoutStyle.getValue(currTestStyleKey);
      
      // Getting and testing each individual value 
      final Float gotValue1      = Float.valueOf(valuePair.getFirstValue().getCSSText());
      final Float gotValue2      = Float.valueOf(valuePair.getSecondValue().getCSSText());
      final Float expectedValue1 = Float.valueOf(passValues[counter].getFirstValue().getCSSText());
      final Float expectedValue2 = Float.valueOf(passValues[counter].getSecondValue().getCSSText());

      System.out.println("Expected : (" + expectedValue1 + ", " + expectedValue2 + ") - Got : ("+gotValue1+ ", " + gotValue2 + ")"); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$//$NON-NLS-5$ 
      assertEquals(expectedValue1, gotValue1);
      assertEquals(expectedValue2, gotValue2);
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
    
    System.out.println("----------Test Complete---------\n"); //$NON-NLS-1$
  }
}
