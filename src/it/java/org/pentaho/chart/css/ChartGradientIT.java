/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.css;

import java.awt.Color;

import junit.framework.TestCase;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartGradientType;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;

public class ChartGradientIT extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testGradient() throws IllegalStateException, ResourceException {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartGradientTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSValuePair[][] passValues = new CSSValuePair[][] {
        getCSSValuePair(Color.RED, Color.WHITE, ChartGradientType.HORIZONTAL, 1,10, 2,20),
        getCSSValuePair(Color.BLACK, Color.WHITE, ChartGradientType.NONE, 0,0, 1,1),
        getCSSValuePair(Color.BLACK, Color.WHITE, ChartGradientType.NONE, 0,0, 1,1),
        getCSSValuePair(Color.BLACK, Color.WHITE, ChartGradientType.NONE, 0,0, 1,1),
        getCSSValuePair(Color.BLACK, Color.WHITE, ChartGradientType.NONE, 0,0, 1,1),
        getCSSValuePair(Color.BLACK, Color.WHITE, ChartGradientType.NONE, 0,0, 1,1),
    };
    
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      
      CSSValuePair valuePair = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_COLOR);
      CSSValue gotValue1      = valuePair.getFirstValue();
      CSSValue gotValue2      = valuePair.getSecondValue();
      CSSValue expectedValue1 = passValues[counter][0].getFirstValue();
      CSSValue expectedValue2 = passValues[counter][0].getSecondValue();
      assertEquals("Counter# " + counter + " COLOR#1 does not match: ", expectedValue1, gotValue1); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals("Counter# " + counter + " COLOR#2 does not match: ",expectedValue2, gotValue2); //$NON-NLS-1$ //$NON-NLS-2$

      gotValue1  =  layoutStyle.getValue(ChartStyleKeys.GRADIENT_TYPE);
      expectedValue1 = passValues[counter][1].getFirstValue();
      assertEquals("Counter# " + counter + " GRADIENT TYPE does not match: ",expectedValue1, gotValue1); //$NON-NLS-1$ //$NON-NLS-2$      
      
      valuePair = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_START);
      gotValue1      = valuePair.getFirstValue();
      gotValue2      = valuePair.getSecondValue();
      expectedValue1 = passValues[counter][2].getFirstValue();
      expectedValue2 = passValues[counter][2].getSecondValue();
      assertEquals("Counter# " + counter + " Start Pos #1 does not match: ", expectedValue1, gotValue1); //$NON-NLS-1$ //$NON-NLS-2$      
      assertEquals("Counter# " + counter + " Start Pos #2 does not match: ", expectedValue2, gotValue2); //$NON-NLS-1$ //$NON-NLS-2$      

      valuePair = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_END);
      gotValue1      = valuePair.getFirstValue();
      gotValue2      = valuePair.getSecondValue();
      expectedValue1 = passValues[counter][3].getFirstValue();
      expectedValue2 = passValues[counter][3].getSecondValue();
      assertEquals("Counter# " + counter + " End Pos #1 does not match: ", expectedValue1, gotValue1); //$NON-NLS-1$ //$NON-NLS-2$      
      assertEquals("Counter# " + counter + " End Pos #2 does not match: ", expectedValue2, gotValue2); //$NON-NLS-1$ //$NON-NLS-2$      
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
  
  private CSSValuePair[] getCSSValuePair(final Color color1,
      final Color color2,
      final CSSConstant type,
      final double start1,
      final double start2,
      final double end1,
      final double end2) {
      return new CSSValuePair[] {
          new CSSValuePair(new CSSColorValue(color1), new CSSColorValue(color2)), 
          new CSSValuePair(type),
          new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,start1), 
              CSSNumericValue.createValue(CSSNumericType.NUMBER,start2)),
              new CSSValuePair( CSSNumericValue.createValue(CSSNumericType.NUMBER,end1), 
                  CSSNumericValue.createValue(CSSNumericType.NUMBER,end2))
      };
  }
}
