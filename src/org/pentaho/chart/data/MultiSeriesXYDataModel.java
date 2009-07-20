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
    }
    
    return seriesList;
  }
}
