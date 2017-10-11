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

package org.pentaho.chart.plugin.jfreechart.chart.line;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartLineStyle;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * @author wseyler
 *
 */
public class JFreeLineChartGeneratorFactory {

  /**
   * @param chartDocumentContext
   * @param data
   * @return
   */
  public JFreeChart createChart(ChartDocumentContext chartDocumentContext, ChartTableModel data) {
    boolean threeD = false;

    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    for (final ChartElement element : elements) {
      CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.LINE_STYLE);
      threeD |= ChartLineStyle.THREE_D.equals(value);
      break;
    }

    final JFreeLineChartGenerator lineChartGenerator;

    if (threeD) {
      lineChartGenerator = new JFree3DLineChartGenerator();
    } else {
      lineChartGenerator = new JFreeDefaultLineChartGenerator();
    }

    return lineChartGenerator.createChart(chartDocumentContext, data);
  }

}
