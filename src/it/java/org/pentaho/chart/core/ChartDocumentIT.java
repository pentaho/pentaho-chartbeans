/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.chart.core;

import junit.framework.TestCase;

import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;

/**
 * Tests for the ChartDocument class
 *
 * @author David Kincade
 */
public class ChartDocumentIT extends TestCase {
	
  public void setUp() {
    StyleKeyRegistry.performBoot();  
  }	
  	
  /**
   * Tests for the <code>booleanAttributeValue()</code> method
   */
  public void testBooleanAttributeValue() {
    // Test the method where the default should be used
    final ChartElement blankElement = new ChartElement();
    assertEquals("The default value is not being used when the attribute does not exist", false, ChartDocument.booleanAttributeValue(blankElement, "test", false));
    assertEquals("The default value is not being used when the attribute does not exist", true, ChartDocument.booleanAttributeValue(blankElement, "test", true));

    // Test the method when the value is null
    final ChartElement nullValueElement = new ChartElement();
    nullValueElement.setAttribute("test", null);
    assertEquals("The default value is not being used when the attribute's value is null", false, ChartDocument.booleanAttributeValue(blankElement, "test", false));
    assertEquals("The default value is not being used when the attribute's value is null", true, ChartDocument.booleanAttributeValue(blankElement, "test", true));

    // Test the method when the attributes have valid values
    final ChartElement validValueElement = new ChartElement();
    validValueElement.setAttribute("true1", "true");
    validValueElement.setAttribute("true2", "tRUE");
    validValueElement.setAttribute("true3", "Yes");
    validValueElement.setAttribute("true4", "oN");
    validValueElement.setAttribute("false1", "false");
    validValueElement.setAttribute("false2", "OFF");
    validValueElement.setAttribute("false3", "nO");
    validValueElement.setAttribute("false4", "");
    validValueElement.setAttribute("junk", "junk");

    assertEquals("The default value is not being used when the attribute's value is neither true or false", false, ChartDocument.booleanAttributeValue(blankElement, "junk", false));
    assertEquals("The default value is not being used when the attribute's value is neither true or false", true, ChartDocument.booleanAttributeValue(blankElement, "junk", true));

    assertEquals("The method is not interpreting the value correctly", true, ChartDocument.booleanAttributeValue(validValueElement, "true1", false));
    assertEquals("The method is not interpreting the value correctly", true, ChartDocument.booleanAttributeValue(validValueElement, "true2", false));
    assertEquals("The method is not interpreting the value correctly", true, ChartDocument.booleanAttributeValue(validValueElement, "true3", false));
    assertEquals("The method is not interpreting the value correctly", true, ChartDocument.booleanAttributeValue(validValueElement, "true4", false));

    assertEquals("The method is not interpreting the value correctly", false, ChartDocument.booleanAttributeValue(validValueElement, "false1", true));
    assertEquals("The method is not interpreting the value correctly", false, ChartDocument.booleanAttributeValue(validValueElement, "false2", true));
    assertEquals("The method is not interpreting the value correctly", false, ChartDocument.booleanAttributeValue(validValueElement, "false3", true));
    assertEquals("The method is not interpreting the value correctly", false, ChartDocument.booleanAttributeValue(validValueElement, "false4", true));
  }

  /**
   * Tests for the <code>isCategorical()</code> method
   */
  public void testIsCategorical() throws ResourceException {
	  
    // Load a chart where the chart element is not the root, but categorical is true
    ChartDocumentContext cdc = ChartFactory.generateChart(this.getClass().getResource("ChartDocumentTest1.xml"));
    assertEquals(false, cdc.getChartDocument().isCategorical());

    // Load a chart where categorical is specified correctly in the root element
    cdc = ChartFactory.generateChart(this.getClass().getResource("ChartDocumentTest2.xml"));
    assertEquals(true, cdc.getChartDocument().isCategorical());

    // Load a chart where the chart tag is the top-most tag, but categorical is not set at all
    cdc = ChartFactory.generateChart(this.getClass().getResource("ChartDocumentTest3.xml"));
    assertEquals(false, cdc.getChartDocument().isCategorical());
  }

  /**
   * Tests for the <code>isByRow()</code> method
   */
  public void testIsByRow() throws ResourceException {
    // Load a chart where the chart element is not the root, but byrow is true
    ChartDocumentContext cdc = ChartFactory.generateChart(this.getClass().getResource("ChartDocumentTest1.xml"));
    assertEquals(false, cdc.getChartDocument().isCategorical());

    // Load a chart where byrow is specified correctly in the root element
    cdc = ChartFactory.generateChart(this.getClass().getResource("ChartDocumentTest2.xml"));
    assertEquals(true, cdc.getChartDocument().isCategorical());

    // Load a chart where the chart tag is the top-most tag, but byrow is not set at all
    cdc = ChartFactory.generateChart(this.getClass().getResource("ChartDocumentTest3.xml"));
    assertEquals(false, cdc.getChartDocument().isCategorical());
  }

  /**
   * Tests for the <code>getSeriesTags()</code> helper methods
   */
  public void testGetSeriesTags() {
    final ChartElement rootElement = new ChartElement();
    rootElement.setTagName(ChartElement.TAG_NAME_CHART);
    final ChartElement series1 = new ChartElement(); series1.setTagName(ChartElement.TAG_NAME_SERIES);
    final ChartElement series2 = new ChartElement(); series2.setTagName(ChartElement.TAG_NAME_SERIES);
    final ChartElement series3 = new ChartElement(); series3.setTagName(ChartElement.TAG_NAME_SERIES);
    final ChartElement series4 = new ChartElement(); series4.setTagName(ChartElement.TAG_NAME_SERIES);
    final ChartDocument doc = new ChartDocument(rootElement);
    rootElement.addChildElement(new ChartElement());
    rootElement.addChildElement(series1);
    rootElement.addChildElement(new ChartElement());
    rootElement.addChildElement(series2);
    series2.addChildElement(series3);
    rootElement.addChildElement(series4);

    final ChartElement[] elements = doc.getSeriesChartElements();
    assertNotNull(elements);
    assertEquals(3, elements.length);
    assertEquals(series1, elements[0]);
    assertEquals(series2, elements[1]);
    assertEquals(series4, elements[2]);
  }

  /**
   * Tests for the <code>getSeriesTags()</code> helper methods
   */
  public void testGetGroupTags() {
    final ChartElement rootElement = new ChartElement();
    rootElement.setTagName(ChartElement.TAG_NAME_CHART);
    final ChartElement series1 = new ChartElement(); series1.setTagName(ChartElement.TAG_NAME_GROUP);
    final ChartElement series2 = new ChartElement(); series2.setTagName(ChartElement.TAG_NAME_GROUP);
    final ChartElement series3 = new ChartElement(); series3.setTagName(ChartElement.TAG_NAME_GROUP);
    final ChartElement series4 = new ChartElement(); series4.setTagName(ChartElement.TAG_NAME_GROUP);
    final ChartDocument doc = new ChartDocument(rootElement);
    rootElement.addChildElement(series1);
    rootElement.addChildElement(new ChartElement());
    rootElement.addChildElement(series2);
    series2.addChildElement(series3);
    rootElement.addChildElement(series4);
    rootElement.addChildElement(new ChartElement());

    final ChartElement[] elements = doc.getGroupChartElements();
    assertNotNull(elements);
    assertEquals(3, elements.length);
    assertEquals(series1, elements[0]);
    assertEquals(series2, elements[1]);
    assertEquals(series4, elements[2]);
  }

  public void testGetPlotElement() throws ResourceException {
    final String[] testFileNames = {
        "ChartDocumentTest1.xml", //$NON-NLS-1$
        "ChartDocumentTest2.xml", //$NON-NLS-1$
        "ChartDocumentTest3.xml", //$NON-NLS-1$
        "ChartDocumentTest4.xml", //$NON-NLS-1$
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
        assertNotNull(plotElement);
      }
    }
  }
}
