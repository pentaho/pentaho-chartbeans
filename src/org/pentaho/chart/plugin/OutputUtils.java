/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

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
