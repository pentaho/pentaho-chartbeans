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
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Basic implementation for returning DefaultIntervalCategoryDataset.
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 2:43:02 PM
 */
public class JFreeDefaultIntervalCategoryDatasetGenerator implements IJFreeDatasetGenerator, Dataset {

  public Dataset createDataset(final ChartDocumentContext chartDocContext, final ChartTableModel data) {
    return null;
  }

  public Dataset createDataset(ChartDocumentContext chartDocContext, ChartTableModel data, Integer[] columnPosArr) {
    return null;  
  }

  public void addChangeListener(DatasetChangeListener datasetChangeListener) {
  }

  public void removeChangeListener(DatasetChangeListener datasetChangeListener) {
  }

  public DatasetGroup getGroup() {
    return null;
  }

  public void setGroup(DatasetGroup datasetGroup) {
  }
}
