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

import org.pentaho.chart.model.AreaPlot;
import org.pentaho.chart.model.Axis;
import org.pentaho.chart.model.BarPlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.CssStyle;
import org.pentaho.chart.model.DialPlot;
import org.pentaho.chart.model.Grid;
import org.pentaho.chart.model.LinePlot;
import org.pentaho.chart.model.NumericAxis;
import org.pentaho.chart.model.Palette;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.Plot;
import org.pentaho.chart.model.ScatterPlot;
import org.pentaho.chart.model.StyledText;
import org.pentaho.chart.model.TwoAxisPlot;
import org.pentaho.chart.model.Axis.LabelOrientation;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.ChartTitle.TitleLocation;
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

  public void marshal( Object value, HierarchicalStreamWriter writer, MarshallingContext context ) {
    ChartModel chartModel = (ChartModel) value;
    if ( chartModel.getChartEngineId() != null ) {
      writer.addAttribute( "chartEngine", chartModel.getChartEngineId() );
    }

    if ( chartModel.getTheme() != null ) {
      writer.addAttribute( "theme", chartModel.getTheme().toString() );
    }

    if ( chartModel.getStyle().size() > 0 ) {
      writer.addAttribute( "style", chartModel.getStyle().getStyleString() );
    }

    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null ) && (
        chartModel.getTitle().getText().length() > 0 ) ) {
      ExtendedHierarchicalStreamWriterHelper.startNode( writer, "title", chartModel.getTitle().getClass() );
      context.convertAnother( chartModel.getTitle() );
      writer.endNode();
    }

    for ( StyledText subtitle : chartModel.getSubtitles() ) {
      if ( ( subtitle.getText() != null ) && ( subtitle.getText().trim().length() > 0 ) ) {
        ExtendedHierarchicalStreamWriterHelper.startNode( writer, "subtitle", subtitle.getClass() );
        context.convertAnother( subtitle );
        writer.endNode();
      }
    }

    if ( ( chartModel.getLegend() != null ) && chartModel.getLegend().getVisible() ) {
      ExtendedHierarchicalStreamWriterHelper.startNode( writer, "legend", chartModel.getLegend().getClass() );
      context.convertAnother( chartModel.getLegend() );
      writer.endNode();
    }

    if ( chartModel.getPlot() != null ) {
      String plotType = chartModel.getPlot().getClass().getSimpleName();
      plotType = plotType.substring( 0, 1 ).toLowerCase() + plotType.substring( 1 );
      ExtendedHierarchicalStreamWriterHelper.startNode( writer, plotType, chartModel.getPlot().getClass() );
      context.convertAnother( chartModel.getPlot() );
      if ( chartModel.getPlot() instanceof PiePlot ) {
        PiePlot piePlot = (PiePlot) chartModel.getPlot();
        if ( piePlot.getLabels().getVisible() ) {
          ExtendedHierarchicalStreamWriterHelper.startNode( writer, "labels", piePlot.getLabels().getClass() );
          context.convertAnother( piePlot.getLabels() );
          writer.endNode();
        }
      }
      if ( chartModel.getPlot() instanceof TwoAxisPlot ) {
        TwoAxisPlot twoAxisPlot = (TwoAxisPlot) chartModel.getPlot();

        Axis xAxis = twoAxisPlot.getHorizontalAxis();
        ExtendedHierarchicalStreamWriterHelper.startNode( writer, "horizontalAxis", xAxis.getClass() );
        context.convertAnother( xAxis );
        writer.endNode();

        Axis yAxis = twoAxisPlot.getVerticalAxis();
        ExtendedHierarchicalStreamWriterHelper.startNode( writer, "verticalAxis", yAxis.getClass() );
        context.convertAnother( yAxis );
        writer.endNode();

        Grid grid = twoAxisPlot.getGrid();
        if ( grid.getVisible() ) {
          ExtendedHierarchicalStreamWriterHelper.startNode( writer, "grid", grid.getClass() );
          context.convertAnother( grid );
          writer.endNode();
        }
      }
      writer.endNode();
    }
  }

  public Object unmarshal( HierarchicalStreamReader reader, UnmarshallingContext context ) {

    ChartModel chartModel = new ChartModel();
    chartModel.setChartEngineId( reader.getAttribute( "chartEngine" ) );

    String attribute = reader.getAttribute( "theme" );
    if ( attribute != null ) {
      try {
        chartModel.setTheme( Enum.valueOf( ChartTheme.class, attribute.toUpperCase() ) );
      } catch ( Exception e ) {
        // Do nothing
      }
    }

    String cssStyle = reader.getAttribute( "style" );
    if ( cssStyle != null ) {
      chartModel.getStyle().setStyleString( cssStyle );
    }

    while ( reader.hasMoreChildren() ) {
      reader.moveDown();
      if ( reader.getNodeName().equals( "title" ) ) {
        String title = reader.getValue();
        if ( title != null ) {
          chartModel.getTitle().setText( title );
        }
        cssStyle = reader.getAttribute( "style" );
        if ( cssStyle != null ) {
          chartModel.getTitle().getStyle().setStyleString( cssStyle );
        }
        attribute = reader.getAttribute( "location" );
        if ( attribute != null ) {
          try {
            chartModel.getTitle().setLocation( Enum.valueOf( TitleLocation.class, attribute.toUpperCase() ) );
          } catch ( Exception e ) {
            // Do nothing
          }
        }
      } else if ( reader.getNodeName().equals( "subtitle" ) ) {
        String subtitle = reader.getValue();
        if ( ( subtitle != null ) && ( subtitle.trim().length() > 0 ) ) {
          StyledText styledText = new StyledText( subtitle );
          cssStyle = reader.getAttribute( "style" );
          if ( cssStyle != null ) {
            styledText.getStyle().setStyleString( cssStyle );
          }
          chartModel.getSubtitles().add( styledText );
        }
      } else if ( reader.getNodeName().equals( "legend" ) ) {
        chartModel.getLegend().setVisible( true );
        cssStyle = reader.getAttribute( "style" );
        if ( cssStyle != null ) {
          chartModel.getLegend().getStyle().setStyleString( cssStyle );
        }
      } else if ( reader.getNodeName().equals( "barPlot" )
          || reader.getNodeName().equals( "linePlot" )
          || reader.getNodeName().equals( "areaPlot" )
          || reader.getNodeName().equals( "piePlot" )
          || reader.getNodeName().equals( "dialPlot" )
          || reader.getNodeName().equals( "scatterPlot" ) ) {
        chartModel.setPlot( createPlot( reader ) );
      }
      reader.moveUp();
    }

    return chartModel;
  }

  private Plot createPlot( HierarchicalStreamReader reader ) {
    Plot plot = null;
    if ( reader.getNodeName().equals( "barPlot" ) ) {
      BarPlot barPlot = new BarPlot();
      barPlot.getGrid().setVisible( false );
      String flavor = reader.getAttribute( "flavor" );
      if ( flavor != null ) {
        try {
          barPlot.setFlavor( Enum.valueOf( BarPlotFlavor.class, flavor.toUpperCase() ) );
        } catch ( Exception ex ) {
          // Do nothing, we'll stay with the default.
        }
      }
      plot = barPlot;
    } else if ( reader.getNodeName().equals( "linePlot" ) ) {
      LinePlot linePlot = new LinePlot();
      linePlot.getGrid().setVisible( false );
      String flavor = reader.getAttribute( "flavor" );
      if ( flavor != null ) {
        try {
          linePlot.setFlavor( Enum.valueOf( LinePlotFlavor.class, flavor.toUpperCase() ) );
        } catch ( Exception ex ) {
          // Do nothing, we'll stay with the default.
        }
      }
      plot = linePlot;
    } else if ( reader.getNodeName().equals( "areaPlot" ) ) {
      AreaPlot areaPlot = new AreaPlot();
      areaPlot.getGrid().setVisible( false );
      plot = areaPlot;
    } else if ( reader.getNodeName().equals( "scatterPlot" ) ) {
      ScatterPlot scatterPlot = new ScatterPlot();
      scatterPlot.getGrid().setVisible( false );
      plot = scatterPlot;
    } else if ( reader.getNodeName().equals( "piePlot" ) ) {
      PiePlot piePlot = new PiePlot();
      piePlot.getLabels().setVisible( false );
      piePlot.setAnimate( Boolean.parseBoolean( reader.getAttribute( "animate" ) ) );
      try {
        piePlot.setStartAngle( Integer.parseInt( reader.getAttribute( "startAngle" ) ) );
      } catch ( Exception ex ) {
        // Do nothing.We won't set the start angle
      }
      plot = piePlot;
    } else if ( reader.getNodeName().equals( "dialPlot" ) ) {
      DialPlot dialPlot = new DialPlot();
      dialPlot.setAnimate( Boolean.parseBoolean( reader.getAttribute( "animate" ) ) );
      plot = dialPlot;
    }

    String orientation = reader.getAttribute( "orientation" );
    if ( orientation != null ) {
      try {
        plot.setOrientation( Enum.valueOf( Orientation.class, orientation.toUpperCase() ) );
      } catch ( Exception ex ) {
        // Do nothing, we'll stay with the default.
      }
    }

    String cssStyle = reader.getAttribute( "style" );
    if ( cssStyle != null ) {
      plot.getStyle().setStyleString( cssStyle );
    }

    while ( reader.hasMoreChildren() ) {
      reader.moveDown();
      if ( reader.getNodeName().equals( "palette" ) ) {
        CssStyle paintStyle = new CssStyle();
        Palette palette = new Palette();
        while ( reader.hasMoreChildren() ) {
          reader.moveDown();
          if ( reader.getNodeName().equals( "paint" ) ) {
            cssStyle = reader.getAttribute( "style" );
            if ( cssStyle != null ) {
              paintStyle.setStyleString( cssStyle );
              Integer color = paintStyle.getColor();
              if ( color != null ) {
                palette.add( color );
              }
            }
          }
          reader.moveUp();
        }
        if ( palette.size() > 0 ) {
          plot.setPalette( palette );
        }
      }
      if ( ( reader.getNodeName().equals( "verticalAxis" ) || reader.getNodeName().equals( "horizontalAxis" ) )
          && ( plot instanceof TwoAxisPlot ) ) {
        TwoAxisPlot twoAxisPlot = (TwoAxisPlot) plot;
        Axis axis = ( reader.getNodeName().equals( "verticalAxis" ) ? twoAxisPlot.getVerticalAxis()
            : twoAxisPlot.getHorizontalAxis() );
        String axisLabelOrientation = reader.getAttribute( "labelOrientation" );
        try {
          axis.setLabelOrientation( Enum.valueOf( LabelOrientation.class, axisLabelOrientation.toUpperCase() ) );
        } catch ( Exception ex ) {
          // Do nothing, we'll stay with the default.
        }
        if ( axis instanceof NumericAxis ) {
          NumericAxis numericAxis = (NumericAxis) axis;
          String minValueStr = reader.getAttribute( "minValue" );
          if ( minValueStr != null ) {
            try {
              numericAxis.setMinValue( Integer.parseInt( minValueStr ) );
            } catch ( NumberFormatException ex ) {
              try {
                numericAxis.setMinValue( Double.parseDouble( minValueStr ) );
              } catch ( NumberFormatException ex2 ) {
                // Do nothing. No min value will be assigned.
              }
            }
          }
          String maxValueStr = reader.getAttribute( "maxValue" );
          if ( maxValueStr != null ) {
            if ( maxValueStr != null ) {
              try {
                numericAxis.setMaxValue( Integer.parseInt( maxValueStr ) );
              } catch ( NumberFormatException ex ) {
                try {
                  numericAxis.setMaxValue( Double.parseDouble( maxValueStr ) );
                } catch ( NumberFormatException ex2 ) {
                  // Do nothing. No min value will be assigned.
                }
              }
            }
          }
        }
        cssStyle = reader.getAttribute( "style" );
        if ( cssStyle != null ) {
          axis.getStyle().setStyleString( cssStyle );
        }
        while ( reader.hasMoreChildren() ) {
          reader.moveDown();
          String legend = reader.getValue();
          if ( legend != null ) {
            axis.getLegend().setText( legend );
          }
          cssStyle = reader.getAttribute( "style" );
          if ( cssStyle != null ) {
            axis.getLegend().getStyle().setStyleString( cssStyle );
          }
          reader.moveUp();
        }
      }

      if ( reader.getNodeName().equals( "grid" ) && ( plot instanceof TwoAxisPlot ) ) {
        TwoAxisPlot twoAxisPlot = (TwoAxisPlot) plot;
        Grid grid = twoAxisPlot.getGrid();
        grid.setVisible( true );
        while ( reader.hasMoreChildren() ) {
          reader.moveDown();
          if ( reader.getNodeName().equals( "verticalLines" ) ) {
            cssStyle = reader.getAttribute( "style" );
            if ( cssStyle != null ) {
              grid.getVerticalLineStyle().setStyleString( cssStyle );
            }
          } else if ( reader.getNodeName().equals( "horizontalLines" ) ) {
            cssStyle = reader.getAttribute( "style" );
            if ( cssStyle != null ) {
              grid.getHorizontalLineStyle().setStyleString( cssStyle );
            }
          }
          reader.moveUp();
        }
      }

      if ( reader.getNodeName().equals( "scale" ) && ( plot instanceof DialPlot ) ) {
        while ( reader.hasMoreChildren() ) {
          CssStyle rangeStyle = new CssStyle();
          Integer color = null;
          Double rangeMin = null;
          Double rangeMax = null;
          reader.moveDown();
          if ( reader.getNodeName().equals( "range" ) ) {
            cssStyle = reader.getAttribute( "style" );
            if ( cssStyle != null ) {
              rangeStyle.setStyleString( cssStyle );
              color = rangeStyle.getColor();
            }
            String str = reader.getAttribute( "min" );
            if ( str != null ) {
              rangeMin = new Double( str );
            }
            str = reader.getAttribute( "max" );
            if ( str != null ) {
              rangeMax = new Double( str );
            }
            ( (DialPlot) plot ).getScale().addRange( new DialRange( rangeMin, rangeMax, color ) );
          }
          reader.moveUp();
        }
      }
      if ( reader.getNodeName().equals( "labels" ) && ( plot instanceof PiePlot ) ) {
        PiePlot piePlot = (PiePlot) plot;
        piePlot.getLabels().setVisible( true );
        cssStyle = reader.getAttribute( "style" );
        if ( cssStyle != null ) {
          piePlot.getLabels().getStyle().setStyleString( cssStyle );
        }
      }
      if ( reader.getNodeName().equals( "annotation" ) && ( plot instanceof DialPlot ) ) {
        DialPlot dialPlot = (DialPlot) plot;
        String annotation = reader.getValue();
        if ( annotation != null ) {
          dialPlot.getAnnotation().setText( annotation );
        }
        cssStyle = reader.getAttribute( "style" );
        if ( cssStyle != null ) {
          dialPlot.getAnnotation().getStyle().setStyleString( cssStyle );
        }
      }
      reader.moveUp();
    }
    return plot;
  }

  public boolean canConvert( Class clazz ) {
    return clazz.equals( ChartModel.class );
  }

}
