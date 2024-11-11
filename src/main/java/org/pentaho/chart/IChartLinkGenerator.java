/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart;


/**
 * This interface provides a collection of callback methods used by the chart beans API which
 * give the api user the ability link data points in the chart URLs or javascript. When the user
 * clicks on a bar, point, or pie slice the user will execute the link or javascript provided by 
 * implementors of this interface
 * @author arodriguez
 */
public interface IChartLinkGenerator {
  
  /**
   * Callback used to generate links for the various parts of a chart. An example would
   * be a line chart with multiple lines(series) where the x-axis contains string values.
   * @param seriesName the series to which the current data point belongs. If your drawing 
   * a line/area chart with multiple lines/areas the series name identifies each of the 
   * line/area. With bar charts the series names defines the collection of bars of the same 
   * color. If your chart only contains a single line, area, or bar collection this value may 
   * be null. This will be null for pie and dial charts.
   * @param domainName the data point domain value. When drawing a chart with an X & Y axis the
   * domain value appears along x-axis. Unless the chart has a horizontal orientation, which will 
   * cause the domain value to appear along the y-axis (ex. horizontal bar charts).
   * @param rangeValue the data point range value. When drawing a chart with an X & Y axis the
   * range value appears along y-axis. Unless the chart has a horizontal orientation, which will 
   * cause the range value to appear along the x-axis (ex. horizontal bar charts).
   * @return the URL or a javascript function call to be executed when the data point is clicked. 
   * May be null. If returning a javascript function call and a JFree chart is being generated then
   * the returned string must start with "javascript:" (Ex. javascript:alert('hello world')). The javascript:
   * prefix should be loft off for open flash charts.
   * 
   */
  public String generateLink(String seriesName, String domainName, Number rangeValue);
  
  /**
   * Callback used to generate links for the various parts of a chart. An example would
   * be a line chart with multiple lines(series) where the x-axis contains string values.
   * @param seriesName the series to which the current data point belongs. If your drawing 
   * a line/area chart with multiple lines/areas the series name identifies each of the 
   * line/area. With bar charts the series names defines the collection of bars of the same 
   * color. If your chart only contains a single line, area, or bar collection this value may 
   * be null. This will be null for pie and dial charts.
   * @param domainName the data point domain value. When drawing a chart with an X & Y axis the
   * domain value appears along x-axis. Unless the chart has a horizontal orientation, which will 
   * cause the domain value to appear along the y-axis (ex. horizontal bar charts).
   * @param rangeValue the data point range value. When drawing a chart with an X & Y axis the
   * range value appears along y-axis. Unless the chart has a horizontal orientation, which will 
   * cause the range value to appear along the x-axis (ex. horizontal bar charts).
   * @return the URL or javascript to be executed when the data point is clicked. May be null.
   * If returning a javascript function call and a JFree chart is being generated then
   * the returned string must start with "javascript:" (Ex. javascript:alert('hello world')). The 
   * javascript: prefix should be loft off for open flash charts.
   */
  public String generateLink(String seriesName, Number domainValue, Number rangeValue);
}
