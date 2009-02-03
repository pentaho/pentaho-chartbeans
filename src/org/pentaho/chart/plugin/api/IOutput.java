package org.pentaho.chart.plugin.api;

import java.io.OutputStream;
import java.io.Writer;


/**
 * @author wseyler
 */
public interface IOutput
{
  public enum OutputTypes
  {
    FILE_TYPE_JPEG,
    FILE_TYPE_PNG,
    DATA_TYPE_STREAM
  }
//  public static final int FILE_TYPE_JPEG = 1;
  //  public static final int FILE_TYPE_PNG = 2;
  //public static final int CHART_DEFINITION = 3;

  /**
   * Sends the current chart to the outputStream and formats it to the file of fileType.
   *
   * @param outputStream
   * @param fileType
   * @return
   * @throws PersistenceException
   */
  public OutputStream persistChart(OutputStream outputStream, OutputTypes fileType, int width, int height) throws PersistenceException;

  /**
   * Persists the current Map to the outputStream using the mapname for the generated map.
   * <p/>
   * For this call to be useful it should follow a call to PersistChart.
   *
   * @param outputStream
   * @param mapName
   * @return
   * @throws PersistenceException
   */
  public Writer persistMap(Writer outputStream, String mapName) throws PersistenceException;

  public Object getDrawable();
}
