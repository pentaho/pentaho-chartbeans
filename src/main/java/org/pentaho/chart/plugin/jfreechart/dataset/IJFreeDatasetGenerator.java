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

package org.pentaho.chart.plugin.jfreechart.dataset;

import org.jfree.data.general.Dataset;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

/**
 * This interface binds the implementing classes to create a createDataset method that returns
 * org.jfree.data.general.Dataset type. We return the generic Dataset type so as to allow changes
 * to the implementation without affecting the interface.
 * </p>
 * Author: Ravi Hasija
 * Date: May 13, 2008
 * Time: 1:03:19 PM
 */
public interface IJFreeDatasetGenerator {
  public Dataset createDataset(final ChartDocumentContext chartDocContext, final ChartTableModel data);
  public Dataset createDataset(final ChartDocumentContext chartDocContext, final ChartTableModel data, final Integer[] columnPosArr);
}
