package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class DialPlot extends Plot implements Serializable {
  public static class Scale extends ArrayList<DialRange> {
  }
  
  public static class DialRange implements Serializable, Comparable<DialRange> {
    CssStyle style = new CssStyle();
    Number minValue = 0;
    Number maxValue = 0;

    public DialRange() {  
    }
    
    public DialRange(Number minValue, Number maxValue) {
      this(minValue, maxValue, null);
    }
    public DialRange(Number minValue, Number maxValue, Integer color) {
      setRange(minValue, maxValue);
      if (color != null) {
        setColor(color);
      }
    }
    

    public CssStyle getStyle() {
      return style;
    }

    public Integer getColor() {
      return style.getColor();
    }

    public void setColor(Integer color) {
      style.setColor(color);
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
  Scale scale = new Scale();

  public DialPlot() {
    setPalette(null);
  }
  
  public SortedSet<DialRange> getRanges() {
    return new TreeSet<DialRange>(scale);
  }
  
  public void addRange(DialRange dialRange) {
    SortedSet<DialRange> ranges = getRanges();
    if (!scale.contains(dialRange)) {
      ranges.add(dialRange);
      scale.clear();
      scale.addAll(ranges);
      int index = scale.indexOf(dialRange);
      if (index > 0) {
        if (scale.get(index - 1).getMaxValue().doubleValue() > dialRange.getMinValue().doubleValue()) {
          scale.get(index - 1).setMaxValue(dialRange.getMinValue());
        }
      }
      if (index < scale.size() - 1) {
        if (scale.get(index + 1).getMinValue().doubleValue() < dialRange.getMaxValue().doubleValue()) {
          scale.get(index + 1).setMinValue(dialRange.getMaxValue());
        }
      }
    }    
  }
  
  public void removeRange(DialRange dialRange) {
    scale.remove(dialRange);
  }
  
}
