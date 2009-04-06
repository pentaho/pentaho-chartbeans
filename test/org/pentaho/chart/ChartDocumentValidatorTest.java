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
 * Created 4/8/2008 
 * @author David Kincade 
 */
package org.pentaho.chart;

import junit.framework.TestCase;

import org.pentaho.chart.ChartDocumentValidator;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.reporting.libraries.base.util.StringUtils;

/**
 * Tests for the <code>ChartDocumentValidator</code> class.
 * @author David Kincade
 */
public class ChartDocumentValidatorTest extends TestCase {
  /**
   * Tests for the <code>validateGroupTags</code> method
   */
  public void testValidateGroupTags() throws Exception {
    // A blank example should produce no messages
    {
      ChartDocumentValidator validator = new ChartDocumentValidator();
      ChartElement[] list = new ChartElement[0];
      validator.validateGroupTags(list);
      assertEquals(0, validator.getMessageCount());
    }

    // A simple example should produce no errors (and skip the non-group tags)
    {
      ChartDocumentValidator validator = new ChartDocumentValidator();
      ChartElement [] elements = createChartElements(new String[] {ChartElement.TAG_NAME_GROUP, ChartElement.TAG_NAME_GROUP, "test", ChartElement.TAG_NAME_GROUP, ChartElement.TAG_NAME_GROUP});
      elements[0].addChildElement(elements[1]);
      elements[1].addChildElement(elements[2]);
      elements[1].addChildElement(elements[3]);
      elements[3].addChildElement(elements[4]);
      elements[3].setAttribute(ChartElement.STACKED, "on");
      ChartElement[] list = new ChartElement[] { elements[0] };
      validator.validateGroupTags(list);
      assertEquals(0, validator.getMessageCount());
      assertNull(elements[0].getAttribute(ChartElement.STACKED));
      assertNull(elements[1].getAttribute(ChartElement.STACKED));
      assertNull(elements[2].getAttribute(ChartElement.STACKED));
      assertTrue(StringUtils.toBoolean((String)elements[3].getAttribute(ChartElement.STACKED)));
      assertTrue(StringUtils.toBoolean((String)elements[4].getAttribute(ChartElement.STACKED)));
    }
  }

  private ChartElement[] createChartElements(String [] tagNames) {
    ChartElement[] elements = new ChartElement[tagNames.length];
    for (int i = 0; i < elements.length; ++i) {
      elements[i] = new ChartElement();
      elements[i].setTagName(tagNames[i]);
    }
    return elements;
  }
}
