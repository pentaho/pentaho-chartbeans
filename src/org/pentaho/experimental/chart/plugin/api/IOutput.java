package org.pentaho.experimental.chart.plugin.api;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.OutputStream;


public interface IOutput {
  public static final int FILE_TYPE_JPEG = 1;
  public static final int FILE_TYPE_PNG = 2;
  public static final int CHART_DEFINITION = 3;
  
  public static final String MAP_EXTENSION = ".map"; //$NON-NLS-1$
  
  public void persistChart(String filePath, int fileType) throws PersistenceException; 
  public OutputStream persistChart(OutputStream outputStream, int fileType) throws PersistenceException;
  public void persistMap(String filePath) throws PersistenceException;
  public OutputStream persistMap(OutputStream outputStream, String mapName) throws PersistenceException;
  public String getMap(String mapName);
  public void setChart(final Object chart);
  public void draw(Graphics2D graphics, Rectangle2D rectangle);
}
