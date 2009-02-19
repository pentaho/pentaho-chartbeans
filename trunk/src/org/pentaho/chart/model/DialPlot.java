package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class DialPlot extends Plot implements Serializable {
  public static class DialRange implements Serializable {
    Integer color;
    Number minValue;
    Number maxValue;

    public DialRange() {  
    }
    
    public DialRange(Number minValue, Number maxValue) {
      setRange(minValue, maxValue);
    }
    
    public Integer getColor() {
      return color;
    }

    public void setColor(Integer foregroundColor) {
      this.color = foregroundColor;
    }

    public void setRange(Number minValue, Number maxValue) {
      if (minValue.doubleValue() >= maxValue.doubleValue()) {
        setMinValue(maxValue);
        setMaxValue(minValue);
      } else {
        setMinValue(minValue);
        setMaxValue(maxValue);
      }
    }
    
    public Number getMinValue() {
      return minValue;
    }

    protected void setMinValue(Number minValue) {
      this.minValue = minValue;
    }

    public Number getMaxValue() {
      return maxValue;
    }

    protected void setMaxValue(Number maxValue) {
      this.maxValue = maxValue;
    }

  }
  
  // Really need a sorted set. But gwt 1.5.2 can't serialize sorted sets.
  List<DialRange> divisions = new ArrayList<DialRange>();

  public SortedSet<DialRange> getDivisions() {
    return new TreeSet<DialRange>(divisions);
  }

  public void setDivisions(SortedSet<DialRange> divisions) {
    divisions.clear();
    divisions.addAll(divisions);
  }

}
