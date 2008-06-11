package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class ChartScaleTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testChartScale() throws IllegalStateException, ResourceException {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartScaleTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSValue[] passValues = new CSSValue[]{
        CSSNumericValue.createValue(CSSNumericType.NUMBER,1),
        CSSNumericValue.createValue(CSSNumericType.NUMBER,2.5),
        CSSNumericValue.createValue(CSSNumericType.NUMBER,0.001),
        CSSNumericValue.createValue(CSSNumericType.NUMBER,1),
        CSSNumericValue.createValue(CSSNumericType.NUMBER,1),
    };
    
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      
      final Float expectedValue = Float.valueOf(layoutStyle.getValue(ChartStyleKeys.SCALE_NUM).getCSSText());
      final Float gotValue      = Float.valueOf(passValues[counter].getCSSText());
      
      System.out.println("Expected: "+expectedValue+" - Got: "+gotValue); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(expectedValue, gotValue);      
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  
  }
}
