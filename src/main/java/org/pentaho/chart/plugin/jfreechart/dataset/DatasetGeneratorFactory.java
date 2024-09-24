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

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.data.ChartTableModel;

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
  private final String PIE="pie"; //$NON-NLS-1$

  /**
   * Returns default pie dataset.
   * </p>
   * @param chartDocContext -- Current chart's document context.
   * @param data            -- Data for current chart.
   * @return Returns DefaultCategoryDataset object.
   */
  public DefaultPieDataset createDefaultPieDataset(final ChartDocumentContext chartDocContext,
                                                   final ChartTableModel data) {
    final Dataset dataset = createDataset(chartDocContext, data);
    DefaultPieDataset pieDataset = null;

    if (dataset != null && dataset instanceof DefaultPieDataset) {
        pieDataset = (DefaultPieDataset) dataset;
    }
    return pieDataset;
  }

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
   * @throws IllegalArgumentException -- Chart document context or data was null
   * @throws IllegalStateException -- Chart document was null 
   */
  public DefaultCategoryDataset createDefaultCategoryDataset(final ChartDocumentContext chartDocContext,
                                                             final ChartTableModel data,
                                                             final Integer[] columnPosArr)
  throws IllegalArgumentException, IllegalStateException {
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
   * @throws IllegalArgumentException -- Chart document context or data was null
   * @throws IllegalStateException -- Chart document was null
   */
  public Dataset createDataset(final ChartDocumentContext chartDocContext,
                               final ChartTableModel data)
  throws IllegalArgumentException,
         IllegalStateException {
    Dataset dataset = null;

    if (chartDocContext == null || data == null) {
      throw new IllegalArgumentException("Arguments cannot be null. ChartDocumentContext: " + chartDocContext + ", ChartTableModel: "+ data); //$NON-NLS-1$ //$NON-NLS-2$
    } else {
      final ChartDocument chartDoc = chartDocContext.getChartDocument();

      if (chartDoc == null) {
        throw new IllegalStateException("Chart document cannot be null!"); //$NON-NLS-1$
      }

      final String datasetType = getDatasetType(chartDoc.getPlotElement());
      IJFreeDatasetGenerator datasetGenerator = null;

      if (datasetType != null) {
        if (CATEGORICAL.equalsIgnoreCase(datasetType)) {
          datasetGenerator = new JFreeDefaultCategoryDatasetGenerator();
        } else if (PIE.equalsIgnoreCase(datasetType)) {
          datasetGenerator = new JFreeDefaultPieDatasetGenerator();
        } else if (XY.equalsIgnoreCase(datasetType)) {
        } else if (VALUE.equalsIgnoreCase(datasetType)) {
          datasetGenerator = new JFreeDefaultValueDatasetGenerator();
        } else if (TIME_SERIES.equalsIgnoreCase(datasetType)) {
        } else if (INTERVAL.equalsIgnoreCase(datasetType)) {
           datasetGenerator = new JFreeDefaultIntervalCategoryDatasetGenerator();
        }
      } else {
        // If dataset type is not defined then default to DefaultCategory dataset
        datasetGenerator = new JFreeDefaultCategoryDatasetGenerator();
      }

      if (datasetGenerator != null) {
        dataset = datasetGenerator.createDataset(chartDocContext, data);
      }
    }

    return dataset;
  }

    /**
   * This method looks into the dataset tag inside plot element and creates a dataset creator based
   * on the datset type found.
   *
   * @param chartDocContext Chart document context object that holds current chart document
   * @param data Chart table model that holds the data for the chart
   * @param columnPosArr Column positions for which data would be retrieved.
   * @return IJFreeDatasetCreator that is a creator of specific type of dataset.
   * @throws IllegalArgumentException If any of the arguments are null.
   * @throws IllegalStateException If chart document is null.
   */
  private Dataset createDataset(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data,
                                final Integer[] columnPosArr)
    throws IllegalArgumentException,
           IllegalStateException {

    Dataset dataset = null;

    if (chartDocContext != null && data != null) {
      final ChartDocument chartDoc = chartDocContext.getChartDocument();

      if (chartDoc == null) {
        throw new IllegalStateException("Chart document cannot be null!"); //$NON-NLS-1$
      }

      final String datasetType = getDatasetType(chartDoc.getPlotElement());
      IJFreeDatasetGenerator datasetGenerator = null;

      if (datasetType != null) {                             
        if (CATEGORICAL.equalsIgnoreCase(datasetType)) {
          datasetGenerator = new JFreeDefaultCategoryDatasetGenerator();
        } else if (XY.equalsIgnoreCase(datasetType)) {
        } else if (VALUE.equalsIgnoreCase(datasetType)) {
        } else if (TIME_SERIES.equalsIgnoreCase(datasetType)) {
        } else if (INTERVAL.equalsIgnoreCase(datasetType)) {
           datasetGenerator = new JFreeDefaultIntervalCategoryDatasetGenerator();
        }
      } else {
        // If dataset type is not defined then default to DefaultCategory dataset
        datasetGenerator =  new JFreeDefaultCategoryDatasetGenerator();
      }

      if (datasetGenerator != null) {
        dataset = datasetGenerator.createDataset(chartDocContext, data,columnPosArr);
      }
    } else {
      throw new IllegalArgumentException("Arguments cannot be null. ChartDocumentContext: " + chartDocContext + ", ChartTableModel: "+ data); //$NON-NLS-1$ //$NON-NLS-2$
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
      if (DATASET.equals(plotChildElement.getTagName())) {
        return (String)plotChildElement.getAttribute(TYPE);
      } else {
        plotChildElement = plotChildElement.getNextItem();
      }
    }
    return null;
  }
}
