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
