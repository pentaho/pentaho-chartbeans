package org.pentaho.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.chart.model.BarPlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.StyledText;
import org.pentaho.chart.model.Plot.Orientation;
import org.pentaho.chart.model.Theme.ChartTheme;
import org.pentaho.chart.model.util.ChartSerializer;

public class SerializationTest {
  private Integer color = 0x757146;

  private ChartModel chartModel = new ChartModel();
  private ChartModel model2;
  
  @Before
  public void init(){
    chartModel.setTitle(new StyledText("Test Model"));
    chartModel.setBackground(color);
    chartModel.setTheme(ChartTheme.THEME1);
    
    BarPlot barPlot = new BarPlot();
    
    barPlot.setOrientation(Orientation.VERTICAL);

    chartModel.setPlot(barPlot);
    
    String result = ChartSerializer.serialize(chartModel);
    System.out.println(result);
    model2 = ChartSerializer.deSerialize(result);
  }
  
  @Test
  public void testModelClass(){
    
    assertEquals(chartModel.getTitle(), model2.getTitle());
    assertEquals(chartModel.getBackground(), model2.getBackground());

  }
  
  @Test
  public void testPlot(){
    assertEquals(chartModel.getPlot().getClass(), model2.getPlot().getClass());
    assertEquals(((BarPlot) chartModel.getPlot()).getPalette().size()
        ,((BarPlot) model2.getPlot()).getPalette().size());
  }
  
  @Test
  public void testTheme(){
    assertEquals(chartModel.getTheme(), model2.getTheme());
  }
}
