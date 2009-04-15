package org.pentaho.chart.model.util;

import org.pentaho.chart.model.AreaPlot;
import org.pentaho.chart.model.BarPlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.CssStyle;
import org.pentaho.chart.model.DialPlot;
import org.pentaho.chart.model.GraphPlot;
import org.pentaho.chart.model.LinePlot;
import org.pentaho.chart.model.Palette;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.Plot;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.DialPlot.DialRange;
import org.pentaho.chart.model.LinePlot.LinePlotFlavor;
import org.pentaho.chart.model.Plot.Orientation;
import org.pentaho.chart.model.Theme.ChartTheme;

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
    String attribute = reader.getAttribute("chartEngine");
    if (attribute != null) {
      try {
        chartModel.setChartEngine(Integer.parseInt(attribute));
      } catch (Exception e) {
        // Do nothing
      }
    }
    
    attribute = reader.getAttribute("theme");
    if (attribute != null) {
      ;
      try {
        chartModel.setTheme(Enum.valueOf(ChartTheme.class, attribute.toUpperCase()));
      } catch (Exception e) {
        // Do nothing
      }
    }
    
    String cssStyle = reader.getAttribute("style");
    if (cssStyle != null) {
      chartModel.getStyle().setStyleString(cssStyle);
    }
    
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (reader.getNodeName().equals("title")) {
        String title = reader.getValue();
        if (title != null) {
          chartModel.getTitle().setText(title);
        } 
        cssStyle = reader.getAttribute("style");
        if (cssStyle != null) {
          chartModel.getTitle().getStyle().setStyleString(cssStyle);
        }
      } else if (reader.getNodeName().equals("legend")) {
        chartModel.getLegend().setVisible(true);
        cssStyle = reader.getAttribute("style");
        if (cssStyle != null) {
          chartModel.getLegend().getStyle().setStyleString(cssStyle);
        }
      } else if (reader.getNodeName().equals("barPlot")
          || reader.getNodeName().equals("linePlot") 
          || reader.getNodeName().equals("areaPlot")
          || reader.getNodeName().equals("piePlot")
          || reader.getNodeName().equals("dialPlot")) {        
        chartModel.setPlot(createPlot(reader));
      }
      reader.moveUp();
    }
      	
    return chartModel;
  }

  private Plot createPlot(HierarchicalStreamReader reader) {
    Plot plot = null;
    if (reader.getNodeName().equals("barPlot")) {
      BarPlot barPlot = new BarPlot();       
      String flavor = reader.getAttribute("flavor");
      if (flavor != null) {
        try {
          barPlot.setFlavor(Enum.valueOf(BarPlotFlavor.class, flavor.toUpperCase()));
        } catch (Exception ex) {
          // Do nothing, we'll stay with the default.
        }
      }
      plot = barPlot;
    } else if (reader.getNodeName().equals("linePlot")) {
      LinePlot linePlot = new LinePlot();
      String flavor = reader.getAttribute("flavor");
      if (flavor != null) {
        try {
          linePlot.setFlavor(Enum.valueOf(LinePlotFlavor.class, flavor.toUpperCase()));
        } catch (Exception ex) {
          // Do nothing, we'll stay with the default.
        }
      }
      plot = linePlot;
    } else if (reader.getNodeName().equals("areaPlot")) {
      plot = new AreaPlot();
    } else if (reader.getNodeName().equals("piePlot")) {
      PiePlot piePlot = new PiePlot();
      piePlot.setAnimate(Boolean.parseBoolean(reader.getAttribute("animate")));
      try {
        piePlot.setStartAngle(Integer.parseInt(reader.getAttribute("startAngle")));
      } catch (Exception ex) {
        // Do nothing.We won't set the start angle
      }
      plot = piePlot;
    } else if (reader.getNodeName().equals("dialPlot")) {
      DialPlot dialPlot = new DialPlot();
      plot = dialPlot;
    }
    
    String orientation = reader.getAttribute("orientation");
    if (orientation != null) {
      try {
        plot.setOrientation(Enum.valueOf(Orientation.class, orientation.toUpperCase()));
      } catch (Exception ex) {
        // Do nothing, we'll stay with the default.
      }
    }
    
    String cssStyle = reader.getAttribute("style");
    if (cssStyle != null) {
      plot.getStyle().setStyleString(cssStyle);
    }
    
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (reader.getNodeName().equals("palette")) {
        CssStyle paintStyle = new CssStyle();
        Palette palette = new Palette();
        while (reader.hasMoreChildren()) {
          reader.moveDown();
          if (reader.getNodeName().equals("paint")) {
            cssStyle = reader.getAttribute("style");
            if (cssStyle != null) {
              paintStyle.setStyleString(cssStyle);
              Integer color = paintStyle.getColor();
              if (color != null) {
                palette.add(color);
              }
            }
          }
          reader.moveUp();
        }
        if (palette.size() > 0) {
          plot.setPalette(palette);
        }
      }
      if (reader.getNodeName().equals("scale") && (plot instanceof DialPlot)) {
        while (reader.hasMoreChildren()) {
          CssStyle rangeStyle = new CssStyle();
          Integer color = null;
          Integer rangeMin = null;
          Integer rangeMax = null;
          reader.moveDown();
          if (reader.getNodeName().equals("range")) {
            cssStyle = reader.getAttribute("style");
            if (cssStyle != null) {
              rangeStyle.setStyleString(cssStyle);
              color = rangeStyle.getColor();
            }
            String str = reader.getAttribute("min");
            if (str != null) {
              rangeMin = new Integer(str);
            }
            str = reader.getAttribute("max");
            if (str != null) {
              rangeMax = new Integer(str);
            }
            ((DialPlot)plot).addRange(new DialRange(rangeMin, rangeMax, color));
          }
          reader.moveUp();
        }
      }
      if (reader.getNodeName().equals("xAxisLabel") && (plot instanceof GraphPlot)) {
        String title = reader.getValue();
        if (title != null) {
          ((GraphPlot)plot).getXAxisLabel().setText(title);
        } 
        cssStyle = reader.getAttribute("style");
        if (cssStyle != null) {
          ((GraphPlot)plot).getXAxisLabel().getStyle().setStyleString(cssStyle);
        }
      } else if (reader.getNodeName().equals("yAxisLabel") && (plot instanceof GraphPlot)) {
        String title = reader.getValue();
        if (title != null) {
          ((GraphPlot)plot).getYAxisLabel().setText(title);
        } 
        cssStyle = reader.getAttribute("style");
        if (cssStyle != null) {
          ((GraphPlot)plot).getYAxisLabel().getStyle().setStyleString(cssStyle);
        }
      }  
      reader.moveUp();
    }
    return plot;
  }
  
  public boolean canConvert(Class clazz) {
    return clazz.equals(ChartModel.class);
  }

}
