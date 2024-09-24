/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

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
