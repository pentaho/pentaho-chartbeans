/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package org.pentaho.experimental.chart.plugin;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;

import org.pentaho.experimental.chart.plugin.api.PersistenceException;
import org.pentaho.experimental.chart.plugin.api.IOutput;

/**
 * Todo: Document me!
 *
 * @author : Thomas Morgner
 */
public class OutputUtils
{
  public static void persistChart(IOutput output,
                           String filePath,
                           IOutput.OutputTypes fileType)
      throws PersistenceException, IOException
  {
    final FileOutputStream stream = new FileOutputStream(filePath);
    try
    {
      output.persistChart(stream , fileType);
    }
    finally {
      stream.close();
    }
  }


  /**
   * Persists the current map to the filePath.  The the postfix of ".map" is added to the filename.
   * <p>
   * For this call to be useful it should follow a call to PersistChart.
   *
   * @param output
   * @param filePath
   * @throws PersistenceException
   * @throws java.io.IOException
   */
  public static void persistMap(IOutput output, String filePath)
      throws PersistenceException, IOException
  {
    final FileWriter filestream = new FileWriter(filePath);
    BufferedWriter outputStream = new BufferedWriter(filestream);
    try
    {
      output.persistMap(outputStream, filePath);
    }
    finally {
      outputStream.close();
    }
  }

}
