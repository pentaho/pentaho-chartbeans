package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class DialPlot extends Plot implements Serializable {
  public static class DialRange implements Serializable, Comparable<DialRange> {
    Integer color;
    Number minValue = 0;
    Number maxValue = 0;

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

    public int compareTo(DialRange o) {
      int comparison = 0;
      
      double thisValue = minValue.doubleValue();
      double thatValue = o.minValue.doubleValue();
      if (thisValue < thatValue) {
        comparison = -1;
      } else if (thisValue > thatValue) {
        comparison = 1;
      }
      if (comparison == 0) {
        thisValue = maxValue.doubleValue();
        thatValue = o.maxValue.doubleValue();
        if (thisValue < thatValue) {
          comparison = -1;
        } else if (thisValue > thatValue) {
          comparison = 1;
        }
      }
      return comparison;
    }

    public boolean equals(Object obj) {
      boolean result = false;
      if (obj instanceof DialRange) {
        DialRange otherRange = (DialRange)obj;
        result = minValue.equals(otherRange.minValue) && maxValue.equals(otherRange.maxValue);
      }
      return result;
    }

  }
  
  // Really need a sorted set. But gwt 1.5.2 can't serialize sorted sets.
  ArrayList<DialRange> divisions = new ArrayList<DialRange>();

  public SortedSet<DialRange> getRanges() {
    return new TreeSet<DialRange>(divisions);
  }
  
  public void addRange(DialRange dialRange) {
    SortedSet<DialRange> ranges = getRanges();
    if (!divisions.contains(dialRange)) {
      ranges.add(dialRange);
      divisions.clear();
      divisions.addAll(ranges);
      int index = divisions.indexOf(dialRange);
      if (index > 0) {
        if (divisions.get(index - 1).getMaxValue().doubleValue() > dialRange.getMinValue().doubleValue()) {
          divisions.get(index - 1).setMaxValue(dialRange.getMinValue());
        }
      }
      if (index < divisions.size() - 1) {
        if (divisions.get(index + 1).getMinValue().doubleValue() < dialRange.getMaxValue().doubleValue()) {
          divisions.get(index + 1).setMinValue(dialRange.getMaxValue());
        }
      }
    }    
  }
  
  public void removeRange(DialRange dialRange) {
    divisions.remove(dialRange);
  }
  
}
