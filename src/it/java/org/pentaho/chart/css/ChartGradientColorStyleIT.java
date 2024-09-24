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

import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;
import org.pentaho.reporting.libraries.css.values.CSSFunctionValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;

public class ChartGradientColorStyleIT extends TestCase {

  @Override
  protected void setUp() throws Exception {    
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }

  private CSSValue getCSSFunctionValue(final int r, final int g, final int b) {
    
    return new CSSColorValue(r, g, b);
  }
  
  public void testChartGradientColorStyle() throws Exception {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartGradientColorStyleTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);
    
    

    final CSSValuePair[] passValues = new CSSValuePair[] {
        new CSSValuePair(getCSSFunctionValue(0, 255, 0), getCSSFunctionValue(255, 0, 0)),
        new CSSValuePair(new CSSColorValue(Color.GRAY), new CSSColorValue(Color.CYAN)),
        new CSSValuePair(getCSSFunctionValue(255, 255, 0), getCSSFunctionValue(0, 0, 255)),                        
        new CSSValuePair(new CSSColorValue(Color.YELLOW), new CSSColorValue(Color.BLUE)),
        new CSSValuePair(new CSSColorValue(Color.RED),   new CSSColorValue(Color.WHITE)),
        new CSSValuePair(new CSSColorValue(Color.BLACK), new CSSColorValue(Color.WHITE)),
        new CSSValuePair(new CSSColorValue(Color.BLACK), new CSSColorValue(Color.WHITE)),
        new CSSValuePair(new CSSColorValue(Color.BLACK), new CSSColorValue(Color.WHITE)),
        new CSSValuePair(new CSSColorValue(Color.BLACK), new CSSColorValue(Color.WHITE)),
        new CSSValuePair(getCSSFunctionValue(0, 255, 0), new CSSColorValue(Color.YELLOW)),                        
        new CSSValuePair(getCSSFunctionValue(0, 255, 0), getCSSFunctionValue(0, 0, 255)),                        
        new CSSValuePair(new CSSColorValue(Color.YELLOW), getCSSFunctionValue(0, 0, 255)),
    };
    
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      final CSSValue value = layoutStyle.getValue(ChartStyleKeys.GRADIENT_COLOR);
      assertNotNull(value);
      
      System.out.println("Expected: " + passValues[counter].getCSSText() + //$NON-NLS-1$
          " - Got: "+ value.getCSSText()); //$NON-NLS-1$ 
      
      final CSSValuePair valuePair = (CSSValuePair) value;
      final CSSValue gotValue1      = valuePair.getFirstValue();
      final CSSValue expectedValue1 = passValues[counter].getFirstValue();
      assertEquals("Counter# " + counter + " COLOR#1 does not match: ", expectedValue1, gotValue1); //$NON-NLS-1$ //$NON-NLS-2$
      
      final CSSValue gotValue2      = valuePair.getSecondValue();
      final CSSValue expectedValue2 = passValues[counter].getSecondValue();
      assertEquals("Counter# " + counter + " COLOR#2 does not match: ",expectedValue2, gotValue2); //$NON-NLS-1$ //$NON-NLS-2$
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
}
