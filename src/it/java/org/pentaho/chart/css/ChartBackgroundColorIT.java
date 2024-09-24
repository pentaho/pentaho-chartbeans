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
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;

public class ChartBackgroundColorIT extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    ChartBoot.getInstance().start();
  }

  public void testChartBackgroundColor() throws Exception {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ChartBackgroundColorTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    CSSColorValue value = (CSSColorValue) element.getLayoutStyle().getValue(BorderStyleKeys.BACKGROUND_COLOR);
    
    // background-color style property on chart element (root) should be red
    assertEquals(255, value.getRed());
    assertEquals(0, value.getGreen());
    assertEquals(0, value.getBlue());
  }
}
