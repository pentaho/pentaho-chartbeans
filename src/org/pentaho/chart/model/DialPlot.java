/*!
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
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

public class DialPlot extends Plot implements Serializable {
  
  // Really need a sorted set. But gwt 1.5.2 can't serialize sorted sets.
  public static class Scale extends ArrayList<DialRange> {
    public Number getMinValue() {
      Number minValue = new Integer(0);
      if (size() > 0) {
        minValue = get(0).minValue;
      }
      return minValue;
    }
    
    public Number getMaxValue() {
      Number maxValue = new Integer(0);
      if (size() > 0) {
        maxValue = get(size() - 1).maxValue;
      }
      return maxValue;
    }
    
    /**
     * Each range has a minimum value, a maximum value and a color in RGB Hex format.
     * @param dialRange
     */
    public void addRange(DialRange dialRange) {
      if (!contains(dialRange)) {
        TreeSet<DialRange> ranges = new TreeSet<DialRange>(this);
        ranges.add(dialRange);
        clear();
        addAll(ranges);
        int index = indexOf(dialRange);
        if (index > 0) {
          if (get(index - 1).getMaxValue().doubleValue() > dialRange.getMinValue().doubleValue()) {
            get(index - 1).setMaxValue(dialRange.getMinValue());
          }
        }
        if (index < size() - 1) {
          if (get(index + 1).getMinValue().doubleValue() < dialRange.getMaxValue().doubleValue()) {
            get(index + 1).setMinValue(dialRange.getMaxValue());
          }
        }
      }    
    }
    
    public void removeRange(DialRange dialRange) {
      remove(dialRange);
    }
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

    public void setMinValue(Number minValue) {
      this.minValue = minValue;
    }

    public Number getMaxValue() {
      return maxValue;
    }

    public void setMaxValue(Number maxValue) {
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
  
  Scale scale = new Scale();
  StyledText annotation = new StyledText();
  boolean animate = false;

  public DialPlot() {
    setPalette(null);
  }
  
  public Scale getScale() {
    return scale;
  }

  public StyledText getAnnotation() {
    return annotation;
  }

  /**
   * @see StyledText
   * @param annotation
   */
  public void setAnnotation(StyledText annotation) {
    this.annotation = annotation;
  }
  
  public boolean getAnimate() {
    return animate;
  }
  
  /**
   * {@link org.pentaho.chart.model.PiePlot#setAnimate(boolean)}
   * @return
   */
  public void setAnimate(boolean animate) {
    this.animate = animate;
  }

}
