package org.pentaho.experimental.chart.data;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * This class tests ChartTableModel class
 */
public class ChartTableModelTest extends TestCase {
	Object[][] dataSample = {
			{"Mary", "Campione",//$NON-NLS-1$ //$NON-NLS-2$
			"Snowboarding", new Integer(5), new Boolean(false)}, //$NON-NLS-1$  
			{"Alison", "Huml",//$NON-NLS-1$ //$NON-NLS-2$
			"Rowing", new Integer(3), new Boolean(true)}, //$NON-NLS-1$ 
			{"Kathy", "Walrath",//$NON-NLS-1$ //$NON-NLS-2$
			"Knitting", new Integer(2), new Boolean(false)}, //$NON-NLS-1$ 
			{"Sharon", "Zakhour",//$NON-NLS-1$ //$NON-NLS-2$
			"Speed reading", new Integer(20), new Boolean(true)}, //$NON-NLS-1$ 
			{"Philip", "Milne", //$NON-NLS-1$ //$NON-NLS-2$
			"Pool", new Integer(10), new Boolean(false)}, //$NON-NLS-1$ 
		};

	@Test
  /**
   * Performs tests on null data
   */
	public final void testNullData() {
		ChartTableModel ct = new ChartTableModel();
		
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
		ChartTableModel ct = new ChartTableModel();
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
      // Cell -> Incorrect Key1 -> Correct
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
    data = ct.getCellMetadata(0,0,0);
    assertNull(data); 		
	}
	
  @Test
  /**
   * Initializes data and performs tests on it
   */
	public final void testDataInit() {
		ChartTableModel ct = new ChartTableModel();
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
    ChartTableModel ct = new ChartTableModel();
    
    Object[][] data1 = {
        {"abc", 2, false, "some"}, //$NON-NLS-1$ //$NON-NLS-2$ 
        {"def", 1, 2, 3, 4}, //$NON-NLS-1$
        {"gh", 10} //$NON-NLS-1$
        };
    ct.setData(data1);
    
    assertEquals(3, ct.getRowCount());
    assertEquals(5, ct.getColumnCount());
    
    Object[][] data2 = {
        {"abc", 2}, //$NON-NLS-1$
        {"def"} //$NON-NLS-1$        
        };
    ct.setData(data2);
    
    assertEquals(2, ct.getRowCount());
    assertEquals(2, ct.getColumnCount());

    Object[][] data3 = {
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
    
    ChartTableModel ct = new ChartTableModel();

    Object[][] data1 = {
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
    
    Object[][] data2 = {
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
		final String NAME = "ChartTableModel.CoulumnName";//$NON-NLS-1$
		ChartTableModel ct = new ChartTableModel();
		Object data = null;
    
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
		ct.setColumnName(1,"Last Name"); //$NON-NLS-1$
		ct.setColumnName(2,"Favorite Sport"); //$NON-NLS-1$
		ct.setColumnName(3,"Number of Years"); //$NON-NLS-1$
		ct.setColMetadata(4, NAME, "Professional"); //$NON-NLS-1$
    ct.setColMetadata(5, "Width", null); //$NON-NLS-1$
    ct.setColMetadata(6, "Width", null); //$NON-NLS-1$
		
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
      // Cell -> Incorrect Key1 -> Correct
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
    data = ct.getCellMetadata(0,0,0);
    assertNull(data);     
    
		// Check row level stuff
		assertEquals("red", ct.getRowMetadata(0, COLOR)); //$NON-NLS-1$
		assertEquals("purple", ct.getRowMetadata(1, COLOR)); //$NON-NLS-1$
		assertEquals("white", ct.getRowMetadata(4, COLOR)); //$NON-NLS-1$
    assertNull(ct.getRowMetadata(5, COLOR));
    
		// Cell specific
		assertEquals("blue", ct.getCellMetadata(0, 0, COLOR)); //$NON-NLS-1$
		assertEquals("###", ct.getCellMetadata(0, 2, "format")); //$NON-NLS-1$  //$NON-NLS-2$
    assertNull(ct.getCellMetadata(0 ,4, "format")); //$NON-NLS-1$
    
		// Check col specific stuff
		assertEquals( "First Name" , ct.getColumnName(0)); //$NON-NLS-1$
		assertEquals( "Professional", ct.getColumnName(4)); //$NON-NLS-1$
    assertNull(ct.getColumnName(10));
    assertNull(ct.getColumnName(-1));
    assertNull(ct.getColMetadata(5, "Width")); //$NON-NLS-1$
	}
	
  @Test
  /**
   * Tests simple data initialization
   */
	public final void testHappyTest() {
		ChartTableModel ct = new ChartTableModel();
    ct.setData(dataSample);
    assertEquals("Mary", ct.getValueAt(0, 0)); //$NON-NLS-1$  
		assertEquals(false, ct.getValueAt(4, 4)); 
		assertEquals(10, ct.getValueAt(4, 3)); 
		assertNull(ct.getValueAt(10, 10)); 		
	}
}