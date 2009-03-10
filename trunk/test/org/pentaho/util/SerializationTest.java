package org.pentaho.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.chart.model.CategoricalBarPlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.Series;
import org.pentaho.chart.model.Plot.Orientation;
import org.pentaho.chart.model.Theme.ChartTheme;
import org.pentaho.chart.model.util.ChartSerializer;

public class SerializationTest {
  private Integer color = 0x757146;

  private ChartModel chartModel = new ChartModel();
  private ChartModel model2;
  
  @Before
  public void init(){
    chartModel.setTitle("Test Model");
    chartModel.setBackgroundColor(color);
    chartModel.setTheme(ChartTheme.THEME1);
    
    CategoricalBarPlot barPlot = new CategoricalBarPlot();
    
    barPlot.setOrientation(Orientation.VERTICAL);

    barPlot.getSeries().add(new Series());
    barPlot.getSeries().add(new Series());
    barPlot.getSeries().add(new Series());
    barPlot.getSeries().add(new Series());
    barPlot.getSeries().add(new Series());
    
    chartModel.setPlot(barPlot);
    
    String result = ChartSerializer.serialize(chartModel);
    System.out.println(result);
    model2 = ChartSerializer.deSerialize(result);
  }
  
  @Test
  public void testModelClass(){
    
    assertEquals(chartModel.getTitle(), model2.getTitle());
    assertEquals(chartModel.getBackgroundColor(), model2.getBackgroundColor());

  }
  
  @Test
  public void testPlot(){
    assertEquals(chartModel.getPlot().getClass(), model2.getPlot().getClass());
    assertEquals(((CategoricalBarPlot) chartModel.getPlot()).getSeries().size()
        ,((CategoricalBarPlot) model2.getPlot()).getSeries().size());
  }
  
  @Test
  public void testTheme(){
    assertEquals(chartModel.getTheme(), model2.getTheme());
  }
}
