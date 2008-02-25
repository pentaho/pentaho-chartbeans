/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created 2/7/2008 
 * @author David Kincade 
 */
package org.pentaho.experimental.chart;

import junit.framework.TestCase;
import org.jfree.resourceloader.ResourceException;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.parser.ChartXMLParser;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;
import org.pentaho.reporting.libraries.css.resolver.StyleResolver;

/**
 * Unit tests for the ChartFactory class.
 *
 * @author David Kincade
 */
public class ChartFactoryTest extends TestCase {
  /**
   * Performs the ChartBoot before performing the tests
   *
   * @throws Exception
   */
  protected void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }

  /**
   * Tests the loading and consolidation of the style sheet information. This
   * test will load the sample report which has the following style sheet information
   * in the following order:
   * <pre>
   *   <stylesheet>
   *     .test1 { width: 100px; color: BLUE }
   *   </stylesheet>
   * <p/>
   *   <stylesheet href="test1.css" /> <!-- contains .test1 { border: SOLID } -->
   * <p/>
   *   <stylesheet href="test2.css" >  <!-- contains .test1 { color: RED }  .test2 { width: 150px; color: BLACK } -->
   *     .test2 { width: 200 px }
   *   </stylesheet>
   * </pre>
   */
  public void testLoadStyleSheet() throws ResourceException {
    ChartFactory.generateChart(getClass().getResource("test1.xml"));
  }

  /**
   * Tests the style resolution to make sure the <code>StyleResolver</code> class is initialized correctly
   */
  public void testStyleResolver() throws ResourceException {
    // Create the chart for testing
    ChartDocument cd = new ChartXMLParser().parseChartDocument(this.getClass().getResource("style_test.xml"));
    assertNotNull(cd);
    ChartElement element = cd.getRootElement();
    assertNotNull(element);
    ChartElement child1 = element.getFirstChildItem();
    assertNotNull(child1);
    ChartElement child2 = element.getLastChildItem();
    assertNotNull(child2);

    // Create the chart document context
    ChartDocumentContext cdc = new ChartDocumentContext(cd);

    // Get the initialized style resolver
    StyleResolver sr = ChartFactory.getStyleResolver(cdc);
    assertNotNull(sr);

    // The 1st element of the chart should have no style information
    sr.resolveStyle(element);
    LayoutStyle parentLayoutStyle = element.getLayoutStyle();
    assertNotNull(parentLayoutStyle);

    // The 1st child shouldn't have any style information either
    sr.resolveStyle(child1);
    LayoutStyle child1LayoutStyle = child1.getLayoutStyle();
    assertNotNull(child1LayoutStyle);

    // The 2nd child should get different information
    sr.resolveStyle(child2);
    LayoutStyle child2LayoutStyle = child2.getLayoutStyle();
    assertNotNull(child2LayoutStyle);

    StyleKey fontFamily = StyleKeyRegistry.getRegistry().findKeyByName("font-family");
    StyleKey fontSize = StyleKeyRegistry.getRegistry().findKeyByName("font-size");
    StyleKey fontWeight = StyleKeyRegistry.getRegistry().findKeyByName("font-weight");
    StyleKey fontStyle = StyleKeyRegistry.getRegistry().findKeyByName("font-style");
    StyleKey textAlign = StyleKeyRegistry.getRegistry().findKeyByName("text-align");
    StyleKey color = StyleKeyRegistry.getRegistry().findKeyByName("color");
    StyleKey backgroundColor = StyleKeyRegistry.getRegistry().findKeyByName("background-color");
    assertNotNull("Could not retrieve the StyleKey for [font-family]", fontFamily);
    assertNotNull("Could not retrieve the StyleKey for [font-size]", fontSize);
    assertNotNull("Could not retrieve the StyleKey for [font-weight]", fontWeight);
    assertNotNull("Could not retrieve the StyleKey for [font-style]", fontStyle);
    assertNotNull("Could not retrieve the StyleKey for [text-align]", textAlign);
    assertNotNull("Could not retrieve the StyleKey for [color]", color);
    assertNotNull("Could not retrieve the StyleKey for [background-color]", backgroundColor);
    StyleKey[] different1Keys = new StyleKey[]{fontFamily, fontSize, fontWeight, fontStyle, textAlign, color, backgroundColor};
    StyleKey[] different2Keys = new StyleKey[]{color};

    //******************************************************************************************************************
    // NOTE: THE FOLLOWING TESTS WILL START FAILING WHEN CHANGED ARE MADE TO THE chart.css FILE! THIS IS EXPECTED!
    //       THAT MEANS THAT THIS TEST CASE SHOULD BE CHANGED SO THAT IT PASSES WHEN THAT OCCURS! - the management
    //******************************************************************************************************************

    // The parent tag (chart) and the 1st child tag (title) should have some different styles due to the "default"
    // style information for the <title> tag in the chart.css file
    StyleKey[] allKeys = StyleKeyRegistry.getRegistry().getKeys();
    for (int i = 0; i < allKeys.length; ++i) {
      boolean different = false;
      for (int j = 0; j < different1Keys.length && !different; ++j) {
        different = allKeys[i].equals(different1Keys[j]);
      }

      if (different) {
        assertNotSame("parent & child1 - The style information for key [" + allKeys[i] + "] should be different - but it is not! value=[" + parentLayoutStyle.getValue(allKeys[i]) + "]",
            parentLayoutStyle.getValue(allKeys[i]), child1LayoutStyle.getValue(allKeys[i]));
      } else {
        assertEquals("parent & child1 - The style information for key [" + allKeys[i] + "] should be the same - but it is not!",
            parentLayoutStyle.getValue(allKeys[i]), child1LayoutStyle.getValue(allKeys[i]));
      }
    }

    // The parent tag (chart) and the 2nd child tag (plot) should only have one difference due to the style
    // information added to the plot tag in the style_text.xml document
    for (int i = 0; i < allKeys.length; ++i) {
      boolean different = false;
      for (int j = 0; j < different2Keys.length && !different; ++j) {
        different = allKeys[i].equals(different2Keys[j]);
      }

      if (different) {
        assertNotSame("The style information for key [" + allKeys[i] + "] should be different - but it is not! value=[" + parentLayoutStyle.getValue(allKeys[i]) + "]",
            child1LayoutStyle.getValue(allKeys[i]), child2LayoutStyle.getValue(allKeys[i]));
      } else {
        assertEquals("The style information for key [" + allKeys[i] + "] should be the same - but it is not!",
            child1LayoutStyle.getValue(allKeys[i]), child2LayoutStyle.getValue(allKeys[i]));
      }
    }
  }
}
