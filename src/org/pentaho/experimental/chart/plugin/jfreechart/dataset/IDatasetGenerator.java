package org.pentaho.experimental.chart.plugin.jfreechart.dataset;

import org.jfree.data.general.Dataset;

/**
 * Author: Ravi Hasija
 * Date: May 13, 2008
 * Time: 1:03:19 PM
 *
 * This interface binds the implementing classes to create a createDataset method that returns
 * org.jfree.data.general.Dataset type. We return the generic Dataset type so as to allow changes
 * to the implementation without affecting the interface.  
 */
public interface IDatasetGenerator {
  public Dataset createDataset();
}
