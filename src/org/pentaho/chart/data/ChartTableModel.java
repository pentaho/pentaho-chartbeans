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

package org.pentaho.chart.data;

/**
 * Contains the data and metadata using with the charting API.
 *
 * @author David Kincade
 */
public class ChartTableModel extends BaseChartTableModel {
  /**
   * Flag indicating is this table model should be rotated (row accessed as columns and columns accessed as rows)
   */
  private boolean rotated = false;

  /**
   * Creats an empty ChartTableModel
   */
  public ChartTableModel() {
  }

  /**
   * Creats an empty ChartTableModel
   */
  public ChartTableModel(final boolean rotation) {
    setRotated(rotation);
  }

  /**
   * Returns the current rotation
   */
  public boolean isRotated() {
    return rotated;
  }

  /**
   * Sets the rotation. If the rotation is <code>true</code>, then the rows will be accessed as columns
   * and the columns will be accessed as rows. If the rotation is <code>false</code>, then the table will be
   * accessed as normal.
   */
  public void setRotated(final boolean rotated) {
    this.rotated = rotated;
  }

  /**
   * Returns the number of columns in the table
   */
  public int getColumnCount() {
    return (rotated ? super.getRowCount() : super.getColumnCount());
  }

  /**
   * Returns the number of rows in the table
   */
  public int getRowCount() {
    return (rotated ? super.getColumnCount() : super.getRowCount());
  }

  /**
   * Returns the name of the specified 0-based column number
   */
  public String getColumnName(final int col) {
    return (rotated ? super.getRowName(col) : super.getColumnName(col));
  }

  /**
   * Sets the name of the column for the specified 0-based column number
   *
   * @param col  the 0-based column number
   * @param name the name for the specified column
   */
  public void setColumnName(final int col, final String name) {
    if (rotated) {
      super.setRowName(col, name);
    } else {
      super.setColumnName(col, name);
    }
  }

  /**
   * Returns the data at the specified (row, col)
   */
  public Object getValueAt(final int row, final int col) {
    return (rotated ? super.getValueAt(col, row) : super.getValueAt(row, col));
  }

  /**
   * Sets the data at the specified (row, col)
   *
   * @throws ArrayIndexOutOfBoundsException indicates the row or column are invalid
   * @throws IllegalStateException          indicates there is no data in the table
   */
  public void setValueAt(final Object value, final int row, final int col) throws ArrayIndexOutOfBoundsException, IllegalStateException {
    if (rotated) {
      super.setValueAt(value, col, row);
    } else {
      super.setValueAt(value, row, col);
    }
  }

  /**
   * Sets a piece of metadata for the specified row
   *
   * @param row   the 0-based row number upon which to set the metadata
   * @param key   the key for the metadata
   * @param value the value of the metadata
   * @throws IllegalArgumentException indicates an invalid parameter value
   */
  public void setRowMetadata(final int row, final Object key, final Object value) throws IllegalArgumentException {
    if (rotated) {
      super.setColMetadata(row, key, value);
    } else {
      super.setRowMetadata(row, key, value);
    }
  }

  /**
   * Retrieves the metadata for the specified row and key.
   *
   * @param row the 0-based row number to check for the metadata
   * @param key the metadata key used to retrieve the metadata value
   * @return the metadata value for the specified row and key. This method will return <code>null</code> if the metadata
   *         or row do not exist.
   * @throws IllegalArgumentException indicates an invalid row number or key
   */
  public Object getRowMetadata(final int row, final Object key) throws IllegalArgumentException {
    return (rotated ? super.getColMetadata(row, key) : super.getRowMetadata(row, key));
  }

  /**
   * Sets a pieces of metadata for the specified column
   *
   * @param col   the 0-based column number upon which the metadata will be set
   * @param key   the metadata key
   * @param value the metadata value
   * @throws IllegalArgumentException indicates an invalid parameter value
   */
  public void setColMetadata(final int col, final Object key, final Object value) throws IllegalArgumentException {
    if (rotated) {
      super.setRowMetadata(col, key, value);
    } else {
      super.setColMetadata(col, key, value);
    }
  }

  /**
   * Retrieves the metadata for the specified key and column
   *
   * @param col the 0-based column from which the metadata will be retrieved
   * @param key the key used to retrieve the metadata
   * @return the value of the metadata. If the data does not exist, <code>null</code> will be returned
   * @throws IllegalArgumentException indicates an invalid patameter
   */
  public Object getColMetadata(final int col, final Object key) throws IllegalArgumentException {
    return (rotated ? super.getRowMetadata(col, key) : super.getColMetadata(col, key));
  }

  /**
   * Sets a piece of metadata for a specified cell
   *
   * @param row   the 0-based row of the cell for which the metadata will be set
   * @param col   the 0-based column of the cell for which the metadata will be set
   * @param key   the key of the metadata
   * @param value the value of the metadata
   * @throws IllegalArgumentException indicates an invalid parameter
   */
  public void setCellMetadata(final int row, final int col, final Object key, final Object value) throws IllegalArgumentException {
    if (rotated) {
      super.setCellMetadata(col, row, key, value);
    } else {
      super.setCellMetadata(row, col, key, value);
    }
  }

  /**
   * Retrieves the valid of the metadata for the specified cell and key
   *
   * @param row the 0-based row for the cell
   * @param col the 0-based column for the cell
   * @param key the metadata key
   * @return the value of the metadata
   */
  public Object getCellMetadata(final int row, final int col, final Object key) {
    return (rotated ? super.getCellMetadata(col, row, key) : super.getCellMetadata(row, col, key));
  }

  /**
   * Returns the name of the specified 0-based row
   */
  public String getRowName(final int row) {
    return (rotated ? super.getColumnName(row) : super.getRowName(row));
  }

  /**
   * Sets the name of the specified row
   *
   * @param row  the 0-based row index
   * @param name the name to assign to the specified row
   */
  public void setRowName(final int row, final String name) {
    if (rotated) {
      super.setColumnName(row, name);
    } else {
      super.setRowName(row, name);
    }
  }

  public int findRow(String rowName) {
    return (rotated ? super.findColumn(rowName) : findRow(rowName));
  }
}
