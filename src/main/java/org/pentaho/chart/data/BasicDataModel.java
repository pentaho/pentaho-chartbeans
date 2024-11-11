/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart.data;

import java.util.ArrayList;
import java.util.List;

public class BasicDataModel implements IChartDataModel, IScalableDataModel {
  
  Number scalingFactor = 1;
  
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

  public Number getScalingFactor() {
    return scalingFactor;
  }

  public void setScalingFactor(Number scalingFactor) {
    this.scalingFactor = scalingFactor;
  }
}
