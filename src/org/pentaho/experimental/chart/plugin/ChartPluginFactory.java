/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * @created Feb 25, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.reporting.libraries.base.config.Configuration;

/**
 * @author wseyler
 *
 * This is a factory class that returns the desired implementation of IChartPlugin based either
 * on the chart.properties document or on the requested class.
 */
public class ChartPluginFactory  {
  private static final Log logger = LogFactory.getLog(ChartPluginFactory.class);
  private static IChartPlugin chartPlugin = null;

  private ChartPluginFactory() {
  }

  /**
   * Creates an instance of the IChartPlugin as defined in the chart.properties document if one
   * doesn't already exist.  If one exists it will return that one.
   * 
   * @return an implementation of the IChartPlugin
   */
  public static synchronized IChartPlugin getChartPlugin() {
    final Configuration config = ChartBoot.getInstance().loadConfiguration();
    final String className = config.getConfigProperty("IChartPlugin"); //$NON-NLS-1$
    Class<IChartPlugin> pluginClass = null;
    try {
      pluginClass = (Class<IChartPlugin>) Class.forName(className);
    } catch (ClassNotFoundException e) {
      ChartPluginFactory.logger.error(e);
    }
    if (ChartPluginFactory.chartPlugin == null || !ChartPluginFactory.chartPlugin.getClass().equals(pluginClass)) {
      ChartPluginFactory.chartPlugin = ChartPluginFactory.getChartPlugin(className);
    }
    return ChartPluginFactory.chartPlugin;
  }

  /**
   * Returns an instance of the IChartPlugin from the className.  Logs errors and
   * returns null if the class couldn't be created.
   * 
   * @param className
   * @return
   */
  public static IChartPlugin getChartPlugin(final String className) {
    try {
      return (IChartPlugin) Class.forName(className).newInstance();
    } catch (ClassNotFoundException e) {
      ChartPluginFactory.logger.error(e);
    } catch (InstantiationException e) {
      ChartPluginFactory.logger.error(e);
    } catch (IllegalAccessException e) {
      ChartPluginFactory.logger.error(e);
    }
    return null;
  }
  
  /**
   * Creates an new instance of IOutput as defined in the chart.properties document.
   * 
   * @return an implementation of IOutput
   */
  public static IOutput getChartOutput() {
    final Configuration config = ChartBoot.getInstance().loadConfiguration();
    final String className = config.getConfigProperty("IOutput"); //$NON-NLS-1$
    return ChartPluginFactory.getChartOutput(className);
  }

  /**
   * Creates an new instance of IOutput as defined by the classname parameter.  Logs 
   * any errors and returns null of class couldn't be created.
   * 
   * @param className
   * @return an implementation of IOutput
   */
  public static IOutput getChartOutput(final String className) {
    try {
      return (IOutput) Class.forName(className).newInstance();
    } catch (ClassNotFoundException e) {
      ChartPluginFactory.logger.error(e);
    } catch (InstantiationException e) {
      ChartPluginFactory.logger.error(e);
    } catch (IllegalAccessException e) {
      ChartPluginFactory.logger.error(e);
    }
    return null;
  }

}
