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
