package org.pentaho.chart.plugin.jfreechart;

import org.jfree.chart.renderer.AbstractRenderer;
import org.pentaho.chart.api.engine.Series;

public class SeriesBean extends AbstractBean implements Series {

  public enum Type {UNKNOWN, BAR, LINE, PIE};

  private static final long serialVersionUID = -4586605034115052978L;
  
  private Comparable<?> key;
  
  private int index;
  
  private Type type;
  
  private AbstractRenderer renderer; 
  
  public SeriesBean() {
    type = Type.UNKNOWN;
  }

  /**
   * @return the key
   */
  public final Comparable<?> getKey() {
    return key;
  }

  /**
   * @param key the key to set
   */
  public final void setKey(Comparable<?> key) {
    this.key = key;
  }

  /**
   * @return the index
   */
  public final int getIndex() {
    return index;
  }

  /**
   * @param index the index to set
   */
  public final void setIndex(int index) {
    this.index = index;
  }

  /**
   * @return the type
   */
  public final Type getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public final void setType(Type type) {
    this.type = type;
  }

  /**
   * @return the renderer
   */
  public final AbstractRenderer getRenderer() {
    return renderer;
  }

  /**
   * @param renderer the renderer to set
   */
  public final void setRenderer(AbstractRenderer renderer) {
    this.renderer = renderer;
  }
  
  


}
