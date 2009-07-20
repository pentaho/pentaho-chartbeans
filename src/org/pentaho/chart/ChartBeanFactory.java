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
 * Created 2/7/2008 
 * @author David Kincade 
 */
package org.pentaho.chart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.pentaho.chart.data.CategoricalDataModel;
import org.pentaho.chart.data.MultiSeriesXYDataModel;
import org.pentaho.chart.data.IChartDataModel;
import org.pentaho.chart.data.NamedValue;
import org.pentaho.chart.data.NamedValuesDataModel;
import org.pentaho.chart.data.BasicDataModel;
import org.pentaho.chart.data.XYDataModel;
import org.pentaho.chart.data.XYDataPoint;
import org.pentaho.chart.data.CategoricalDataModel.Category;
import org.pentaho.chart.data.MultiSeriesXYDataModel.Series;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.ScatterPlot;
import org.pentaho.chart.plugin.ChartProcessingException;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.chart.plugin.api.PersistenceException;
import org.pentaho.chart.plugin.api.IOutput.OutputTypes;
import org.pentaho.chart.plugin.jfreechart.JFreeChartPlugin;
import org.pentaho.chart.plugin.openflashchart.OpenFlashChartPlugin;
import org.pentaho.reporting.libraries.resourceloader.ResourceKeyCreationException;

/**
 * API for generating charts
 *
 * @author David Kincade
 */
public class ChartBeanFactory {
  private static List<IChartPlugin> chartPlugins = initPlugins();

  private static final String CHART_PLUGINS_PROPERTIES_FILE = "chartPlugins.properties";

  private ChartBeanFactory() {
  }

  public static IChartPlugin getPlugin(String pluginId) {
    IChartPlugin plugin = null;
    for (IChartPlugin tmpPlugin : chartPlugins) {
      if (tmpPlugin.getPluginId().equals(pluginId)) {
        plugin = tmpPlugin;
      }
    }
    if ((plugin == null) && (chartPlugins.size() == 0)) {
      if (JFreeChartPlugin.PLUGIN_ID.equals(pluginId)) {
        plugin = new JFreeChartPlugin();
      } else if (OpenFlashChartPlugin.PLUGIN_ID.equals(pluginId)){
        plugin = new OpenFlashChartPlugin();
      }
    }
    return plugin;
  }

  private static List<IChartPlugin> initPlugins() {
    ArrayList<IChartPlugin> plugins = new ArrayList<IChartPlugin>();
    InputStream in = ChartBeanFactory.class.getClassLoader().getResourceAsStream(CHART_PLUGINS_PROPERTIES_FILE);
    if (in != null) {
      Properties properties = new Properties();
      try {
        properties.load(in);
        for (Enumeration enumeration = properties.elements(); enumeration.hasMoreElements();) {
          try {
            plugins.add((IChartPlugin) Class.forName(enumeration.nextElement().toString()).newInstance());
          } catch (Exception ex) {
            //Not able to construct the plugin so we won't add it to the list.
          }
        }
      } catch (Exception ex) {
        // We'll return an empty plugin list.
      }
    }
    return plugins;
  }



  public static InputStream createChart(Object[][] queryResults, ChartModel chartModel, int width, int height,
      OutputTypes outputType) throws ChartProcessingException, SQLException, ResourceKeyCreationException,
      PersistenceException {
    int rangeColumnIndex = 0;
    int domainColumnIndex = -1;
    int categoryColumnIndex = -1;

    if ((queryResults.length > 0) && (queryResults[0].length > 1)) {
      domainColumnIndex = 1;
    }

    if ((queryResults.length > 0) && (queryResults[0].length > 2)) {
      categoryColumnIndex = 2;
    }
    return createChart(queryResults, false, rangeColumnIndex, domainColumnIndex, categoryColumnIndex, chartModel,
        width, height, outputType);
  }

  public static InputStream createChart(Object[][] queryResults, boolean convertNullsToZero, int rangeColumnIndex,
      int domainColumnIdx, int categoryColumnIdx, ChartModel chartModel, int width, int height, OutputTypes outputType)
      throws ChartProcessingException, SQLException, ResourceKeyCreationException, PersistenceException {
    ByteArrayInputStream inputStream = null;

    IChartDataModel chartDataModel = null;
    
    if (categoryColumnIdx >= 0) {
      if (chartModel.getPlot() instanceof ScatterPlot) {
        MultiSeriesXYDataModel categoricalXYDataModel = createMultiSeriesXYDataModel(queryResults, categoryColumnIdx, domainColumnIdx, rangeColumnIndex, convertNullsToZero);
        for (Series series : categoricalXYDataModel.getSeries()) {
          if (series.size() > 0) {
            chartDataModel = categoricalXYDataModel;
            break;
          }
        }
      } else {
        CategoricalDataModel categoricalDataModel = createCategoricalDataModel(queryResults, categoryColumnIdx, domainColumnIdx, rangeColumnIndex, convertNullsToZero);
        List<Category> categories = categoricalDataModel.getCategories();
        if (categories.size() > 0) {
          for (Category category : categories) {
            if (category.size() > 0) {
              chartDataModel = categoricalDataModel;
              break;
            }
          }
        }
      }
    } else if (domainColumnIdx >= 0) {
      if (chartModel.getPlot() instanceof ScatterPlot) {
        XYDataModel xyDataModel = createXYDataModel(queryResults, domainColumnIdx, rangeColumnIndex, convertNullsToZero);
        if (xyDataModel.size() > 0) {
          chartDataModel = xyDataModel;
        }
        
      } else {
        NamedValuesDataModel namedValueDataModel = createNamedValueDataModel(queryResults, domainColumnIdx, rangeColumnIndex, convertNullsToZero, true);
        if (namedValueDataModel.size() > 0) {
          chartDataModel = namedValueDataModel;
        }
      }
    } else {
      chartDataModel = createOneDimensionalDataModel(queryResults, rangeColumnIndex, true, true);
    }
    
    if (chartDataModel != null) {
      IOutput output = null;
      IChartPlugin chartPlugin = getPlugin(chartModel.getChartEngineId());
      if (chartPlugin != null) {
        output = chartPlugin.renderChartDocument(chartModel, chartDataModel);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        output.persistChart(outputStream, outputType, width, height);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
      } else {
        throw new ChartProcessingException("Unknown chart engine.");
      }
    }

    return inputStream;
  }

  private static CategoricalDataModel createCategoricalDataModel(Object[][] queryResults, int categoryColumn, int domainColumn, int rangeColumn, boolean convertNullValuesToZero) {
    CategoricalDataModel categoricalChartDataModel = new CategoricalDataModel();
    
    for (int i = 0; i < queryResults.length; i++) {
      String categoryName = queryResults[i][categoryColumn] != null ? queryResults[i][categoryColumn].toString() : "null";        
      Object domainValue = queryResults[i][domainColumn] != null ? queryResults[i][domainColumn].toString() : "null";
      Object rangeValue = queryResults[i][rangeColumn];
      if (rangeValue == null) {
        if (convertNullValuesToZero) {
          rangeValue = new Integer(0);
        }
      } else if (!(rangeValue instanceof Number)) {
        rangeValue = null;
      }
      
      categoricalChartDataModel.addValue(categoryName, domainValue.toString(), (Number)rangeValue);
    }
    
    return categoricalChartDataModel;
  }
  
  private static MultiSeriesXYDataModel createMultiSeriesXYDataModel(Object[][] queryResults, int categoryColumn, int domainColumn, int rangeColumn, boolean convertNullValuesToZero) {
    MultiSeriesXYDataModel categoricalChartDataModel = new MultiSeriesXYDataModel();
    
    for (int i = 0; i < queryResults.length; i++) {
      String categoryName = queryResults[i][categoryColumn] != null ? queryResults[i][categoryColumn].toString() : "null";  
      
      Object domainValue = queryResults[i][rangeColumn];
      if (domainValue == null) {
        if (convertNullValuesToZero) {
          domainValue = new Integer(0);
        }
      } else if (!(domainValue instanceof Number)) {
        domainValue = null;
      }
      
      Object rangeValue = queryResults[i][rangeColumn];
      if (rangeValue == null) {
        if (convertNullValuesToZero) {
          rangeValue = new Integer(0);
        }
      } else if (!(rangeValue instanceof Number)) {
        rangeValue = null;
      }
      
      categoricalChartDataModel.addDataPoint(categoryName, (Number)domainValue, (Number)rangeValue);
    }
    
    return categoricalChartDataModel;
  }
  
  private static NamedValuesDataModel createNamedValueDataModel(Object[][] queryResults, int domainColumn, int rangeColumn, boolean convertNullsToZero, boolean autoSum) {
    NamedValuesDataModel basicChartDataModel = new NamedValuesDataModel();
    
    for (int i = 0; i < queryResults.length; i++) {
      Object domainValue = queryResults[i][domainColumn];     
      if (domainValue == null) {
        domainValue = "null";
      }
      
      String name = domainValue.toString();
      
      Object rangeValue = queryResults[i][rangeColumn];
      if (rangeValue == null) {
        if (convertNullsToZero) {
          rangeValue = new Integer(0);
        }
      } else if (!(rangeValue instanceof Number)) {
        rangeValue = null;
      }
      
      if (autoSum) {
        NamedValue existingDataPoint = basicChartDataModel.getNamedValue(name);
        if (existingDataPoint == null) {
          basicChartDataModel.add(new NamedValue(name, (Number)rangeValue));
        } else if (existingDataPoint.getValue() == null) {
          existingDataPoint.setValue((Number)rangeValue);
        } else if (rangeValue != null) {
          existingDataPoint.setValue(((Number)existingDataPoint.getValue()).doubleValue() + ((Number)rangeValue).doubleValue());
        }
      } else {
        basicChartDataModel.add(new NamedValue(name, (Number)rangeValue));
      }
    }      
    
    return basicChartDataModel;
  }
  
  private static BasicDataModel createOneDimensionalDataModel(Object[][] queryResults, int rangeColumn, boolean convertNullsToZero, boolean autoSum) {
    BasicDataModel oneDimensionalDataModel = new BasicDataModel(autoSum);
    
    for (int i = 0; i < queryResults.length; i++) {
      Object rangeValue = queryResults[i][rangeColumn];
      if (rangeValue == null) {
        if (convertNullsToZero) {
          rangeValue = new Integer(0);
        }
      } else if (!(rangeValue instanceof Number)) {
        rangeValue = null;
      }
      
      oneDimensionalDataModel.addDataPoint((Number)rangeValue);
    }      
    
    return oneDimensionalDataModel;
  }
  
  private static XYDataModel createXYDataModel(Object[][] queryResults, int domainColumn, int rangeColumn, boolean convertNullsToZero) {
    XYDataModel basicChartDataModel = new XYDataModel();
    
    for (int i = 0; i < queryResults.length; i++) {
      Object domainValue = queryResults[i][rangeColumn];
      if (domainValue == null) {
        if (convertNullsToZero) {
          domainValue = new Integer(0);
        }
      } else if (!(domainValue instanceof Number)) {
        domainValue = null;
      }
      
      Object rangeValue = queryResults[i][rangeColumn];
      if (rangeValue == null) {
        if (convertNullsToZero) {
          rangeValue = new Integer(0);
        }
      } else if (!(rangeValue instanceof Number)) {
        rangeValue = null;
      }
      
      if ((domainValue != null) && (rangeValue != null)) {
        basicChartDataModel.add(new XYDataPoint((Number)domainValue, (Number)rangeValue));
      }
    }      
    
    return basicChartDataModel;
  }
  
}
