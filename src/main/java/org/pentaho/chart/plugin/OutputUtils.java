/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.chart.plugin;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;

import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.chart.plugin.api.PersistenceException;

/**
 * Todo: Document me!
 *
 * @author : Thomas Morgner
 */
public class OutputUtils
{
  public static void persistChart(IOutput output,
                           String filePath,
                           IOutput.OutputTypes fileType,
                           int width,
                           int height)
      throws PersistenceException, IOException
  {
    final FileOutputStream stream = new FileOutputStream(filePath);
    try
    {
      output.persistChart(stream , fileType, width, height);
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
