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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.util.messages.Messages;

/**
 * This factory class generates the ChartSeriesDataLinkInfo class object.
 *
 * @author Ravi Hasija
 *         <p/>
 *         Assumptions:
 *         <p/>
 *         1. We are iterating at level 1 and not doing depth traversal on each element.
 *         2. We do not parse the ChartDocument when we do not really have any data.
 *         3. When comparing the column names we ignore case.
 *         4. The column numbers are zero based.
 *         5. The order of matching the series to a column is based on the following priority list.
 *         a. If we find a column number specified in a series tag and it is correct then we
 *         use that column number for the given series element.
 *         b. If we find a column number and it is incorrect AND we have a correct column name
 *         specified, then we use the column name to find the column position for the given
 *         series element.
 *         c. The series element position in the XML doc is used if we do not have a column position
 *         or column name specified for the given series element.
 */

public class ChartSeriesDataLinkInfoFactory {
  private static final Log logger = LogFactory.getLog(ChartSeriesDataLinkInfoFactory.class);

  private static final String COL_NAME = "column-name"; //$NON-NLS-1$
  private static final String COL_POS = "column-pos"; //$NON-NLS-1$
  private static final String SERIES = "series"; //$NON-NLS-1$

  private ChartSeriesDataLinkInfoFactory() {
  }

  /**
   * Creates the chart series data link info object, updates it and returns the updated object.
   *
   * @param chartDoc ChartDocument that contains the XML elements.
   * @param data
   * @return The chart series data link info that has the data.
   * @throws IllegalArgumentException
   */
  public static ChartSeriesDataLinkInfo generateSeriesDataLinkInfo(final ChartDocument chartDoc, final ChartTableModel data)
      throws IllegalArgumentException {
    if (null == chartDoc) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartSeriesDataLinkInfoFactory.ERROR_0001_CHART_DOC_IS_NULL")); //$NON-NLS-1$
    } else if (null == data) {
      throw new IllegalArgumentException(Messages.getErrorString("ChartSeriesDataLinkInfoFactory.ERROR_0002_TABLE_MODEL_IS_NULL")); //$NON-NLS-1$
    }

    final ChartSeriesDataLinkInfo seriesDataLinkInfo = new ChartSeriesDataLinkInfo(data);
    ChartSeriesDataLinkInfoFactory.parseChartDocument(chartDoc, data, seriesDataLinkInfo);
    return seriesDataLinkInfo;
  }

  /**
   * Parse the chart document, and process the series element.
   *
   * @param chartDoc
   * @param chartTableModel
   * @param seriesDataLinkInfo
   * @throws IllegalStateException
   */
  private static void parseChartDocument(final ChartDocument chartDoc, final ChartTableModel chartTableModel, final ChartSeriesDataLinkInfo seriesDataLinkInfo) throws IllegalStateException {
    // We do not parse the ChartDocument when we do not have any data
    final int dataColumnCount = chartTableModel.getColumnCount();
    if (dataColumnCount <= 0) {
      ChartSeriesDataLinkInfoFactory.logger.warn(Messages.getString("ChartSeriesDataLinkInfoFactory.WARN_DATA_IS_NULL"));//$NON-NLS-1$
      return;
    }

    final ChartElement rootChartElement = chartDoc.getRootElement();
    ChartElement currentChartElement = rootChartElement.getFirstChildItem();
    int seriesCount = 0;

    /*
    * Iterate through entire ChartDocument starting at the first child element looking for "series" tag.
    * NOTE: We are iterating at level 1 and not doing depth traversal on each element.
    */
    while (currentChartElement != null) {
      if (ChartSeriesDataLinkInfoFactory.SERIES.equalsIgnoreCase(currentChartElement.getTagName())) {
        boolean foundColumn = false;
        final Object currentColumnNum = currentChartElement.getAttribute(ChartSeriesDataLinkInfoFactory.COL_POS);
        final Object currentColumnName = currentChartElement.getAttribute(ChartSeriesDataLinkInfoFactory.COL_NAME);

        /*
        * |-> If column name and position are not specified/are null then process current
        *     chart element based on series count.
        * |-> Check if the column num is not null
        *    |-> If so process the current chart element based on column position
        *    |-> If we could process current chart element successfully based on the column pos
        *        information then progress to the next chart element.
        *    |-> If we fail to process by column poition and column name is specified
        *        then try to process the current chart element based on the column name.
        *    |-> If column pos or name was specified and we failed to process current chart
        *        element based on column pos & name then move to the next element.
        */

        // Get the column position based on the column number provided.
        if (currentColumnNum != null) {
          try {
            foundColumn = ChartSeriesDataLinkInfoFactory.processColumnPos(currentColumnNum, currentChartElement, chartTableModel, seriesDataLinkInfo);
          } catch (NumberFormatException ignore) {
            ChartSeriesDataLinkInfoFactory.logger.warn(Messages.getString("ChartSeriesDataLinkInfoFactory.WARN_COLUMN_NUM_IS_NOT_VALID_INTEGER", (String) currentColumnNum, null)); //$NON-NLS-1$
          }
        }

        // If we haven't matched the current series to a column position and if we are provided 
        // with a column name, then get the column position based on column name. 
        if (!foundColumn && currentColumnName != null) {
          foundColumn = ChartSeriesDataLinkInfoFactory.processColumnName(currentColumnName, currentChartElement, chartTableModel, seriesDataLinkInfo);
        }

        // If we haven't found the column and col name and col pos are null 
        // then get the col position based on series count
        if (!foundColumn && currentColumnName == null && currentColumnNum == null) {
          if (seriesCount < dataColumnCount) {
            seriesDataLinkInfo.setColumnNum(currentChartElement, seriesCount);
            foundColumn = true;
          }
        }

        //Update the series counter        
        seriesCount++;
      }

      currentChartElement = currentChartElement.getNextItem();
    }
  }

  /**
   * Get the column position based on the column position given in the series data.
   * If column position specified is correct then update the hash map.
   *
   * @param currentColumnNum
   * @param currentChartElement
   * @param chartTableModel
   * @param seriesDataLinkInfo
   * @return Returns true if the column position was found.
   * @throws NumberFormatException
   */
  private static boolean processColumnPos(final Object currentColumnNum, final ChartElement currentChartElement,
                                          final ChartTableModel chartTableModel, final ChartSeriesDataLinkInfo seriesDataLinkInfo)
      throws NumberFormatException {
    boolean foundColumn = false;

    int columnNum = -1;
    
    if (currentColumnNum instanceof String) {
      columnNum = Integer.parseInt(((String) currentColumnNum).trim());     
    } else {
      columnNum = ((Number) currentColumnNum).intValue();
    }
    
    

    // Check if it is a valid column number. The column number(s) are zero based.   
    if (columnNum >= 0 && columnNum < chartTableModel.getColumnCount()) {
      // Casting from int to Integer implicitly
      seriesDataLinkInfo.setColumnNum(currentChartElement, columnNum);
      foundColumn = true;
    } else {
      ChartSeriesDataLinkInfoFactory.logger.warn(Messages.getString("ChartSeriesDataLinkInfoFactory.WARN_INCORECT_COLUMN_NUM", currentColumnNum.toString(), null)); //$NON-NLS-1$
    }

    return foundColumn;
  }

  /**
   * Get the column position based on the column name given in the series data.
   * If found then update the hash map.
   *
   * @param currentColumnName
   * @param currentChartElement
   * @param chartTableModel
   * @param seriesDataLinkInfo
   * @return Returns true if the column name matches the a column name in metadata.
   */
  private static boolean processColumnName(final Object currentColumnName, final ChartElement currentChartElement,
                                           final ChartTableModel chartTableModel, final ChartSeriesDataLinkInfo seriesDataLinkInfo) {
    final String columnName = ((String) currentColumnName).trim();
    boolean foundColumn = false;
    final int dataColumnCount = chartTableModel.getColumnCount();

    if (columnName.length() <= 0) {
      ChartSeriesDataLinkInfoFactory.logger.warn(Messages.getString("ChartSeriesDataLinkInfoFactory.WARN_COLUMN_NAME_IS_NULL")); //$NON-NLS-1$
    } else {
      /* 
       * We ignore case when matching the column names in the series tag to the metadata.
       * We are iterating through the data array and getting the column name for each
       * column in the data array. If found then we insert the given chart element into the
       * hashmap with the given column position
       */
      for (int i = 0; i < dataColumnCount; i++) {
        final String columnNameFromChartTableModel = chartTableModel.getColumnName(i);
        if (columnNameFromChartTableModel != null && columnNameFromChartTableModel.equalsIgnoreCase(columnName)) {
          seriesDataLinkInfo.setColumnNum(currentChartElement, i);
          foundColumn = true;
          break;
        }
      }
    }
    return foundColumn;
  }
}
