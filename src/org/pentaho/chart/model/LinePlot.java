package org.pentaho.chart.model;

import java.io.Serializable;

import org.pentaho.chart.model.CssStyle.LineStyle;

public class LinePlot extends GraphPlot implements Serializable {

  public enum LinePlotFlavor {PLAIN, THREED, DOT, DASH, DASHDOT, DASHDOTDOT};
  
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
  
  public void setLineWidth(Integer width) {
    style.setBorderWidth(width);
  }
  
  public Integer getLineWidth() {
    return style.getBorderWidth();
  }
}
