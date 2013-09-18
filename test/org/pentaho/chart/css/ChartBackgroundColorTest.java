package org.pentaho.chart.css;

import junit.framework.TestCase;

import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;

public class ChartBackgroundColorTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    ChartBoot.getInstance().start();
  }

  public void testChartBackgroundColor() throws Exception {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartBackgroundColorTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    CSSColorValue value = (CSSColorValue) element.getLayoutStyle().getValue(BorderStyleKeys.BACKGROUND_COLOR);
    
    // background-color style property on chart element (root) should be red
    assertEquals(255, value.getRed());
    assertEquals(0, value.getGreen());
    assertEquals(0, value.getBlue());
  }
}
