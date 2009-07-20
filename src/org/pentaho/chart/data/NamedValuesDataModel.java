package org.pentaho.chart.data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class NamedValuesDataModel extends LinkedHashSet<NamedValue> implements IChartDataModel {
  
  public List<String> getNames() {
    List<String> names = new ArrayList<String>();
    
    for (NamedValue namedValue : this) {
      names.add(namedValue.getName());
    }
    
    return names;
  }
  
  public NamedValue getNamedValue(String name) {
    NamedValue namedValue = null;
    
    for (NamedValue tmpNamedValue : this) {
      if (tmpNamedValue.getName().equals(name)) {
        namedValue = tmpNamedValue;
        break;
      }
    }
    
    return namedValue;
  }
}
