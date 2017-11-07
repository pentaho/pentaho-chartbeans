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

package org.pentaho.chart.plugin.jfreechart.chart.area;

import org.jfree.chart.JFreeChart;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.css.styles.ChartAreaStyle;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Creates stacked area charts.
 * Author: Ravi Hasija
 * Date: May 19, 2008
 * Time: 4:37:13 PM
 */
public class JFreeStackedAreaChartGenerator extends JFreeAreaChartGenerator {
  //TODO: Not working quite right. Has spaces in the final chart that look odd.
  protected JFreeChart doCreateChart(ChartDocumentContext chartDocContext, ChartTableModel data) {
    final JFreeChart chart = createChart(chartDocContext, data, ChartAreaStyle.STACKED);

    /*
     * NOTE: The chart object will be updated.
     */
    createRangeAxis(chartDocContext, data, chart);
    return chart;
  }

}
