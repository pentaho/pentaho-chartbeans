package org.pentaho.chart.plugin.jfreechart;

import java.awt.Paint;

import org.jfree.chart.renderer.category.LineAndShapeRenderer;

public class LineSeriesBean extends SeriesBean {
  
  private static final long serialVersionUID = -1857365570287119647L;

  private LineAndShapeRenderer renderer;

  public LineSeriesBean() {
    setType(Type.LINE);
    renderer = new LineAndShapeRenderer();
  }

  public final LineAndShapeRenderer getRenderer() {
    return renderer;
  }

  public final void setRenderer(LineAndShapeRenderer renderer) {
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
  
  public boolean isShapesVisible(){
    if (null == renderer.getSeriesShapesVisible(getIndex())){
      return false;
    }
    return renderer.getSeriesShapesVisible(getIndex());
  }

  public final void setShapesVisible(boolean vis){
    boolean old = isShapesVisible();
    renderer.setSeriesShapesVisible(getIndex(), vis);
    firePropertyChange("shapesVisible", old, vis);
  }
}
