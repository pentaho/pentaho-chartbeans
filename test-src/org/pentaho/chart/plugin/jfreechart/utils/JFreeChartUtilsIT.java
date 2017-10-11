/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

/**
 * 
 */

package org.pentaho.chart.plugin.jfreechart.utils;


import java.awt.Color;
import java.awt.GradientPaint;

import junit.framework.TestCase;

import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.jfree.ui.GradientPaintTransformType;
import org.junit.After;
import org.junit.Before;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;

/**
 * @author Ravi Hasija
 *
 */
public class JFreeChartUtilsIT extends TestCase {

  final String[] testFileNames = {
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
	  StyleKeyRegistry.performBoot();	  
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }
  
  public void testGetOrientation() throws ResourceException {
    for (int i = 0; i < testFileNames.length; i++) {
      final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource(testFileNames[i]));
      assertNotNull(cdc);
      final ChartDocument cd = cdc.getChartDocument();
      assertNotNull(cd);    

      final ChartElement plotElement = cd.getPlotElement();
      if (i == 0) {
        assertEquals(null, plotElement);
      } else {  
        //assertEquals(expectedValues[i], JFreeChartUtils.getPlotOrientation(cd));
      }
    }
  }

  public void testShowURLs() throws ResourceException {

    final boolean[] expectedValues = {
         false,
         true,
         false,
         false,
    };

    for (int i = 0; i < testFileNames.length; i++) {
      final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource(testFileNames[i]));
      assertNotNull(cdc);
      final ChartDocument cd = cdc.getChartDocument();
      assertNotNull(cd);    

      final ChartElement plotElement = cd.getPlotElement();
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
    
    final GradientPaint[] expectedValues = new GradientPaint[] {
      null,
      new GradientPaint(0,0, Color.black, 1,1, Color.white),
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
    
    final String[] testGradFileNames = {
        "JFreeChartUtilsTestGradColor.xml", //$NON-NLS-1$
    };

    
    for (int i = 0; i < testGradFileNames.length; i++) {
      final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource(testGradFileNames[i]));
      assertNotNull(cdc);
      final ChartDocument cd = cdc.getChartDocument();
      assertNotNull(cd);
      
      final ChartElement[] seriesList = cd.getSeriesChartElements();
      if (seriesList.length == 0) {
        fail("The Series list should never be empty."); //$NON-NLS-1$       
      }
      
      for (int seriesCounter = 0; seriesCounter < seriesList.length; seriesCounter++) {
        final ChartElement currElement = seriesList[seriesCounter];
        final GradientPaint gotGradientPaint = JFreeChartUtils.getGradientPaint(currElement);
        if (seriesCounter==0) {
          assertNull(gotGradientPaint);
        } else {
          assertNotNull(gotGradientPaint);
          assertEquals("Counter# "+seriesCounter+" Color#1:", expectedValues[seriesCounter].getColor1(), gotGradientPaint.getColor1()); //$NON-NLS-1$//$NON-NLS-2$
          assertEquals("Counter# "+seriesCounter+" Color#2:", expectedValues[seriesCounter].getColor2(), gotGradientPaint.getColor2()); //$NON-NLS-1$//$NON-NLS-2$
          assertEquals("Counter# "+seriesCounter+" Pos#1:", gotGradientPaint.getPoint1(), expectedValues[seriesCounter].getPoint1()); //$NON-NLS-1$//$NON-NLS-2$
          assertEquals("Counter# "+seriesCounter+" Pos#2:", gotGradientPaint.getPoint2(), expectedValues[seriesCounter].getPoint2()); //$NON-NLS-1$//$NON-NLS-2$
        }
      }
    }
  }
  
  public void testGetStandardGradientPaint() throws ResourceException {
    final String[] testGradFileNames = {
        "StandardGradientPaint.xml", //$NON-NLS-1$
    };
    
    final GradientPaintTransformType[] expectedValues = new GradientPaintTransformType[] {
      null,
      null,
      null,
      GradientPaintTransformType.HORIZONTAL,
      GradientPaintTransformType.VERTICAL,
      GradientPaintTransformType.CENTER_HORIZONTAL,
      GradientPaintTransformType.CENTER_VERTICAL,
    };
    
    for (final String fileName : testGradFileNames) {
      final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource(fileName));
      assertNotNull(cdc);
      final ChartDocument cd = cdc.getChartDocument();
      assertNotNull(cd);
      
      final ChartElement[] seriesList = cd.getSeriesChartElements();
      if (seriesList.length == 0) {
        fail("The Series list should never be empty."); //$NON-NLS-1$       
      }
      
      for (int i=0; i< seriesList.length; i++){
        final ChartElement ce = seriesList[i];
        if (i == 0 || i == 1 || i == 2) {
          assertNull(JFreeChartUtils.getStandardGradientPaintTrans(ce));
        } else {          
          assertEquals(expectedValues[i], JFreeChartUtils.getStandardGradientPaintTrans(ce).getType());
        }
      }      
    }    
  }    
}
