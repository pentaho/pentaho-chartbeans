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
import java.util.HashMap;
import java.util.Map;

public class ChartDataDefinition implements Serializable {
  
  private String query;

  /**
   * A map where the keys are the names of parameters (i.e. placeholders) that appear in the <code>query</code> string. 
   * The values are the default values: the values to use if a "real" value isn't supplied at runtime.
   */
  private  Map<String, Object> defaultParameterMap = new HashMap<String, Object>();
  
  private String rangeColumn;

  private String domainColumn;

  private String categoryColumn;
  
  Number scalingFactor = 1;
  
  boolean convertNullsToZero = false;

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  /**
   * Equals returns true if the parameter Object is an instanceof ChartDataDefinition
   * with ALL properties being "*.equals()" to this instance's. 
   */
  public boolean equals(Object obj) {
    if(obj == null){
      return false;
    }
    
    if (!(obj instanceof ChartDataDefinition)) {
      return false;
    }

    ChartDataDefinition chartDataQuery = (ChartDataDefinition) obj;
    
    if(scalingFactor == null){
      if(chartDataQuery.scalingFactor != null){
        return false;
      }
    } else {
      if(!scalingFactor.equals(chartDataQuery.scalingFactor)){
        return false;
      }
    }
    
    if(query == null){
      if(chartDataQuery.query != null){
        return false;
      }
    } else {
      if(!query.equals(chartDataQuery.query)){
        return false;
      }
    }
    
    if(rangeColumn == null){
      if(chartDataQuery.rangeColumn != null){
        return false;
      }
    } else {
      if(!rangeColumn.equals(chartDataQuery.rangeColumn)){
        return false;
      }
    }

    if(domainColumn == null){
      if(chartDataQuery.domainColumn != null){
        return false;
      }
    } else {
      if(!domainColumn.equals(chartDataQuery.domainColumn)){
        return false;
      }
    }

    if(categoryColumn == null){
      if(chartDataQuery.categoryColumn != null){
        return false;
      }
    } else {
      if(!categoryColumn.equals(chartDataQuery.categoryColumn)){
        return false;
      }
    }

    if (convertNullsToZero != chartDataQuery.convertNullsToZero) {
      return false;
    }
    
    if (defaultParameterMap.size() != chartDataQuery.defaultParameterMap.size()) {
      return false;
    }
    
    for (Map.Entry<String, Object> mapEntry : defaultParameterMap.entrySet()) {
      if (!defaultParameterMap.containsKey(mapEntry.getKey())) {
        return false;
      }
      Object thisValue = mapEntry.getValue();
      Object thatValue = defaultParameterMap.get(mapEntry.getKey());
      if(thisValue == null){
        if(thatValue != null){
          return false;
        }
      } else {
        if(!thisValue.equals(thatValue)){
          return false;
        }
      }
    }
    return true;
  }

  public String getRangeColumn() {
    return rangeColumn;
  }

  public void setRangeColumn(String rangeColumn) {
    this.rangeColumn = rangeColumn;
  }

  public String getDomainColumn() {
    return domainColumn;
  }

  public void setDomainColumn(String domainColumn) {
    this.domainColumn = domainColumn;
  }

  public String getCategoryColumn() {
    return categoryColumn;
  }

  public void setCategoryColumn(String categoryColumn) {
    this.categoryColumn = categoryColumn;
  }

  /**
   * @return defaultParameterMap (never <code>null</code>)
   */
  public Map<String, Object> getDefaultParameterMap() {
    return defaultParameterMap;
  }

  public void setDefaultParameterMap(Map<String, Object> defaultParameterMap) {
    if (defaultParameterMap == null) {
      this.defaultParameterMap = new HashMap<String, Object>();
    } else {
      this.defaultParameterMap = defaultParameterMap;
    }
  }
  
  public Number getScalingFactor() {
    return scalingFactor;
  }

  public void setScalingFactor(Number scalingFactor) {
    this.scalingFactor = scalingFactor;
  }

  public boolean getConvertNullsToZero() {
    return convertNullsToZero;
  }

  public void setConvertNullsToZero(boolean convert) {
    this.convertNullsToZero = convert;
  }

}
