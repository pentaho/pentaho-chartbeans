/*
 * Copyright 2008 Pentaho Corporation.  All rights reserved. 
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
 * Created  
 * @author David Kincade
 */
package org.pentaho.experimental.chart.core;

import junit.framework.TestCase;
import org.jfree.resourceloader.Resource;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.ChartBoot;

import java.net.URL;

public class ParseTest extends TestCase {

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
   * Tests the ability of the system to parse the supplied XML document and constuct a correct
   * ChartDocument and ChartElement tree. The tree should be complete with all elements
   * and each element should contain the information (tag name, text, attributes, etc.)
   */
  @SuppressWarnings("nls")
  public void testParser() throws Exception {
    // Parse the test document located in the same class location as this class
    URL xmlDocument = this.getClass().getResource("ParseTest.xml");
    ResourceManager resourceManager = new ResourceManager();
    resourceManager.registerDefaults();
    Resource res = resourceManager.createDirectly(xmlDocument, ChartDocument.class);
    ChartDocument doc = (ChartDocument) res.getResource();
    if (doc == null) {
      fail("A null document should never be returned");
    }

    // Test the resulting document
    //
    // <chart xmlns="http://www.pentaho.org/namespaces/chart">
    // ...
    // </chart>
    //
    ChartElement rootElement = doc.getRootElement();
    assertNotNull(rootElement);
    assertEquals("chart", rootElement.getTagName());
    assertEquals(12, rootElement.getChildCount());
    assertNull(rootElement.getText());

    // 1st Element
    //
    //   <inline-stylesheet>
    //     axis {
    //         color: #FFF000;
    //         font-family: sans-serif;
    //     }
    //     axis label {
    //         font-weight: bold;
    //     }
    //     axis[type="numeric"] {
    //         format: "#.00%";
    //     }
    //     axis[type="datetime"] {
    //         format: "MM/dd  hh:mm a";
    //     }
    //     axis.myAxis {
    //         color: blue;
    //         font-family: "Comic Sans MS";
    //     }
    //
    //   </inline-stylesheet>
    ChartElement element = rootElement.getFirstChildItem();
    assertNotNull(element);
    assertEquals("inline-stylesheet", element.getTagName());
    assertNotNull(element.getText());
    assertTrue(element.getText().length() > 0);
    assertTrue(element.getText().indexOf("\"Comic Sans MS\"") > -1);
    assertEquals(0, element.getChildCount());

    // 2nd element
    // <external-stylesheet href="sample1.css"/>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("external-stylesheet", element.getTagName());
    assertNull(element.getText());
    assertNotNull(element.getAttributeMap());
    assertEquals("sample1.css", element.getAttribute("href"));

    // 3rd element
    //   <plot/>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("plot", element.getTagName());
    assertNull(element.getText());
    assertNotNull(element.getAttributeMap());
    assertNull(element.getAttribute("test"));

    // 4th element
    //  <axis type="numeric" id="y1" position="left" style="">
    //    <!-- This label represents the title for the axis -->
    //    <label><![CDATA[This is <b>my</b> chart]]></label>
    //  </axis>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("axis", element.getTagName());
    assertNull(element.getText());
    assertNotNull(element.getAttributeMap());
    assertEquals("numeric", element.getAttribute("type"));
    assertEquals("y1", element.getAttribute("id"));
    assertEquals("left", element.getAttribute("position"));
    assertEquals("", element.getAttribute("style"));
    assertNull(element.getAttribute("Position")); // Note the uppercase 'P'
    ChartElement childElement = element.getFirstChildItem();
    assertNotNull(childElement);
    assertEquals(childElement, element.getLastChildItem());
    assertEquals("label", childElement.getTagName());
    assertEquals("This is <b>my</b> chart", childElement.getText());
    assertEquals(0, childElement.getChildCount());

    // 5th element - http://www.imdb.com/title/tt0119116/
    //   <axis class="myAxis" type="numeric" id="y2" position="right">
    //    <!-- This label represents the title for the axis -->
    //    <label>Test</label>
    //  </axis>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("axis", element.getTagName());
    assertNull(element.getText());
    assertNotNull(element.getAttributeMap());
    assertEquals("numeric", element.getAttribute("type"));
    assertEquals("y2", element.getAttribute("id"));
    assertEquals("right", element.getAttribute("position"));
    assertNull(element.getAttribute("style"));
    assertNull(element.getAttribute("Position")); // Note the uppercase 'P'
    childElement = element.getFirstChildItem();
    assertNotNull(childElement);
    assertEquals(childElement, element.getLastChildItem());
    assertEquals("label", childElement.getTagName());
    assertEquals("Test", childElement.getText());
    assertEquals(0, childElement.getChildCount());

    // 6th element
    //   <axis type="datetime" id="x1" position="bottom">
    //    <!-- This label represents the title for the axis -->
    //    <label/>
    //  </axis>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("axis", element.getTagName());
    assertNull(element.getText());
    assertNotNull(element.getAttributeMap());
    assertEquals("datetime", element.getAttribute("type"));
    assertEquals("x1", element.getAttribute("id"));
    assertEquals("bottom", element.getAttribute("position"));
    assertNull(element.getAttribute("style"));
    childElement = element.getFirstChildItem();
    assertNotNull(childElement);
    assertEquals(childElement, element.getLastChildItem());
    assertEquals("label", childElement.getTagName());
    assertNull(childElement.getText());
    assertEquals(0, childElement.getChildCount());

    // 7th element
    // <legend/>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("legend", element.getTagName());

    // 8th element
    // <title/>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("title", element.getTagName());

    // 9th element
    // <subtitles>
    //   <subtitle></subtitle>
    //   <subtitle></subtitle>
    // </subtitles>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("subtitles", element.getTagName());
    assertEquals(2, element.getChildCount());
    assertNotSame(element.getFirstChildItem(), element.getLastChildItem());
    assertEquals(element.getFirstChildItem().getTagName(), element.getLastChildItem().getTagName());

    // 10th element
    // <chart-series>
    //   <series id="" position="0" axis="y1"></series>
    //   <series id="" position="1" axis="y2"></series>
    // </chart-series>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("chart-series", element.getTagName());
    childElement = element.getFirstChildItem();
    assertNotNull(childElement);
    assertEquals("series", childElement.getTagName());
    assertEquals("0", childElement.getAttribute("position"));
    assertEquals("y1", childElement.getAttribute("axis"));
    assertEquals("", childElement.getAttribute("id"));
    assertNull(childElement.getAttribute("ID"));
    assertEquals(0, childElement.getChildCount());

    // 11th element
    // <annotations>
    //   <annotation></annotation>
    // </annotations>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("annotations", element.getTagName());

    // 12th elemenet
    // <intervals>
    //   <interval></interval>
    // </intervals>
    element = element.getNextItem();
    assertNotNull(element);
    assertEquals("intervals", element.getTagName());

    // 13th element - ok ... there isn't one
    element = element.getNextItem();
    assertNull(element);

    // Test the ability to retrieve children by name
    assertEquals(0, rootElement.findChildrenByName(null).length); // no key ... array should be empty
    assertEquals(0, rootElement.findChildrenByName("").length); // blank key ... none match that
    assertEquals(0, rootElement.findChildrenByName("foo").length); // foo doesn't exist in the test
    assertEquals(0, rootElement.findChildrenByName("subtitle").length); // subtitle exists, but not as a direct descendent of the root
    final ChartElement[] elements = rootElement.findChildrenByName("axis");
    assertEquals(3, elements.length);
    assertTrue(!elements[0].getAttribute("id").equals(elements[1].getAttribute("id")));
    assertTrue(!elements[0].getAttribute("id").equals(elements[2].getAttribute("id")));
    assertTrue(!elements[1].getAttribute("id").equals(elements[2].getAttribute("id")));
  }
}
