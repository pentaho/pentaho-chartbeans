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
