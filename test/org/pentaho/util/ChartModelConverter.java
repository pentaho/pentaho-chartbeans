package org.pentaho.util;

import org.pentaho.chart.model.ChartModel;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ChartModelConverter implements Converter {

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    ChartModel chartModel = (ChartModel)value;
    writer.addAttribute("chartEngine", Integer.toString(chartModel.getChartEngine()));
    
    if (chartModel.getTheme() != null) {
      writer.addAttribute("theme", chartModel.getTheme().toString());
    }
    
    if (chartModel.getStyle().size() > 0) {
      writer.addAttribute("style", chartModel.getStyle().toString());
    }
    
    if ((chartModel.getTitle() != null) && (chartModel.getTitle().getText() != null) && (chartModel.getTitle().getText().length() > 0)) {
      ExtendedHierarchicalStreamWriterHelper.startNode(writer, "title", chartModel.getTitle().getClass());
      context.convertAnother(chartModel.getTitle());
      writer.endNode();
    }
    
    if ((chartModel.getLegend() != null) && chartModel.getLegend().getVisible()) {
      ExtendedHierarchicalStreamWriterHelper.startNode(writer, "legend", chartModel.getLegend().getClass());
      context.convertAnother(chartModel.getLegend());
      writer.endNode();
    }
    
    if (chartModel.getPlot() != null) {
      String plotType = chartModel.getPlot().getClass().getSimpleName();
      plotType = plotType.substring(0, 1).toLowerCase() + plotType.substring(1);
      ExtendedHierarchicalStreamWriterHelper.startNode(writer, plotType, chartModel.getPlot().getClass());
      context.convertAnother(chartModel.getPlot());
      writer.endNode();
    }
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    
    ChartModel chartModel = new ChartModel();
    String nodeName = reader.getNodeName();
    reader.moveDown();
    nodeName = reader.getNodeName();
    reader.moveUp();
    reader.moveDown();
    nodeName = reader.getNodeName();
    reader.moveDown();
    return chartModel;
  }

  public boolean canConvert(Class clazz) {
    return clazz.equals(ChartModel.class);
  }

}
