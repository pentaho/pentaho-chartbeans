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


package org.pentaho.chart;

import javax.swing.table.TableModel;

/**
 * The interface which defines the API for access into data used for charting.
 *
 * @author David Kincade
 */
public interface ChartData extends TableModel {
  /**
   * Sets the name of the column in the charting data
   * @param columnIndex the index of the column whose name is being set
   * @param columnName the name being assigned to the column
   */
  public void setColumnName(final int columnIndex, final String columnName);

  /**
   * Sets the name of the row in the charting data
   * @param rowIndex the index of the
   * @param rowName the name being assigned to the row
   */
  public void setRowName(final int rowIndex, final String rowName);

  /**
   * Retrieves the name assigned to the specified row
   * @param rowIndex the index of the whose name should be retrieved
   * @return the name assigned to the specified row, or <code>null</code> if none is assgined
   */
  public String getRowName(final int rowIndex);

  /**
   * Sets a piece of metadata for the specified row. If the metadata key already exists for the specified row
   * index, the existing metadata value will be replaced with the new value provided.
   * @param rowIndex the index of the row
   * @param metadataKey the key for the metadata being set
   * @param metadataValue the value of the metadata being set (a value of <code>null</code> will remove any
   *   existing metadata value for the specified metadataKey for the specified row)
   */
  public void setRowMetadata(final int rowIndex, final Object metadataKey, final Object metadataValue);

  /**
   * Sets a piece of metadata for the specified column. If the metadata key already exists for the specified column
   * index, the existing metadata value will be replaced with the new value provided.
   * @param colIndex the index of the row
   * @param metadataKey the key for the metadata being set
   * @param metadataValue the value of the metadata being set (a value of <code>null</code> will remove any
   *   existing metadata value for the specified metadataKey for the specified column)
   */
  public void setColMetadata(final int colIndex, final Object metadataKey, final Object metadataValue);

  /**
   * Sets a piece of metadata for the specified cell. If the metadata key already exists for the specified cell
   * index, the existing metadata value will be replaced with the new value provided.
   * @param rowIndex the row index of the cell
   * @param colIndex the column index of the cell
   * @param metadataKey the key for the metadata being set
   * @param metadataValue the value of the metadata being set (a value of <code>null</code> will remove any
   *   existing metadata value for the specified metadataKey for the specified cell)
   */
  public void setCellMetadata(final int rowIndex, final int colIndex, final Object metadataKey, final Object metadataValue);

  /**
   * Retrieves the metadata for the specified row and key
   * @param rowIndex the row index
   * @param metadataKey the metadata key
   * @return the metadata value for the specified row and key.
   *   If there is no metadata value, <code>null</code> will be returned.
   */
  public Object getRowMetadata(final int rowIndex, final Object metadataKey);

  /**
   * Retrieves the metadata for the specified column and key
   * @param colIndex the row index
   * @param metadataKey the metadata key
   * @return the metadata value for the specified column and key.
   *   If there is no metadata value, <code>null</code> will be returned.
   */
  public Object getColMetadata(final int colIndex, final Object metadataKey);

  /**
   * Retrieves the metadata for the specified cell and key
   * @param rowIndex the row index of the cell
   * @param colIndex the row index of the cell
   * @param metadataKey the metadata key
   * @return the metadata value for the specified cell and key.
   *   If there is no metadata value, <code>null</code> will be returned.
   */
  public Object getCellMetadata(final int rowIndex, final int colIndex, final Object metadataKey);

  /**
   * Returns the 1st column index with the specified name
   * @param columnName the name of the column
   * @return the index of the 1st column with the specified name. If none can be found,
   * this method will return <code>-1</code>.
   */
  public int findColumn(final String columnName);

  /**
   * Returns the 1st row number of the row which has the specified row name.
   * @param rowName the name of the row
   * @return the row number of the 1st row with the specified name. If none can be found.
   *  this method will return <code>-1</code>.
   */
  public int findRow(final String rowName);
}
