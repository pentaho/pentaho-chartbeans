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


package org.pentaho.chart.data;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class NamedValuesDataModel extends LinkedHashSet<NamedValue> implements IChartDataModel, IScalableDataModel {
  Number scalingFactor = 1;

  public List<String> getNames() {
    List<String> names = new ArrayList<>();

    for ( NamedValue namedValue : this ) {
      names.add( namedValue.getName() );
    }

    return names;
  }

  public List<String> getFormattedNames() {
    List<String> formattedNames = new ArrayList<>();

    for ( NamedValue namedValue : this ) {
      formattedNames.add( namedValue.getFormattedName() );
    }

    return formattedNames;
  }

  public NamedValue getNamedValue( String key ) {
    NamedValue namedValue = null;

    for ( NamedValue tmpNamedValue : this ) {
      if ( tmpNamedValue.getName().equals( key ) ) {
        namedValue = tmpNamedValue;
        break;
      }
    }

    return namedValue;
  }

  public Number getScalingFactor() {
    return scalingFactor;
  }

  public void setScalingFactor( Number scalingFactor ) {
    this.scalingFactor = scalingFactor;
  }
}
