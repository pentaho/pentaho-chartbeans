package org.pentaho.chart.data;

import java.util.ArrayList;
import java.util.List;

public class BasicDataModel implements IChartDataModel {
  
  List<Number> values = new ArrayList<Number>();
  boolean autoSum = true;
  
  public BasicDataModel() {
    this(true);
  }
  
  public BasicDataModel(boolean autoSum) {
    this.autoSum = autoSum;
  }

  public void addDataPoint(Number rangeValue) {
    if (values.size() == 0) {
      values.add(rangeValue);
    } else if (autoSum) {
      if (values.get(0) != null) {
        values.set(0, values.get(0).doubleValue() + rangeValue.doubleValue());
      } else {
        values.set(0, rangeValue);
      }
    } else {
      values.add(rangeValue);
    }     
  }
  
  public List<Number> getData() {
    return values;
  }
}
