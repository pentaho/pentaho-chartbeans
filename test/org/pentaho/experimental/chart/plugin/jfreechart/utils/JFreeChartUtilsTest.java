/**
 * 
 */
package org.pentaho.experimental.chart.plugin.jfreechart.utils;


import junit.framework.TestCase;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.resourceloader.ResourceException;
import org.junit.After;
import org.junit.Before;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;

/**
 * @author Ravi Hasija
 *
 */
public class JFreeChartUtilsTest extends TestCase {

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }
  
  public void testGetOrientation() throws ResourceException {
    String[] testFileNames = {
        "JFreeChartUtilsTest1.xml", //$NON-NLS-1$
        "JFreeChartUtilsTest2.xml", //$NON-NLS-1$
        "JFreeChartUtilsTest3.xml", //$NON-NLS-1$
        "JFreeChartUtilsTest4.xml", //$NON-NLS-1$
    };

    PlotOrientation[] expectedValues = {
         PlotOrientation.VERTICAL,
         PlotOrientation.HORIZONTAL,
         PlotOrientation.VERTICAL,
         PlotOrientation.HORIZONTAL,
    };

    for (int i = 0; i < testFileNames.length; i++) {
      ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource(testFileNames[i])); 
      assertNotNull(cdc);
      ChartDocument cd = cdc.getChartDocument();
      assertNotNull(cd);    

      ChartElement plotElement = cd.getPlotElement();
      if (i == 0) {
        assertEquals(null, plotElement);
      } else {  
        assertEquals(expectedValues[i], JFreeChartUtils.getPlotOrientation(cd));
      }
    }
  }

}
