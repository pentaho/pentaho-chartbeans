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

public class MultiSeriesDataModel implements IChartDataModel, IScalableDataModel {
  
  LinkedHashMap<String, NamedValuesDataModel> chartData = new LinkedHashMap<String, NamedValuesDataModel>();
  Number scalingFactor = 1;
  
  public class SeriesData extends NamedValuesDataModel {
    String seriesName;
    
    SeriesData() {
    }
    
    SeriesData(String seriesName) {
      this.seriesName = seriesName;
    }
    
    public List<String> getDomains() {
      return getNames();
    }
    
    public String getSeriesName() {
      return seriesName;
    }

    public void setSeriesName(String seriesName) {
      this.seriesName = seriesName;
    }
  }
  
  public class DomainData extends NamedValuesDataModel {
    String domainName;
    
    DomainData() {
    }
    
    DomainData(String domainName) {
      this.domainName = domainName;
    }
    
    public List<String> getSeries() {
      return getNames();
    }
    
    public String getDomainName() {
      return domainName;
    }

    public void setDomainName(String domainName) {
      this.domainName = domainName;
    }
  }
  
  public void addValue(String categoryName, String seriesName, Number value) {    
    NamedValuesDataModel namedValueDataModel = chartData.get(categoryName);
    if (namedValueDataModel == null) {
      namedValueDataModel = new NamedValuesDataModel();
      if (chartData.size() > 0) {
         for (NamedValue existingDataPoint : chartData.values().iterator().next()) {
           namedValueDataModel.add(new NamedValue(existingDataPoint.getName(), null));
        }
      }
      chartData.put(categoryName, namedValueDataModel);
    }
    
    NamedValue existingDataPoint = namedValueDataModel.getNamedValue(seriesName);
    if (existingDataPoint == null) {
      namedValueDataModel.add(new NamedValue(seriesName.toString(), value));
      for (String tmpCategory : chartData.keySet()) {
        if (!categoryName.equals(tmpCategory)) {
          chartData.get(tmpCategory).add(new NamedValue(seriesName.toString(), null));
        }
      }
    } else if (existingDataPoint.getValue() == null) {
      existingDataPoint.setValue(value);
    } else if (value != null){
      existingDataPoint.setValue(((Number)existingDataPoint.getValue()).doubleValue() + value.doubleValue());
    }
  }
  
  public DomainData getDomainData(String domainName) {
    DomainData domainData = null;
    NamedValuesDataModel namedValueDataModel = chartData.get(domainName);
    if (namedValueDataModel != null) {
      domainData = new DomainData(domainName);
      domainData.addAll(namedValueDataModel);
    }
    return domainData;
  }
  
  public List<DomainData> getDomainData() {
    List<DomainData> domainData = new ArrayList<DomainData>();
    for (Map.Entry<String, NamedValuesDataModel> mapEntry : chartData.entrySet()) {
      DomainData domain = new DomainData(mapEntry.getKey());
      domain.addAll(mapEntry.getValue());
      domainData.add(domain);
    }
    return domainData;
  }
  
  public SeriesData getSeriesData(String seriesName) {
    SeriesData seriesData = null;
    
    if ((chartData.size() > 0) && chartData.values().iterator().next().getNames().contains(seriesName)) {
      seriesData = new SeriesData(seriesName);
      for (Map.Entry<String, NamedValuesDataModel> mapEntry : chartData.entrySet()) {
        String domainName = mapEntry.getKey();
        Number value = mapEntry.getValue().getNamedValue(seriesName).value; 
        seriesData.add(new NamedValue(domainName, value));
      }
    }
       
    return seriesData;
  }
  
  public List<SeriesData> getSeriesData() {
    List<SeriesData> seriesList = new ArrayList<SeriesData>();
    
    List<String> seriesNames = new ArrayList<String>();
    if (chartData.size() > 0) {
      seriesNames.addAll(chartData.values().iterator().next().getNames());
    }
    
    for (String seriesName : seriesNames) {
      SeriesData series = new SeriesData(seriesName);
      
      for (Map.Entry<String, NamedValuesDataModel> mapEntry : chartData.entrySet()) {
        String domainName = mapEntry.getKey();
        Number value = mapEntry.getValue().getNamedValue(seriesName).value; 
        series.add(new NamedValue(domainName, value));
      }
      seriesList.add(series);
    }
    
    return seriesList;
  }

  public Number getScalingFactor() {
    return scalingFactor;
  }

  public void setScalingFactor(Number scalingFactor) {
    this.scalingFactor = scalingFactor;
  }
  
}
