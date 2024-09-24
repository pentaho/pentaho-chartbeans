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

package org.pentaho.chart.css;

import junit.framework.TestCase;

import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSStringType;
import org.pentaho.reporting.libraries.css.values.CSSStringValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * Tests the -x-pentaho-chart-drill-url-template style
 */
public class DrillUrlStyleIT extends TestCase {
  protected void setUp() throws Exception {
    super.setUp();
    ChartBoot.getInstance().start();
  }

  public void testDrillUrl() throws Exception {
    // Get the chart
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("DrillUrlTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    ChartElement element = cd.getRootElement();
    assertNotNull(element);
    element = element.getFirstChildItem();
    assertNotNull(element);

    // Check the values
    final Object [] validResults = new Object[] {
      new CSSConstant("none"),
      new CSSConstant("none"),
      new CSSStringValue(CSSStringType.URI, "DrillUrlTest.xml"),
      new CSSStringValue(CSSStringType.URI, "DoesNotExist.file"),
    };
    int i = 0;
    while(element != null) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.DRILL_URL);
      System.out.println("expecting: "+validResults[i]+" : received: "+value);
      validResults[i].equals(value);
      assertEquals(validResults[i], value);
      element = element.getNextItem();
      ++i;
    }
  }
}
