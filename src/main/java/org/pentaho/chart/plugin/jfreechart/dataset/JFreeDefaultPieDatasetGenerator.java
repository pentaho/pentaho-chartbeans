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
import org.jfree.data.general.DefaultPieDataset;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.base.config.Configuration;

/**
 * Creates DefaultPieDataset.
 * </p>
 * Author: Ravi Hasija
 * Date: May 20, 2008
 * Time: 3:41:15 PM 
 */
public class JFreeDefaultPieDatasetGenerator implements IJFreeDatasetGenerator {

  public Dataset createDataset(final ChartDocumentContext chartDocContext,
                               final ChartTableModel data) {
    final DefaultPieDataset dataset = new DefaultPieDataset();
    final int rowCount = data.getRowCount();
    final Configuration config = ChartBoot.getInstance().getGlobalConfig();
    final String noRowNameSpecified = config.getConfigProperty("org.pentaho.chart.namespace.row_name_not_defined"); //$NON-NLS-1$
    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final double scale = JFreeChartUtils.getScale(chartDocument);

    for (int row = 0; row < rowCount; row++) {
      final Object rawRowName = JFreeChartUtils.getRawRowName(data, chartDocument, row);
      updateDatasetBasedOnScale(data, dataset, row, rawRowName, noRowNameSpecified, scale);
    }
    return dataset;
  }

  private void updateDatasetBasedOnScale(final ChartTableModel data,
                                         final DefaultPieDataset dataset,
                                         final int row,
                                         final Object rawRowName,
                                         final String noRowNameSpecified,
                                         final double scale) {
    final String rowName = rawRowName != null ? String.valueOf(rawRowName): (noRowNameSpecified + row);
    final Object rawValue = data.getValueAt(row, 0);
    if (rawValue instanceof Number) {
      final Number number = (Number) rawValue;
      double value = number.doubleValue();
      value *= scale;
      dataset.setValue(rowName, value);
    }
  }

  public Dataset createDataset(final ChartDocumentContext chartDocContext, final ChartTableModel data, final Integer[] columnPosArr) {
    return null;  
  }
}
