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

import org.pentaho.chart.data.ChartTableModel;

import java.util.HashMap;

/**
 * This class stores the link between the chart series element and the column position it
 * refers to.
 *
 * @author Ravi Hasija
 */
public class ChartSeriesDataLinkInfo {

  private final HashMap<ChartElement, Integer> seriesDataLinkHashMap = new HashMap<ChartElement, Integer>();

  ChartSeriesDataLinkInfo(final ChartTableModel data) {
    final ChartTableModel chartTableModelObj = data;
  }

  /**
   * Inserts the series element as the Key and the column position as the value.
   *
   * @param seriesElement Series element is put into the hash as a KEY.
   * @param columnPos     Column position acts as the VALUE for the chart element KEY.
   */
  public void setColumnNum(final ChartElement seriesElement, final Integer columnPos) {
    seriesDataLinkHashMap.put(seriesElement, columnPos);
  }

  /**
   * Returns the column number based on the chart element object (provided as an input).
   *
   * @param seriesElement
   * @return columnPosition Returns the column number based on the series element.
   */
  public Integer getColumnNum(final ChartElement seriesElement) {
    return seriesDataLinkHashMap.get(seriesElement);
  }

  /**
   * Returns the size of the chart series data hash map size
   *
   * @return Returns the size of the chart series data hash map size
   */
  public int getDataSize() {
    return seriesDataLinkHashMap.size();
  }

  /**
   * Returns true if the hash map has the given column position, false otherwise.
   *
   * @param columnPos
   * @return Returns true if the hash map has column position.
   */
  public boolean hasColumnPos(final Integer columnPos) {
    return seriesDataLinkHashMap.containsValue(columnPos);
  }

  /**
   * Returns true if the data set has the given series element.
   *
   * @param seriesElement
   * @return Returns true if the hash map has series element.
   */
  public boolean hasSeriesElement(final ChartElement seriesElement) {
    return seriesDataLinkHashMap.containsKey(seriesElement);
  }
}
