package org.pentaho.experimental.chart.plugin.api;

import java.io.OutputStream;


public interface IOutput {
  public static final int FILE_TYPE_JPEG = 1;
  public static final int FILE_TYPE_PNG = 2;
  public static final int CHART_DEFINITION = 3;
  
  public Object getChart();
  public void setChart(Object chart);
  public void persist() throws PersistenceException;

  public void setFileType(int fileType);
  public int getFileType();
  
  /**
   * Gets the filename that is currently being used to persist the chart
   * 
   * @return a path/filename that represents the current
   * persist location.
   */
  public String getFilename();
  
  /**
   * Sets the filename that will be used to persist the chart
   * @param filename
   */
  public void setFilename(String filename);
  
  /**
   * Sets the output stream to the concrete outputStream.  This allows the user to create
   * an arbitrary outputstream type and have it filled in when a call is made to getChartAsStream
   *
   * @param outputStream
   */
  public void setOutputStream(OutputStream outputStream);

  /**
   * @return an OutputStream.  Implementations should default to ByteArrayOutputStream otherwise 
   * it returns a reference to the outputstream set in the setOutputStream method.
   * @throws PersistenceException
   */
  public OutputStream getChartAsStream() throws PersistenceException;
}
