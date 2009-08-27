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
package org.pentaho.chart.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultiSeriesXYDataModel implements IChartDataModel {
  
  public class Series extends XYDataModel {
    String seriesName;
    
    Series() {
    }
    
    Series(String seriesName) {
      this.seriesName = seriesName;
    }
    
    public String getSeriesName() {
      return seriesName;
    }

    public void setSeriesName(String seriesName) {
      this.seriesName = seriesName;
    }
  }
  
  LinkedHashMap<String, XYDataModel> chartData = new LinkedHashMap<String, XYDataModel>();
  
  public MultiSeriesXYDataModel() {
  }
 
  public void addDataPoint(String seriesName, Number domainValue, Number rangeValue) {    
    XYDataModel xyDataModel = chartData.get(seriesName);    
    
    if (xyDataModel == null) {
      xyDataModel = new XYDataModel();
      chartData.put(seriesName, xyDataModel);
    }
    
    xyDataModel.add(new XYDataPoint(domainValue, rangeValue));    
  }
  
  public Series getSeries(String seriesName) {
    Series series = null;
    XYDataModel xyDataModel = chartData.get(seriesName);
    if (xyDataModel != null) {
      series = new Series(seriesName);
      series.addAll(xyDataModel);
    }
    return series;
  }
  
  public List<Series> getSeries() {
    List<Series> seriesList = new ArrayList<Series>();
    
    for (Map.Entry<String, XYDataModel> mapEntry : chartData.entrySet()) {
      Series series = new Series(mapEntry.getKey());
      series.addAll(mapEntry.getValue());      
      seriesList.add(series);
    }
    
    return seriesList;
  }
}
