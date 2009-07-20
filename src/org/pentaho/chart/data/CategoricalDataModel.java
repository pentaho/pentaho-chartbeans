package org.pentaho.chart.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoricalDataModel implements IChartDataModel {
  
  LinkedHashMap<String, NamedValuesDataModel> chartData = new LinkedHashMap<String, NamedValuesDataModel>();
  
  public class Series extends NamedValuesDataModel {
    String seriesName;
    
    Series() {
    }
    
    Series(String seriesName) {
      this.seriesName = seriesName;
    }
    
    public List<String> getCategories() {
      return getNames();
    }
    
    public String getSeriesName() {
      return seriesName;
    }

    public void setSeriesName(String seriesName) {
      this.seriesName = seriesName;
    }
  }
  
  public class Category extends NamedValuesDataModel {
    String categoryName;
    
    Category() {
    }
    
    Category(String categoryName) {
      this.categoryName = categoryName;
    }
    
    public List<String> getSeries() {
      return getNames();
    }
    
    public String getCategoryName() {
      return categoryName;
    }

    public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
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
  
  public Category getCategory(String categoryName) {
    Category category = null;
    NamedValuesDataModel namedValueDataModel = chartData.get(categoryName);
    if (namedValueDataModel != null) {
      category = new Category(categoryName);
      category.addAll(namedValueDataModel);
    }
    return category;
  }
  
  public List<Category> getCategories() {
    List<Category> categories = new ArrayList<Category>();
    for (Map.Entry<String, NamedValuesDataModel> mapEntry : chartData.entrySet()) {
      Category category = new Category(mapEntry.getKey());
      category.addAll(mapEntry.getValue());
      categories.add(category);
    }
    return categories;
  }
  
  public Series getSeries(String seriesName) {
    Series series = null;
    
    if ((chartData.size() > 0) && chartData.values().iterator().next().getNames().contains(seriesName)) {
      series = new Series(seriesName);
      for (Map.Entry<String, NamedValuesDataModel> mapEntry : chartData.entrySet()) {
        String categoryName = mapEntry.getKey();
        Number value = mapEntry.getValue().getNamedValue(seriesName).value; 
        series.add(new NamedValue(categoryName, value));
      }
    }
       
    return series;
  }
  
  public List<Series> getSeries() {
    List<Series> seriesList = new ArrayList<Series>();
    
    List<String> seriesNames = new ArrayList<String>();
    if (chartData.size() > 0) {
      seriesNames.addAll(chartData.values().iterator().next().getNames());
    }
    
    for (String seriesName : seriesNames) {
      Series series = new Series(seriesName);
      
      for (Map.Entry<String, NamedValuesDataModel> mapEntry : chartData.entrySet()) {
        String categoryName = mapEntry.getKey();
        Number value = mapEntry.getValue().getNamedValue(seriesName).value; 
        series.add(new NamedValue(categoryName, value));
      }
      seriesList.add(series);
    }
    
    return seriesList;
  }
  
}
