package org.pentaho.experimental.chart.plugin.api;

import java.io.OutputStream;

import org.pentaho.experimental.chart.plugin.api.engine.Chart;


public interface IOutput {
  public static final int FILE_TYPE_JPG = 1;
  public static final int FILE_TYPE_PNG = 2;
  public static final int CHART_DEFINITION = 3;
  
  public Chart getChart();
  public void setChart(Chart chart);
  public void persist() throws PersistenceException;

  public void setFileType(int fileType);
  public int getFileType();
  public void setFilename(String filename);
  /**
   * @param outputStream
   * Sets the output stream to the concrete outputStream.  This allows the user to create
   * an arbitrary outputstream type and have it filled in when a call is made to getChartAsStream
   */
  public void setOutputStream(OutputStream outputStream);
  /**
   * @return an OutputStream.  Defaults to ByteArrayOutputStream otherwise it returns a
   * reference to the outputstream set in the setOutputStream method.
   * @throws PersistenceException
   */
  public OutputStream getChartAsStream() throws PersistenceException;
}
