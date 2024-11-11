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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultiSeriesXYDataModel implements IChartDataModel {

  LinkedHashMap<FormattableName, Series> chartData = new LinkedHashMap<>();

  public class Series extends XYDataModel {
    FormattableName seriesName;

    Series() {
    }

    Series( FormattableName seriesName ) {
      this.seriesName = seriesName;
    }

    Series( String seriesKey ) {
      this.seriesName = new FormattableName( seriesKey );
    }

    public FormattableName getSeriesNameInstance() {
      return seriesName;
    }

    public String getSeriesName() {
      return seriesName.getKey();
    }

    public String getSeriesFormattedName() {
      return seriesName.getFormatted();
    }

    public void setSeriesName( String seriesKey ) {
      this.seriesName.setKey( seriesKey );
    }

    public void setSeriesFormattedName( String seriesFormattedName ) {
      this.seriesName.setFormatted( seriesFormattedName );
    }
  }

  public MultiSeriesXYDataModel() {
  }

  public void addDataPoint( String seriesKey, Number domainValue, Number rangeValue ) {
    addDataPoint( seriesKey, null, domainValue, rangeValue );
  }

  public void addDataPoint( String seriesKey, String seriesFormatted, Number domainValue, Number rangeValue ) {

    FormattableName seriesName = new FormattableName( seriesKey, seriesFormatted );
    Series seriesData = chartData.get( seriesName );

    if ( seriesData == null ) {
      seriesData = new Series( seriesName );
      chartData.put( seriesName, seriesData );
    }

    seriesData.add( new XYDataPoint( domainValue, rangeValue ) );
  }

  public Series getSeries( String seriesKey ) {
    Series seriesData = null;
    Series existingSeriesData = chartData.get( new FormattableName( seriesKey ) );
    if ( existingSeriesData != null ) {
      seriesData = new Series( existingSeriesData.seriesName );
      seriesData.addAll( existingSeriesData );
    }
    return seriesData;
  }

  public List<Series> getSeries() {
    List<Series> seriesDataList = new ArrayList<>();

    for ( Map.Entry<FormattableName, Series> mapEntry : chartData.entrySet() ) {
      Series series = new Series( mapEntry.getKey() );
      series.addAll( mapEntry.getValue() );
      seriesDataList.add( series );
    }

    return seriesDataList;
  }
}
