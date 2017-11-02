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

package org.pentaho.chart.plugin.jfreechart.chart.multi;

/**
 * These are the different high level bar chart types based on the dataset and the chart type used to create them.
 * </p> 
 * Author: Ravi Hasija
 * Date: May 15, 2008
 * Time: 1:07:41 PM 
 */
public class JFreeMultiChartTypes {
  public static final String AREALINE="areaLine";  //$NON-NLS-1$
  public static final String BARLINE="barLine";  //$NON-NLS-1$
  public static final String AREABAR="areaBar";  //$NON-NLS-1$
  public static final String MULTI = "multi";  //$NON-NLS-1$

  /**
   * To avoid instantiation from outside world.
   */
  private JFreeMultiChartTypes() {
  }
}
