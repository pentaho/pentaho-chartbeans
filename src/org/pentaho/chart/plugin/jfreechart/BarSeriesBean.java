package org.pentaho.chart.plugin.jfreechart;

import java.awt.Paint;

import org.jfree.chart.renderer.category.BarRenderer;
import org.pentaho.chart.api.engine.BarSeries;

public class BarSeriesBean extends SeriesBean implements BarSeries{
  
  private static final long serialVersionUID = 6141134618934571564L;

  private BarRenderer renderer;

  public BarSeriesBean() {
    setType(Type.BAR);
    renderer = new BarRenderer();
  }

  public final BarRenderer getRenderer() {
    return renderer;
  }

  public final void setRenderer(BarRenderer renderer) {
    this.renderer = renderer;
  }

  public Paint getColor() {
    return renderer.getSeriesPaint(getIndex());
  }

  public void setColor(Paint color) {
    Paint old = getColor();
    renderer.setSeriesPaint(getIndex(), color);
    firePropertyChange("color", old, color);
  }

}
