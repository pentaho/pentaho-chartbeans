package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;

import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartLineStyle;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;

public class LineStyleTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    // TODO Auto-generated method stub
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testLineStyle() throws Exception {
    ChartDocument cd = new ChartXMLParser().parseChartDocument(this.getClass().getResource("LineStyleTest.xml"));
    assertNotNull(cd);
    ChartElement element = cd.getRootElement();
    assertNotNull(element);
    ChartElement child1 = element.getFirstChildItem();
    LayoutStyle tempStyle = child1.getLayoutStyle();
    assertNotNull(tempStyle);
    assertEquals(ChartLineStyle.SOLID, tempStyle.getValue(ChartStyleKeys.LINE_STYLE));
     
    ChartElement child2= child1.getNextItem();
    tempStyle = child2.getLayoutStyle();
    assertNotNull(tempStyle);
    assertEquals(ChartLineStyle.DOTTED, tempStyle.getValue(ChartStyleKeys.LINE_STYLE));
    
    ChartElement child3 = child1.getNextItem();
    tempStyle = child3.getLayoutStyle();
    assertNotNull(tempStyle);
    assertEquals(ChartLineStyle.SOLID, tempStyle.getValue(ChartStyleKeys.LINE_STYLE));
  }
  
}
