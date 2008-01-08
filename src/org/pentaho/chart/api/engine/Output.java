package org.pentaho.chart.api.engine;

import org.pentaho.chart.api.exceptions.PersistenceException;

public interface Output {
  
  public Chart getChart();
  public void setChart(Chart chart);
  public void persist() throws PersistenceException;

}
