package org.pentaho.experimental.chart.plugin.api.engine;

import org.pentaho.experimental.chart.plugin.jfreechart.beans.SeriesBean.Type;


public interface Series {

  public Comparable<?> getKey();
  public void setKey(Comparable<?> key);
  public int getIndex();
  public void setIndex(int index);
  public Type getType();
  public void setType(Type type);

}
