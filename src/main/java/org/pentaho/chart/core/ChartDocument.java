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
import java.util.List;

import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * This is the object that contains the root element of the parsed chart defintion
 */
public class ChartDocument {

  /**
   * The top-most element in the parsed chart definition
   */
  private ChartElement rootElement;

  /**
   * The ResourceManager used to load the resources of the chart
   * (which may include the chart itself)
   */
  private ResourceManager resourceManager;

  /**
   * The resource key that servers as the base location for loading relative infomraiton
   */
  private ResourceKey resourceKey;
  
    /**
   * Cache of the series elements
   */
  private ChartElement[] cachedSeriesElements = null;

  /**
   * Modification number when the series elements cache was created (used to invalidate the cache)
   */
  private long cachedSeriesElementModNumber = 0L;

  /**
   * Cache of the group elements
   */
  private ChartElement[] cachedGroupElements = null;

  /**
   * Modification number when the group elements cache was created (used to invalidate the cache)
   */
  private long cachedGroupElementModNumber = 0L;

  /**
   * The cached version of the plot element
   */
  private ChartElement cachedPlotElement = null;

  /**
   * Modification number of when the plot element was cached (used to invalidate the cache)
   */
  private long cachedPlotElementModNumber = 0L;

  /**
   * The cached version of the plot element
   */
  private ChartElement[] cachedAxisElements = null;

  /**
   * Modification number of when the plot element was cached (used to invalidate the cache)
   */
  private long cachedAxisElementModNumber = 0L;

  
  /**
   * Is true if we have processed the axis elements and retrieved pertinent information.
   * This is so that we can use the cache instead of processing the axis elements every time.
   */
  private boolean processedAxisElements = false;
  
  /**
   * This class object stores the series mapping to axis element by way of axis-id.
   */
  private AxisSeriesLinkInfo axisSeriesLinkInfo;

  /**
   * Constructor that creats the chart document.
   *
   * @param rootElement the parsed root element of the chart document
   */
  public ChartDocument(final ChartElement rootElement) {
    if (rootElement == null) {
      throw new IllegalArgumentException("Root Element can not be null"); //$NON-NLS-1$
    }
    this.rootElement = rootElement;    
  }

  /**
   * Returns the root element of the parsed chart document
   */
  public ChartElement getRootElement() {
    return rootElement;
  }

  /**
   * Returns the <code>ResourceManager</code> used to load the chart document
   */
  public ResourceManager getResourceManager() {
    return resourceManager;
  }

  /**
   * Returns the <code>ResourceKey</code> that serves as the base location for loading relative information
   */
  public ResourceKey getResourceKey() {
    return resourceKey;
  }

  /**
   * Sets the resource manager for this chart document
   */
  public void setResourceManager(final ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  /**
   * Sets the resource key for this chart document
   */
  public void setResourceKey(final ResourceKey resourceKey) {
    this.resourceKey = resourceKey;
  }

  /**
   * Returns the modification number from the root element. This number
   * can be used to invalidate cache information.
   */
  public long getModNumber() {
    return rootElement.getModNumber();
  }

  /**
   * Generates a string representation of the chart document
   */
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append(getClass().getName());
    if (rootElement == null) {
      sb.append(" [null]"); //$NON-NLS-1
    } else {
      sb.append("\n").append(rootElement.toString("  "));  //$NON-NLS-1$ //$NON-NLS-2$
    }
    return sb.toString();
  }

  /**
   * Indicates if the chart is a categorical chart. For this to be true, the chart element tag
   * must have the categorical attribute set to true.
   */
  public boolean isCategorical() {
    boolean result = false;
    final ChartElement root = getRootElement();
    if (root != null) {
      if (ChartElement.TAG_NAME_CHART.equals(root.getTagName())) {
        result = booleanAttributeValue(root, ChartElement.CATEGORICAL, false);
      }
    }
    return result;
  }

  /**
   * Indicates if the chart is a categorical chart. For this to be true, the chart element tag
   * must have the categorical attribute set to true.
   */
  public boolean isByRow() {
    boolean result = false;
    final ChartElement root = getRootElement();
    if (root != null) {
      if (ChartElement.TAG_NAME_CHART.equals(root.getTagName())) {
        result = booleanAttributeValue(root, ChartElement.BYROW, false);
      }
    }
    return result;
  }

  /**
   * Tests the specified chart element has the specified attribute AND if the value of that
   * attribute evalutates to <code>true</code>. If the attribute does not exist, the default value is used.
   * NOTE: this method is protected for testing purposes only.
   *
   * @param element       the element to test for the attribute
   * @param attributeName the name of the attribute to test for existance AND value
   * @param defaultResult the result that will be returned if the attribute does not exist for the element
   */
  protected static boolean booleanAttributeValue(final ChartElement element, final String attributeName, final boolean defaultResult) {
    boolean result = defaultResult;
    if (element != null) {
      final Object value = element.getAttribute(attributeName);
      if (value != null) {
        result = StringUtils.toBoolean(value.toString());
      }
    }
    return result;
  }

  /**
   * Creates a list of all the <code>series</code> ChartElements that are the children of the <code>chart</code> tag.
   * @return a list of <code>ChartElements</code> which represent the <code>series</code> tags. If there are none,
   * the list will be empty.
   */
  public ChartElement[] getSeriesChartElements() {
    final long currentModNumber = getModNumber();
    if (cachedSeriesElementModNumber != currentModNumber) {
      cachedSeriesElements = getChartLevelElements(ChartElement.TAG_NAME_SERIES);
      cachedSeriesElementModNumber = currentModNumber;
  }
    return cachedSeriesElements;
  }

  /**
   * Creates a list of all the <code>series</code> ChartElements that are the children of the <code>chart</code> tag.
   *
   * @return a list of <code>ChartElements</code> which represent the <code>series</code> tags. If there are none,
   *         the list will be empty.
   */
  public ChartElement[] getGroupChartElements() {
    final long currentModNumber = getModNumber();
    if (cachedGroupElementModNumber != currentModNumber) {
      cachedGroupElements = getChartLevelElements(ChartElement.TAG_NAME_GROUP);
      cachedGroupElementModNumber = currentModNumber;
    }
    return cachedGroupElements;
  }

  /**
   * Provides the plot element in the given chart document. Returns null if not found.
   *
   * @return ChartElement Returns the plot element for the given chart document.
   */
  public ChartElement getPlotElement() {
    final long currentModNumber = getModNumber();
    if (cachedPlotElementModNumber != currentModNumber) {
      cachedPlotElement = getChartLevelElement(ChartElement.TAG_NAME_PLOT);
      cachedPlotElementModNumber = currentModNumber;
    }
    return cachedPlotElement;
  }

  /**
   * Provides the axis element in the given chart document. Returns null if not found.
   *
   * @return ChartElement Returns the axis element for the given chart document.
   */
  public ChartElement[] getAxisElements() {
    final long currentModNumber = getModNumber();
    if (cachedAxisElementModNumber != currentModNumber) {
      cachedAxisElements = getChartLevelElements(ChartElement.TAG_NAME_AXIS);
      cachedAxisElementModNumber = currentModNumber;
    }
    return cachedAxisElements;
  }

  /**
   * Provides the CSSValue for Plot Orientation eg: horizontal or vertical
   *
   * @return CSSValue Represents the value for Plot Orientation.
   */
  public CSSValue getPlotOrientation() {
    final ChartElement plotElement = getPlotElement();
    CSSValue value = null;
    if (plotElement != null) {
      value = plotElement.getStyle(ChartStyleKeys.ORIENTATION);
    }
    return value;
  }

  /**
   * Gets the specified element from the chart document (achart element).
   * Returns the first occurrence of the given tag.
   * @param tagName The tagName to be looked up in the chart document at one level
   *                deeper than the chart tag.
   * @return ChartElement Returns the chart element.
   */
  public ChartElement getChartLevelElement(final String tagName) {
    ChartElement returnValue = null;

    if (rootElement != null && ChartElement.TAG_NAME_CHART.equals(rootElement.getTagName())) {
      ChartElement element = rootElement.getFirstChildItem();

      while (element != null) {
        if (tagName.equals(element.getTagName())) {
          returnValue = element;
          break;
        }
        element = element.getNextItem();
      }
    }
    return returnValue;
  }

  /**
   * Creats a list of chart elements with the specified tag name that are child elements of the root level (chart) element
   * @param tagname the tagname used in selecting elements
   */
  private ChartElement[] getChartLevelElements(final String tagname) {
    final List<ChartElement> elements = new ArrayList<ChartElement>();
    ChartElement element = rootElement.getFirstChildItem();
    while (element != null) {
      if (tagname.equals(element.getTagName())) {
        elements.add(element);
      }
      element = element.getNextItem();
    }
    return elements.toArray(new ChartElement[elements.size()]);
  }
  

  /**
   * Get all the axis elements from the chart document and store it in a multi key HashMap
   * where the key is the axis-id and "axis" tag. This is so that we can then retrieve each axis element by
   * their id and then retrieve the individual attribute from the axis element object.
   * We also store the series elements that refer the same axis id in an array list
   * that can be accessed using the axis id and "series" as the key.
   *
   * For eg: The data structure in AxisSeriesLinkInfo looks like below:
   * hashMap{axisID-1}{axis} = AxisElement;
   * hashMap{axisID-1}{series} = ArrayList of SeriesElements that refer axisID-1
   *
   * @return AxisSeriesLinkInfo - The class that stores the axis and series information in a multi key hash map.
   */
  public AxisSeriesLinkInfo getAxisSeriesLinkInfo() {
    /*
      If we have not processed the axis elements for the given chart document then
      1. Create axis series link info object
      2. Iterate over the elements
            If the element is a series element then store the series element in the array for the given axis id.
            If the element is an axis element then create a new hash map entry for the given axis id. 
     */
    if (!processedAxisElements) {
      axisSeriesLinkInfo = new AxisSeriesLinkInfo();

      ChartElement element = rootElement.getFirstChildItem();
      while (element != null) {
         // if the current element is an axis element then get the id of the element and
         // update the axis series link info
          if (ChartElement.TAG_NAME_AXIS.equals(element.getTagName())) {
            final Object axisId = element.getAttribute("id");
            if (axisId != null) {
              axisSeriesLinkInfo.setAxisElement(axisId, element);
            }
          } else if (ChartElement.TAG_NAME_SERIES.equalsIgnoreCase(element.getTagName())) {
            // if the current element is a series element then get the axis id of the series element and
            // update the axis series link info
            final Object axisId = element.getAttribute("axis-id");
            if (axisId != null) {
              axisSeriesLinkInfo.setSeriesElements(axisId, element);
            }
          }
        element = element.getNextItem();
      }
      processedAxisElements = true;
    }
    return axisSeriesLinkInfo;
  }
}
