package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;
import org.jfree.resourceloader.ResourceException;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartCategoricalStyle;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSConstant;

public class ChartCategoricalStyleTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testCategoryStyle() throws IllegalStateException, ResourceException {
    
    String[] testFileNames = new String[] {
        "ChartCategoricalStyleTest.xml", //$NON-NLS-1$
        "ChartCategoricalStyleTest_FalseCase.xml", //$NON-NLS-1$
        "ChartCategoricalStyleTest_Misspelled.xml", //$NON-NLS-1$
        "ChartCategoricalStyleTest_Misspelled.xml"}; //$NON-NLS-1$

    CSSConstant[] passValues = new CSSConstant[]{
        ChartCategoricalStyle.TRUE,
        ChartCategoricalStyle.FALSE,
        ChartCategoricalStyle.FALSE,
        ChartCategoricalStyle.FALSE
    };

    for (int i = 0; i < testFileNames.length; i++) {
      ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource(testFileNames[i])); 
      ChartDocument cd = cdc.getChartDocument();
      assertNotNull(cd);
      ChartElement element = cd.getRootElement();
      assertNotNull(element);
      LayoutStyle layoutStyle = element.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.println("Expected: "+passValues[i]+" - Got: "+layoutStyle.getValue(ChartStyleKeys.CATEGORICAL)); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(passValues[i], layoutStyle.getValue(ChartStyleKeys.CATEGORICAL));
    }
  }
}
