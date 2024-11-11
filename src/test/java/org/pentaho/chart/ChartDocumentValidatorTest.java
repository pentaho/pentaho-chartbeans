/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


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
