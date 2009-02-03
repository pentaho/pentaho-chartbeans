package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;

public class PieExplodePercentStyleTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }

  public void testPieExplodePercentStyle() throws Exception {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("PieExplodePercentStyleTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSNumericValue[] passValues = new CSSNumericValue[]{
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 0), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 100), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 75), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 5), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 0), //$NON-NLS-1$
        CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, 0), //$NON-NLS-1$
    };

    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();

    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.println("Expected: "+passValues[counter]+" - Got: "+layoutStyle.getValue(ChartStyleKeys.PIE_EXPLODE_PERCENT)); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(passValues[counter++].getCSSText(), layoutStyle.getValue(ChartStyleKeys.PIE_EXPLODE_PERCENT).getCSSText());
      child = child.getNextItem();
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  }
}