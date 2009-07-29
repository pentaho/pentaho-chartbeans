/*
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
 * Copyright 2008 Pentaho Corporation.  All rights reserved.
 */
package org.pentaho.chart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.chart.data.BasicDataModel;
import org.pentaho.chart.data.CategoricalDataModel;
import org.pentaho.chart.data.IChartDataModel;
import org.pentaho.chart.data.IScalableDataModel;
import org.pentaho.chart.data.MultiSeriesXYDataModel;
import org.pentaho.chart.data.NamedValue;
import org.pentaho.chart.data.NamedValuesDataModel;
import org.pentaho.chart.data.XYDataModel;
import org.pentaho.chart.data.XYDataPoint;
import org.pentaho.chart.data.CategoricalDataModel.Category;
import org.pentaho.chart.data.MultiSeriesXYDataModel.Series;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.DialPlot;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.ScatterPlot;
import org.pentaho.chart.plugin.ChartProcessingException;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.chart.plugin.api.PersistenceException;
import org.pentaho.chart.plugin.api.IOutput.OutputTypes;
import org.pentaho.chart.plugin.jfreechart.JFreeChartPlugin;
import org.pentaho.chart.plugin.openflashchart.OpenFlashChartPlugin;
import org.pentaho.reporting.libraries.resourceloader.ResourceKeyCreationException;

public class ChartBeanFactory {
  private static List<IChartPlugin> chartPlugins = new ArrayList<IChartPlugin>();

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

  /**
   *  This method is called from a platform system listener on startup,
   *  to initialize the available plugins from the chartbeans configuration file. 
   */
  public static void loadDefaultChartPlugins(List <IChartPlugin> plugins) {
    chartPlugins = plugins;
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
    
    return createChart(queryResults, 1, false, rangeColumnIndex, domainColumnIndex, categoryColumnIndex, chartModel,
        width, height, outputType);
  }

  public static InputStream createChart(Object[][] queryResults, Number scalingFactor, boolean convertNullsToZero, int rangeColumnIndex,
      int domainColumnIdx, int categoryColumnIdx, ChartModel chartModel, int width, int height, OutputTypes outputType)
      throws ChartProcessingException, SQLException, ResourceKeyCreationException, PersistenceException {
    ByteArrayInputStream inputStream = null;

    IChartDataModel chartDataModel = null;
    
    if ((categoryColumnIdx >= 0) 
        && !(chartModel.getPlot() instanceof PiePlot)
        && !(chartModel.getPlot() instanceof DialPlot)) {
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
    } else if ((domainColumnIdx >= 0)
        && !(chartModel.getPlot() instanceof DialPlot)) {
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
      chartDataModel = createBasicDataModel(queryResults, rangeColumnIndex, true, true);
    }
    
    if (chartDataModel != null) {
      if (chartDataModel instanceof IScalableDataModel) {
        ((IScalableDataModel)chartDataModel).setScalingFactor(scalingFactor);
      }
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
  
  private static BasicDataModel createBasicDataModel(Object[][] queryResults, int rangeColumn, boolean convertNullsToZero, boolean autoSum) {
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
