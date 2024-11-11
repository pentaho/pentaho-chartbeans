/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


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
