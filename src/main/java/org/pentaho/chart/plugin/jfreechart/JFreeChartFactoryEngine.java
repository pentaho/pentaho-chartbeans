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
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.chart.plugin.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.Stroke;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartUtils;
import org.pentaho.chart.IChartLinkGenerator;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartSeriesType;
import org.pentaho.chart.data.BasicDataModel;
import org.pentaho.chart.data.MultiSeriesDataModel;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.data.IChartDataModel;
import org.pentaho.chart.data.MultiSeriesXYDataModel;
import org.pentaho.chart.data.FormattableName;
import org.pentaho.chart.data.NamedValue;
import org.pentaho.chart.data.NamedValuesDataModel;
import org.pentaho.chart.data.XYDataModel;
import org.pentaho.chart.data.XYDataPoint;
import org.pentaho.chart.data.MultiSeriesDataModel.DomainData;
import org.pentaho.chart.data.MultiSeriesXYDataModel.Series;
import org.pentaho.chart.model.AreaPlot;
import org.pentaho.chart.model.BarPlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.DialPlot;
import org.pentaho.chart.model.Grid;
import org.pentaho.chart.model.LinePlot;
import org.pentaho.chart.model.NumericAxis;
import org.pentaho.chart.model.Plot;
import org.pentaho.chart.model.ScatterPlot;
import org.pentaho.chart.model.StyledText;
import org.pentaho.chart.model.Axis.LabelOrientation;
import org.pentaho.chart.model.BarPlot.BarPlotFlavor;
import org.pentaho.chart.model.CssStyle.FontStyle;
import org.pentaho.chart.model.CssStyle.FontWeight;
import org.pentaho.chart.model.DialPlot.DialRange;
import org.pentaho.chart.model.LinePlot.LinePlotFlavor;
import org.pentaho.chart.model.Plot.Orientation;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.ChartResult;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.chart.plugin.jfreechart.chart.area.JFreeAreaChartGeneratorFactory;
import org.pentaho.chart.plugin.jfreechart.chart.bar.JFreeBarChartGeneratorFactory;
import org.pentaho.chart.plugin.jfreechart.chart.dial.JFreeDialChartGeneratorFactory;
import org.pentaho.chart.plugin.jfreechart.chart.dial.JFreeDialChartGenerator.DoubleLineDialFrame;
import org.pentaho.chart.plugin.jfreechart.chart.dial.JFreeDialChartGenerator.FixedStandardDialScale;
import org.pentaho.chart.plugin.jfreechart.chart.dial.JFreeDialChartGenerator.SingleLineDialRange;
import org.pentaho.chart.plugin.jfreechart.chart.dial.JFreeDialChartGenerator.SquareDialPlot;
import org.pentaho.chart.plugin.jfreechart.chart.dial.JFreeDialChartGenerator.VariableStrokePointer;
import org.pentaho.chart.plugin.jfreechart.chart.line.JFreeLineChartGeneratorFactory;
import org.pentaho.chart.plugin.jfreechart.chart.multi.JFreeMultiChartGeneratorFactory;
import org.pentaho.chart.plugin.jfreechart.chart.pie.JFreePieChartGeneratorFactory;
import org.pentaho.chart.plugin.jfreechart.outputs.JFreeChartOutput;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.messages.Messages;

public class JFreeChartFactoryEngine implements Serializable {
  private class AxesLabels {
    String domainAxisLabel = "";
    String rangeAxisLabel = "";
  }

  private static final Log logger = LogFactory.getLog( JFreeChartFactoryEngine.class );

  private static final long serialVersionUID = -1079376910255750394L;

  public JFreeChartFactoryEngine() {
  }

  public IOutput makeChart( ChartModel chartModel, IChartDataModel chartTableModel ) {
    return makeChart( chartModel, chartTableModel, null );
  }

  public IOutput makeChart( ChartModel chartModel, IChartDataModel chartDataModel, IChartLinkGenerator linkGenerator ) {
    IOutput chartOutput = null;
    if ( chartModel.getPlot() instanceof BarPlot ) {
      chartOutput =
        new JFreeChartOutput( makeBarChart( chartModel, (MultiSeriesDataModel) chartDataModel, linkGenerator ) );
    } else if ( chartModel.getPlot() instanceof LinePlot ) {
      chartOutput =
        new JFreeChartOutput( makeLineChart( chartModel, (MultiSeriesDataModel) chartDataModel, linkGenerator ) );
    } else if ( chartModel.getPlot() instanceof AreaPlot ) {
      chartOutput =
        new JFreeChartOutput( makeAreaChart( chartModel, (MultiSeriesDataModel) chartDataModel, linkGenerator ) );
    } else if ( chartModel.getPlot() instanceof DialPlot ) {
      chartOutput = new JFreeChartOutput( makeDialChart( chartModel, (BasicDataModel) chartDataModel ) );
    } else if ( chartModel.getPlot() instanceof org.pentaho.chart.model.PiePlot ) {
      chartOutput =
        new JFreeChartOutput( makePieChart( chartModel, (NamedValuesDataModel) chartDataModel, linkGenerator ) );
    } else if ( chartModel.getPlot() instanceof ScatterPlot ) {
      if ( chartDataModel instanceof MultiSeriesXYDataModel ) {
        chartOutput = new JFreeChartOutput( makeScatterChart( chartModel, (MultiSeriesXYDataModel) chartDataModel ) );
      } else {
        chartOutput = new JFreeChartOutput( makeScatterChart( chartModel, (XYDataModel) chartDataModel ) );
      }
    }
    return chartOutput;
  }

  public JFreeChart makePieChart( ChartModel chartModel, NamedValuesDataModel dataModel,
                                  final IChartLinkGenerator linkGenerator ) {
    final DefaultPieDataset dataset = new DefaultPieDataset();
    for ( NamedValue namedValue : dataModel ) {
      if ( namedValue.getName() != null ) {
        dataset
          .setValue( namedValue.getNameInstance(), scaleNumber( namedValue.getValue(), dataModel.getScalingFactor() ) );
      }
    }

    boolean showLegend = ( chartModel.getLegend() != null ) && ( chartModel.getLegend().getVisible() );

    String title = "";
    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null )
      && ( chartModel.getTitle().getText().trim().length() > 0 ) ) {
      title = chartModel.getTitle().getText();
    }

    final JFreeChart chart = ChartFactory.createPieChart( title, dataset, showLegend, true, false );

    initChart( chart, chartModel );

    final PiePlot jFreePiePlot = (PiePlot) chart.getPlot();

    if ( linkGenerator != null ) {
      jFreePiePlot.setURLGenerator( new PieURLGenerator() {

        public String generateURL( PieDataset pieDataSet, Comparable category, int arg2 ) {
          String categoryKey = ( (FormattableName) category ).getKey();
          return linkGenerator.generateLink( categoryKey, categoryKey, pieDataSet.getValue( category ) );
        }
      } );
    }

    jFreePiePlot.setNoDataMessage( "No data available" ); //$NON-NLS-1$
    jFreePiePlot.setCircular( true );
    jFreePiePlot.setLabelGap( 0.02 );

    org.pentaho.chart.model.PiePlot chartBeansPiePlot = (org.pentaho.chart.model.PiePlot) chartModel.getPlot();

    List<Integer> colors = getPlotColors( chartBeansPiePlot );

    int index = 0;
    for ( NamedValue namedValue : dataModel ) {
      if ( namedValue.getName() != null ) {
        jFreePiePlot.setSectionPaint( namedValue.getNameInstance(),
          new Color( 0x00FFFFFF & colors.get( index % colors.size() ) ) );
      }
      index++;
    }

    if ( chartBeansPiePlot.getLabels().getVisible() ) {
      jFreePiePlot.setLabelGenerator( new StandardPieSectionLabelGenerator() );

      Font font = ChartUtils
        .getFont( chartBeansPiePlot.getLabels().getFontFamily(), chartBeansPiePlot.getLabels().getFontStyle(),
          chartBeansPiePlot.getLabels().getFontWeight(), chartBeansPiePlot.getLabels().getFontSize() );
      if ( font != null ) {
        jFreePiePlot.setLabelFont( font );
        if ( chartBeansPiePlot.getLabels().getColor() != null ) {
          jFreePiePlot.setLabelPaint( new Color( 0x00FFFFFF & chartBeansPiePlot.getLabels().getColor() ) );
        }
        if ( chartBeansPiePlot.getLabels().getBackgroundColor() != null ) {
          jFreePiePlot
            .setLabelBackgroundPaint( new Color( 0x00FFFFFF & chartBeansPiePlot.getLabels().getBackgroundColor() ) );
        }
      }
    } else {
      jFreePiePlot.setLabelGenerator( null );
    }

    jFreePiePlot.setStartAngle( -chartBeansPiePlot.getStartAngle() );


    return chart;
  }

  protected JFreeChart makeDialChart( ChartModel chartModel, BasicDataModel data ) {
    DialPlot chartBeansDialPlot = (DialPlot) chartModel.getPlot();

    org.jfree.chart.plot.dial.DialPlot jFreeDialPlot = new SquareDialPlot();

    final DefaultValueDataset dataset = new DefaultValueDataset();
    dataset.setValue( scaleNumber( data.getData().get( 0 ), data.getScalingFactor() ) );

    jFreeDialPlot.setDataset( dataset );

    final DoubleLineDialFrame dialFrame = new DoubleLineDialFrame();
    dialFrame.setForegroundPaint( new Color( 0x8d8d8d ) );
    dialFrame.setInnerForegroundPaint( new Color( 0x5d5d5d ) );
    dialFrame.setStroke( new BasicStroke( 2 ) );
    dialFrame.setBackgroundPaint( Color.WHITE );
    jFreeDialPlot.setDialFrame( dialFrame );

    for ( DialRange dialRange : chartBeansDialPlot.getScale() ) {
      if ( dialRange.getColor() != null ) {
        SingleLineDialRange standarddialrange =
          new SingleLineDialRange( dialRange.getMinValue().doubleValue(), dialRange.getMaxValue().doubleValue(),
            new Color( 0x00FFFFFF & dialRange.getColor() ) );
        standarddialrange.setInnerRadius( 0.4D );
        jFreeDialPlot.addLayer( standarddialrange );
      }
    }

    double scaleMajorTickIncrement =
      ( chartBeansDialPlot.getScale().getMaxValue().doubleValue() - chartBeansDialPlot.getScale().getMinValue()
        .doubleValue() ) / 5;
    FixedStandardDialScale standardDialScale =
      new FixedStandardDialScale( chartBeansDialPlot.getScale().getMinValue().doubleValue(),
        chartBeansDialPlot.getScale().getMaxValue().doubleValue(),
        -150.0, -240.0, scaleMajorTickIncrement, 2 );
    standardDialScale.setTickRadius( 0.88D );
    standardDialScale.setTickLabelOffset( 0.15D );
    standardDialScale.setTickLabelFont( ChartUtils.getFont( "sans-serif", FontStyle.NORMAL, FontWeight.NORMAL, 10 ) );
    standardDialScale.setTickLabelPaint( Color.BLACK );
    standardDialScale.setMajorTickLength( 0.04 );
    standardDialScale.setMajorTickPaint( Color.BLACK );
    standardDialScale.setMajorTickStroke( new BasicStroke( 2 ) );
    standardDialScale.setMinorTickLength( 0.02 );
    standardDialScale.setMinorTickPaint( new Color( 0x8b8b8b ) );
    standardDialScale.setMinorTickStroke( new BasicStroke( 1 ) );
    jFreeDialPlot.addScale( 0, standardDialScale );

    DialCap dialCap = new DialCap();
    dialCap.setRadius( 0.06 );
    dialCap.setFillPaint( new Color( 0x636363 ) );
    dialCap.setOutlinePaint( new Color( 0x5d5d5d ) );
    dialCap.setOutlineStroke( new BasicStroke( 2 ) );
    jFreeDialPlot.setCap( dialCap );

    GradientPaint gradientpaint =
      new GradientPaint( new Point(), new Color( 0xfcfcfc ), new Point(), new Color( 0xd7d8da ) );
    DialBackground dialbackground = new DialBackground( gradientpaint ); // specify Color here for no gradient
    dialbackground
      .setGradientPaintTransformer( new StandardGradientPaintTransformer( GradientPaintTransformType.VERTICAL ) );
    jFreeDialPlot.setBackground( dialbackground );

    VariableStrokePointer pointer = new VariableStrokePointer();
    pointer.setRadius( 0.9 );
    pointer.setOutlineStroke( new BasicStroke( 2 ) );
    pointer.setWidthRadius( 0.05 );
    pointer.setFillPaint( new Color( 0x636363 ) );
    pointer.setOutlinePaint( new Color( 0x5d5d5d ) );
    jFreeDialPlot.addPointer( pointer );

    DialValueIndicator dialValueIndicator = new DialValueIndicator( 0 );
    dialValueIndicator.setTemplateValue( chartBeansDialPlot.getScale().getMaxValue() );
    dialValueIndicator.setFont( ChartUtils.getFont( "Dialog", FontStyle.NORMAL, FontWeight.BOLD, 10 ) );
    dialValueIndicator.setPaint( Color.BLACK );
    dialValueIndicator.setBackgroundPaint( Color.WHITE );
    dialValueIndicator.setOutlineStroke( new BasicStroke( 1 ) );
    dialValueIndicator.setOutlinePaint( new Color( 0x8b8b8b ) );
    jFreeDialPlot.addLayer( dialValueIndicator );

    if ( ( chartBeansDialPlot.getAnnotation() != null ) && ( chartBeansDialPlot.getAnnotation().getText() != null ) && (
      chartBeansDialPlot.getAnnotation().getText().trim().length() > 0 ) ) {
      Font font = ChartUtils
        .getFont( chartBeansDialPlot.getAnnotation().getFontFamily(), chartBeansDialPlot.getAnnotation().getFontStyle(),
          chartBeansDialPlot.getAnnotation().getFontWeight(), chartBeansDialPlot.getAnnotation().getFontSize() );
      if ( font == null ) {
        font = ChartUtils.getFont( "sans-serif", FontStyle.NORMAL, FontWeight.NORMAL, 10 );
      }
      DialTextAnnotation dialTextAnnotation =
        new DialTextAnnotation( chartBeansDialPlot.getAnnotation().getText().trim() );
      dialTextAnnotation.setFont( font );
      dialTextAnnotation.setRadius( 0.6 );
      jFreeDialPlot.addLayer( dialTextAnnotation );
    }

    String title = "";
    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null )
      && ( chartModel.getTitle().getText().trim().length() > 0 ) ) {
      title = chartModel.getTitle().getText();
    }

    JFreeChart chart = new JFreeChart( title, jFreeDialPlot );
    initChart( chart, chartModel );

    return chart;
  }

  protected void initChart( JFreeChart chart, ChartModel chartModel ) {
    if ( chartModel.getBackground() instanceof Integer ) {
      chart.setBackgroundPaint( new Color( 0x00FFFFFF & (Integer) chartModel.getBackground() ) );
    } else {
      chart.setBackgroundPaint( Color.WHITE );
    }

    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null ) && (
      chartModel.getTitle().getText().trim().length() > 0 ) ) {
      Font font = ChartUtils.getFont( chartModel.getTitle().getFontFamily(), chartModel.getTitle().getFontStyle(),
        chartModel.getTitle().getFontWeight(), chartModel.getTitle().getFontSize() );
      if ( font != null ) {
        chart.getTitle().setFont( font );
      }

      RectangleEdge rectangleEdge = RectangleEdge.TOP;
      if ( chartModel.getTitle().getLocation() != null ) {
        switch ( chartModel.getTitle().getLocation() ) {
          case RIGHT:
            rectangleEdge = RectangleEdge.BOTTOM;
            break;
          case LEFT:
            rectangleEdge = RectangleEdge.LEFT;
            break;
          case BOTTOM:
            rectangleEdge = RectangleEdge.BOTTOM;
            break;
        }
      }

      chart.getTitle().setPosition( rectangleEdge );
      if ( RectangleEdge.isTopOrBottom( rectangleEdge ) ) {
        HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;
        if ( chartModel.getTitle().getAlignment() != null ) {
          switch ( chartModel.getTitle().getAlignment() ) {
            case LEFT:
              horizontalAlignment = horizontalAlignment.LEFT;
              break;
            case RIGHT:
              horizontalAlignment = horizontalAlignment.RIGHT;
              break;
          }
        }
        chart.getTitle().setHorizontalAlignment( horizontalAlignment );
      }
    }

    if ( ( chartModel.getLegend() != null ) && chartModel.getLegend().getVisible() ) {
      Font font = ChartUtils.getFont( chartModel.getLegend().getFontFamily(), chartModel.getLegend().getFontStyle(),
        chartModel.getLegend().getFontWeight(), chartModel.getLegend().getFontSize() );
      if ( font != null ) {
        chart.getLegend().setItemFont( font );
      }
      if ( !chartModel.getLegend().getBorderVisible() ) {
        chart.getLegend().setFrame( BlockBorder.NONE );
      }
    }

    chart.setBorderVisible( chartModel.getBorderVisible() );

    if ( chartModel.getBorderColor() instanceof Integer ) {
      chart.setBorderPaint( new Color( 0x00FFFFFF & (Integer) chartModel.getBorderColor() ) );
    }

    for ( StyledText subtitle : chartModel.getSubtitles() ) {
      if ( ( subtitle.getText() ) != null && ( subtitle.getText().trim().length() > 0 ) ) {
        TextTitle textTitle = new TextTitle( subtitle.getText() );
        Font font = ChartUtils.getFont( subtitle.getFontFamily(), subtitle.getFontStyle(), subtitle.getFontWeight(),
          subtitle.getFontSize() );
        if ( font != null ) {
          textTitle.setFont( font );
        }
        if ( subtitle.getColor() != null ) {
          textTitle.setPaint( new Color( 0x00FFFFFF & subtitle.getColor() ) );
        }
        if ( subtitle.getBackgroundColor() != null ) {
          textTitle.setBackgroundPaint( new Color( 0x00FFFFFF & subtitle.getBackgroundColor() ) );
        }
        chart.addSubtitle( textTitle );
      }
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.chart.data
   * .ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeAreaChart( ChartModel chartModel, MultiSeriesDataModel dataModel,
                                   IChartLinkGenerator linkGenerator ) {
    DefaultCategoryDataset categoryDataset = createCategoryDataset( dataModel );
    org.pentaho.chart.model.TwoAxisPlot twoAxisPlot = (org.pentaho.chart.model.TwoAxisPlot) chartModel.getPlot();

    String title = "";
    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null ) && (
      chartModel.getTitle().getText().trim().length() > 0 ) ) {
      title = chartModel.getTitle().getText();
    }
    AxesLabels axesLabels = getAxesLabels( chartModel );
    PlotOrientation plotOrientation =
      ( twoAxisPlot.getOrientation() == Orientation.HORIZONTAL ) ? PlotOrientation.HORIZONTAL
        : PlotOrientation.VERTICAL;
    boolean showLegend = ( chartModel.getLegend() != null ) && ( chartModel.getLegend().getVisible() );

    JFreeChart chart = ChartFactory
      .createAreaChart( title, axesLabels.domainAxisLabel, axesLabels.rangeAxisLabel, categoryDataset, plotOrientation,
        showLegend, true, false );

    initCategoryPlot( chart, chartModel, linkGenerator );
    initChart( chart, chartModel );


    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.chart.data
   * .ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeLineChart( ChartModel chartModel, MultiSeriesDataModel dataModel,
                                   IChartLinkGenerator linkGenerator ) {
    DefaultCategoryDataset categoryDataset = createCategoryDataset( dataModel );
    org.pentaho.chart.model.TwoAxisPlot twoAxisPlot = (org.pentaho.chart.model.TwoAxisPlot) chartModel.getPlot();

    String title = "";
    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null ) && (
      chartModel.getTitle().getText().trim().length() > 0 ) ) {
      title = chartModel.getTitle().getText();
    }
    AxesLabels axesLabels = getAxesLabels( chartModel );
    PlotOrientation plotOrientation =
      ( twoAxisPlot.getOrientation() == Orientation.HORIZONTAL ) ? PlotOrientation.HORIZONTAL
        : PlotOrientation.VERTICAL;
    boolean showLegend = ( chartModel.getLegend() != null ) && ( chartModel.getLegend().getVisible() );
    JFreeChart chart = null;

    LinePlot linePlot = (LinePlot) twoAxisPlot;
    if ( linePlot.getFlavor() == LinePlotFlavor.THREED ) {
      chart = ChartFactory
        .createLineChart3D( title, axesLabels.domainAxisLabel, axesLabels.rangeAxisLabel, categoryDataset,
          plotOrientation,
          showLegend, true, false );
    } else {
      chart = ChartFactory
        .createLineChart( title, axesLabels.domainAxisLabel, axesLabels.rangeAxisLabel, categoryDataset,
          plotOrientation,
          showLegend, true, false );
      Stroke stroke = getLineStyleStroke( linePlot.getFlavor(), linePlot.getLineWidth() );
      ( (CategoryPlot) chart.getPlot() ).getRenderer().setStroke( stroke );
    }

    initCategoryPlot( chart, chartModel, linkGenerator );
    initChart( chart, chartModel );

    return chart;
  }

  private XYSeries createXYSeries( XYDataModel xyDataModel ) {

    XYSeries series = null;
    if ( xyDataModel instanceof Series ) {
      series = new XYSeries( ( (Series) xyDataModel ).getSeriesNameInstance() );
    } else {
      series = new XYSeries( "" );
    }
    for ( XYDataPoint dataPoint : xyDataModel ) {
      series.add( dataPoint.getDomainValue(), dataPoint.getRangeValue() );
    }

    return series;
  }

  protected JFreeChart makeScatterChart( ChartModel chartModel, XYDataModel data ) {
    XYSeriesCollection dataset = new XYSeriesCollection();

    dataset.addSeries( createXYSeries( data ) );

    org.pentaho.chart.model.TwoAxisPlot twoAxisPlot = (org.pentaho.chart.model.TwoAxisPlot) chartModel.getPlot();

    String title = "";
    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null ) && (
      chartModel.getTitle().getText().trim().length() > 0 ) ) {
      title = chartModel.getTitle().getText();
    }

    AxesLabels axesLabels = getAxesLabels( chartModel );
    PlotOrientation plotOrientation =
      ( twoAxisPlot.getOrientation() == Orientation.HORIZONTAL ) ? PlotOrientation.HORIZONTAL
        : PlotOrientation.VERTICAL;
    boolean showLegend = ( chartModel.getLegend() != null ) && ( chartModel.getLegend().getVisible() );
    JFreeChart chart = ChartFactory
      .createScatterPlot( title, axesLabels.domainAxisLabel, axesLabels.rangeAxisLabel, dataset, plotOrientation,
        showLegend, true, false );

    initXYPlot( chart, chartModel );
    initChart( chart, chartModel );

    return chart;
  }

  protected JFreeChart makeScatterChart( ChartModel chartModel, MultiSeriesXYDataModel data ) {

    XYSeriesCollection dataset = new XYSeriesCollection();

    for ( Series series : data.getSeries() ) {
      dataset.addSeries( createXYSeries( series ) );
    }

    org.pentaho.chart.model.TwoAxisPlot twoAxisPlot = (org.pentaho.chart.model.TwoAxisPlot) chartModel.getPlot();

    String title = "";
    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null ) && (
      chartModel.getTitle().getText().trim().length() > 0 ) ) {
      title = chartModel.getTitle().getText();
    }

    AxesLabels axesLabels = getAxesLabels( chartModel );
    PlotOrientation plotOrientation =
      ( twoAxisPlot.getOrientation() == Orientation.HORIZONTAL ) ? PlotOrientation.HORIZONTAL
        : PlotOrientation.VERTICAL;
    boolean showLegend = ( chartModel.getLegend() != null ) && ( chartModel.getLegend().getVisible() );
    JFreeChart chart = ChartFactory
      .createScatterPlot( title, axesLabels.domainAxisLabel, axesLabels.rangeAxisLabel, dataset, plotOrientation,
        showLegend, true, false );

    initXYPlot( chart, chartModel );
    initChart( chart, chartModel );

    return chart;
  }

  protected DefaultCategoryDataset createCategoryDataset( MultiSeriesDataModel data ) {
    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    for ( DomainData category : data.getDomainData() ) {
      for ( NamedValue dataPoint : category ) {
        categoryDataset.setValue(
          scaleNumber( dataPoint.getValue(), data.getScalingFactor() ),
          dataPoint.getNameInstance(),
          category.getDomainNameInstance() );
      }
    }
    return categoryDataset;
  }

  private AxesLabels getAxesLabels( ChartModel chartModel ) {
    org.pentaho.chart.model.TwoAxisPlot twoAxisPlot = (org.pentaho.chart.model.TwoAxisPlot) chartModel.getPlot();
    AxesLabels graphLabelsAndFonts = new AxesLabels();

    if ( ( twoAxisPlot.getRangeAxis() != null )
      && ( twoAxisPlot.getRangeAxis().getLegend() != null )
      && ( twoAxisPlot.getRangeAxis().getLegend().getText() != null )
      && ( twoAxisPlot.getRangeAxis().getLegend().getText().trim().length() > 0 ) ) {
      graphLabelsAndFonts.rangeAxisLabel = twoAxisPlot.getRangeAxis().getLegend().getText();
    }
    if ( ( twoAxisPlot.getDomainAxis() != null )
      && ( twoAxisPlot.getDomainAxis().getLegend() != null )
      && ( twoAxisPlot.getDomainAxis().getLegend().getText() != null )
      && ( twoAxisPlot.getDomainAxis().getLegend().getText().trim().length() > 0 ) ) {
      graphLabelsAndFonts.domainAxisLabel = twoAxisPlot.getDomainAxis().getLegend().getText();
    }

    return graphLabelsAndFonts;
  }

  private List<Integer> getPlotColors( Plot plot ) {
    ArrayList<Integer> colors = new ArrayList<Integer>();
    if ( plot.getPalette() != null ) {
      colors.addAll( plot.getPalette() );
    }

    ArrayList<Integer> defaultColors = new ArrayList<Integer>( org.pentaho.chart.model.Plot.DEFAULT_PALETTE );
    defaultColors.removeAll( colors );
    colors.addAll( defaultColors );

    return colors;
  }

  private void initCategoryPlot( JFreeChart chart, ChartModel chartModel, final IChartLinkGenerator linkGenerator ) {
    initPlot( chart, chartModel );

    org.pentaho.chart.model.TwoAxisPlot twoAxisPlot = (org.pentaho.chart.model.TwoAxisPlot) chartModel.getPlot();
    CategoryPlot categoryPlot = chart.getCategoryPlot();

    Grid grid = twoAxisPlot.getGrid();
    if ( twoAxisPlot.getOrientation() != Orientation.HORIZONTAL ) {
      Color color = ( grid.getVerticalLineColor() != null ? new Color( 0x00FFFFFF & grid.getVerticalLineColor() )
        : new Color( 0x00FFFFFF & Grid.DEFAULT_GRID_COLOR ) );
      categoryPlot.setDomainGridlinesVisible( grid.getVerticalLinesVisible() );
      categoryPlot.setDomainGridlinePaint( color );

      color = ( grid.getHorizontalLineColor() != null ? new Color( 0x00FFFFFF & grid.getHorizontalLineColor() )
        : new Color( 0x00FFFFFF & Grid.DEFAULT_GRID_COLOR ) );
      categoryPlot.setRangeGridlinesVisible( grid.getHorizontalLinesVisible() );
      categoryPlot.setRangeGridlinePaint( color );
    } else {
      Color color = ( grid.getHorizontalLineColor() != null ? new Color( 0x00FFFFFF & grid.getHorizontalLineColor() )
        : new Color( 0x00FFFFFF & Grid.DEFAULT_GRID_COLOR ) );
      categoryPlot.setDomainGridlinesVisible( grid.getHorizontalLinesVisible() );
      categoryPlot.setDomainGridlinePaint( color );

      color = ( grid.getVerticalLineColor() != null ? new Color( 0x00FFFFFF & grid.getVerticalLineColor() )
        : new Color( 0x00FFFFFF & Grid.DEFAULT_GRID_COLOR ) );
      categoryPlot.setRangeGridlinesVisible( grid.getVerticalLinesVisible() );
      categoryPlot.setRangeGridlinePaint( color );
    }

    categoryPlot.setDomainGridlineStroke( new BasicStroke( 1 ) );
    categoryPlot.setRangeGridlineStroke( new BasicStroke( 1 ) );

    List<Integer> colors = getPlotColors( twoAxisPlot );

    for ( int j = 0; j < categoryPlot.getDatasetCount(); j++ ) {
      if ( linkGenerator != null ) {
        categoryPlot.getRenderer( j ).setBaseItemURLGenerator( new CategoryURLGenerator() {
          public String generateURL( CategoryDataset dataset, int series, int category ) {
            FormattableName seriesName = (FormattableName) dataset.getRowKey( series );
            FormattableName categoryName = (FormattableName) dataset.getColumnKey( category );
            return linkGenerator
              .generateLink( seriesName.getKey(), categoryName.getKey(), dataset.getValue( series, category ) );
          }
        } );
      }
      for ( int i = 0; i < colors.size(); i++ ) {
        categoryPlot.getRenderer( j ).setSeriesPaint( i, new Color( 0x00FFFFFF & colors.get( i ) ) );
      }
    }

    Font domainAxisFont = ChartUtils
      .getFont( twoAxisPlot.getDomainAxis().getFontFamily(), twoAxisPlot.getDomainAxis().getFontStyle(),
        twoAxisPlot.getDomainAxis().getFontWeight(), twoAxisPlot.getDomainAxis().getFontSize() );
    Font rangeAxisFont = ChartUtils
      .getFont( twoAxisPlot.getRangeAxis().getFontFamily(), twoAxisPlot.getRangeAxis().getFontStyle(),
        twoAxisPlot.getRangeAxis().getFontWeight(), twoAxisPlot.getRangeAxis().getFontSize() );
    Font rangeTitleFont = ChartUtils.getFont( twoAxisPlot.getRangeAxis().getLegend().getFontFamily(),
      twoAxisPlot.getRangeAxis().getLegend().getFontStyle(), twoAxisPlot.getRangeAxis().getLegend().getFontWeight(),
      twoAxisPlot.getRangeAxis().getLegend().getFontSize() );
    Font domainTitleFont = ChartUtils.getFont( twoAxisPlot.getDomainAxis().getLegend().getFontFamily(),
      twoAxisPlot.getDomainAxis().getLegend().getFontStyle(), twoAxisPlot.getDomainAxis().getLegend().getFontWeight(),
      twoAxisPlot.getDomainAxis().getLegend().getFontSize() );

    CategoryAxis domainAxis = categoryPlot.getDomainAxis();
    ValueAxis rangeAxis = categoryPlot.getRangeAxis();

    AxesLabels axesLabels = getAxesLabels( chartModel );
    if ( ( axesLabels.rangeAxisLabel.length() > 0 ) && ( rangeTitleFont != null ) ) {
      rangeAxis.setLabelFont( rangeTitleFont );
    }

    if ( ( axesLabels.domainAxisLabel.length() > 0 ) && ( domainTitleFont != null ) ) {
      domainAxis.setLabelFont( domainTitleFont );
    }

    LabelOrientation labelOrientation = twoAxisPlot.getHorizontalAxis().getLabelOrientation();
    if ( ( labelOrientation != null ) && ( labelOrientation != LabelOrientation.HORIZONTAL ) ) {
      if ( twoAxisPlot.getOrientation() == Orientation.HORIZONTAL ) {
        if ( labelOrientation == LabelOrientation.VERTICAL ) {
          rangeAxis.setVerticalTickLabels( true );
        }
      } else {
        switch ( labelOrientation ) {
          case VERTICAL:
            domainAxis.setCategoryLabelPositions( CategoryLabelPositions.UP_90 );
            break;
          case DIAGONAL:
            domainAxis.setCategoryLabelPositions( CategoryLabelPositions.UP_45 );
            break;
        }
      }
    }

    if ( domainAxisFont != null ) {
      domainAxis.setTickLabelFont( domainAxisFont );
    }
    if ( rangeAxisFont != null ) {
      rangeAxis.setTickLabelFont( rangeAxisFont );
    }

    Number rangeMin = twoAxisPlot.getRangeAxis().getMinValue();
    if ( rangeMin != null ) {
      rangeAxis.setLowerBound( rangeMin.doubleValue() );
    }
    Number rangeMax = twoAxisPlot.getRangeAxis().getMaxValue();
    if ( rangeMax != null ) {
      rangeAxis.setUpperBound( rangeMax.doubleValue() );
    }
  }

  private void initXYPlot( JFreeChart chart, ChartModel chartModel ) {
    initPlot( chart, chartModel );

    org.pentaho.chart.model.TwoAxisPlot twoAxisPlot = (org.pentaho.chart.model.TwoAxisPlot) chartModel.getPlot();
    XYPlot xyPlot = chart.getXYPlot();

    List<Integer> colors = getPlotColors( twoAxisPlot );

    for ( int i = 0; i < colors.size(); i++ ) {
      for ( int j = 0; j < xyPlot.getDatasetCount(); j++ ) {
        xyPlot.getRenderer( j ).setSeriesPaint( i, new Color( 0x00FFFFFF & colors.get( i ) ) );
      }
    }

    Font domainAxisFont = ChartUtils
      .getFont( twoAxisPlot.getDomainAxis().getFontFamily(), twoAxisPlot.getDomainAxis().getFontStyle(),
        twoAxisPlot.getDomainAxis().getFontWeight(), twoAxisPlot.getDomainAxis().getFontSize() );
    Font rangeAxisFont = ChartUtils
      .getFont( twoAxisPlot.getRangeAxis().getFontFamily(), twoAxisPlot.getRangeAxis().getFontStyle(),
        twoAxisPlot.getRangeAxis().getFontWeight(), twoAxisPlot.getRangeAxis().getFontSize() );
    Font rangeTitleFont = ChartUtils.getFont( twoAxisPlot.getRangeAxis().getLegend().getFontFamily(),
      twoAxisPlot.getRangeAxis().getLegend().getFontStyle(), twoAxisPlot.getRangeAxis().getLegend().getFontWeight(),
      twoAxisPlot.getRangeAxis().getLegend().getFontSize() );
    Font domainTitleFont = ChartUtils.getFont( twoAxisPlot.getDomainAxis().getLegend().getFontFamily(),
      twoAxisPlot.getDomainAxis().getLegend().getFontStyle(), twoAxisPlot.getDomainAxis().getLegend().getFontWeight(),
      twoAxisPlot.getDomainAxis().getLegend().getFontSize() );


    NumberAxis domainAxis = (NumberAxis) xyPlot.getDomainAxis();
    NumberAxis rangeAxis = (NumberAxis) xyPlot.getRangeAxis();

    domainAxis.setAutoRangeIncludesZero( true );
    rangeAxis.setAutoRangeIncludesZero( true );

    AxesLabels axesLabels = getAxesLabels( chartModel );
    if ( ( axesLabels.rangeAxisLabel.length() > 0 ) && ( rangeTitleFont != null ) ) {
      rangeAxis.setLabelFont( rangeTitleFont );
    }

    if ( ( axesLabels.domainAxisLabel.length() > 0 ) && ( domainTitleFont != null ) ) {
      domainAxis.setLabelFont( domainTitleFont );
    }

    domainAxis
      .setVerticalTickLabels( twoAxisPlot.getHorizontalAxis().getLabelOrientation() == LabelOrientation.VERTICAL );

    if ( domainAxisFont != null ) {
      domainAxis.setTickLabelFont( domainAxisFont );
    }
    if ( rangeAxisFont != null ) {
      rangeAxis.setTickLabelFont( rangeAxisFont );
    }

    Number rangeMin = ( (NumericAxis) twoAxisPlot.getRangeAxis() ).getMinValue();
    if ( rangeMin != null ) {
      rangeAxis.setLowerBound( rangeMin.doubleValue() );
    }
    Number rangeMax = ( (NumericAxis) twoAxisPlot.getRangeAxis() ).getMaxValue();
    if ( rangeMax != null ) {
      rangeAxis.setUpperBound( rangeMax.doubleValue() );
    }
  }

  private void initPlot( JFreeChart chart, ChartModel chartModel ) {
    Plot plot = chartModel.getPlot();

    if ( plot.getBackground() instanceof Integer ) {
      chart.getPlot().setBackgroundPaint( new Color( 0x00FFFFFF & (Integer) chartModel.getPlot().getBackground() ) );
    } else {
      chart.getPlot().setBackgroundPaint( Color.WHITE );
    }

    if ( plot.getOpacity() != null ) {
      chart.getPlot().setForegroundAlpha( chartModel.getPlot().getOpacity() );
    }


  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeBarChart(org.pentaho.chart.data.ChartTableModel,
   * org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeBarChart( ChartModel chartModel, MultiSeriesDataModel dataModel,
                                  IChartLinkGenerator linkGenerator ) {
    DefaultCategoryDataset categoryDataset = createCategoryDataset( dataModel );
    org.pentaho.chart.model.TwoAxisPlot twoAxisPlot = (org.pentaho.chart.model.TwoAxisPlot) chartModel.getPlot();

    String title = "";
    if ( ( chartModel.getTitle() != null ) && ( chartModel.getTitle().getText() != null ) && (
      chartModel.getTitle().getText().trim().length() > 0 ) ) {
      title = chartModel.getTitle().getText();
    }
    AxesLabels axesLabels = getAxesLabels( chartModel );
    PlotOrientation plotOrientation =
      ( twoAxisPlot.getOrientation() == Orientation.HORIZONTAL ) ? PlotOrientation.HORIZONTAL
        : PlotOrientation.VERTICAL;
    boolean showLegend = ( chartModel.getLegend() != null ) && ( chartModel.getLegend().getVisible() );
    JFreeChart chart = null;

    if ( BarPlotFlavor.THREED == ( (BarPlot) twoAxisPlot ).getFlavor() ) {
      chart = ChartFactory
        .createBarChart3D( title, axesLabels.domainAxisLabel, axesLabels.rangeAxisLabel, categoryDataset,
          plotOrientation, showLegend, true, false );
    } else if ( BarPlotFlavor.STACKED == ( (BarPlot) twoAxisPlot ).getFlavor() ) {
      chart = ChartFactory
        .createStackedBarChart( title, axesLabels.domainAxisLabel, axesLabels.rangeAxisLabel, categoryDataset,
          plotOrientation, showLegend, true, false );
    } else {
      chart = ChartFactory
        .createBarChart( title, axesLabels.domainAxisLabel, axesLabels.rangeAxisLabel, categoryDataset, plotOrientation,
          showLegend, true, false );
    }

    initCategoryPlot( chart, chartModel, linkGenerator );
    initChart( chart, chartModel );

    return chart;
  }

  public IOutput makeChart( final ChartTableModel data, final ChartDocumentContext chartDocumentContext,
                            final ChartResult chartResult ) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final CSSConstant currentChartType = determineChartType( chartDocument );
    if ( currentChartType == ChartSeriesType.UNDEFINED ) {
      chartResult.setErrorCode( IChartPlugin.ERROR_INDETERMINATE_CHART_TYPE );
      chartResult.setDescription(
        Messages.getErrorString( "JFreeChartPlugin.ERROR_0001_CHART_TYPE_INDETERMINABLE" ) ); //$NON-NLS-1$
    }

    if ( currentChartType == ChartSeriesType.BAR ) {
      try {
        final JFreeChart chart = makeBarChart( data, chartDocumentContext );
        return new JFreeChartOutput( chart );
      } catch ( Exception e ) {
        logger.error( "", e ); //$NON-NLS-1$
        chartResult.setErrorCode( IChartPlugin.RESULT_ERROR );
        chartResult.setDescription( e.getLocalizedMessage() );
      }
    } else if ( currentChartType == ChartSeriesType.LINE ) {
      try {
        return new JFreeChartOutput( makeLineChart( data, chartDocumentContext ) );
      } catch ( Exception e ) {
        logger.error( "", e ); //$NON-NLS-1$
        chartResult.setErrorCode( IChartPlugin.RESULT_ERROR );
        chartResult.setDescription( e.getLocalizedMessage() );
      }
    } else if ( currentChartType == ChartSeriesType.AREA ) {
      try {
        return new JFreeChartOutput( ( makeAreaChart( data, chartDocumentContext ) ) );
      } catch ( Exception e ) {
        logger.error( "", e ); //$NON-NLS-1$
        chartResult.setErrorCode( IChartPlugin.RESULT_ERROR );
        chartResult.setDescription( e.getLocalizedMessage() );
      }
    } else if ( currentChartType == ChartSeriesType.PIE ) {
      try {
        return new JFreeChartOutput( ( makePieChart( data, chartDocumentContext ) ) );
      } catch ( Exception e ) {
        logger.error( "", e ); //$NON-NLS-1$
        chartResult.setErrorCode( IChartPlugin.RESULT_ERROR );
        chartResult.setDescription( e.getLocalizedMessage() );
      }
    } else if ( currentChartType == ChartSeriesType.MULTI ) {
      try {
        return new JFreeChartOutput( ( makeMultiChart( data, chartDocumentContext ) ) );
      } catch ( Exception e ) {
        logger.error( "", e ); //$NON-NLS-1$
        chartResult.setErrorCode( IChartPlugin.RESULT_ERROR );
        chartResult.setDescription( e.getLocalizedMessage() );
      }
    } else if ( currentChartType == ChartSeriesType.DIAL ) {
      try {
        return new JFreeChartOutput( ( makeDialChart( data, chartDocumentContext ) ) );
      } catch ( Exception e ) {
        logger.error( "", e ); //$NON-NLS-1$
        chartResult.setErrorCode( IChartPlugin.RESULT_ERROR );
        chartResult.setDescription( e.getLocalizedMessage() );
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.chart.data
   * .ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeAreaChart( final ChartTableModel data, final ChartDocumentContext chartDocumentContext ) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createAreaChartSubtype( chartDocumentContext, data );
    JFreeChartUtils.setPlotAttributes( chart.getCategoryPlot(), chartDocument );
    return chart;
  }

  private JFreeChart createAreaChartSubtype( final ChartDocumentContext chartDocumentContext,
                                             final ChartTableModel data ) {
    final JFreeAreaChartGeneratorFactory chartFacEngine = new JFreeAreaChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart( chartDocumentContext, data );
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.chart.data
   * .ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makePieChart( final ChartTableModel data, final ChartDocumentContext chartDocumentContext ) {
    final JFreePieChartGeneratorFactory chartFacEngine = new JFreePieChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart( chartDocumentContext, data );
    return chart;
  }

  public JFreeChart makeDialChart( final ChartTableModel data, final ChartDocumentContext chartDocumentContext ) {
    final JFreeDialChartGeneratorFactory chartFacEngine = new JFreeDialChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart( chartDocumentContext, data );
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeBarChart(org.pentaho.chart.data.ChartTableModel,
   * org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeBarChart( final ChartTableModel data, final ChartDocumentContext chartDocumentContext )
    throws Exception {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createBarChartSubtype( chartDocumentContext, data );
    JFreeChartUtils.setPlotAttributes( chart.getCategoryPlot(), chartDocument );

    return chart;
  }

  private JFreeChart createBarChartSubtype( final ChartDocumentContext chartDocumentContext,
                                            final ChartTableModel data ) {
    final JFreeBarChartGeneratorFactory chartFacEngine = new JFreeBarChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart( chartDocumentContext, data );
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.chart.data
   * .ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeLineChart( final ChartTableModel data, final ChartDocumentContext chartDocumentContext )
    throws Exception {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createLineChartSubtype( chartDocumentContext, data );
    JFreeChartUtils.setPlotAttributes( chart.getCategoryPlot(), chartDocument );

    return chart;
  }

  /**
   * @param chartDocumentContext
   * @param data
   * @return the newly created JFreeChart object
   */
  private JFreeChart createLineChartSubtype( ChartDocumentContext chartDocumentContext, ChartTableModel data ) {
    final JFreeLineChartGeneratorFactory chartFacEngine = new JFreeLineChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart( chartDocumentContext, data );
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.chart.data
   * .ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeMultiChart( final ChartTableModel data, final ChartDocumentContext chartDocumentContext ) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createMultiChartSubtype( chartDocumentContext, data );
    JFreeChartUtils.setPlotAttributes( chart.getCategoryPlot(), chartDocument );
    return chart;
  }

  private JFreeChart createMultiChartSubtype( final ChartDocumentContext chartDocumentContext,
                                              final ChartTableModel data ) {
    final JFreeMultiChartGeneratorFactory chartFacEngine = new JFreeMultiChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart( chartDocumentContext, data );
    return chart;
  }

  /**
   * Determines what type of chart that should be rendered.  It is possible that this method
   * could somehow be moved up into the AbstractChartPlugin
   *
   * @param chartDocument that defines what type of chart to use
   * @return a ChartType that represents the type of chart the chartDocument is requesting.
   */
  public CSSConstant determineChartType( final ChartDocument chartDocument ) {
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName( ChartElement.TAG_NAME_SERIES );
    for ( final ChartElement element : elements ) {
      final CSSValue value = element.getLayoutStyle().getValue( ChartStyleKeys.CHART_TYPE );
      if ( value != null ) {
        if ( value.equals( ChartSeriesType.BAR ) ) {
          return ChartSeriesType.BAR;
        } else if ( value.equals( ChartSeriesType.LINE ) ) {
          return ChartSeriesType.LINE;
        } else if ( value.equals( ChartSeriesType.AREA ) ) {
          return ChartSeriesType.AREA;
        } else if ( value.equals( ChartSeriesType.PIE ) ) {
          return ChartSeriesType.PIE;
        } else if ( value.equals( ChartSeriesType.MULTI ) ) {
          return ChartSeriesType.MULTI;
        } else if ( value.equals( ChartSeriesType.DIAL ) ) {
          return ChartSeriesType.DIAL;
        }
      }
    }
    return ChartSeriesType.UNDEFINED;
  }

  protected Stroke getLineStyleStroke( LinePlotFlavor flavor, Integer lineWidth ) {

    BasicStroke stroke = null;
    float[] strokeSteps = null;

    // Negative linewidths not allowed; reset to default;
    if ( ( lineWidth == null ) || ( lineWidth <= 0 ) ) {
      lineWidth = 1;
    }

    if ( flavor != null ) {
      switch ( flavor ) {
        case DOT:
          strokeSteps = new float[] { 2.0f, 6.0f };
          break;
        case DASH:
          strokeSteps = new float[] { 6.0f, 6.0f };
          break;
        case DASHDOT:
          strokeSteps = new float[] { 10.0f, 6.0f, 2.0f, 6.0f };
          break;
        case DASHDOTDOT:
          strokeSteps = new float[] { 10.0f, 6.0f, 2.0f, 6.0f, 2.0f, 6.0f };
          break;
      }
    }

    if ( strokeSteps != null ) {
      stroke = new BasicStroke( lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, strokeSteps, 0.0f );
    } else {
      stroke = new BasicStroke( lineWidth );
    }
    return stroke;
  }

  protected Number scaleNumber( Number number, Number scale ) {
    Number scaledNumber = number;
    if ( ( number != null ) && ( scale != null ) && !scale.equals( 1 ) && !scale.equals( 0 ) ) {

      int startingSignificantDigits = 0;
      if ( !( number instanceof Integer ) ) {
        int indexOfDecimalPoint = number.toString().indexOf( "." );
        if ( indexOfDecimalPoint >= 0 ) {
          String fractionalPart = number.toString().substring( indexOfDecimalPoint + 1 );
          if ( ( fractionalPart.length() > 1 ) || Integer.parseInt( fractionalPart ) > 0 ) {
            startingSignificantDigits = fractionalPart.length();
          }
        }
      }

      int preferredSignificantDigits = Math.max( 2, Math.min( startingSignificantDigits, 6 ) );

      scaledNumber = number.doubleValue() / scale.doubleValue();
      int scaledSignificantDigits = 0;
      int indexOfDecimalPoint = scaledNumber.toString().indexOf( "." );
      String fractionalPart = scaledNumber.toString().substring( indexOfDecimalPoint + 1 );
      if ( ( fractionalPart.length() > 1 ) || Integer.parseInt( fractionalPart ) > 0 ) {
        scaledSignificantDigits = fractionalPart.length();
      }

      if ( scaledSignificantDigits > preferredSignificantDigits ) {
        double multiplier = Math.pow( 10, preferredSignificantDigits );
        scaledNumber = Math.round( scaledNumber.doubleValue() * multiplier ) / multiplier;
      }
    }
    return scaledNumber;
  }
}
