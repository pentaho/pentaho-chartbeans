package org.pentaho.experimental.chart.plugin.jfreechart.dataset;

import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.data.ChartTableModel;

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
