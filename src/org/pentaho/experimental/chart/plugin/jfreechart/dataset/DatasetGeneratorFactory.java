package org.pentaho.experimental.chart.plugin.jfreechart.dataset;

import org.jfree.data.general.Dataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.data.ChartTableModel;

/**
 * This class creates a Dataset creator object based on the kind of dataset defined in the dataset tag inside plot
 * element.
 * </p>
 * For example: If the dataset is defined as categorical then we would create a JFreeDefaultCategoryDatasetCreator
 * object. This object then allows us to create a DefaultCategoryDataset.
 * </p>
 * Author: Ravi Hasija
 * Date: May 13, 2008
 * Time: 10:48:38 AM
 */
public class DatasetGeneratorFactory {

  private final String DATASET = "dataset"; //$NON-NLS-1$
  private final String TYPE ="type"; //$NON-NLS-1$
  // Different types of supported dataset 
  private final String CATEGORICAL = "categorical"; //$NON-NLS-1$
  private final String XY = "xy"; //$NON-NLS-1$
  private final String VALUE = "value"; //$NON-NLS-1$
  private final String TIME_SERIES = "timeseries"; //$NON-NLS-1$
  private final String INTERVAL = "interval"; //$NON-NLS-1$

  /**
   * Returns default category dataset.
   * </p>
   * @param chartDocContext -- Current chart's document context.
   * @param data            -- Data for current chart.
   * @return Returns DefaultCategoryDataset object.
   */
  public DefaultCategoryDataset createDefaultCategoryDataset(final ChartDocumentContext chartDocContext,
                                                             final ChartTableModel data) {
    final Dataset dataset = createDataset(chartDocContext, data);
    DefaultCategoryDataset categoryDataset = null;

    if (dataset != null && dataset instanceof DefaultCategoryDataset) {
        categoryDataset = (DefaultCategoryDataset) dataset;
    }
    return categoryDataset;
  }

  /**
   * Returns default category dataset.
   * </p>
   * @param chartDocContext -- Current chart's document context.
   * @param data            -- Data for current chart.
   * @param columnPosArr    -- Specific columns to retrieve the data from.
   * @return Returns DefaultCategoryDataset object.
   */
  public DefaultCategoryDataset createDefaultCategoryDataset(final ChartDocumentContext chartDocContext,
                                                             final ChartTableModel data,
                                                             final Integer[] columnPosArr) {
    final Dataset dataset = createDataset(chartDocContext, data, columnPosArr);
    DefaultCategoryDataset categoryDataset = null;

    if (dataset != null && dataset instanceof DefaultCategoryDataset) {
        categoryDataset = (DefaultCategoryDataset) dataset;
    }
    return categoryDataset;
  }

  public DefaultIntervalCategoryDataset createDefaultIntervalCategoryDataset() {
    return null;
  }
  
  /**
   * This method looks into the dataset tag inside plot element and creates a dataset creator based
   * on the datset type found.
   *
   * @param chartDocContext Chart document context object that holds current chart document
   * @param data Chart table model that holds the data for the chart
   * @return IJFreeDatasetCreator that is a creator of specific type of dataset.
   */
  public Dataset createDataset(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data)
  throws IllegalArgumentException,
         IllegalStateException {

    Dataset dataset;
    IJFreeDatasetGenerator datasetGenerator = null;
    
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
          datasetGenerator = new IJFreeDefaultCategoryDatasetGenerator();
        } else if (XY.equalsIgnoreCase(datasetType)) {
        } else if (VALUE.equalsIgnoreCase(datasetType)) {
        } else if (TIME_SERIES.equalsIgnoreCase(datasetType)) {
        } else if (INTERVAL.equalsIgnoreCase(datasetType)) {
           datasetGenerator = new IJFreeDefaultIntervalCategoryDatasetGenerator();
        }
      } else {
        // If dataset type is not defined then default to DefaultCategory dataset
        datasetGenerator =  new IJFreeDefaultCategoryDatasetGenerator();
      }

      dataset = datasetGenerator.createDataset(chartDocContext, data);
    }

    return dataset;
  }

    /**
   * This method looks into the dataset tag inside plot element and creates a dataset creator based
   * on the datset type found.
   *
   * @param chartDocContext Chart document context object that holds current chart document
   * @param data Chart table model that holds the data for the chart
   * @return IJFreeDatasetCreator that is a creator of specific type of dataset.
   * @throws IllegalArgumentException If any of the arguments are null.
   */
  private Dataset createDataset(final ChartDocumentContext chartDocContext,
                               final ChartTableModel data,
                               final Integer[] columnPosArr)
    throws IllegalArgumentException,
         IllegalStateException {

    Dataset dataset = null;
    IJFreeDatasetGenerator datasetGenerator = null;

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
          datasetGenerator = new IJFreeDefaultCategoryDatasetGenerator();
        } else if (XY.equalsIgnoreCase(datasetType)) {
        } else if (VALUE.equalsIgnoreCase(datasetType)) {
        } else if (TIME_SERIES.equalsIgnoreCase(datasetType)) {
        } else if (INTERVAL.equalsIgnoreCase(datasetType)) {
           datasetGenerator = new IJFreeDefaultIntervalCategoryDatasetGenerator();
        }
      } else {
        // If dataset type is not defined then default to DefaultCategory dataset
        datasetGenerator =  new IJFreeDefaultCategoryDatasetGenerator();
      }

      dataset = datasetGenerator.createDataset(chartDocContext, data,columnPosArr);
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
