package org.pentaho.chart.plugin.jfreechart.outputs;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtilities;
import org.pentaho.chart.api.engine.Output;
import org.pentaho.chart.api.exceptions.PersistenceException;
import org.pentaho.chart.outputs.SimpleChartOutput;
import org.pentaho.chart.plugin.jfreechart.BaseJFreeChartBean;

public class JFreeChartPngChartOutput extends SimpleChartOutput implements Output {
  
  private String fileName;
  
  public JFreeChartPngChartOutput(String name){
    this.fileName = name;
  }
  
  public void persist() throws PersistenceException{
    try {
      ChartUtilities.saveChartAsPNG(new File(fileName), ((BaseJFreeChartBean)getChart()).getChart(), 600, 400);
    } catch (IOException e) {
      throw new PersistenceException(e);
    }
  }

}
