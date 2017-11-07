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

package org.pentaho.chart.core;

import java.net.URL;

import junit.framework.TestCase;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.core.ChartSeriesDataLinkInfo;
import org.pentaho.chart.core.ChartSeriesDataLinkInfoFactory;
import org.pentaho.chart.core.parser.ChartXMLParser;
import org.pentaho.chart.data.ChartTableModel;

/**
 * Tests the ChartSeriesDataLinkInfo and ChartSeriesDataLinkInfoFactory class. 
 * 
 * @author Ravi Hasija
 *
 */
public class ChartSeriesDataLinkInfoIT extends TestCase {
  Boolean nay = new Boolean(false);
  Boolean yay = new Boolean(true);

  final Object[][] dataSample = {
      {"Mary", "Campione",//$NON-NLS-1$ //$NON-NLS-2$
      "Snowboarding", new Integer(5), nay}, //$NON-NLS-1$
      {"Alison", "Huml",//$NON-NLS-1$ //$NON-NLS-2$
      "Rowing", new Integer(3), yay}, //$NON-NLS-1$
      {"Kathy", "Walrath",//$NON-NLS-1$ //$NON-NLS-2$
      "Knitting", new Integer(2), nay}, //$NON-NLS-1$
      {"Sharon", "Zakhour",//$NON-NLS-1$ //$NON-NLS-2$
      "Speed reading", new Integer(20), yay}, //$NON-NLS-1$
      {"Philip", "Milne", //$NON-NLS-1$ //$NON-NLS-2$
      "Pool", new Integer(10), nay}, //$NON-NLS-1$ 
    };

  /**
   * 
   * @param chartDocFileName
   * @return
   */
  private ChartDocument getChartDoc(final String chartDocFileName) {
    ChartDocument chartDoc = null;
    // Read the XML document and prepare for the test
    try {
      final ChartXMLParser chartParser = new ChartXMLParser();
      final URL chartXmlDocument = this.getClass().getResource(chartDocFileName);
      chartDoc = chartParser.parseChartDocument(chartXmlDocument);
      if (chartDoc == null) {
        fail("A null document should never be returned");//$NON-NLS-1$
      }
    } catch (ResourceException re) {
      System.out.println(re.getLocalizedMessage());
    }
    
    return chartDoc;
  }
  
  /**  
   * <p>
   * Tests for Series tag with column-pos info:
   *    a. the column pos specified is a negative integer
   *    i.e. is a negative integer within and beyond integer range 
   *    b. the column pos specified is not an integer 
   *       i.e is a string, long, value is higher than max integer can hold
   *    c. the column pos specified is null
   *    d. the column pos specified is nothing but spaces
   *    e. the column pos specified is a correct integer but has spaces in it for eg: "  5  "
   *    f. the column pos is greater than the largest column number in the data
   *    g. the column pos is a correct integer but there is no data i.e. data is null
   *    h. the column pos is a correct integer that falls withing the range of columns in data
   *       test with boundary cases 0 , 5, n-1 where n is the total number olf cols.
   * </p>
  */ 
  public final void testColumnPositonsOnly() {
    ChartDocument chartDoc = getChartDoc("NoSeriesTag.xml"); //$NON-NLS-1$
    final ChartTableModel chartTableModel = new ChartTableModel();

    // No data
    ChartSeriesDataLinkInfo seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel);
    assertEquals(0, seriesDataLinkInfo.getDataSize());
    
    chartTableModel.setData(dataSample);
    //  No series tag in the xml, but we have data now
    seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel); 
    assertEquals(0, seriesDataLinkInfo.getDataSize());
    
    // Column pos has several incorrect values
    chartDoc = getChartDoc("SeriesTagWithIncorrectColPositions.xml");//$NON-NLS-1$
    seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel);
    assertEquals(0, seriesDataLinkInfo.getDataSize());

    // Column pos has correct values, but data is null
    chartTableModel.setData(null);
    chartDoc = getChartDoc("SeriesTagWithMixedColPositions.xml");//$NON-NLS-1$
    seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel);
    assertEquals(0, seriesDataLinkInfo.getDataSize());
    
    // Check for correct as well as boundary conditions
    chartTableModel.setData(dataSample);
    chartDoc = getChartDoc("SeriesTagWithMixedColPositions.xml");//$NON-NLS-1$
    seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel);
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(0)); 
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(1)); 
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(2)); 
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(3)); 
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(4)); 
    assertEquals(false, seriesDataLinkInfo.hasColumnPos(-1)); 
    assertEquals(false, seriesDataLinkInfo.hasColumnPos(5)); 
    assertEquals(false, seriesDataLinkInfo.hasColumnPos(20)); 
  } 
  
  /**  
   *  Series tag with col-name info:
   *    a. the column name is specified but data is null
   *    b. the column name is specified but metadata is null
   *    c. the column name is specified but does not match any column names in the metadata
   *    d. the column name specified has a column num in the metadata but the col num is greater than 
   *       num of cols in data
   *    e. the column name specified is null
   *    f. the column name specified is nothing but spaces i.e. "  "
   *    g. the column name specified has leading and trailing spaces in it "  budget   "   *    
   *    h. the column name specified is correct and is in metadata but we don't have as many 
   *       columns in data.
   *    i. the column name specified is has numbers in it for eg: budget123
   *    j. the column name is correct and is in the metadata list of cols.
   */    
  public final void testColumnNamesOnly() {
    final ChartDocument chartDoc = getChartDoc("SeriesTagWithColumnNames.xml"); //$NON-NLS-1$
    final ChartTableModel chartTableModel = new ChartTableModel();

    // No data and meta data
    ChartSeriesDataLinkInfo seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel);
    assertEquals(0, seriesDataLinkInfo.getDataSize());

    // We have data and metadata now.
    chartTableModel.setData(dataSample);
    chartTableModel.setColumnName(0, "First Name"); //$NON-NLS-1$
    chartTableModel.setColumnName(1, "Last Name"); //$NON-NLS-1$
    chartTableModel.setColumnName(2, "Fav Sport"); //$NON-NLS-1$
    chartTableModel.setColumnName(3, "Num of Years"); //$NON-NLS-1$
    chartTableModel.setColumnName(4, "Proffesional"); //$NON-NLS-1$
    chartTableModel.setColumnName(8, "Budget"); //$NON-NLS-1$
    
    seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel); 
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(0));
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(1));
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(2));
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(3));
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(4));
    assertEquals(false, seriesDataLinkInfo.hasColumnPos(8));
    assertEquals(false, seriesDataLinkInfo.hasColumnPos(44));
    assertEquals(false, seriesDataLinkInfo.hasColumnPos(445));
  }
  
  /**
   * Series tag with no column name and no column position
   *  a. Data is null 
   *  b. The series tag position is beyond the max col position in the data 
   *     i.e. series tag# 5 > max col 3   
   */
  public final void testSeriesPosOnly() {
    final ChartDocument chartDoc = getChartDoc("SeriesTagWith_NoColPos_NoColName.xml"); //$NON-NLS-1$
    final ChartTableModel chartTableModel = new ChartTableModel();

    // No data and meta data
    ChartSeriesDataLinkInfo seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel);
    assertEquals(0, seriesDataLinkInfo.getDataSize());

    // We have data and metadata now.
    chartTableModel.setData(dataSample);
    chartTableModel.setColumnName(0, "First Name"); //$NON-NLS-1$
    chartTableModel.setColumnName(1, "Last Name"); //$NON-NLS-1$
    chartTableModel.setColumnName(2, "Fav Sport"); //$NON-NLS-1$
    chartTableModel.setColumnName(3, "Num of Years"); //$NON-NLS-1$
    chartTableModel.setColumnName(4, "Proffesional"); //$NON-NLS-1$
    chartTableModel.setColumnName(8, "Budget"); //$NON-NLS-1$

    seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel); 
    // 5 is the number of columns in data array, we would ignore any 
    // chart element with column number greater than that
    assertEquals(5, seriesDataLinkInfo.getDataSize());     
  }

  /**
   * This is where we test all 3 conditions existing together.
   */
  public final void testSeriesMixed() {
    final ChartDocument chartDoc = getChartDoc("SeriesTagMixed.xml"); //$NON-NLS-1$
    final ChartTableModel chartTableModel = new ChartTableModel();

    // We have data and metadata now.
    chartTableModel.setData(dataSample);
    chartTableModel.setColumnName(0, "First Name"); //$NON-NLS-1$
    chartTableModel.setColumnName(1, "Last Name"); //$NON-NLS-1$
    chartTableModel.setColumnName(2, "Fav Sport"); //$NON-NLS-1$
    chartTableModel.setColumnName(3, "Num of Years"); //$NON-NLS-1$
    chartTableModel.setColumnName(4, "Proffesional"); //$NON-NLS-1$
    chartTableModel.setColumnName(8, "Budget"); //$NON-NLS-1$

    ChartSeriesDataLinkInfo seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel);

    // 5 is the number of columns in data array, we would ignore any 
    // chart element with column number greater than that    
    assertEquals(5, seriesDataLinkInfo.getDataSize());     
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(0));
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(1));
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(2));
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(3));
    assertEquals(true, seriesDataLinkInfo.hasColumnPos(4));
  }
  
  /**
   * This is where we test all 3 conditions existing together.
   */
  public final void testSeriesMixed2() {
    final ChartDocument chartDoc = getChartDoc("SeriesTagMixed2.xml"); //$NON-NLS-1$
    final ChartTableModel chartTableModel = new ChartTableModel();
   
    // We have data and metadata now.
    chartTableModel.setData(dataSample);
    chartTableModel.setColumnName(0, "First Name"); //$NON-NLS-1$
    chartTableModel.setColumnName(1, "Last Name"); //$NON-NLS-1$
    chartTableModel.setColumnName(2, "Fav Sport"); //$NON-NLS-1$
    chartTableModel.setColumnName(3, "Num of Years"); //$NON-NLS-1$
    chartTableModel.setColumnName(4, "Proffesional"); //$NON-NLS-1$
    chartTableModel.setColumnName(8, "Budget"); //$NON-NLS-1$
    
    // If a series element that has incorrect column pos and incorrect column name? 
    // then we are ignoring it.
    final ChartSeriesDataLinkInfo seriesDataLinkInfo = ChartSeriesDataLinkInfoFactory.generateSeriesDataLinkInfo(chartDoc, chartTableModel);
    
    // 5 is the number of columns in data array, we would ignore any 
    // chart element with column number greater than that    
    assertEquals(7, seriesDataLinkInfo.getDataSize());    
   
    final ChartElement rootChartElement = chartDoc.getRootElement();
    
    ChartElement currentChartElement = rootChartElement.getFirstChildItem();
    
    
    int seriesCounter = 0;
    
    while (currentChartElement.getNextItem() != null) {
      final Integer colNum = seriesDataLinkInfo.getColumnNum(currentChartElement);
     
      switch(seriesCounter) {
        case 0: assertEquals(new Integer("2"), colNum); break; //$NON-NLS-1$
        case 1: assertEquals(new Integer("2"), colNum); break; //$NON-NLS-1$
        case 2: assertEquals(new Integer("2"), colNum); break; //$NON-NLS-1$
        case 3: assertEquals(new Integer("0"), colNum); break;  //$NON-NLS-1$
        case 4: assertEquals(new Integer("1"), colNum); break; //$NON-NLS-1$
        case 5: assertNull(colNum); break;
        case 6: assertEquals(new Integer("3"), colNum); break; //$NON-NLS-1$
        case 7: assertEquals(new Integer("4"), colNum); break; //$NON-NLS-1$
        case 8: assertNull(colNum); break;
        case 9: assertNull(colNum); break;
        case 10: assertNull(colNum); break;
        case 11: assertNull(colNum); break;
      }
      seriesCounter++;
      currentChartElement = currentChartElement.getNextItem();
    }
  } 
}
