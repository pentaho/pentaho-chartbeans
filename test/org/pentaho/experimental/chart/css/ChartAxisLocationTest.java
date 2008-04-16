package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;

import org.jfree.resourceloader.ResourceException;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartAxisLocationType;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class ChartAxisLocationTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testChartAxisType() throws IllegalStateException, ResourceException {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartAxisLocationTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSValue[] passValues = new CSSValue[] {
        ChartAxisLocationType.PRIMARY, 
        ChartAxisLocationType.SECONDARY,
        ChartAxisLocationType.PRIMARY, 
        ChartAxisLocationType.PRIMARY, 
    };
    
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.print("Counter#: " + counter + " Expecting: " + passValues[counter]); //$NON-NLS-1$ //$NON-NLS-2$ 
      System.out.println(" -- Got: " + layoutStyle.getValue(ChartStyleKeys.AXIS_LOCATION)); //$NON-NLS-1$  
      assertEquals("Counter# " + counter + " Location: ", passValues[counter], layoutStyle.getValue(ChartStyleKeys.AXIS_LOCATION)); //$NON-NLS-1$ //$NON-NLS-2$ 
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
}
