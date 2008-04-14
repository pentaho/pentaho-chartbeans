package org.pentaho.experimental.chart.css;

import junit.framework.TestCase;
import org.jfree.resourceloader.ResourceException;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartItemLabelVisibleType;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSConstant;

public class ChartItemLabelVisibleTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testItemLabelVisible() throws IllegalStateException, ResourceException {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartItemLabelVisibleTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    final CSSConstant[] passValues = new CSSConstant[]{
        ChartItemLabelVisibleType.NO,
        ChartItemLabelVisibleType.NO,
        ChartItemLabelVisibleType.YES,
        ChartItemLabelVisibleType.NO,
        ChartItemLabelVisibleType.NO,
        ChartItemLabelVisibleType.NO,        
    };
    
    int counter = 0;
    final int lenArray = passValues.length;
    ChartElement child = element.getFirstChildItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.println("Expected: "+passValues[counter]+" - Got: "+layoutStyle.getValue(ChartStyleKeys.ITEM_LABEL_VISIBLE)); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(passValues[counter++], layoutStyle.getValue(ChartStyleKeys.ITEM_LABEL_VISIBLE));
      child = child.getNextItem();
    }

    if (counter < lenArray-1) {
      throw new IllegalStateException("Not all tests covered!");  //$NON-NLS-1$
    }
  
  }
}
