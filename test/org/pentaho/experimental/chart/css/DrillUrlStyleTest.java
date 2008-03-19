package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSStringType;
import org.pentaho.reporting.libraries.css.values.CSSStringValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * Tests the -x-pentaho-chart-drill-url style
 */
public class DrillUrlStyleTest extends TestCase {
  protected void setUp() throws Exception {
    super.setUp();
    ChartBoot.getInstance().start();
  }

  public void testDrillUrl() throws Exception {
    // Get the chart
    ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("DrillUrlTest.xml")); //$NON-NLS-1$
    ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    ChartElement element = cd.getRootElement();
    assertNotNull(element);
    element = element.getFirstChildItem();
    assertNotNull(element);

    // Check the values
    Object [] validResults = new Object[] {
      new CSSConstant("none"),
      new CSSConstant("none"),
      new CSSStringValue(CSSStringType.URI, "DrillUrlTest.xml"),
      new CSSStringValue(CSSStringType.URI, "DoesNotExist.file"),
    };
    int i = 0;
    while(element != null) {
      CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.DRILL_URL);
      System.out.println("expecting: "+validResults[i]+" : received: "+value);
      validResults[i].equals(value);
      assertEquals(validResults[i], value);
      element = element.getNextItem();
      ++i;
    }
  }
}
