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

import org.jfree.resourceloader.ResourceManager;
import org.jfree.resourceloader.ResourceKey;

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
   * Constructor that creats the chart document.
   * @param rootElement the parsed root element of the chart document
   */
  public ChartDocument(ChartElement rootElement) {
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
}
