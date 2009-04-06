package org.pentaho.chart.plugin.jfreechart.chart.bar;

/**
 * These are the different high level bar chart types based on the dataset and the chart type used to create them.
 * </p> 
 * Author: Ravi Hasija
 * Date: May 15, 2008
 * Time: 1:07:41 PM 
 */
public class JFreeBarChartTypes {
  public static final String DEFAULT="default";  //$NON-NLS-1$
  public static final String STACKED="stacked";  //$NON-NLS-1$
  public static final String INTERVAL="interval";  //$NON-NLS-1$
  public static final String WATERFALL = "waterfall";  //$NON-NLS-1$

  /**
   * To avoid instantiation from outside world.
   */
  private JFreeBarChartTypes() {
  }
}
