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

import junit.framework.TestCase;
import org.junit.Test;
import org.pentaho.chart.data.ChartTableModel;

/**
 * This class tests ChartTableModel class
 */
@SuppressWarnings({"AssertEqualsBetweenInconvertibleTypes"})
public class ChartTableModelTest extends TestCase {
  boolean yay = new Boolean(true);
  boolean nay = new Boolean(false);
  final Object[][] dataSample = {
      {"Mary", "Campione",//$NON-NLS-1$ //$NON-NLS-2$
          "Snowboarding", new Integer(5), nay}, //$NON-NLS-1$
      {"Alison", "Huml",//$NON-NLS-1$ //$NON-NLS-2$
          "Rowing", new Integer(3), yay}, //$NON-NLS-1$
      {"Kathy", "Walrath",//$NON-NLS-1$ //$NON-NLS-2$
          "Knitting", new Integer(2),nay}, //$NON-NLS-1$
      {"Sharon", "Zakhour",//$NON-NLS-1$ //$NON-NLS-2$
          "Speed reading", new Integer(20), yay}, //$NON-NLS-1$
      {"Philip", "Milne", //$NON-NLS-1$ //$NON-NLS-2$
          "Pool", new Integer(10), nay}, //$NON-NLS-1$
  };

  @Test
  /**
   * Performs tests on null data
   */
  public final void testNullData() {
    final ChartTableModel ct = new ChartTableModel();

    assertNull("Data is null. We should be getting null back", ct.getValueAt(0, 0)); //$NON-NLS-1$
    assertEquals(0, ct.getRowCount());
    assertEquals(0, ct.getColumnCount());
    assertNull(ct.getValueAt(-1, -1));
    assertNull(ct.getValueAt(0, 0));
    assertNull(ct.getValueAt(0, 0));
    assertNull(ct.getColumnName(-1));
    assertNull(ct.getColumnName(0));
    assertNull(ct.getColumnName(1));
  }

  @Test
  /**
   * Performs tests on null metadata
   */
  public final void testNullMetaData() {
    final ChartTableModel ct = new ChartTableModel();

    try {
      // Row -> Incorrect Key1 -> Incorrect
      ct.getRowMetadata(-10, null);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Col -> Incorrect Key1 -> Incorrect
      ct.getColMetadata(-10, null);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Cell -> Incorrect Key1 -> Correct
      ct.getCellMetadata(-1, -10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Row -> Incorrect Key1 -> Correct
      ct.getRowMetadata(-10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Col -> Incorrect Key1 -> Correct
      ct.getColMetadata(-10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Cell -> Incorrect Key1 -> Correct
      ct.getCellMetadata(-10, -10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Cell -> Incorrect Key1 -> Correct
      ct.getCellMetadata(0, -10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Cell -> Incorrect Key1 -> Correct
      ct.getCellMetadata(-10, 0, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    // Row -> Correct Key1 -> Correct
    Object data = ct.getRowMetadata(0, 0);
    assertNull(data);

    // Col -> Correct Key1 -> Correct
    data = ct.getRowMetadata(0, 0);
    assertNull(data);

    // Cell -> Correct Key1 -> Correct
    data = ct.getCellMetadata(0, 0, 0);
    assertNull(data);
  }

  @Test
  /**
   * Initializes data and performs tests on it
   */
  public final void testDataInit() {
    final ChartTableModel ct = new ChartTableModel();
    ct.setData(dataSample);
    assertEquals("Mary", ct.getValueAt(0, 0)); //$NON-NLS-1$
    assertEquals("Snowboarding", ct.getValueAt(0, 2)); //$NON-NLS-1$
    assertEquals(false, ct.getValueAt(4, 4));
    assertEquals(10, ct.getValueAt(4, 3));
    assertNull(ct.getValueAt(-1, -1));
    assertNull(ct.getValueAt(100, -1));
    assertNull(ct.getValueAt(100, 0));
    assertNull(ct.getValueAt(-1, 100));
    assertNull(ct.getValueAt(0, 100));
    assertNull(ct.getValueAt(100, 100));
  }

  @Test
  /**
   * Tests the row col count for with various configurations of data
   */
  public final void testRowColCount() {
    final ChartTableModel ct = new ChartTableModel();

    final Object[][] data1 = {
        {"abc", 2, false, "some"}, //$NON-NLS-1$ //$NON-NLS-2$ 
        {"def", 1, 2, 3, 4}, //$NON-NLS-1$
        {"gh", 10} //$NON-NLS-1$
    };
    ct.setData(data1);

    assertEquals(3, ct.getRowCount());
    assertEquals(5, ct.getColumnCount());

    final Object[][] data2 = {
        {"abc", 2}, //$NON-NLS-1$
        {"def"} //$NON-NLS-1$        
    };
    ct.setData(data2);

    assertEquals(2, ct.getRowCount());
    assertEquals(2, ct.getColumnCount());

    final Object[][] data3 = {
        {"abc", 2, 1, 3, 4, 9}, //$NON-NLS-1$ 
        {"def"},  //$NON-NLS-1$        
        {"def"},  //$NON-NLS-1$        
        {"def"},  //$NON-NLS-1$        
        {"def"},  //$NON-NLS-1$        
        {"def"},  //$NON-NLS-1$        
    };
    ct.setData(data3);

    assertEquals(6, ct.getRowCount());
    assertEquals(6, ct.getColumnCount());
  }

  @Test
  /**
   * Test for nulls in various positions in the data array
   */
  public final void testDataNulls() {

    final ChartTableModel ct = new ChartTableModel();

    final Object[][] data1 = {
        {"abc", 2, false, "some"}, //$NON-NLS-1$ //$NON-NLS-2$
        {"aaa", "bbb", null, "yes"},  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        {"gh", 10, null}, //$NON-NLS-1$
        {null, 10, null},
        {null, null, null}
    };

    ct.setData(data1);

    assertEquals(5, ct.getRowCount());
    assertEquals(4, ct.getColumnCount());
    assertNull(ct.getColumnName(5));
    assertNull(ct.getValueAt(1, 2));
    assertEquals("gh", ct.getValueAt(2, 0));//$NON-NLS-1$
    assertEquals(10, ct.getValueAt(2, 1));
    assertNull(ct.getValueAt(2, 2));
    assertNull(ct.getValueAt(3, 0));
    assertEquals(10, ct.getValueAt(3, 1));
    assertNull(ct.getValueAt(3, 2));
    assertEquals("yes", ct.getValueAt(1, 3));//$NON-NLS-1$
    assertEquals(10, ct.getValueAt(2, 1));
    assertNull(ct.getValueAt(4, 1));

    final Object[][] data2 = {
        {"abc", 2, false, "some"}, //$NON-NLS-1$ //$NON-NLS-2$ 
        null,
        {"gh", 10} //$NON-NLS-1$
    };

    // The code below is supposed to fail because we don't want to allow null array(s)
    try {
      ct.setData(data2);
      fail("We shouldn't have reached here!"); //$NON-NLS-1$
    } catch (IllegalStateException ioe) {
    }
  }

  @Test
  /**
   * Tests for Metadata initialization
   */
  public void testMetaDataInit() {
    final String COLOR = "color";//$NON-NLS-1$
    final ChartTableModel ct = new ChartTableModel();

    // Some row level stuff
    ct.setRowMetadata(0, COLOR, "red"); //$NON-NLS-1$
    ct.setRowMetadata(1, COLOR, "purple"); //$NON-NLS-1$
    ct.setRowMetadata(2, COLOR, "black"); //$NON-NLS-1$
    ct.setRowMetadata(3, COLOR, "yellow"); //$NON-NLS-1$
    ct.setRowMetadata(4, COLOR, "white"); //$NON-NLS-1$
    ct.setRowMetadata(5, COLOR, null);
    ct.setRowMetadata(6, COLOR, null);

    // Some cell level stuff
    ct.setCellMetadata(0, 0, COLOR, "blue"); //$NON-NLS-1$
    ct.setCellMetadata(0, 1, COLOR, "green"); //$NON-NLS-1$
    ct.setCellMetadata(0, 2, "format", "###"); //$NON-NLS-1$//$NON-NLS-2$
    ct.setCellMetadata(0, 3, "format", null); //$NON-NLS-1$
    ct.setCellMetadata(0, 4, "format", null); //$NON-NLS-1$

    // Some column level stuff
    ct.setColMetadata(0, "col name", "A"); //$NON-NLS-1$//$NON-NLS-2$
    ct.setColMetadata(1, "col name", "B"); //$NON-NLS-1$//$NON-NLS-2$
    ct.setColMetadata(2, "col name", "C"); //$NON-NLS-1$//$NON-NLS-2$
    ct.setColMetadata(3, "col name", "D"); //$NON-NLS-1$//$NON-NLS-2$
    ct.setColMetadata(4, "col name", "E"); //$NON-NLS-1$//$NON-NLS-2$
    ct.setColumnName(0, "First Name"); //$NON-NLS-1$
    ct.setColumnName(1, "Last Name"); //$NON-NLS-1$
    ct.setColumnName(2, "Favorite Sport"); //$NON-NLS-1$
    ct.setColumnName(3, "Number of Years"); //$NON-NLS-1$
    ct.setColMetadata(4, ChartTableModel.COL_NAME, "Professional"); //$NON-NLS-1$
    ct.setColMetadata(5, "Width", null); //$NON-NLS-1$
    ct.setColMetadata(6, "Width", null); //$NON-NLS-1$

    Object data = null;
    try {
      // Row -> Incorrect Key1 -> Incorrect
      data = ct.getRowMetadata(-10, null);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Col -> Incorrect Key1 -> Incorrect
      data = ct.getColMetadata(-10, null);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Cell -> Incorrect Key1 -> Correct
      data = ct.getCellMetadata(-1, -10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Row -> Incorrect Key1 -> Correct
      data = ct.getRowMetadata(-10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Col -> Incorrect Key1 -> Correct
      data = ct.getColMetadata(-10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Cell -> Incorrect Key1 -> Correct
      data = ct.getCellMetadata(-10, -10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
     //  Cell -> Incorrect Key1 -> Correct
      data = ct.getCellMetadata(0, -10, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    try {
      // Cell -> Incorrect Key1 -> Correct
      data = ct.getCellMetadata(-10, 0, 0);
      fail("Shouldn't reach here"); //$NON-NLS-1$
    } catch (IllegalArgumentException iae) {
    }

    // Row -> Correct Key1 -> Correct
    data = ct.getRowMetadata(0, 0);
    assertNull(data);

    // Col -> Correct Key1 -> Correct
    data = ct.getRowMetadata(0, 0);
    assertNull(data);

    // Cell -> Correct Key1 -> Correct
    data = ct.getCellMetadata(0, 0, 0);
    assertNull(data);

    // Check row level stuff
    assertEquals("red", ct.getRowMetadata(0, COLOR)); //$NON-NLS-1$
    assertEquals("purple", ct.getRowMetadata(1, COLOR)); //$NON-NLS-1$
    assertEquals("white", ct.getRowMetadata(4, COLOR)); //$NON-NLS-1$
    assertNull(ct.getRowMetadata(5, COLOR));

    // Cell specific
    assertEquals("blue", ct.getCellMetadata(0, 0, COLOR)); //$NON-NLS-1$
    assertEquals("###", ct.getCellMetadata(0, 2, "format")); //$NON-NLS-1$  //$NON-NLS-2$
    assertNull(ct.getCellMetadata(0, 4, "format")); //$NON-NLS-1$

    // Check col specific stuff
    assertEquals("First Name", ct.getColumnName(0)); //$NON-NLS-1$
    assertEquals("Professional", ct.getColumnName(4)); //$NON-NLS-1$
    assertNull(ct.getColumnName(10));
    assertNull(ct.getColumnName(-1));
    assertNull(ct.getColMetadata(5, "Width")); //$NON-NLS-1$
  }

  @Test
  /**
   * Tests simple data initialization
   */
  public final void testHappyTest() {
    final ChartTableModel ct = new ChartTableModel();
    ct.setData(dataSample);
    assertEquals("Mary", ct.getValueAt(0, 0)); //$NON-NLS-1$  
    assertEquals(false, ct.getValueAt(4, 4));
    assertEquals(10, ct.getValueAt(4, 3));
    assertNull(ct.getValueAt(10, 10));
  }

  /**
   * Tests the rotation functionality of the chart table model
   */
  public void testRotation() {
    // Rotate after data loaded
    final ChartTableModel data1 = new ChartTableModel();
    populateModel(data1);
    assertEquals(false, data1.isRotated());
    data1.setRotated(true);
    assertEquals(true, data1.isRotated());

    // Check the data
    assertEquals("test", data1.getCellMetadata(4, 2, "cell"));
    assertNull(data1.getCellMetadata(2, 4, "cell"));
    assertEquals("row1", data1.getColMetadata(1, "test"));
    assertNull(data1.getRowMetadata(1, "test"));
    assertEquals("column2", data1.getRowMetadata(2, "test"));
    assertNull(data1.getColMetadata(2, "test"));
    assertEquals("two,one", data1.getValueAt(1, 2));
    assertEquals("1,2", data1.getValueAt(2, 1));
    assertEquals("col0", data1.getRowName(0));
    assertEquals("col1", data1.getRowName(1));
    assertEquals("col2", data1.getRowName(2));
    assertEquals("col3", data1.getRowName(3));
    assertEquals("col4", data1.getRowName(4));
    assertEquals("col5", data1.getRowName(5));
    assertNull(data1.getRowName(6));
    assertEquals("row0", data1.getColumnName(0));
    assertEquals("row1", data1.getColumnName(1));
    assertEquals("row2", data1.getColumnName(2));
    assertEquals("row3", data1.getColumnName(3));
    assertNull(data1.getColumnName(4));
    assertEquals(5, data1.getRowCount());
    assertEquals(3, data1.getColumnCount());

    // Load the data with the rotated flag set
    final ChartTableModel data2 = new ChartTableModel(true);
    populateModel(data2);
    assertEquals(true, data2.isRotated());
    data2.setRotated(false);
    assertEquals(false, data2.isRotated());

    // Check the data
    assertEquals("test", data1.getCellMetadata(4, 2, "cell"));
    assertNull(data1.getCellMetadata(2, 4, "cell"));
    assertEquals("row1", data1.getColMetadata(1, "test"));
    assertNull(data1.getRowMetadata(1, "test"));
    assertEquals("column2", data1.getRowMetadata(2, "test"));
    assertNull(data1.getColMetadata(2, "test"));
    assertEquals("two,one", data1.getValueAt(1, 2));
    assertEquals("1,2", data1.getValueAt(2, 1));
    assertEquals("col0", data1.getRowName(0));
    assertEquals("col1", data1.getRowName(1));
    assertEquals("col2", data1.getRowName(2));
    assertEquals("col3", data1.getRowName(3));
    assertEquals("col4", data1.getRowName(4));
    assertEquals("col5", data1.getRowName(5));
    assertNull(data1.getRowName(6));
    assertEquals("row0", data1.getColumnName(0));
    assertEquals("row1", data1.getColumnName(1));
    assertEquals("row2", data1.getColumnName(2));
    assertEquals("row3", data1.getColumnName(3));
    assertNull(data1.getColumnName(4));
    assertEquals(5, data1.getRowCount());
    assertEquals(3, data1.getColumnCount());
  }

  /**
   * Adds data and metadata to a ChartTableModel
   */
  private static void populateModel(final ChartTableModel data) {
    // Setup the test data
    data.setData(new Object[][]{  // 3 rows by 5 columns
        //               [0]    [1]    [2]    [3]    [4]
        new Object[]{"0,0", "0,1", "0,2", "0,3",}, // Row [0]
        new Object[]{"1,0", "1,1", "1,2",}, // Row [1]
        new Object[]{"2,0", "2,1", "2,2", "2,3", "2,4",}, // Row [2]
    }
    );

    // Set the row names
    data.setRowName(0, "row0");
    data.setRowName(1, "row1");
    data.setRowName(2, "row2");
    data.setRowName(3, "row3"); // NOTE: row 3 does not exist

    // Set the column names
    data.setColumnName(0, "col0");
    data.setColumnName(1, "col1");
    data.setColumnName(2, "col2");
    data.setColumnName(3, "col3");
    data.setColumnName(4, "col4");
    data.setColumnName(5, "col5"); // NOTE: column 5 does not exist

    // Set other metadata
    data.setCellMetadata(2, 4, "cell", "test");
    data.setRowMetadata(1, "test", "row1");
    data.setColMetadata(2, "test", "column2");
    data.setValueAt("two,one", 2, 1);
  }
}
