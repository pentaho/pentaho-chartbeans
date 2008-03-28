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
import org.pentaho.reporting.libraries.css.values.CSSFunctionValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;

public class ChartGradientColorStyleTest extends TestCase {

  @Override
  protected void setUp() throws Exception {    
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }

  private CSSFunctionValue getCSSFunctionValue(int r, int g, int b) {
    
    CSSFunctionValue value = new CSSFunctionValue("color", //$NON-NLS-1$ 
                                                  new CSSValue[] {CSSNumericValue.createValue(CSSNumericType.NUMBER,r), 
                                                                  CSSNumericValue.createValue(CSSNumericType.NUMBER,g), 
                                                                  CSSNumericValue.createValue(CSSNumericType.NUMBER,b)    
                                                                 });
    return value;
  }
  
  public void testChartGradientColorStyle() throws Exception {
    ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartGradientColorStyleTest.xml")); //$NON-NLS-1$
    ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    ChartElement element = cd.getRootElement();
    assertNotNull(element);
    
    

    CSSValuePair[] passValues = new CSSValuePair[] {
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
    int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.println("Expected: " + passValues[counter].getCSSText() + //$NON-NLS-1$ 
          " - Got: "+layoutStyle.getValue(ChartStyleKeys.GRADIENT_COLOR).getCSSText()); //$NON-NLS-1$ 
      
      CSSValuePair valuePair = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_COLOR);
      CSSValue gotValue1      = valuePair.getFirstValue();
      CSSValue expectedValue1 = passValues[counter].getFirstValue();
      assertEquals("Counter# " + counter + " COLOR#1 does not match: ", expectedValue1, gotValue1); //$NON-NLS-1$ //$NON-NLS-2$
      
      CSSValue gotValue2      = valuePair.getSecondValue();
      CSSValue expectedValue2 = passValues[counter].getSecondValue();
      assertEquals("Counter# " + counter + " COLOR#2 does not match: ",expectedValue2, gotValue2); //$NON-NLS-1$ //$NON-NLS-2$
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
}
