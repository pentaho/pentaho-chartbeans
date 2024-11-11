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


public class NamedValue {
  FormattableName name;
  Number value;

  public NamedValue() {
  }

  public NamedValue( FormattableName name, Number value ) {
    this.name = name;
    this.value = value;
  }

  public NamedValue( String nameKey, Number value ) {
    this.name = new FormattableName( nameKey );
    this.value = value;
  }

  public NamedValue( String nameKey, String nameFormatted, Number value ) {
    this.name = new FormattableName( nameKey, nameFormatted );
    this.value = value;
  }

  public FormattableName getNameInstance() {
    return name;
  }

  public String getName() {
    return name.key;
  }

  public String getFormattedName() {
    return name.formatted;
  }

  public void setName( String nameKey ) {
    this.name.setKey( nameKey );
  }

  public void setFormattedName( String nameFormatted ) {
    this.name.setFormatted( nameFormatted );
  }

  public Number getValue() {
    return value;
  }

  public void setValue( Number value ) {
    this.value = value;
  }

  public int hashCode() {
    return name.hashCode();
  }


}
