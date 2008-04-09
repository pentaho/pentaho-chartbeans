/*
 * Copyright 2008 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created  
 * @Ravi Hasija
 */
package org.pentaho.experimental.chart.data;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.util.messages.Messages;

import javax.swing.table.AbstractTableModel;


/**
 * @author Ravi hasija
 *         Assumptions:
 *         1. We have data in a 2D array.
 *         2. None of the data row should be null. If we are passed a null object array then we
 *         would re-initialize the data to null.
 *         3. Metadata is implemented using MultiKey HashMap. This provides us the flexibility
 *         of supporting cell level metadata. If in the future we decide that we do not need
 *         cell level support, then we can use simple hash map instead of multi-key hash map.
 *         4. Metadata supports only one level of key.
 */
class BaseChartTableModel extends AbstractTableModel {

  private static final long serialVersionUID = -3841939975394981180L;

  private static final Log logger = LogFactory.getLog(BaseChartTableModel.class);

  /**
   * Row data indicator
   */
  private static final String ROW = "row"; //$NON-NLS-1$
  /**
   * Col data indicator
   */
  private static final String COL = "col"; //$NON-NLS-1$
  /**
   * Cell data indicator
   */
  private static final String CELL = "cell";//$NON-NLS-1$
  /**
   * Holds the name of the attribute
   */
  private static final String NAME = "ChartTableModel.Name"; //$NON-NLS-1$
  /**
   * Metadata multi key hash map
   */
  private MultiKeyMap metadataMap = MultiKeyMap.decorate(new HashedMap());
  /**
   * Data array
   */
  private Object[][] data;
  private int rowCount;
  private int colCount;


  /*
    * (non-Javadoc)
    * @see javax.swing.table.TableModel#getColumnCount()
    * Each row can have different number of elements. We are simply returning
    * column count as the max number of elements in the entire array
    */
  public int getColumnCount() {
    return colCount;
  }

  /*
    * (non-Javadoc)
    * @see javax.swing.table.TableModel#getRowCount()
    * Gets the row count of the data
    */
  public int getRowCount() {
    return rowCount;
  }

  /**
   * (non-Javadoc)
   *
   * @see javax.swing.table.AbstractTableModel#getColumnName(int)
   *      Column name is ofcourse column specific, so we use COL as a placeholder to get Column specific data
   */
  public String getColumnName(final int col) {
    String colName = null;

    if (col >= 0) {
      colName = (String) metadataMap.get(COL, col, NAME);
    } else {
      logger.error(Messages.getErrorString("ChartTableModel.ERROR_0001_COLUMN_NUM_LOWER_THAN_ZERO")); //$NON-NLS-1$
    }
    return colName;
  }

  /**
   * Returns the name for the specified row
   *
   * @param row the 0-based row number for which the name should be returned
   * @return the name of the specified row
   */
  public String getRowName(final int row) {
    String rowName = null;

    if (row >= 0) {
      rowName = (String) metadataMap.get(ROW, row, NAME);
    } else {
      logger.error(Messages.getErrorString("ChartTableModel.ERROR_0009_INVALID_ROW_NUMBER", "" + row));
    }
    return rowName;
  }

  /**
   * Set the name of a particular column
   *
   * @param col Column number to be
   */
  public void setColumnName(final int col, final String name) {
    if (col < 0) {
      logger.error(Messages.getErrorString("ChartTableModel.ERROR_0001_COLUMN_NUM_LOWER_THAN_ZERO")); //$NON-NLS-1$
    } else if (null == name || name.trim().length() == 0) {
      logger.warn(Messages.getErrorString("ChartTableModel.WARN_NAME_SHOULD_NOT_BE_NULL")); //$NON-NLS-1$
    } else {
      metadataMap.put(COL, col, NAME, name);
    }
  }

  /**
   * Sets the name for the specified row
   *
   * @param row  the 0-based row number
   * @param name the name to assign to the specified row
   */
  public void setRowName(final int row, final String name) {
    if (row >= 0) {
      metadataMap.put(ROW, row, NAME, name);
    } else {
      logger.warn(Messages.getErrorString("ChartTableModel.ERROR_0010_ROW_NAME_NOT_SET", "" + row));
    }
  }

  /**
   * (non-Javadoc)
   *
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt(final int row, final int col) {
    Object retData = null;

    if (null == data) {
      logger.error(Messages.getErrorString("ChartTableModel.ERROR_0005_NO_DATA_AVAILABLE")); //$NON-NLS-1$
    } else if (row < 0 || row > getRowCount()) {
      logger.error(Messages.getErrorString("ChartTableModel.ERROR_0004_ROW_NUM_OUT_OF_BOUNDS")); //$NON-NLS-1$
    } else if (col < 0 || col > data[row].length) {
      logger.error(Messages.getErrorString("ChartTableModel.ERROR_0002_COLUMN_NUM_OUT_OF_BOUNDS")); //$NON-NLS-1$
    } else {
      retData = data[row][col];
    }

    return retData;
  }

  /**
   * Set the value of the data array.
   *
   * @param value The value to be set
   * @param row   The row number to be used
   * @param col   The col number to be used
   * @throws ArrayIndexOutOfBoundsException if the row or column number are out of bounds
   * @throws IllegalStateException          if the data array is not initialized
   */
  public void setValueAt(final Object value, final int row, final int col) throws ArrayIndexOutOfBoundsException, IllegalStateException {
    if (row > getRowCount() || row < 0) {
      throw new ArrayIndexOutOfBoundsException(Messages.getErrorString("ChartTableModel.ERROR_0004_ROW_NUM_OUT_OF_BOUNDS")); //$NON-NLS-1$
    } else if (col > getColumnCount() || col < 0) {
      throw new ArrayIndexOutOfBoundsException(Messages.getErrorString("ChartTableModel.ERROR_0002_COLUMN_NUM_OUT_OF_BOUNDS")); //$NON-NLS-1$
    } else if (null == data) {
      throw new IllegalStateException("Data array not initialized."); //$NON-NLS-1$
    }
    data[row][col] = value;
  }

  /**
   * Sets the data based on the input Object array passed.
   *
   * @param inData The input data
   * @throws IllegalStateException if any data element within the array is null.
   */
  public void setData(final Object[][] inData) throws IllegalStateException {
    // Reinitialize before setting new data
    rowCount = 0;
    colCount = 0;
    data = null;

    /*
    * If we are passed null array then we reinitialize the data to null.
    * Else we would read each element to check for null. If entire row is null
    * then throw an IllegalStateException else initialize the data appropriately.
    */
    if (null != inData) {
      for (int i = 0; i < inData.length; i++) {
        // If the entire row is null, we throw an exception
        if (null != inData[i]) {
          // Update the colCount if this row has more columns
          if (inData[i].length > colCount) {
            colCount = inData[i].length;
          }
        } else {
          throw new IllegalStateException(Messages.getErrorString("ChartTableModel.ERROR_0008_DATA_HAS_NULL_ELEMENTS")); //$NON-NLS-1$
        }
      }
      // We are copying the entire data set here. This takes care of the initialization issue.
      data = inData;
      // Finally set the row count
      rowCount = inData.length;
    }
  }

  /**
   * Sets row specific information/metadata.
   *
   * @param row   Row #
   * @param key   Object key
   * @param value Data to be set
   * @throws IllegalArgumentException when the row number is less than zero or when the key is null
   */
  public void setRowMetadata(final int row, final Object key, final Object value) throws IllegalArgumentException {
    if (row < 0) {
      throw new IllegalArgumentException("Row number cannot be less than zero."); //$NON-NLS-1$
    } else if (null == key) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0006_KEY_IS_NULL")); //$NON-NLS-1$
    }

    metadataMap.put(ROW, row, key, value);
  }

  /**
   * Return row specific metadata. row positions are expected to be int.
   *
   * @param row Row number for which we want the metadata
   * @param key Attribute for which we want the metadata
   * @return The actual metadata
   * @throws IllegalArgumentException when the row number is less than zero or when the key is null
   */
  public Object getRowMetadata(final int row, final Object key)
      throws IllegalArgumentException {
    Object metadata;

    if (row < 0) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0003_ROW_NUM_LOWER_THAN_ZERO")); //$NON-NLS-1$
    } else if (null == key) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0005_KEY_IS_NULL"));//$NON-NLS-1$
    } else {
      // Row specific
      metadata = metadataMap.get(ROW, row, key);
    }

    return metadata;
  }

  /**
   * Sets column specific information/metadata.
   *
   * @param col   Column #
   * @param key   Object key
   * @param value Data to be set
   * @throws IllegalArgumentException when the column number is less than zero or when the key is null
   */
  public void setColMetadata(final int col, final Object key, final Object value)
      throws IllegalArgumentException {
    if (col < 0) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0001_COLUMN_NUM_LOWER_THAN_ZERO")); //$NON-NLS-1$
    } else if (null == key) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0006_KEY_IS_NULL")); //$NON-NLS-1$
    }

    metadataMap.put(COL, col, key, value);
  }

  /**
   * Return column specific metadata. Column positions are expected to be int.
   *
   * @param col Column number for which we want the metadata
   * @param key Attribute for which we want the metadata
   * @return The actual metadata
   * @throws IllegalArgumentException when the column number is less than zero or when the key is null
   */
  public Object getColMetadata(final int col, final Object key)
      throws IllegalArgumentException {
    Object metadata;

    if (col < 0) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0001_COLUMN_NUM_LOWER_THAN_ZERO")); //$NON-NLS-1$
    } else if (null == key) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0006_KEY_IS_NULL"));//$NON-NLS-1$
    } else {
      // Column specific
      metadata = metadataMap.get(COL, col, key);
    }

    return metadata;
  }

  /**
   * Sets cell specific information/metadata.
   *
   * @param row   Row #
   * @param col   Column #
   * @param key   Object key
   * @param value Data to be set
   * @throws IllegalArgumentException when the row number or column number is less than zero or when the key is null
   */
  public void setCellMetadata(final int row, final int col, final Object key, final Object value)
      throws IllegalArgumentException {
    if (col < 0 || row < 0) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0007_ROW_COL_IS_OUT_OF_BOUNDS")); //$NON-NLS-1$
    } else if (null == key) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0006_KEY_IS_NULL")); //$NON-NLS-1$
    }

    metadataMap.put(CELL, row, col, key, value);
  }

  /**
   * Returns cell specific metadata. row and column positions are expected to be int.
   *
   * @param row Row number for which we want the metadata
   * @param col Column number for which we want the metadata
   * @param key Attribute for which we want the metadata
   * @return The actual metadata
   */
  public Object getCellMetadata(final int row, final int col, final Object key) {
    Object metadata;

    if (row < 0 || col < 0) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0007_ROW_COL_IS_OUT_OF_BOUNDS")); //$NON-NLS-1$
    } else if (null == key) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartTableModel.ERROR_0006_KEY_IS_NULL"));//$NON-NLS-1$
    } else {
      // Cell specific
      metadata = metadataMap.get(CELL, row, col, key);
    }

    return metadata;
  }
}