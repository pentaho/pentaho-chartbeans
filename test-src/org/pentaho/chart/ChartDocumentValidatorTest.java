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
