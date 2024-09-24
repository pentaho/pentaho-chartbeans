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

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.MultiKeyMap;

/**
 * This class stores the axis to series mapping in addition to storing the respective axis element.
 *
 * For eg: the data structure stores the axis element and series elements that refer to the axis element.
 * This information can be retrieved using axis id.
 *
 * {axis-id1}{axisElement} = <ChartElement> that stores the axis info
 * {axis-id1}{seriesElements} = <ArrayList<ChartElement>> the array list stores the series elements that refer axis-id1
 *  
 * @author Ravi Hasija
 */
public class AxisSeriesLinkInfo {
  
  private final MultiKeyMap axisSeriesMap;
  private final String RANGE="range"; //$NON-NLS-1$
  private final String DOMAIN="domain"; //$NON-NLS-1$
  private final int AXIS_ID_POSN=0;
  private final int ELEMENT_TYPE_POSN=1;

  /**
   * The constructor initializes requisiste data structures
   */
  public AxisSeriesLinkInfo() {
    axisSeriesMap = MultiKeyMap.decorate(new LinkedMap());
  }

  /**
   * Stores the value: axis element in the hash map where the key is axis id.
   * @param axisID      - Axis ID for the current axis element
   * @param axisElement - Current axis element
   */
  public void setAxisElement(final Object axisID, final ChartElement axisElement) {
    if (axisID != null && axisElement != null) {
      axisSeriesMap.put(axisID, ChartElement.TAG_NAME_AXIS, axisElement);
    }
  }

  /**
   * Returns chart axis element with the given axis id.
   * @param axisID - AxisID for the element you want to retrieve.
   * @return ChartElement - Chart axis element with the given axis id.
   */
  public ChartElement getAxisElement(final Object axisID) {
    if (axisID != null) {
      return (ChartElement)axisSeriesMap.get(axisID, ChartElement.TAG_NAME_AXIS);
    } else {
      return null;
    }
  }

  /**
   * Updates the series elements array list with the given element.
   * @param axisID      - Axis ID for the current axis element
   * @param seriesElement - Series element that belongs to the given axis id
   */
  public void setSeriesElements(final Object axisID, final ChartElement seriesElement) {
    if (axisID != null && seriesElement != null) {
      ArrayList<ChartElement> seriesElementsList = (ArrayList<ChartElement>)axisSeriesMap.get(axisID, ChartElement.TAG_NAME_SERIES);

      if (seriesElementsList == null) {
        seriesElementsList = new ArrayList<ChartElement>();
      }
      seriesElementsList.add(seriesElement);
      axisSeriesMap.put(axisID, ChartElement.TAG_NAME_SERIES, seriesElementsList);
    }
  }

  /**
   * Returns the array list consisting of the series elements that belong to the given axis id.
   * 
   * @param axisID - The axis id to get the series elements for.
   * @return ArrayList<ChartElement> - Returns the array list consisting of the series elements that
   *                                   belong to the given axis id.
   */
  public ArrayList<ChartElement> getSeriesElements(final Object axisID) {
      if (axisID == null) {
        return null;
      } else {
        return (ArrayList<ChartElement>)axisSeriesMap.get(axisID, ChartElement.TAG_NAME_SERIES);
      }
    }

  /**
   * Returns an array list of range axis elements.
   * If there are no range axis elements then returns an empty list.
   *
   * @return ArrayList<ChartElement> - ArrayList consisting of range axis elements.
   */
  public ArrayList<ChartElement> getRangeAxisElements() {
    return getAxisElements(RANGE);    
  }

  /**
   * Returns an array list of domain axis elements.
   * If there are no domain axis elements then returns an empty list
   *
   * @return ArrayList<ChartElement> - ArrayList consisting of domain axis elements.
   */
  public ArrayList<ChartElement> getDomainAxisElements() {
    return getAxisElements(DOMAIN);
  }

  /**
   * Helper method that actually does go through the entire map to return requested axis type elements.
   *
   * @param  axisType  - Range/Domain axis type.
   * @return ArrayList<ChartElement> - ArrayList consisting of axist type axis elements.
   */
  private ArrayList<ChartElement> getAxisElements(final String axisType) {
    ArrayList<ChartElement> axisElements = null;

    final Set<MultiKeyMap.Entry<MultiKey, Object>> setOfAxisSeriesMap = axisSeriesMap.entrySet();

    if (setOfAxisSeriesMap != null) {
      axisElements = new ArrayList<ChartElement>();
      for (MultiKeyMap.Entry<MultiKey, Object> currSetEntry : setOfAxisSeriesMap) {
        final MultiKey key = (MultiKey)currSetEntry.getKey();

        // If the current element is of type axis and if the type of axis element is the same as requested type
        // then store it in the axisElements array list
        if (key.getKey(ELEMENT_TYPE_POSN).equals(ChartElement.TAG_NAME_AXIS)) {
          // Get the axis ID from the current multi-key ([axis id, element type] data structure).
          final Object axisID = key.getKey(AXIS_ID_POSN);
          // Get the axis element for the given axis id.
          final ChartElement currentAxisElement = (ChartElement)axisSeriesMap.get(axisID, ChartElement.TAG_NAME_AXIS);

          // Check if the axis type (range / domain) is the same as requested axisType. If so then add the current axis
          // element to the array list
          if (currentAxisElement != null) {
            final String currentType = (String)currentAxisElement.getAttribute("type"); //$NON-NLS-1$
            if (currentType != null && axisType.equalsIgnoreCase(currentType)) {
              axisElements.add(currentAxisElement);
            }
          }
        }
      }
    }
    return axisElements;
  }
}// Class Ends
