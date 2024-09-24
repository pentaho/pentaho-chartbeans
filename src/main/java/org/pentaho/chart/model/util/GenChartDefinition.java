/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.model.util;

import java.awt.Color;

import org.pentaho.chart.model.AreaPlot;
import org.pentaho.chart.model.BarPlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.DialPlot;
import org.pentaho.chart.model.LinePlot;
import org.pentaho.chart.model.Palette;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.ScatterPlot;
import org.pentaho.chart.model.StyledText;
import org.pentaho.chart.model.TwoAxisPlot;
import org.pentaho.chart.model.Axis.LabelOrientation;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.ChartTitle.TitleLocation;
import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;
import org.pentaho.chart.model.CssStyle.TextAlignment;
import org.pentaho.chart.model.DialPlot.DialRange;
import org.pentaho.chart.model.LinePlot.LinePlotFlavor;
import org.pentaho.chart.model.Plot.Orientation;
import org.pentaho.chart.model.Theme.ChartTheme;
import org.pentaho.chart.model.util.ChartSerializer.ChartSerializationFormat;
import org.pentaho.chart.plugin.jfreechart.JFreeChartPlugin;

public class GenChartDefinition {

  private enum charts {BAR, LINE, HBAR, PIE, AREA, DIAL, SCATTER};
  
  public static void main(String[] args) {

    if ((args == null) || (args.length<=0)){
      System.out.println("========================");
      System.out.println("USAGE: GenChartDefinition");
      System.out.println("========================");
      System.out.println();
      System.out.println("      GenChartDefinition will accept any number of space separated strings as ");
      System.out.println("      arguments, and will print to system out a full featured chart definition in XML. ");
      System.out.println("      Valid chart strings are: ");
      System.out.println("      BAR LINE HBAR PIE AREA DIAL SCATTER ");
      System.out.println();
      System.out.println("      GenChartDefinition string1 string2 stringN ..."   );
      System.out.println();
      System.out.println();
    }

    for (String chart : args) {
      charts type = charts.valueOf(chart);
      switch (type) {
        case BAR:
          genBarChartDefinition();
          break;
        case HBAR:
          genHorizontalBarChartDefinition();
          break;
        case LINE:
          genLineChartDefinition();
          break;
        case PIE:
          genPieChartDefinition();
          break;
        case AREA:
          genAreaChartDefinition();
          break;
        case DIAL:
          genDialChartDefinition();
          break;
        case SCATTER:
          genScatterChartDefinition();
          break;
        default:
          break;
      } 
    }
  }
  
  private static void genDialChartDefinition() {
    
    ChartModel chartModel = new ChartModel();
    fillStandardFeatures(chartModel);

    DialPlot dialPlot = new DialPlot();
    dialPlot.setAnimate(true);
    dialPlot.setBackground(0xFFFFF0);
    dialPlot.setOpacity(0.75f);

    dialPlot.getScale().addRange(new DialRange(0, 75, Color.RED.getRGB()));
    dialPlot.getScale().addRange(new DialRange(75, 125, Color.YELLOW.getRGB()));
    dialPlot.getScale().addRange(new DialRange(125, 200, Color.GREEN.getRGB()));
    
    dialPlot.getAnnotation().setText("annotation");
    dialPlot.getAnnotation().setFont("verdana", 10, FontStyle.ITALIC, FontWeight.BOLD);
    
    dialPlot.setAnimate(true);
    
    chartModel.setPlot(dialPlot);
    
    String result = ChartSerializer.serialize(chartModel, ChartSerializationFormat.XML);
    
    System.out.println(result);
    
  }

  private static void genAreaChartDefinition() {

    ChartModel chartModel = new ChartModel();
    fillStandardFeatures(chartModel);
    
    AreaPlot areaPlot = new AreaPlot();
    fillTwoAxisFeatures(areaPlot);

    areaPlot.setBackground(0xFFFFF0);
    areaPlot.setOpacity(0.75f);
    areaPlot.setPalette(new Palette(0x001111, 0x222222, 0x333333));
    
    chartModel.setPlot(areaPlot);
    String result = ChartSerializer.serialize(chartModel, ChartSerializationFormat.XML);
    System.out.println(result);
    
  }

  private static void genPieChartDefinition(){

    ChartModel chartModel = new ChartModel();
    fillStandardFeatures(chartModel);

    PiePlot piePlot = new PiePlot();
    piePlot.setBackground(0xFFFFF0);
    piePlot.setStartAngle(65);
    piePlot.setOpacity(0.75f);

    piePlot.setPalette(new Palette(0x880a0f, 0xB09A6B, 0x772200, 0xC52F0D, 0x123D82, 0x4A0866, 0xFFAA00, 0x1E8AD3, 0xAA6611, 0x772200, 0x8b2834, 0x333333));
    
    piePlot.setAnimate(true);
    piePlot.getLabels().setFont("monospace", 8, FontStyle.OBLIQUE, FontWeight.BOLD);
    
    chartModel.setPlot(piePlot);
    String result = ChartSerializer.serialize(chartModel, ChartSerializationFormat.XML);
    System.out.println(result);

  }
  
  private static void genLineChartDefinition(){

    ChartModel chartModel = new ChartModel();
    fillStandardFeatures(chartModel);
    
    LinePlot linePlot = new LinePlot();
    fillTwoAxisFeatures(linePlot);
    
    linePlot.setBackground(0xFFFFF0);
    linePlot.setFlavor(LinePlotFlavor.THREED);
    linePlot.setOpacity(0.75f);
    linePlot.setLineWidth(5);

    linePlot.setPalette(new Palette(0x880a0f, 0xB09A6B, 0x772200, 0xC52F0D, 0x123D82, 0x4A0866, 0xFFAA00, 0x1E8AD3, 0xAA6611, 0x772200, 0x8b2834, 0x333333));
    
    chartModel.setPlot(linePlot);
    String result = ChartSerializer.serialize(chartModel, ChartSerializationFormat.XML);
    System.out.println(result);
    
  }
  
  private static void genHorizontalBarChartDefinition(){
    
    ChartModel chartModel = new ChartModel();
    fillStandardFeatures(chartModel);
    
    BarPlot barPlot = fillBarPlot(Orientation.HORIZONTAL);
    chartModel.setPlot(barPlot);
    
    String result = ChartSerializer.serialize(chartModel, ChartSerializationFormat.XML);
    
    System.out.println(result);

  }
  
  private static void genBarChartDefinition(){
    
    ChartModel chartModel = new ChartModel();
    fillStandardFeatures(chartModel);
    
    BarPlot barPlot = fillBarPlot(Orientation.VERTICAL);
    chartModel.setPlot(barPlot);
    
    String result = ChartSerializer.serialize(chartModel, ChartSerializationFormat.XML);
    System.out.println(result);
  }

  public static void genScatterChartDefinition(){
    
    ChartModel chartModel = new ChartModel();
    fillStandardFeatures(chartModel);
    
    ScatterPlot scPlot = new ScatterPlot();
    fillTwoAxisFeatures(scPlot);
    
    scPlot.setPalette(new Palette(0x880a0f, 0xB09A6B, 0x772200, 0xC52F0D, 0x123D82, 0x4A0866, 0xFFAA00, 0x1E8AD3, 0xAA6611, 0x772200, 0x8b2834, 0x333333));

    scPlot.setBackground(0x765890);
    scPlot.setOpacity(0.75f);
    scPlot.setOrientation(Orientation.VERTICAL);
    
    chartModel.setPlot(scPlot);
    
    String result = ChartSerializer.serialize(chartModel, ChartSerializationFormat.XML);
    System.out.println(result);
  }
  
  private static void fillStandardFeatures(ChartModel chartModel){
    
    chartModel.setChartEngineId(JFreeChartPlugin.PLUGIN_ID);
    chartModel.setTheme(ChartTheme.THEME4);
    chartModel.setBackground(0xFFFACD);
    
    chartModel.setBorderColor(0x987654);
    chartModel.setBorderVisible(true);
    chartModel.setBorderWidth(5);
    
    chartModel.getTitle().setText("Chart Title");
    chartModel.getTitle().setAlignment(TextAlignment.CENTER);
    chartModel.getTitle().setColor(0xFFAA00);
    chartModel.getTitle().setFont("courier", 20, FontStyle.OBLIQUE, FontWeight.BOLD);
    chartModel.getTitle().setBackgroundColor(0xFFFACD);
    chartModel.getTitle().setLocation(TitleLocation.TOP);
    
    chartModel.getLegend().setVisible(true);
    chartModel.getLegend().setBorderColor(0x880a0f);
    chartModel.getLegend().setBorderVisible(true);
    chartModel.getLegend().setBorderWidth(10);
    chartModel.getLegend().setFont("verdana", 18, FontStyle.ITALIC, FontWeight.BOLD);

    StyledText subtitle = new StyledText("subtitle", "monospace", FontStyle.ITALIC, FontWeight.BOLD, 12);
    subtitle.setColor(0x00FF00);
    subtitle.setBackgroundColor(0x0000FF);
    chartModel.getSubtitles().add(subtitle);
    
  }
  
  private static void fillTwoAxisFeatures(TwoAxisPlot plot){
    
    plot.getHorizontalAxis().setLabelOrientation(LabelOrientation.DIAGONAL);
    plot.getHorizontalAxis().getLegend().setText("xAxis");
    plot.getHorizontalAxis().getLegend().setColor(0x772200);
    plot.getHorizontalAxis().getLegend().setFont("san-serif", 10, FontStyle.NORMAL, FontWeight.NORMAL);
    plot.getHorizontalAxis().getLegend().setAlignment(TextAlignment.LEFT);
    plot.getHorizontalAxis().getLegend().setBackgroundColor(0xFFAA00);
    
    plot.getVerticalAxis().getLegend().setText( "yAxis");
    plot.getVerticalAxis().getLegend().setColor(0x772200);
    plot.getVerticalAxis().getLegend().setFont("san-serif", 12, FontStyle.OBLIQUE, FontWeight.BOLD);
    plot.getVerticalAxis().getLegend().setAlignment(TextAlignment.RIGHT);
    plot.getVerticalAxis().getLegend().setBackgroundColor(0xFFAA00);

  }
  
  private static BarPlot fillBarPlot(Orientation o){
    BarPlot barPlot = new BarPlot();
    barPlot.setOrientation(o);

    fillTwoAxisFeatures(barPlot);

    barPlot.setBackground(0xFFFFF0);
    barPlot.setFlavor(BarPlotFlavor.PLAIN);
    barPlot.setOpacity(0.75f);
    
    barPlot.setPalette(new Palette(0x880a0f, 0xB09A6B, 0x772200, 0xC52F0D, 0x123D82, 0x4A0866, 0xFFAA00, 0x1E8AD3, 0xAA6611, 0x772200, 0x8b2834, 0x333333));

    return barPlot;
  }
  
}
