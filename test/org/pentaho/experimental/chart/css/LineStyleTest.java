package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartLineStyle;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSConstant;

public class LineStyleTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testLineStyle() throws Exception {
    ChartDocument cd = ChartFactory.generateChart(getClass().getResource("LineStyleTest.xml")); //$NON-NLS-1$
    assertNotNull(cd);
    ChartElement element = cd.getRootElement();
    assertNotNull(element);

    int index = 0;
    CSSConstant[] passValues = new CSSConstant[] { ChartLineStyle.SOLID, ChartLineStyle.DASHED, ChartLineStyle.DOT_DOT_DASH, ChartLineStyle.SOLID, ChartLineStyle.SOLID };
    ChartElement child = element.getFirstChildItem().getNextItem();
    
    while(child != null) {
      LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.println("expected: "+passValues[index]+" - got: "+layoutStyle.getValue(ChartStyleKeys.LINE_STYLE)); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(passValues[index++], layoutStyle.getValue(ChartStyleKeys.LINE_STYLE));
      child = child.getNextItem();
    }
  }
  
}
