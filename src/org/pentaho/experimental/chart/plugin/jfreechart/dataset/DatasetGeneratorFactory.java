package org.pentaho.experimental.chart.plugin.jfreechart.dataset;

import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.data.ChartTableModel;

/**
 * Author: Ravi Hasija
 * Date: May 13, 2008
 * Time: 10:48:38 AM
 *
 * This class creates a Dataset creator object based on the kind of dataset defined in the dataset tag inside plot
 * element.
 * </p>
 * For example: If the dataset is defined as categorical then we would create a JFreeDefaultCategoryDatasetCreator
 * object. This object then allows us to create a DefaultCategoryDataset.
 */
public class DatasetGeneratorFactory {

  private final String DATASET = "dataset"; //$NON-NLS-1$
  private final String TYPE ="type"; //$NON-NLS-1$
  // Different types of supported dataset 
  private final String CATEGORICAL = "categorical"; //$NON-NLS-1$
  private final String XY = "xy"; //$NON-NLS-1$
  private final String VALUE = "value"; //$NON-NLS-1$
  private final String TIME_SERIES = "timeseries"; //$NON-NLS-1$


  /**
   * This method looks into the dataset tag inside plot element and creates a dataset creator based
   * on the datset type found.
   *
   * @param chartDocContext Chart document context object that holds current chart document
   * @param data Chart table model that holds the data for the chart
   * @return IJFreeDatasetCreator that is a creator of specific type of dataset.
   * @throws IllegalArgumentException If any of the arguments are null.
   */
  public IDatasetGenerator generateDatasetCreator(final ChartDocumentContext chartDocContext,
                                                       final ChartTableModel data)
  throws IllegalArgumentException,
         IllegalStateException {

    IDatasetGenerator dataset = null;
    
    if (chartDocContext == null && data == null) {
      throw new IllegalArgumentException("Arguments cannot be null. ChartDocumentContext: " + chartDocContext + ", ChartTableModel: "+ data); //$NON-NLS-1$ //$NON-NLS-2$
    } else {
      final ChartDocument chartDoc = chartDocContext.getChartDocument();

      if (chartDoc == null) {
        throw new IllegalStateException("Chart document cannot be null!"); //$NON-NLS-1$
      }

      final String datasetType = getDatasetType(chartDoc.getPlotElement());

      if (datasetType != null) {
        if (CATEGORICAL.equalsIgnoreCase(datasetType)) {
          dataset = new DefaultCategoryDatasetGenerator(chartDocContext, data);
        } else if (XY.equalsIgnoreCase(datasetType)) {
        } else if (VALUE.equalsIgnoreCase(datasetType)) {
        } else if (TIME_SERIES.equalsIgnoreCase(datasetType)) {
        }
      } else {
        // If dataset type is not defined then default to DefaultCategory dataset
        dataset =  new DefaultCategoryDatasetGenerator(chartDocContext, data);
      }
    }

    return dataset;
  }

  /**
   *  Returns the dataset type string. Possible values are categorical, xy, timeseries, value
   * 
   * @param plotElement The plot element within the current chart document.
   * @return Returns the dataset type for the current plot.
   */
  private String getDatasetType(final ChartElement plotElement) {
    final int childCount = plotElement.getChildCount();
    ChartElement plotChildElement = plotElement.getFirstChildItem();

    // Iterate through all the child items of the plot element until you find dataset element
    for (int i=0; i<childCount; i++) {
      if (DATASET.equals(plotChildElement.getName())) {
        return (String)plotChildElement.getAttribute(TYPE);
      } else {
        plotChildElement = plotChildElement.getNextItem();
      }
    }
    return null;
  }
}
