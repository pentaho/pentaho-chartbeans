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

package org.pentaho.chart;

import java.util.HashSet;
import java.util.Arrays;

import junit.framework.TestCase;

import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.core.parser.ChartXMLParser;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;
import org.pentaho.reporting.libraries.css.resolver.StyleResolver;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;

/**
 * Unit tests for the ChartFactory class.
 *
 * @author David Kincade
 */
public class ChartFactoryIT extends TestCase
{
  /**
   * Performs the ChartBoot before performing the tests
   *
   * @throws Exception
   */
  protected void setUp() throws Exception
  {
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
  public void testLoadStyleSheet() throws ResourceException
  {
    ChartFactory.generateChart(getClass().getResource("test1.xml"));
  }

  /**
   * Tests the style resolution to make sure the <code>StyleResolver</code> class is initialized correctly
   */
  public void testStyleResolver() throws ResourceException
  {
    // Create the chart for testing
    final ChartDocument cd = new ChartXMLParser().parseChartDocument(this.getClass().getResource("style_test.xml"));
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);
    final ChartElement child1 = element.getFirstChildItem();
    assertNotNull(child1);
    final ChartElement child2 = element.getLastChildItem();
    assertNotNull(child2);

    // Create the chart document context
    final ChartDocumentContext cdc = new ChartDocumentContext(cd);

    // Get the initialized style resolver
    final StyleResolver sr = ChartFactory.getStyleResolver(cdc);
    assertNotNull(sr);

    // The 1st element of the chart should have no style information
    sr.resolveStyle(element);
    final LayoutStyle parentLayoutStyle = element.getLayoutStyle();
    assertNotNull(parentLayoutStyle);

    // The 1st child shouldn't have any style information either
    sr.resolveStyle(child1);
    final LayoutStyle child1LayoutStyle = child1.getLayoutStyle();
    assertNotNull(child1LayoutStyle);

    // The 2nd child should get different information
    sr.resolveStyle(child2);
    final LayoutStyle child2LayoutStyle = child2.getLayoutStyle();
    assertNotNull(child2LayoutStyle);

    final StyleKey fontFamily = StyleKeyRegistry.getRegistry().findKeyByName("font-family");
    final StyleKey fontSize = StyleKeyRegistry.getRegistry().findKeyByName("font-size");
    final StyleKey fontWeight = StyleKeyRegistry.getRegistry().findKeyByName("font-weight");
    final StyleKey fontStyle = StyleKeyRegistry.getRegistry().findKeyByName("font-style");
    final StyleKey textAlign = StyleKeyRegistry.getRegistry().findKeyByName("text-align");
    final StyleKey color = StyleKeyRegistry.getRegistry().findKeyByName("color");
    final StyleKey backgroundColor = StyleKeyRegistry.getRegistry().findKeyByName("background-color");
    assertNotNull("Could not retrieve the StyleKey for [font-family]", fontFamily);
    assertNotNull("Could not retrieve the StyleKey for [font-size]", fontSize);
    assertNotNull("Could not retrieve the StyleKey for [font-weight]", fontWeight);
    assertNotNull("Could not retrieve the StyleKey for [font-style]", fontStyle);
    assertNotNull("Could not retrieve the StyleKey for [text-align]", textAlign);
    assertNotNull("Could not retrieve the StyleKey for [color]", color);
    assertNotNull("Could not retrieve the StyleKey for [background-color]", backgroundColor);
    final StyleKey[] different1Keys = new StyleKey[]{fontFamily, fontSize, fontWeight, fontStyle, textAlign, color, backgroundColor};
    final StyleKey[] different2Keys = new StyleKey[]{color};

    //******************************************************************************************************************
    // NOTE: THE FOLLOWING TESTS WILL START FAILING WHEN CHANGED ARE MADE TO THE chart.css FILE! THIS IS EXPECTED!
    //       THAT MEANS THAT THIS TEST CASE SHOULD BE CHANGED SO THAT IT PASSES WHEN THAT OCCURS! - the management
    //******************************************************************************************************************

    // The parent tag (chart) and the 1st child tag (title) should have some different styles due to the "default"
    // style information for the <title> tag in the chart.css file
    final StyleKey[] allKeys = StyleKeyRegistry.getRegistry().getKeys();
    final HashSet different1Set = new HashSet(Arrays.asList(different1Keys));
    for (int i = 0; i < allKeys.length; ++i)
    {
      final boolean shouldBeDifferent = different1Set.contains(allKeys[i]);
      if (shouldBeDifferent)
      {
        assertNotSame("parent & child1 - The style information for key [" + allKeys[i] + "] should be different - but it is not! value=[" + parentLayoutStyle.getValue(allKeys[i]) + "]",
            parentLayoutStyle.getValue(allKeys[i]), child1LayoutStyle.getValue(allKeys[i]));
      }
      else
      {
        assertEquals("parent & child1 - The style information for key [" + allKeys[i] + "] should be the same - but it is not!",
            parentLayoutStyle.getValue(allKeys[i]), child1LayoutStyle.getValue(allKeys[i]));
      }
    }

    // The parent tag (chart) and the 2nd child tag (plot) should only have one
    // difference due to the style information added to the plot tag in the style_text.xml document
    final HashSet different2Set = new HashSet(Arrays.asList(different2Keys));
    different2Set.addAll(Arrays.asList(different1Keys));
    for (int i = 0; i < allKeys.length; ++i)
    {
      final boolean shouldBeDifferent = different2Set.contains(allKeys[i]);

      if (shouldBeDifferent)
      {
        assertNotSame("The style information for key [" + allKeys[i] + "] should be different - but it is not! value=[" + parentLayoutStyle.getValue(allKeys[i]) + "]",
            child1LayoutStyle.getValue(allKeys[i]), child2LayoutStyle.getValue(allKeys[i]));
      }
      else
      {
        assertEquals("The style information for key [" + allKeys[i] + "] should be the same - but it is not!",
            child1LayoutStyle.getValue(allKeys[i]), child2LayoutStyle.getValue(allKeys[i]));
      }
    }
  }
}
