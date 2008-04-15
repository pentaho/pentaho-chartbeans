package org.pentaho.experimental.chart.plugin.api;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.OutputStream;


/**
 * @author wseyler
 *
 */
public interface IOutput {
  public enum OutputTypes {
    FILE_TYPE_JPEG,
    FILE_TYPE_PNG,
    DATA_TYPE_STREAM
  }
//  public static final int FILE_TYPE_JPEG = 1;
//  public static final int FILE_TYPE_PNG = 2;
  public static final int CHART_DEFINITION = 3;
  
  public static final String MAP_EXTENSION = ".map"; //$NON-NLS-1$
  
  /**
   * Persists the current chart using the filepath as the path/name of the file and
   * fileType as the file type to persist it as.
   * 
   * @param filePath
   * @param fileType
   * @throws PersistenceException
   */
  public void persistChart(String filePath, OutputTypes fileType) throws PersistenceException; 
  
  /**
   * Sends the current chart to the outputStream and formats it to the file of fileType.
   * 
   * @param outputStream
   * @param fileType
   * @return
   * @throws PersistenceException
   */
  public OutputStream persistChart(OutputStream outputStream, OutputTypes fileType) throws PersistenceException;

  /**
   * Persists the current map to the filePath.  The the postfix of ".map" is added to the filename.
   * <p>
   * For this call to be useful it should follow a call to PersistChart.
   * 
   * @param filePath
   * @throws PersistenceException
   */
  public void persistMap(String filePath) throws PersistenceException;
  
  /**
   * Persists the current Map to the outputStream using the mapname for the generated map.
   * <p>
   * For this call to be useful it should follow a call to PersistChart.
   * @param outputStream
   * @param mapName
   * @return
   * @throws PersistenceException
   */
  public OutputStream persistMap(OutputStream outputStream, String mapName) throws PersistenceException;

  /**
   * Get the string representation of the map.
   * <p>
   * For this call to be useful it need to follow a call to PersistChart.
   * 
   * @param mapName
   * @return
   */
  public String getMap(String mapName); 
  
  /**
   * Set the current chart to chart
   * 
   * @param chart
   */
  public void setChart(final Object chart);
  
  /**
   * Draws the current chart to the graphics canvas locating it and sizing it to the rectangle.
   * 
   * @param graphics
   * @param rectangle
   */
  public void draw(Graphics2D graphics, Rectangle2D rectangle);
}
