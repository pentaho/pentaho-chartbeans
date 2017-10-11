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

package org.pentaho.chart.css.styles;

import org.pentaho.reporting.libraries.css.values.CSSConstant;

/**
 * Defines the only valid values for the <code>-x-pentaho-chart-series-type</code> style
 *
 * @author Ravi Hasija
 */
public class ChartSeriesType {
  public static final CSSConstant UNDEFINED = new CSSConstant("undefined"); //$NON-NLS-1$
  public static final CSSConstant BAR = new CSSConstant("bar"); //$NON-NLS-1$
  public static final CSSConstant PIE = new CSSConstant("pie"); //$NON-NLS-1$
  public static final CSSConstant LINE = new CSSConstant("line"); //$NON-NLS-1$
  public static final CSSConstant AREA = new CSSConstant("area"); //$NON-NLS-1$
  public static final CSSConstant WATERFALL = new CSSConstant ("waterfall"); //$NON-NLS-1$
  public static final CSSConstant MULTI = new CSSConstant ("multi");  //$NON-NLS-1$
  public static final CSSConstant DIAL = new CSSConstant ("dial");  //$NON-NLS-1$

//  public ChartSeriesType() {
//  }
}
