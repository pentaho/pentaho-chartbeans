package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Palette extends ArrayList<Integer> implements Serializable { 
  public Palette(Palette otherPalette) {
    this.addAll(otherPalette);
  }
  
  public Palette(Integer... colors) {
    for (Integer rgb : colors) {
      add(rgb);
    }
  } 
}
