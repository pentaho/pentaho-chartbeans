package org.pentaho.chart.model.util;

import org.pentaho.chart.model.ChartLegend;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.CssStyle;
import org.pentaho.chart.model.GraphPlot;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.LinePlot.LinePlotFlavor;
import org.pentaho.chart.model.Plot.Orientation;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.pentaho.platform.plugin.action.chartbeans.ChartDataDefinition;

public class ChartSerializer2 {
  private static XStream chartWriter = new XStream(new DomDriver());
  
  private static XStream chartDefWriter = new XStream(new JettisonMappedXmlDriver());
  
  static{
    chartWriter.alias("chartModel", ChartModel.class);
    chartWriter.useAttributeFor(CssStyle.class); //$NON-NLS-1$
    chartWriter.useAttributeFor(Orientation.class); //$NON-NLS-1$
    chartWriter.useAttributeFor(LinePlotFlavor.class); //$NON-NLS-1$
    chartWriter.useAttributeFor(BarPlotFlavor.class); //$NON-NLS-1$
    chartWriter.registerConverter(new CssStylesConverter());
    chartWriter.omitField(ChartLegend.class, "visible");
    chartWriter.registerConverter(new StyledTextConverter());
    chartWriter.registerConverter(new PaletteConverter());
    chartWriter.registerConverter(new ChartModelConverter());
    
    chartDefWriter.setMode(XStream.NO_REFERENCES);
    chartDefWriter.alias("ChartDataModel", ChartDataDefinition.class); //$NON-NLS-1$
    
  }
  public static String serialize(ChartModel model){
    return chartWriter.toXML(model);
  }
  
  public static ChartModel deSerialize(String input){
    return (ChartModel) chartWriter.fromXML(input);
  }
  
  
  public static String serializeDataDefinition(ChartDataDefinition def){
    return chartDefWriter.toXML(def);
    
  }
  
  public static ChartDataDefinition deSerializeDataDefinition(String input){
    return (ChartDataDefinition) chartDefWriter.fromXML(input);
    
  }
}
