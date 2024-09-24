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
import org.pentaho.chart.css.styles.ChartLineStyle;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSConstant;

public class LineStyleIT extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
  }
  
  public void testLineStyle() throws Exception {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("LineStyleTest.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    final ChartElement element = cd.getRootElement();
    assertNotNull(element);

    int index = 0;
    final CSSConstant[] passValues = new CSSConstant[] { ChartLineStyle.SOLID, ChartLineStyle.DASHED, ChartLineStyle.DOT_DOT_DASH, ChartLineStyle.SOLID, ChartLineStyle.SOLID };
    ChartElement child = element.getFirstChildItem().getNextItem();
    
    while(child != null) {
      final LayoutStyle layoutStyle = child.getLayoutStyle();
      assertNotNull(layoutStyle);
      System.out.println("expected: "+passValues[index]+" - got: "+layoutStyle.getValue(ChartStyleKeys.LINE_STYLE)); //$NON-NLS-1$ //$NON-NLS-2$
      assertEquals(passValues[index++], layoutStyle.getValue(ChartStyleKeys.LINE_STYLE));
      child = child.getNextItem();
    }
  }
  
}
