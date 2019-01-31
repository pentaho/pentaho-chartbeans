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
* Copyright (c) 2002-2019 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.model.util;

import org.pentaho.chart.model.ChartDataDefinition;
import org.pentaho.chart.model.ChartLegend;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.CssStyle;
import org.pentaho.chart.model.DialPlot;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.TwoAxisPlot;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.LinePlot.LinePlotFlavor;
import org.pentaho.chart.model.PiePlot.PieLabels;
import org.pentaho.chart.model.Plot.Orientation;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ChartSerializer {
  public enum ChartSerializationFormat { JSON, XML }
  private static XStream jsonChartWriter = new XStream( new JettisonMappedXmlDriver() );
  private static XStream jsonChartDefWriter = new XStream( new JettisonMappedXmlDriver() );
  private static XStream xmlChartWriter = new XStream( new DomDriver() );
  private static XStream xmlChartDefWriter = new XStream( new DomDriver() );
  static {
    initChartWriter( jsonChartWriter );
    initChartWriter( xmlChartWriter );
    initChartDefWriter( jsonChartDefWriter );
    initChartDefWriter( xmlChartDefWriter );
    initWriterSecurity( jsonChartWriter );
    initWriterSecurity( jsonChartDefWriter );
    initWriterSecurity( xmlChartWriter );
    initWriterSecurity( xmlChartDefWriter );
  }

  private static void initChartWriter( XStream chartWriter ) {
    chartWriter.setMode( XStream.NO_REFERENCES );
    chartWriter.alias( "chartModel", ChartModel.class ); //$NON-NLS-1$
    chartWriter.useAttributeFor( CssStyle.class );
    chartWriter.useAttributeFor( Orientation.class );
    chartWriter.useAttributeFor( LinePlotFlavor.class );
    chartWriter.useAttributeFor( BarPlotFlavor.class );
    chartWriter.registerConverter( new CssStylesConverter() );
    chartWriter.omitField( ChartLegend.class, "visible" ); //$NON-NLS-1$
    chartWriter.registerConverter( new StyledTextConverter() );
    chartWriter.registerConverter( new PaletteConverter() );
    chartWriter.registerConverter( new ScaleConverter() );
    chartWriter.registerConverter( new ChartModelConverter() );
    chartWriter.registerConverter( new AxisConverter() );
    chartWriter.registerConverter( new ChartTitleConverter() );
    chartWriter.registerConverter( new GridConverter() );
    chartWriter.useAttributeFor( PiePlot.class, "animate" ); //$NON-NLS-1$
    chartWriter.useAttributeFor( DialPlot.class, "animate" ); //$NON-NLS-1$
    chartWriter.useAttributeFor( PiePlot.class, "startAngle" ); //$NON-NLS-1$
    chartWriter.omitField( PiePlot.class, "slices" ); //$NON-NLS-1$
    chartWriter.omitField( PiePlot.class, "labels" ); //$NON-NLS-1$
    chartWriter.omitField( PieLabels.class, "visible" ); //$NON-NLS-1$
    chartWriter.omitField( TwoAxisPlot.class, "horizontalAxis" ); //$NON-NLS-1$
    chartWriter.omitField( TwoAxisPlot.class, "verticalAxis" ); //$NON-NLS-1$
    chartWriter.omitField( TwoAxisPlot.class, "grid" ); //$NON-NLS-1$
  }

  private static void initChartDefWriter( XStream chartDefWriter ) {
    chartDefWriter.setMode( XStream.NO_REFERENCES );
    chartDefWriter.alias( "chartDataModel", ChartDataDefinition.class ); //$NON-NLS-1$
  }

  private static void initWriterSecurity( XStream chartWriter ) {
    XStream.setupDefaultSecurity( chartWriter );
    Class[] allowedTypes = new Class[]{ ChartModel.class, ChartDataDefinition.class };
    chartWriter.allowTypes( allowedTypes );
  }

  public static String serialize( ChartModel model, ChartSerializationFormat outputFormat ) {
    String result = null;
    switch ( outputFormat ) {
      case JSON:
        result = jsonChartWriter.toXML( model );
        break;
      case XML:
        result = xmlChartWriter.toXML( model );
        break;
    }
    return result;
  }
  public static ChartModel deSerialize( String input, ChartSerializationFormat inputFormat ) {
    ChartModel chartModel = null;
    switch ( inputFormat ) {
      case JSON:
        chartModel = (ChartModel) jsonChartWriter.fromXML( input );
        break;
      case XML:
        chartModel = (ChartModel) xmlChartWriter.fromXML( input );
        break;
    }
    return chartModel;
  }
  public static String serializeDataDefinition( ChartDataDefinition def, ChartSerializationFormat outputFormat ) {
    String result = null;
    switch ( outputFormat ) {
      case JSON:
        result = jsonChartDefWriter.toXML( def );
        break;
      case XML:
        result = xmlChartDefWriter.toXML( def );
        break;
    }
    return result;
  }
  public static ChartDataDefinition deSerializeDataDefinition( String input, ChartSerializationFormat inputFormat ) {
    ChartDataDefinition chartDataDefinition = null;
    switch ( inputFormat ) {
      case JSON:
        chartDataDefinition = (ChartDataDefinition) jsonChartDefWriter.fromXML( input );
        break;
      case XML:
        chartDataDefinition = (ChartDataDefinition) xmlChartDefWriter.fromXML( input );
        break;
    }
    return chartDataDefinition;
  }
}
