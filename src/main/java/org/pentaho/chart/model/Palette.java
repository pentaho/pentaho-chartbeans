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
