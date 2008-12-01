package org.pentaho.experimental.chart.plugin.jfreechart.dataset;

import org.jfree.data.general.Dataset;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.data.ChartTableModel;

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
