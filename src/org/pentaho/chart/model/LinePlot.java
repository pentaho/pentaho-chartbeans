package org.pentaho.chart.model;

import java.io.Serializable;

public class LinePlot extends GraphPlot implements Serializable {

  public enum LinePlotFlavor {PLAIN, THREED};
  
  LinePlotFlavor flavor = LinePlotFlavor.PLAIN;

  public LinePlot() {
  }

  public LinePlot(LinePlotFlavor flavor) {
    setFlavor(flavor);
  }
  
  public LinePlotFlavor getFlavor() {
    return flavor;
  }

  public void setFlavor(LinePlotFlavor flavor) {
    this.flavor = flavor;
  }
}
