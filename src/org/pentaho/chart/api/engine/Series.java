package org.pentaho.chart.api.engine;

import org.pentaho.chart.plugin.jfreechart.SeriesBean.Type;

public interface Series {

  public Comparable<?> getKey();
  public void setKey(Comparable<?> key);
  public int getIndex();
  public void setIndex(int index);
  public Type getType();
  public void setType(Type type);

}
