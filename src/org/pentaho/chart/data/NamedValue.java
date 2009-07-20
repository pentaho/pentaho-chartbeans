package org.pentaho.chart.data;


public class NamedValue {
  String name;
  Number value;
  
  public NamedValue() {    
  }
  
  public NamedValue(String name, Number value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Number getValue() {
    return value;
  }

  public void setValue(Number value) {
    this.value = value;
  }

  public int hashCode() {
    return name.hashCode();
  }


}
