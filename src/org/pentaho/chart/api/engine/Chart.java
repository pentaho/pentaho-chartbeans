package org.pentaho.chart.api.engine;

import org.jfree.data.category.CategoryDataset;

public interface Chart {
  
  public String getTitle();
  public void setTitle(String title);
  
  public Axis[] getAxes();
  public Axis getAxes(int index);
  public void setAxes(Axis[] axes);
  public void setAxes(int index, Axis axis);
  
  public Series[] getSeries();
  public Series getSeries(int index);
  public void setSeries(Series[] series);
  public void setSeries(int index, Series series);
  
  public CategoryDataset[] getData();
  public CategoryDataset getData(int index);
  public void setData(CategoryDataset[] data);
  public void setData(int index, CategoryDataset data);
  
}
