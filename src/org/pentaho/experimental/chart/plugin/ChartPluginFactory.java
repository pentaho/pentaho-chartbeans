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
 */
public class ChartPluginFactory  {
  private static final Log logger = LogFactory.getLog(ChartPluginFactory.class);
  static IChartPlugin chartPlugin = null;
  
  public static synchronized IChartPlugin getChartPlugin() {
    Configuration config = ChartBoot.getInstance().loadConfiguration();
    String className = config.getConfigProperty("IChartPlugin"); //$NON-NLS-1$
    Class pluginClass = null;
    try {
      pluginClass = Class.forName(className);
    } catch (ClassNotFoundException e) {
      logger.error(e);
    }
    if (chartPlugin == null || !chartPlugin.getClass().equals(pluginClass)) {
      chartPlugin = getChartPlugin(className);
    }
    return chartPlugin;
  }

  /**
   * @param className
   * @return
   */
  public static IChartPlugin getChartPlugin(String className) {
    try {
      return (IChartPlugin) Class.forName(className).newInstance();
    } catch (ClassNotFoundException e) {
      logger.error(e);
    } catch (InstantiationException e) {
      logger.error(e);
    } catch (IllegalAccessException e) {
      logger.error(e);
    }
    return null;
  }
  
  public static IOutput getChartOutput() {
    Configuration config = ChartBoot.getInstance().loadConfiguration();
    String className = config.getConfigProperty("IOutput");
    try {
      return  (IOutput) Class.forName(className).newInstance();
    } catch (InstantiationException e) {
      logger.error(e);
    } catch (IllegalAccessException e) {
      logger.error(e);
    } catch (ClassNotFoundException e) {
      logger.error(e);
    }
    return null;
  }

}
