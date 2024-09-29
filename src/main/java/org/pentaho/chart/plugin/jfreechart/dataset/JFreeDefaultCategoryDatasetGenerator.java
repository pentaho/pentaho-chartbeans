/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.chart.plugin.jfreechart.dataset;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.base.config.Configuration;

/**
 * This class generates default category dataset generator.
 * 
 * Author: Ravi Hasija
 * Date: May 12, 2008
 * Time: 4:00:35 PM
 */
public class JFreeDefaultCategoryDatasetGenerator implements IJFreeDatasetGenerator, Dataset {
  /**
   * The default method that creates a dataset of DefaultCategoryDataset type.
   *
   * @return DefaultCategoryDataset type dataset object.
   */
  public DefaultCategoryDataset createDataset(final ChartDocumentContext chartDocContext,
                                              final ChartTableModel data) {
   return createDefaultCategoryDataset(chartDocContext, data, null);
  }

   /**
   * Returns custom dataset based on certain column positions. The column positions are retrieved by iterating
   * over series elements looking for a specific/given axis id.
   * @return DefaultCategoryDataset that has information from specific column positions.
   */
  public DefaultCategoryDataset createDataset(final ChartDocumentContext chartDocContext,
                                              final ChartTableModel data,
                                              final Integer[] columnPosArr) {
    // Create custom dataset based on the given column positions.
    return createDefaultCategoryDataset(chartDocContext, data, columnPosArr);
  }

 /**
   * This method iterates through the rows and columns to populate a DefaultCategoryDataset.
   * Since a CategoryDataset stores values based on a multikey hash we supply as the keys
   * either the metadata column name or the column number and the metadata row name or row number
   * as the keys.
   * <p/>
   * As it's processing the data from the ChartTableModel into the DefaultCategoryDataset it
   * applies the scale specified in the
   *
   * @param chartDocContext - Chart document context for the current chart.
   * @param data - Data for the current chart.  
   * @param columnIndexArr - Contains column position information. When not null, we would get data from specified columns.
   * @return DefaultCategoryDataset that can be used as a source for JFreeChart
   */
  private DefaultCategoryDataset createDefaultCategoryDataset(final ChartDocumentContext chartDocContext,
                                                              final ChartTableModel data,
                                                              final Integer[] columnIndexArr) {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    final int rowCount = data.getRowCount();
    final int colCount = data.getColumnCount();
    final Configuration config = ChartBoot.getInstance().getGlobalConfig();
    final String noRowNameSpecified = config.getConfigProperty("org.pentaho.chart.namespace.row_name_not_defined"); //$NON-NLS-1$
    final String noColumnName = config.getConfigProperty("org.pentaho.chart.namespace.column_name_not_defined"); //$NON-NLS-1$
    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final double scale = JFreeChartUtils.getScale(chartDocument);

    // Only if we have to separate datasets then do we do some column processing in the given data
    // else we simply process all rows and all columns
    if (columnIndexArr != null) {
      final int columnIndexArrLength = columnIndexArr.length;
      for (int row = 0; row < rowCount; row++) {
        int columnIndexArrCounter = 0;
        for (int column = 0; column < colCount; column++) {
          if (columnIndexArrCounter < columnIndexArrLength) {
            // if the current column is what we want in the dataset (based on column indexes in columnIndexArr),
            // then process the data
            // Else move to the next column
            if (column == columnIndexArr[columnIndexArrCounter]) {
              updateDatasetBasedOnScale(chartDocument, data, dataset, row, column, noRowNameSpecified, noColumnName, scale);
              // Increment the counter so that we can process the next column in the columnIndexArr
              columnIndexArrCounter++;
            }
          } else {
            // if we have reached beyond the last element in the column index array then simply start processing
            // the next row of data.
            break;
          }
        }
      }
    }
    // If we do not want to process entire data as is (without dividing the dataset)
    // then simply process all the dataset
    else {
    for (int row = 0; row < rowCount; row++) {
      for (int column = 0; column < colCount; column++) {
          updateDatasetBasedOnScale(chartDocument, data, dataset, row, column, noRowNameSpecified, noColumnName, scale);
        }
      }
    }
    return dataset;
  }

  /**
   * Updates the dataset values based on the scale info.
   * </p>
   * @param chartDocument -- Current chart document.
   * @param data          -- Data for the current chart.
   * @param dataset       -- Dataset to be updated. 
   * @param row           -- Curent row nuber in the data.
   * @param column        -- Current column number in the data.
   * @param noRowNameSpecified -- Default row name if row name is not specified.
   * @param noColumnName      -- Default column name if column name is not specified.
   * @param scale   -- Scale to be used to modify the dataset.
   */
  private void updateDatasetBasedOnScale(final ChartDocument chartDocument,
                                         final ChartTableModel data,
                                         final DefaultCategoryDataset dataset,
                                         final int row,
                                         final int column,
                                         final String noRowNameSpecified,
                                         final String noColumnName,
                                         final double scale) {
    final String rawColumnName = JFreeChartUtils.getColumnName(data, column);
    final String columnName = rawColumnName != null ? rawColumnName : noColumnName + column ;
    final Object rawRowName = JFreeChartUtils.getRawRowName(data, chartDocument, row);
    final String rowName = rawRowName != null ? String.valueOf(rawRowName): (noRowNameSpecified + row);
    final Object rawValue = data.getValueAt(row, column);
    if (rawValue instanceof Number) {
      final Number number = (Number) rawValue;
      double value = number.doubleValue();
      value *= scale;
      dataset.setValue(value, rowName, columnName);
    }
  }

  public void addChangeListener(final DatasetChangeListener datasetChangeListener) {
  }

  public void removeChangeListener(final DatasetChangeListener datasetChangeListener) {
  }

  public DatasetGroup getGroup() {
    return null;
  }

  public void setGroup(final DatasetGroup datasetGroup) {    
  }
}
