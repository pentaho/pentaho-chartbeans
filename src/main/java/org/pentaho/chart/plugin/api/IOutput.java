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
    DATA_TYPE_STREAM,
    FILE_TYPE_HTML
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
