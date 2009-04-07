package org.pentaho.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.chart.model.BarPlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.Palette;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;
import org.pentaho.chart.model.Plot.Orientation;
import org.pentaho.chart.model.Theme.ChartTheme;
import org.pentaho.chart.model.util.ChartSerializer2;

public class SerializationTest2 {

  @Before
  public void init(){}
  
  @Test
  public void testBarPlot(){
    
    ChartModel chartModel = new ChartModel();
    chartModel.setTheme(ChartTheme.THEME4);
    chartModel.setBackground(0x343434);
    chartModel.setBorderColor(0x987654);
    chartModel.setBorderVisible(true);
    chartModel.getTitle().setText("Chart Title");
    chartModel.getTitle().setColor(0x123456);
    chartModel.getTitle().setFont("monospace", 20, FontStyle.OBLIQUE, FontWeight.BOLD);
    chartModel.getLegend().setVisible(true);
    chartModel.getLegend().setBorderColor(0x654321);
    chartModel.getLegend().setBorderVisible(true);
    chartModel.getLegend().setBorderWidth(2);
    chartModel.getLegend().setFont("verdana", 18, FontStyle.ITALIC, FontWeight.BOLD);
    
    BarPlot barPlot = new BarPlot();
    barPlot.setBackground(0x765890);
    barPlot.setFlavor(BarPlotFlavor.THREED);
    barPlot.setOpacity(0.75f);
    barPlot.setOrientation(Orientation.HORIZONTAL);
    barPlot.setPalette(new Palette(0x111111, 0x222222, 0x333333));
    barPlot.getXAxisLabel().setText("xAxis");
    barPlot.getXAxisLabel().setColor(0x192837);
    barPlot.getXAxisLabel().setFont("san-serif", 10, FontStyle.NORMAL, FontWeight.NORMAL);
    barPlot.getYAxisLabel().setText("yAxis");
    barPlot.getYAxisLabel().setColor(0x192837);
    barPlot.getYAxisLabel().setFont("san-serif", 12, FontStyle.OBLIQUE, FontWeight.BOLD);
    
    chartModel.setPlot(barPlot);
    
    
    String result = ChartSerializer2.serialize(chartModel);
    
    System.out.println("Bar Plot");
    System.out.println(result);
    
    ChartModel chartModel2 = ChartSerializer2.deSerialize(result);
    assertEquals(chartModel2.getTheme(), ChartTheme.THEME4);
    assertEquals(chartModel2.getBackground(), new Integer(0x343434));
    assertTrue(chartModel2.getBorderVisible());
    assertEquals(chartModel2.getBorderColor(), new Integer(0x987654));
    assertEquals(chartModel2.getBorderWidth(), new Integer(1));
    assertEquals(chartModel2.getTitle().getText(), "Chart Title");
    assertEquals(chartModel2.getTitle().getColor(), 0x123456);
    assertEquals(chartModel2.getTitle().getFontFamily(), "monospace");
    assertEquals(chartModel2.getTitle().getFontSize(), new Integer(20));
    assertEquals(chartModel2.getTitle().getFontStyle(), FontStyle.OBLIQUE);
    assertEquals(chartModel2.getTitle().getFontWeight(), FontWeight.BOLD);
    assertTrue(chartModel2.getLegend().getVisible());
    assertTrue(chartModel2.getLegend().getBorderVisible());
    assertEquals(chartModel2.getLegend().getBorderWidth(), 2);
    assertEquals(chartModel2.getLegend().getFontFamily(), "verdana");
    assertEquals(chartModel2.getLegend().getFontSize(), new Integer(18));
    assertEquals(chartModel2.getLegend().getFontStyle(), FontStyle.ITALIC);
    assertEquals(chartModel2.getLegend().getFontWeight(), FontWeight.BOLD);
    
    assertTrue(chartModel2.getPlot() instanceof BarPlot);    
    barPlot = (BarPlot)chartModel2.getPlot();
    assertEquals(barPlot.getBackground(), new Integer(0x765890));
    assertEquals(barPlot.getFlavor(), BarPlotFlavor.THREED);
    assertEquals(barPlot.getOpacity(), new Float(0.75));
    assertEquals(barPlot.getOrientation(), Orientation.HORIZONTAL);
    Palette palette = chartModel2.getPlot().getPalette();
    assertEquals(palette.size(), 3);
    assertEquals(palette.get(0), 0x111111);
    assertEquals(palette.get(1), 0x222222);
    assertEquals(palette.get(2), 0x333333);
    assertEquals(barPlot.getXAxisLabel().getText(), "xAxis");
    assertEquals(barPlot.getXAxisLabel().getColor(), 0x192837);
    assertEquals(barPlot.getXAxisLabel().getFontFamily(), "san-serif");
    assertEquals(barPlot.getXAxisLabel().getFontSize(), 10);
    assertEquals(barPlot.getXAxisLabel().getFontStyle(), FontStyle.NORMAL);
    assertEquals(barPlot.getXAxisLabel().getFontWeight(), FontWeight.NORMAL);
    assertEquals(barPlot.getYAxisLabel().getText(), "yAxis");
    assertEquals(barPlot.getYAxisLabel().getColor(), 0x192837);
    assertEquals(barPlot.getYAxisLabel().getFontFamily(), "san-serif");
    assertEquals(barPlot.getYAxisLabel().getFontSize(), 12);
    assertEquals(barPlot.getYAxisLabel().getFontStyle(), FontStyle.OBLIQUE);
    assertEquals(barPlot.getYAxisLabel().getFontWeight(), FontWeight.BOLD);    
  }
}
