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

package org.pentaho.chart.css.parser.stylehandler;

import org.pentaho.chart.css.styles.ChartBarStyle;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-bar-style</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartBarStyleReadHandler extends OneOfConstantsReadHandler {
  public ChartBarStyleReadHandler() {
    super(false);
    addValue(ChartBarStyle.BAR);
    addValue(ChartBarStyle.CYLINDER);
    addValue(ChartBarStyle.INTERVAL);
    addValue(ChartBarStyle.LAYERED);
    addValue(ChartBarStyle.STACKED);
    addValue(ChartBarStyle.STACK_PERCENT);
    addValue(ChartBarStyle.STACK_100_PERCENT);
    addValue(ChartBarStyle.WATERFALL);
  }
}
