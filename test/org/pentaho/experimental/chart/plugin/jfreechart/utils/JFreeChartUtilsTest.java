/**
 * 
 */
package org.pentaho.experimental.chart.plugin.jfreechart.utils;


import java.awt.Color;
import java.awt.GradientPaint;
import java.util.List;

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

  String[] testFileNames = {
      "JFreeChartUtilsTest1.xml", //$NON-NLS-1$
      "JFreeChartUtilsTest2.xml", //$NON-NLS-1$
      "JFreeChartUtilsTest3.xml", //$NON-NLS-1$
      "JFreeChartUtilsTest4.xml", //$NON-NLS-1$
  };

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

  public void testShowURLs() throws ResourceException {

    boolean[] expectedValues = {
         false,
         true,
         false,
         false,
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
        assertEquals("Counter# " + i, expectedValues[i], JFreeChartUtils.getShowUrls(cd)); //$NON-NLS-1$
      }
    }
  }

  /*
   * 
   */
  public void testGradientPaint() throws ResourceException {
    
    GradientPaint[] expectedValues = new GradientPaint[] {
      new GradientPaint(0.5f,99.90f, Color.black, 1,1, Color.white),  
      new GradientPaint(1f,2f, Color.black, 1,1, Color.white),  
      new GradientPaint(0,0, Color.black, 1.5f,8.5f, Color.white),  
      new GradientPaint(0,0, Color.black, 3,4, Color.white),  
      new GradientPaint(1,2, Color.gray, 3,4, Color.cyan),  
      new GradientPaint(1,10, Color.red, 2,20, Color.white),  
      new GradientPaint(0,0, new Color(0,255,0), 1,1, new Color(255,0,0)),
      new GradientPaint(0,0, Color.gray, 1,1, Color.cyan),  
      new GradientPaint(0,0, new Color(0xFFFF00), 1,1, new Color(0x0000FF)),  
      new GradientPaint(0,0, Color.yellow, 1,1, Color.blue),  
      new GradientPaint(0,0, Color.red, 1,1, Color.white),  
      new GradientPaint(0,0, Color.black, 1,1, Color.white),  
      new GradientPaint(0,0, Color.black, 1,1, Color.white),  
      new GradientPaint(0,0, Color.black, 1,1, Color.white),  
      new GradientPaint(0,0, Color.black, 1,1, Color.white),  
      new GradientPaint(0,0, new Color(0,255,0), 1,1, Color.yellow),  
      new GradientPaint(0,0, new Color(0,255,0), 1,1, new Color(0x0000FF)),  
      new GradientPaint(0,0, Color.yellow, 1,1, new Color(0x0000FF)),  
    };
    
    String[] testGradFileNames = { 
        "JFreeChartUtilsTestGradColor.xml", //$NON-NLS-1$
    };

    
    for (int i = 0; i < testGradFileNames.length; i++) {
      ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource(testGradFileNames[i])); 
      assertNotNull(cdc);
      ChartDocument cd = cdc.getChartDocument();
      assertNotNull(cd);
      
      List seriesList = cd.getSeriesChartElements();
      if (seriesList.size() == 0) {
        System.out.println("No series element found."); //$NON-NLS-1$
        continue;
      }
      
      for (int j = 0; j < seriesList.size(); j++) {
        ChartElement currElement = (ChartElement)seriesList.get(j);
        GradientPaint gotGradientPaint = JFreeChartUtils.getGradientPaint(currElement);
        assertNotNull(gotGradientPaint);
        assertEquals("Counter# "+j+" Color#1:", expectedValues[j].getColor1(), gotGradientPaint.getColor1()); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("Counter# "+j+" Color#2:", expectedValues[j].getColor2(), gotGradientPaint.getColor2()); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("Counter# "+j+" Pos#1:", gotGradientPaint.getPoint1(), expectedValues[j].getPoint1()); //$NON-NLS-1$//$NON-NLS-2$
        assertEquals("Counter# "+j+" Pos#2:", gotGradientPaint.getPoint2(), expectedValues[j].getPoint2()); //$NON-NLS-1$//$NON-NLS-2$
      }
    }
  }
}
