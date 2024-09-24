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
