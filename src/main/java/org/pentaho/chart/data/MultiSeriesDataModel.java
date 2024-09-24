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
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.chart.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultiSeriesDataModel implements IChartDataModel, IScalableDataModel {

  LinkedHashMap<FormattableName, DomainData> chartData = new LinkedHashMap<>();
  Number scalingFactor = 1;

  public class SeriesData extends NamedValuesDataModel {
    FormattableName series;

    SeriesData() {
    }

    SeriesData( FormattableName series ) {
      this.series = series;
    }

    SeriesData( String seriesKey ) {
      this.series = new FormattableName( seriesKey );
    }

    public List<String> getDomains() {
      return getNames();
    }

    public List<String> getFormattedDomains() {
      return getFormattedNames();
    }

    public String getSeriesName() {
      return series.getKey();
    }

    public String getSeriesFormattedName() {
      return series.getFormatted();
    }

    public void setSeriesName( String seriesName ) {
      this.series.setKey( seriesName );
    }
  }

  public class DomainData extends NamedValuesDataModel {
    FormattableName domain;

    DomainData() {
    }

    DomainData( FormattableName domain ) {
      this.domain = domain;
    }

    DomainData( String domainKey ) {
      this.domain = new FormattableName( domainKey );
    }

    public List<String> getSeries() {
      return getNames();
    }

    public List<String> getFormattedSeries() {
      return getFormattedNames();
    }

    public FormattableName getDomainNameInstance() {
      return this.domain;
    }

    public String getDomainName() {
      return domain.getKey();
    }

    public String getDomainFormattedName() {
      return domain.getFormatted();
    }

    public void setDomainName( String domainName ) {
      this.domain.setKey( domainName );
    }
  }

  public void addValue( String categoryKey, String seriesKey, Number value ) {
    addValue( categoryKey, null, seriesKey, null, value );
  }

  public void addValue( String categoryKey, String categoryFormatted, String seriesKey, String seriesFormatted,
                        Number value ) {

    FormattableName category = new FormattableName( categoryKey, categoryFormatted );

    DomainData domainData = chartData.get( category );
    if ( domainData == null ) {
      domainData = new DomainData( category );
      if ( chartData.size() > 0 ) {
        for ( NamedValue existingDataPoint : chartData.values().iterator().next() ) {
          domainData.add( new NamedValue( existingDataPoint.getName(), existingDataPoint.getFormattedName(), null ) );
        }
      }
      chartData.put( category, domainData );
    }

    NamedValue existingDataPoint = domainData.getNamedValue( seriesKey );
    if ( existingDataPoint == null ) {
      domainData.add( new NamedValue( seriesKey, seriesFormatted, value ) );
      for ( FormattableName tmpCategory : chartData.keySet() ) {
        if ( !categoryKey.equals( tmpCategory.getKey() ) ) {
          chartData.get( tmpCategory ).add( new NamedValue( seriesKey, seriesFormatted, null ) );
        }
      }
    } else if ( existingDataPoint.getValue() == null ) {
      existingDataPoint.setValue( value );
    } else if ( value != null ) {
      existingDataPoint.setValue( existingDataPoint.getValue().doubleValue() + value.doubleValue() );
    }
  }

  public DomainData getDomainData( String domainName ) {
    DomainData domainData = null;
    DomainData existingDomainData = chartData.get( new FormattableName( domainName ) );
    if ( existingDomainData != null ) {
      domainData = new DomainData( existingDomainData.domain );
      domainData.addAll( existingDomainData );
    }
    return domainData;
  }

  public List<DomainData> getDomainData() {
    List<DomainData> domainData = new ArrayList<>();
    for ( Map.Entry<FormattableName, DomainData> mapEntry : chartData.entrySet() ) {
      DomainData domain = new DomainData( mapEntry.getKey() );
      domain.addAll( mapEntry.getValue() );
      domainData.add( domain );
    }
    return domainData;
  }

  public SeriesData getSeriesData( String seriesKey ) {
    SeriesData seriesData = null;

    if ( chartData.size() > 0 ) {
      DomainData firstCategoryDomainData = chartData.values().iterator().next();
      NamedValue existingDataPoint = firstCategoryDomainData.getNamedValue( seriesKey );
      if ( existingDataPoint != null ) {
        seriesData = new SeriesData( existingDataPoint.name );

        for ( Map.Entry<FormattableName, DomainData> mapEntry : chartData.entrySet() ) {
          FormattableName domain = mapEntry.getKey();
          Number value = mapEntry.getValue().getNamedValue( seriesKey ).value;
          seriesData.add( new NamedValue( domain, value ) );
        }
      }
    }

    return seriesData;
  }

  public List<SeriesData> getSeriesData() {
    List<SeriesData> seriesList = new ArrayList<>();

    List<FormattableName> seriesNames = new ArrayList<>();
    if ( chartData.size() > 0 ) {
      DomainData firstCategoryDomainData = chartData.values().iterator().next();
      for ( NamedValue namedValue : firstCategoryDomainData ) {
        seriesNames.add( namedValue.name );
      }
    }

    for ( FormattableName seriesName : seriesNames ) {
      SeriesData seriesData = new SeriesData( seriesName );

      for ( Map.Entry<FormattableName, DomainData> mapEntry : chartData.entrySet() ) {
        FormattableName domainName = mapEntry.getKey();
        Number value = mapEntry.getValue().getNamedValue( seriesName.getKey() ).value;
        seriesData.add( new NamedValue( domainName, value ) );
      }
      seriesList.add( seriesData );
    }

    return seriesList;
  }

  public Number getScalingFactor() {
    return scalingFactor;
  }

  public void setScalingFactor( Number scalingFactor ) {
    this.scalingFactor = scalingFactor;
  }

}
