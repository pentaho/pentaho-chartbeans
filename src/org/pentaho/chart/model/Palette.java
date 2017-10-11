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
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Palette extends ArrayList<Integer> implements Serializable { 
  public Palette() {
  }
  
  public Palette(Palette otherPalette) {
    this.addAll(otherPalette);
  }
  
  /**
   * Create a palette with a given list of colors in RGB Hex format (i.e- #ffffff)
   * @param colors
   */
  public Palette(Integer... colors) {
    for (Integer rgb : colors) {
      add(rgb);
    }
  } 
}
