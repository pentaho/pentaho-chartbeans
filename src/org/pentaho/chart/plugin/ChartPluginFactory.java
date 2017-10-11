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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.reporting.libraries.base.config.Configuration;
import org.pentaho.reporting.libraries.base.util.ObjectUtilities;

/**
 * @author wseyler
 *
 * This is a factory class that returns the desired implementation of IChartPlugin based either
 * on the chart.properties document or on the requested class.
 */
public class ChartPluginFactory  {
  private static final Log logger = LogFactory.getLog(ChartPluginFactory.class);

  private ChartPluginFactory() {
  }

  /**
   * Creates an instance of the IChartPlugin as defined in the chart.properties document if one
   * doesn't already exist.  If one exists it will return that one.
   * 
   * @return an implementation of the IChartPlugin
   */
  public static synchronized IChartPlugin getInstance() throws ChartProcessingException
  {
    return getInstance(null);
  }

  /**
   * Returns an instance of the IChartPlugin from the className.  Logs errors and
   * returns null if the class couldn't be created.
   * 
   * @param className
   * @return
   */
  public static IChartPlugin getInstance(String className) throws ChartProcessingException
  {
    if (className == null)
    {
      final Configuration config = ChartBoot.getInstance().loadConfiguration();
      className = config.getConfigProperty("org.pentaho.chart.plugin.Default-IChartPlugin"); //$NON-NLS-1$
      if (className == null)
      {
        throw new ChartProcessingException("No ChartPlugin defined as default");
      }
    }
    try {
      final IChartPlugin o = (IChartPlugin)
          ObjectUtilities.loadAndInstantiate(className, ChartPluginFactory.class, IChartPlugin.class);
      if (o == null)
      {
        throw new ChartProcessingException("Unable to instantiate the requested chart-plugin: " + className);
      }
      return o;
    } catch (Exception e) {
      ChartPluginFactory.logger.error(e);
      throw new ChartProcessingException("Error while instantiating the Chart-Plugin " + className, e);
    }
  }
//
//  /**
//   * Creates an new instance of IOutput as defined in the chart.properties document.
//   *
//   * @return an implementation of IOutput
//   */
//  public static IOutput getChartOutput() throws ChartProcessingException
//  {
//    final Configuration config = ChartBoot.getInstance().loadConfiguration();
//    final String className = config.getConfigProperty("org.pentaho.chart.plugin.Default-IOutput"); //$NON-NLS-1$
//    return ChartPluginFactory.getChartOutput(className);
//  }
//
//  /**
//   * Creates an new instance of IOutput as defined by the classname parameter.  Logs
//   * any errors and returns null of class couldn't be created.
//   *
//   * @param className
//   * @return an implementation of IOutput
//   */
//  public static IOutput getChartOutput(final String className) throws ChartProcessingException
//  {
//    try {
//      final IOutput o = (IOutput)
//          ObjectUtilities.loadAndInstantiate(className, ChartPluginFactory.class, IOutput.class);
//      if (o == null)
//      {
//        throw new ChartProcessingException("Unable to instantiate the requested chart-output: " + className);
//      }
//      return o;
//    } catch (Exception e) {
//      ChartPluginFactory.logger.error(e);
//      throw new ChartProcessingException("Unable to instantiate the requested chart-plugin: " + className, e);
//    }
//  }

}
