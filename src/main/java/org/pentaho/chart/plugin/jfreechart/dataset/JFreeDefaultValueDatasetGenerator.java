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
import org.jfree.data.general.DefaultValueDataset;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Returns a <code>ValueDataset</code> from the given <code>ChartTableModel</code>. A <code>ValueDataset</code> contains
 * a single value. This value would be suitable for a dial chart.
 * 
 * @author mlowery
 */
public class JFreeDefaultValueDatasetGenerator implements IJFreeDatasetGenerator {

  public Dataset createDataset(ChartDocumentContext chartDocContext, ChartTableModel data) {
    DefaultValueDataset dataset = new DefaultValueDataset();
    dataset.setValue((Number) data.getValueAt(0, 0));
    return dataset;
  }

  public Dataset createDataset(ChartDocumentContext chartDocContext, ChartTableModel data, Integer[] columnPosArr) {
    return null;
  }

}
