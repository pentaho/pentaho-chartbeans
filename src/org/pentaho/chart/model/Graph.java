package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph extends Plot implements Serializable {
  
  String categoryAxisLabel;
  String valueAxisLabel;
  List<Series> series = new ArrayList<Series>();
  
  public String getCategoryAxisLabel() {
    return categoryAxisLabel;
  }
  
  public void setCategoryAxisLabel(String categoryAxisLabel) {
    this.categoryAxisLabel = categoryAxisLabel;
  }
  
  public String getValueAxisLabel() {
    return valueAxisLabel;
  }
  
  public void setValueAxisLabel(String valueAxisLabel) {
    this.valueAxisLabel = valueAxisLabel;
  }

  public List<Series> getSeries() {
    return series;
  }

  public void setSeries(List<Series> series) {
    this.series = series;
  }
}
