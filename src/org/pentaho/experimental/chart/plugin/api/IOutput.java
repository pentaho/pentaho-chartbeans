package org.pentaho.experimental.chart.plugin.api;

import java.io.OutputStream;
import java.net.URL;

import org.pentaho.experimental.chart.plugin.api.engine.Chart;


public interface IOutput {
  public static final int FILE_TYPE_JPG = 1;
  public static final int FILE_TYPE_PNG = 2;
  public static final int CHART_DEFINITION = 3;
  
  public Chart getChart();
  public void setChart(Chart chart);
  public void persist() throws PersistenceException;

  public void setLocation(URL baseLocationURL);
  public void setFileType(int fileType);
  public int getFileType();
  public void setFilename(String filename);
  public URL getLastSaveLocation();
  public void persist(byte[] data) throws PersistenceException;
  public void persist(URL baseLocationURL, String filename, int fileType, byte[] data);
  public OutputStream getAsStream() throws Exception;
}
