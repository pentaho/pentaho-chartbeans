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
 * @author David Kincade
 */
package org.pentaho.experimental.chart.core;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.BooleanUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.resourceloader.ResourceKey;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartOrientationStyle;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.model.StyleKey;
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
  private List seriesTags;

  /**
   * Constructor that creats the chart document.
   *
   * @param rootElement the parsed root element of the chart document
   */
  public ChartDocument(ChartElement rootElement) {
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
  public void setResourceManager(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  /**
   * Sets the resource key for this chart document
   */
  public void setResourceKey(ResourceKey resourceKey) {
    this.resourceKey = resourceKey;
  }

  /**
   * Generates a string representation of the chart document
   */
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append(getClass().getName());
    if (rootElement == null) {
      sb.append(" [null]");
    } else {
      sb.append("\n").append(rootElement.toString("  "));
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
        result = BooleanUtils.toBoolean(value.toString());
      }
    }
    return result;
  }

  /**
   * Creates a list of all the <code>series</code> ChartElements that are the children of the <code>chart</code> tag.
   * @return a list of <code>ChartElements</code> which represent the <code>series</code> tags. If there are none,
   * the list will be empty.
   */
  public List getSeriesChartElements() {
    return getChartLevelElements(ChartElement.TAG_NAME_SERIES);
  }
  
  /**
   * 
   * @param tagName
   * @return
   */
  private ChartElement getChartLevelElement(String tagName) {
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
   * Creates a list of all the <code>series</code> ChartElements that are the children of the <code>chart</code> tag.
   * @return a list of <code>ChartElements</code> which represent the <code>series</code> tags. If there are none,
   * the list will be empty.
   */
  public List getGroupChartElements() {
    return getChartLevelElements(ChartElement.TAG_NAME_GROUP);
  }

  /**
   * Creats a list of chart elements with the specified tag name that are child elements of the root level (chart) element
   * @param tagname the tagname used in selecting elements
   */
  private List getChartLevelElements(String tagname) {
    List elements = new ArrayList();
    ChartElement element = rootElement.getFirstChildItem();
    while (element != null) {
      if (tagname.equals(element.getTagName())) {
        elements.add(element);
      }
      element = element.getNextItem();
    }
    return elements;
  }
  
  /**
   * Provides the plot orientation for the given chart document.
   * @return PlotOrientation  Returns the plot orientation for the given chart document.
   */
  public ChartElement getPlotElement(){
    ChartElement plotElement = getChartLevelElement(ChartElement.TAG_NAME_PLOT);
    return plotElement;
  }
  
}
