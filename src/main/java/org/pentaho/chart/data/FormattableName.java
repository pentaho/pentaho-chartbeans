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


public class FormattableName implements Comparable<FormattableName> {
  String key;
  String formatted;

  public FormattableName() {
  }

  public FormattableName( String key ) {
    this.key = key;
    this.formatted = key;
  }

  public FormattableName( String key, String formattedName ) {
    this.key = key;
    this.formatted = ( formattedName == null ? key : formattedName );
  }

  public String getKey() {
    return key;
  }

  public void setKey( String key ) {
    this.key = key;
  }

  public String getFormatted() {
    return formatted;
  }

  public void setFormatted( String formatted ) {
    this.formatted = formatted;
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public String toString() {
    return formatted;
  }

  public int compareTo( FormattableName other ) {
    return this.key.compareTo( other.key );
  }

  @Override
  public boolean equals( Object obj ) {
    if ( this == obj ) {
      return true;
    }
    if ( !( obj instanceof FormattableName ) ) {
      return false;
    }
    return this.key.equals( ( (FormattableName) obj ).key );
  }
}
