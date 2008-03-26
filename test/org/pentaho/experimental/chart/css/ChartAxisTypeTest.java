package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;

import org.jfree.resourceloader.ResourceException;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartAxisDimension;
import org.pentaho.experimental.chart.css.styles.ChartAxisPosition;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class ChartAxisTypeTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testChartAxisType() throws IllegalStateException, ResourceException {
    ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartAxisTypeTest.xml")); //$NON-NLS-1$
    ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    ChartElement element = cd.getRootElement();
    assertNotNull(element);

    CSSValue[][] passValues = new CSSValue[][] {
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
        new CSSValue[] {ChartAxisDimension.DOMAIN, ChartAxisPosition.SECONDARY, new CSSConstant("1")}, //$NON-NLS-1$
    };
    
    int counter = 0;
    int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.print("Counter#: " + counter + " Expecting: " + passValues[counter][0] + "," + passValues[counter][1] + "," + passValues[counter][2]); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
      System.out.println(" -- Got: " + layoutStyle.getValue(ChartStyleKeys.AXIS_DIMENSION) + "," + layoutStyle.getValue(ChartStyleKeys.AXIS_POSITION) + "," + layoutStyle.getValue(ChartStyleKeys.AXIS_ORDER).getCSSText()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      assertEquals(passValues[counter][0], layoutStyle.getValue(ChartStyleKeys.AXIS_DIMENSION));
      assertEquals(passValues[counter][1], layoutStyle.getValue(ChartStyleKeys.AXIS_POSITION));
      assertEquals(passValues[counter][2].getCSSText(), layoutStyle.getValue(ChartStyleKeys.AXIS_ORDER).getCSSText());
      
      child = child.getNextItem();
      counter++;
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
}
