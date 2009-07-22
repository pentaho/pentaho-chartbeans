/*
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
 * Copyright 2008 Pentaho Corporation.  All rights reserved.
 */
package org.pentaho.chart.model;

import java.io.Serializable;

import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;

public class ChartTitle extends StyledText implements Serializable {
  public enum TitleLocation {BOTTOM, LEFT, TOP, RIGHT};
  
  TitleLocation location = TitleLocation.TOP;

  public ChartTitle() {
    super();
  }
  
  public ChartTitle(String text) {
    super(text);
  }
  
  public ChartTitle(String text, String fontFamily, FontStyle fontStyle, FontWeight fontWeight, int fontSize) {
    super(text, fontFamily, fontStyle, fontWeight, fontSize);
  }
  
  public TitleLocation getLocation() {
    return location;
  }

  public void setLocation(TitleLocation location) {
    this.location = location;
  }
}
