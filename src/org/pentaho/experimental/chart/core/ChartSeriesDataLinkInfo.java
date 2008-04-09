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
 * @Ravi Hasija
 */
package org.pentaho.experimental.chart.core;

import org.pentaho.experimental.chart.data.ChartTableModel;

import java.util.HashMap;

/**
 * This class stores the link between the chart series element and the column position it
 * refers to.
 *
 * @author Ravi Hasija
 */
public class ChartSeriesDataLinkInfo {

  private HashMap<ChartElement, Integer> seriesDataLinkHashMap = new HashMap<ChartElement, Integer>();

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
    final Integer columnPos = seriesDataLinkHashMap.get(seriesElement);
    return columnPos;
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