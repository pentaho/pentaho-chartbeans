package org.pentaho.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.chart.model.BarPlot;
import org.pentaho.chart.model.ChartLegend;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.CssStyle;
import org.pentaho.chart.model.StyledText;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.LinePlot.LinePlotFlavor;
import org.pentaho.chart.model.Plot.Orientation;
import org.pentaho.chart.model.Theme.ChartTheme;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SerializationTest2 {
  private Integer color = 0x757146;

  private ChartModel chartModel = new ChartModel();
  private ChartModel model2;
  
  @Before
  public void init(){
    chartModel.setTitle(new StyledText("Test Model"));
    chartModel.getLegend().setVisible(true);
    chartModel.getTitle().setFontFamily("arial");
    chartModel.setBackground(color);
    chartModel.setTheme(ChartTheme.THEME1);
    chartModel.getLegend().setFontFamily("arial");
    chartModel.getLegend().setFontSize(14);
    chartModel.getLegend().setBorderVisible(true);
    
    BarPlot barPlot = new BarPlot();
    
    barPlot.setOrientation(Orientation.VERTICAL);

    chartModel.setPlot(barPlot);
    
    XStream xStream = new XStream(new DomDriver());
    xStream.alias("chartModel", ChartModel.class);
    xStream.useAttributeFor(CssStyle.class); //$NON-NLS-1$
    xStream.useAttributeFor(Orientation.class); //$NON-NLS-1$
    xStream.useAttributeFor(LinePlotFlavor.class); //$NON-NLS-1$
    xStream.useAttributeFor(BarPlotFlavor.class); //$NON-NLS-1$
    xStream.registerConverter(new CssStylesConverter());
    xStream.omitField(ChartLegend.class, "visible");
    xStream.registerConverter(new StyledTextConverter());
    xStream.registerConverter(new PaletteConverter());
    xStream.registerConverter(new ChartModelConverter());
    String result = xStream.toXML(chartModel);
    System.out.println(result);
    xStream.fromXML(result);
    int x = 1;
  }
  
  @Test
  public void testTheme(){
    assertEquals(chartModel.getTheme(), model2.getTheme());
  }
}
