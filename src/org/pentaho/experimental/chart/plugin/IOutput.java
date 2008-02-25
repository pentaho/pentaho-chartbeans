package org.pentaho.experimental.chart.plugin;

import java.net.URL;


public interface IOutput {
  public static final int FILE_TYPE_JPG = 1;
  public static final int FILE_TYPE_PNG = 2;
  public static final int CHART_DEFINITION = 3;
  
  public void setLocation(URL baseLocationURL);
  public void setFileType(int FileType);
  public int getFileType();
  public void setFilename(String filename);
  public URL getLastSaveLocation();
  public void persist(byte[] object) throws PersistenceException;
}
