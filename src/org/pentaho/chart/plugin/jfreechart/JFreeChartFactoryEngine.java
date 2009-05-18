package org.pentaho.chart.plugin.jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartUtils;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartSeriesType;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.model.AreaPlot;
import org.pentaho.chart.model.BarPlot;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.DialPlot;
import org.pentaho.chart.model.GraphPlot;
import org.pentaho.chart.model.LinePlot;
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

  private static final Log logger = LogFactory.getLog(JFreeChartFactoryEngine.class);
  
  private static final long serialVersionUID = -1079376910255750394L;

  public JFreeChartFactoryEngine(){
  }

  public IOutput makeChart(ChartModel chartModel, ChartTableModel chartTableModel) {
    IOutput chartOutput = null;
    if (chartModel.getPlot() instanceof BarPlot) {
      chartOutput = new JFreeChartOutput(makeBarChart(chartModel, chartTableModel));
    } else if (chartModel.getPlot() instanceof LinePlot) {
      chartOutput = new JFreeChartOutput(makeLineChart(chartModel, chartTableModel));
    } else if (chartModel.getPlot() instanceof AreaPlot) {
      chartOutput = new JFreeChartOutput(makeAreaChart(chartModel, chartTableModel));
    } else if (chartModel.getPlot() instanceof DialPlot) {
      chartOutput = new JFreeChartOutput(makeDialChart(chartModel, chartTableModel));
    } else if (chartModel.getPlot() instanceof org.pentaho.chart.model.PiePlot) {
      chartOutput = new JFreeChartOutput(makePieChart(chartModel, chartTableModel));
    }
    return chartOutput;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  protected JFreeChart makePieChart(ChartModel chartModel, ChartTableModel data) {
    final DefaultPieDataset dataset = new DefaultPieDataset();
    for (int row = 0; row < data.getRowCount(); row++) {
      if (data.getValueAt(row, 0) instanceof Number) {
        final Number number = (Number) data.getValueAt(row, 0);
        dataset.setValue(data.getRowName(row), number);
      }
    }

    boolean showLegend = (chartModel.getLegend() != null) && (chartModel.getLegend().getVisible());
    
    String title = "";
    if ((chartModel.getTitle() != null) && (chartModel.getTitle().getText() != null)
        && (chartModel.getTitle().getText().trim().length() > 0)) {
      title = chartModel.getTitle().getText();
    }
    
    final JFreeChart chart = ChartFactory.createPieChart(title, dataset, showLegend, true, false);
    initChart(chart, chartModel);
    
    final PiePlot jFreePiePlot = (PiePlot)chart.getPlot();
    
    jFreePiePlot.setNoDataMessage("No data available"); //$NON-NLS-1$
    jFreePiePlot.setCircular(true);
    jFreePiePlot.setLabelGap(0.02);
    
    org.pentaho.chart.model.PiePlot chartBeansPiePlot = (org.pentaho.chart.model.PiePlot)chartModel.getPlot();
    
    ArrayList<Integer> colors = new ArrayList<Integer>();
    if (chartBeansPiePlot.getPalette() != null) {
      colors.addAll(chartBeansPiePlot.getPalette());
    }    
    ArrayList<Integer> defaultColors = new ArrayList<Integer>(org.pentaho.chart.model.Plot.DEFAULT_PALETTE);
    defaultColors.removeAll(colors);
    colors.addAll(defaultColors);

    for (int i = 0; i < data.getRowCount(); i++) {
      final Comparable rowName = data.getRowName(i);
      if ((rowName != null) && (i < colors.size())) {
        jFreePiePlot.setSectionPaint(rowName, new Color(0x00FFFFFF & colors.get(i)));
      }
    }
    
    if (chartBeansPiePlot.getLabels().getVisible()) {
      jFreePiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator());
      
      Font font = ChartUtils.getFont(chartBeansPiePlot.getLabels().getFontFamily(), chartBeansPiePlot.getLabels().getFontStyle(), chartBeansPiePlot.getLabels().getFontWeight(), chartBeansPiePlot.getLabels().getFontSize());
      if (font != null) {
        jFreePiePlot.setLabelFont(font);
        if (chartBeansPiePlot.getLabels().getColor() != null) {
          jFreePiePlot.setLabelPaint(new Color(0x00FFFFFF & chartBeansPiePlot.getLabels().getColor()));
        }
        if (chartBeansPiePlot.getLabels().getBackgroundColor() != null) {
          jFreePiePlot.setLabelBackgroundPaint(new Color(0x00FFFFFF & chartBeansPiePlot.getLabels().getBackgroundColor()));
        }
      }
    } else {
      jFreePiePlot.setLabelGenerator(null);
    }
    
    jFreePiePlot.setStartAngle(chartBeansPiePlot.getStartAngle());
    
    
    return chart;
  }
  
  protected JFreeChart makeDialChart(ChartModel chartModel, ChartTableModel data) {
    DialPlot chartBeansDialPlot = (DialPlot)chartModel.getPlot();

    org.jfree.chart.plot.dial.DialPlot jFreeDialPlot = new SquareDialPlot();

    final DefaultValueDataset dataset = new DefaultValueDataset();
    dataset.setValue((Number) data.getValueAt(0, 0));

    jFreeDialPlot.setDataset(dataset);

    final DoubleLineDialFrame dialFrame = new DoubleLineDialFrame();
    dialFrame.setForegroundPaint(new Color(0x8d8d8d));
    dialFrame.setInnerForegroundPaint(new Color(0x5d5d5d));
    dialFrame.setStroke(new BasicStroke(2));
    dialFrame.setBackgroundPaint(Color.WHITE);
    jFreeDialPlot.setDialFrame(dialFrame);

    for (DialRange dialRange : chartBeansDialPlot.getScale()) {
      if (dialRange.getColor() != null) {
        SingleLineDialRange standarddialrange = new SingleLineDialRange(dialRange.getMinValue().doubleValue(), dialRange.getMaxValue().doubleValue(), new Color(0x00FFFFFF & dialRange.getColor()));
        standarddialrange.setInnerRadius(0.4D);
        jFreeDialPlot.addLayer(standarddialrange);
      }
    }

    double scaleMajorTickIncrement = (chartBeansDialPlot.getScale().getMaxValue().doubleValue() - chartBeansDialPlot.getScale().getMinValue().doubleValue()) / 5;
    FixedStandardDialScale standardDialScale = new FixedStandardDialScale(chartBeansDialPlot.getScale().getMinValue().doubleValue(), chartBeansDialPlot.getScale().getMaxValue().doubleValue(),
        -150.0, -240.0, scaleMajorTickIncrement, 2);
    standardDialScale.setTickRadius(0.88D);
    standardDialScale.setTickLabelOffset(0.15D);
    standardDialScale.setTickLabelFont(ChartUtils.getFont("sans-serif", FontStyle.NORMAL, FontWeight.NORMAL, 10));
    standardDialScale.setTickLabelPaint(Color.BLACK);
    standardDialScale.setMajorTickLength(0.04);
    standardDialScale.setMajorTickPaint(Color.BLACK);
    standardDialScale.setMajorTickStroke(new BasicStroke(2));
    standardDialScale.setMinorTickLength(0.02);
    standardDialScale.setMinorTickPaint(new Color(0x8b8b8b));
    standardDialScale.setMinorTickStroke(new BasicStroke(1));
    jFreeDialPlot.addScale(0, standardDialScale);

    DialCap dialCap = new DialCap();
    dialCap.setRadius(0.06);
    dialCap.setFillPaint(new Color(0x636363));
    dialCap.setOutlinePaint(new Color(0x5d5d5d));
    dialCap.setOutlineStroke(new BasicStroke(2));
    jFreeDialPlot.setCap(dialCap);

    GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(0xfcfcfc), new Point(), new Color(0xd7d8da));
    DialBackground dialbackground = new DialBackground(gradientpaint); // specify Color here for no gradient
    dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
    jFreeDialPlot.setBackground(dialbackground);

    VariableStrokePointer pointer = new VariableStrokePointer();
    pointer.setRadius(0.9);
    pointer.setOutlineStroke(new BasicStroke(2));
    pointer.setWidthRadius(0.05);
    pointer.setFillPaint(new Color(0x636363));
    pointer.setOutlinePaint(new Color(0x5d5d5d));
    jFreeDialPlot.addPointer(pointer);

    DialValueIndicator dialValueIndicator = new DialValueIndicator(0);
    dialValueIndicator.setTemplateValue(chartBeansDialPlot.getScale().getMaxValue());   
    dialValueIndicator.setFont(ChartUtils.getFont("Dialog", FontStyle.NORMAL, FontWeight.BOLD, 10));
    dialValueIndicator.setPaint(Color.BLACK);
    dialValueIndicator.setBackgroundPaint(Color.WHITE);
    dialValueIndicator.setOutlineStroke(new BasicStroke(1));
    dialValueIndicator.setOutlinePaint(new Color(0x8b8b8b));
    jFreeDialPlot.addLayer(dialValueIndicator);
    
    if ((chartBeansDialPlot.getAnnotation() != null) && (chartBeansDialPlot.getAnnotation().getText() != null) && (chartBeansDialPlot.getAnnotation().getText().trim().length() > 0)) {
      Font font = ChartUtils.getFont(chartBeansDialPlot.getAnnotation().getFontFamily(), chartBeansDialPlot.getAnnotation().getFontStyle(), chartBeansDialPlot.getAnnotation().getFontWeight(), chartBeansDialPlot.getAnnotation().getFontSize());
      if (font == null) {
        font = ChartUtils.getFont("sans-serif", FontStyle.NORMAL, FontWeight.NORMAL, 10);
      }
      DialTextAnnotation dialTextAnnotation = new DialTextAnnotation(chartBeansDialPlot.getAnnotation().getText().trim());
      dialTextAnnotation.setFont(font);
      dialTextAnnotation.setRadius(0.6);
      jFreeDialPlot.addLayer(dialTextAnnotation);
    }
    
    String title = "";
    if ((chartModel.getTitle() != null) && (chartModel.getTitle().getText() != null)
        && (chartModel.getTitle().getText().trim().length() > 0)) {
      title = chartModel.getTitle().getText();
    }
    
    JFreeChart chart = new JFreeChart(title, jFreeDialPlot);
    initChart(chart, chartModel);
    
    return chart;
  }
  
  protected void initChart(JFreeChart chart, ChartModel chartModel) {
    if (chartModel.getBackground() instanceof Integer) {
      chart.setBackgroundPaint(new Color(0x00FFFFFF & (Integer)chartModel.getBackground()));
    } else {
      chart.setBackgroundPaint(Color.WHITE);
    }
    
    if (chartModel.getPlot().getBackground() instanceof Integer) {
      chart.getPlot().setBackgroundPaint(new Color(0x00FFFFFF & (Integer)chartModel.getPlot().getBackground()));
    } else {
      chart.getPlot().setBackgroundPaint(Color.WHITE);
    }

    if (chartModel.getPlot().getOpacity() != null) {
      chart.getPlot().setForegroundAlpha(chartModel.getPlot().getOpacity());
    }
    
    if ((chartModel.getTitle() != null) && (chartModel.getTitle().getText() != null) && (chartModel.getTitle().getText().trim().length() > 0)) {
      Font font = ChartUtils.getFont(chartModel.getTitle().getFontFamily(), chartModel.getTitle().getFontStyle(), chartModel.getTitle().getFontWeight(), chartModel.getTitle().getFontSize());
      if (font != null) {
        chart.getTitle().setFont(font);
      }
      
      RectangleEdge rectangleEdge = RectangleEdge.TOP;
      if (chartModel.getTitle().getLocation() != null) {
        switch (chartModel.getTitle().getLocation()) {
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
      
      chart.getTitle().setPosition(rectangleEdge);
      if (RectangleEdge.isTopOrBottom(rectangleEdge)) {
        HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;
        if (chartModel.getTitle().getAlignment() != null) {
          switch (chartModel.getTitle().getAlignment()) {
            case LEFT:
              horizontalAlignment = horizontalAlignment.LEFT;
              break;
            case RIGHT:
              horizontalAlignment = horizontalAlignment.RIGHT;
              break;
          }
        }
        chart.getTitle().setHorizontalAlignment(horizontalAlignment);
      }
    }
    
    if ((chartModel.getLegend() != null) && chartModel.getLegend().getVisible()) {
      Font font = ChartUtils.getFont(chartModel.getLegend().getFontFamily(), chartModel.getLegend().getFontStyle(), chartModel.getLegend().getFontWeight(), chartModel.getLegend().getFontSize());
      if (font != null) {
        chart.getLegend().setItemFont(font);
      }
      if (!chartModel.getLegend().getBorderVisible()) {
        chart.getLegend().setFrame(BlockBorder.NONE);
      }
    }
    
    chart.setBorderVisible(chartModel.getBorderVisible());
    
    if (chartModel.getBorderColor() instanceof Integer) {
      chart.setBorderPaint(new Color(0x00FFFFFF & (Integer)chartModel.getBorderColor()));
    }
    
    for (StyledText subtitle : chartModel.getSubtitles()) {
      if ((subtitle.getText()) != null && (subtitle.getText().trim().length() > 0)) {
        TextTitle textTitle = new TextTitle(subtitle.getText());
        Font font = ChartUtils.getFont(subtitle.getFontFamily(), subtitle.getFontStyle(), subtitle.getFontWeight(), subtitle.getFontSize());
        if (font != null) {
          textTitle.setFont(font);
          if (subtitle.getColor() != null) {
            textTitle.setPaint(new Color(0x00FFFFFF & subtitle.getColor()));
          }
          if (subtitle.getBackgroundColor() != null) {
            textTitle.setBackgroundPaint(new Color(0x00FFFFFF & subtitle.getBackgroundColor()));
          }
        }
        chart.addSubtitle(textTitle);
      }
    }
    
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  protected JFreeChart makeAreaChart(ChartModel chartModel, ChartTableModel data) {
    return makeGraphChart(chartModel, data);
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  protected JFreeChart makeLineChart(ChartModel chartModel, ChartTableModel data) {
    return makeGraphChart(chartModel, data);
  }
  
  protected DefaultCategoryDataset createCategoryDataset(ChartTableModel data) {
    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    for (int row = 0; row < data.getRowCount(); row++) {
      for (int column = 0; column < data.getColumnCount(); column++) {
        final Object rawValue = data.getValueAt(row, column);
        if (rawValue instanceof Number) {
          final Number number = (Number) rawValue;
          categoryDataset.setValue(number, data.getRowName(row), data.getColumnName(column));
        }
      }
    }
    return categoryDataset;
  }
  
  protected JFreeChart makeGraphChart(ChartModel chartModel, ChartTableModel data) {
    DefaultCategoryDataset categoryDataset = createCategoryDataset(data);

    GraphPlot graphPlot = (GraphPlot)chartModel.getPlot();
    
    PlotOrientation plotOrientation = (graphPlot.getOrientation() == Orientation.HORIZONTAL) ? PlotOrientation.HORIZONTAL : PlotOrientation.VERTICAL;
    String title = "";
    if ((chartModel.getTitle() != null) && (chartModel.getTitle().getText() != null) && (chartModel.getTitle().getText().trim().length() > 0)) {
      title = chartModel.getTitle().getText();
    }
    
    String domainAxisLabel = "";
    String rangeAxisLabel = "";
    
    if ((graphPlot.getXAxis() != null) 
        && (graphPlot.getXAxis().getLegend() != null) 
        && (graphPlot.getXAxis().getLegend().getText() != null)
        && (graphPlot.getXAxis().getLegend().getText().trim().length() > 0)) {
      if (graphPlot.getOrientation() == Orientation.HORIZONTAL) {
        rangeAxisLabel = graphPlot.getXAxis().getLegend().getText();
      } else {
        domainAxisLabel = graphPlot.getXAxis().getLegend().getText();
      }
    }
    
    if ((graphPlot.getYAxis() != null) 
        && (graphPlot.getYAxis().getLegend() != null) 
        && (graphPlot.getYAxis().getLegend().getText() != null)
        && (graphPlot.getYAxis().getLegend().getText().trim().length() > 0)) {
      if (graphPlot.getOrientation() == Orientation.HORIZONTAL) {
        domainAxisLabel = graphPlot.getYAxis().getLegend().getText();
      } else {
        rangeAxisLabel = graphPlot.getYAxis().getLegend().getText();
      }
    }
    
    boolean showLegend = (chartModel.getLegend() != null) && (chartModel.getLegend().getVisible());
    JFreeChart chart = null;
    if (chartModel.getPlot() instanceof BarPlot) {
      if (BarPlotFlavor.THREED == ((BarPlot)graphPlot).getFlavor()) {
        chart = ChartFactory.createBarChart3D(title, domainAxisLabel, rangeAxisLabel, categoryDataset, plotOrientation, showLegend, true, false);
      } else {
        chart = ChartFactory.createBarChart(title, domainAxisLabel, rangeAxisLabel, categoryDataset, plotOrientation, showLegend, true, false);
      }
    } else if (chartModel.getPlot() instanceof LinePlot) {
      if (((LinePlot)graphPlot).getFlavor() == LinePlotFlavor.THREED) {
        chart = ChartFactory.createLineChart3D(title, domainAxisLabel, rangeAxisLabel, categoryDataset, plotOrientation,
            showLegend, true, false);
      } else {
        chart = ChartFactory.createLineChart(title, domainAxisLabel, rangeAxisLabel, categoryDataset, plotOrientation,
            showLegend, true, false);
      }
    } else if (chartModel.getPlot() instanceof AreaPlot) {
      chart = ChartFactory.createAreaChart(title, domainAxisLabel, rangeAxisLabel, categoryDataset, plotOrientation,
          showLegend, true, false);
    }

    final CategoryPlot categoryPlot = chart.getCategoryPlot();
    
    ArrayList<Integer> colors = new ArrayList<Integer>();
    if (graphPlot.getPalette() != null) {
      colors.addAll(graphPlot.getPalette());
    }    
    ArrayList<Integer> defaultColors = new ArrayList<Integer>(org.pentaho.chart.model.Plot.DEFAULT_PALETTE);
    defaultColors.removeAll(colors);
    colors.addAll(defaultColors);
    
    for (int i = 0; i < colors.size(); i++) {
      for (int j = 0; j < categoryPlot.getDatasetCount(); j++) {
        categoryPlot.getRenderer(j).setSeriesPaint(i, new Color(0x00FFFFFF & colors.get(i)));
      }
    }
    
    initChart(chart, chartModel);
    
    CategoryAxis domainAxis = categoryPlot.getDomainAxis();
    ValueAxis valueAxis = categoryPlot.getRangeAxis();
    
    if (rangeAxisLabel.length() > 0) {
      Font font = null;
      if (graphPlot.getOrientation() == Orientation.HORIZONTAL) {
        font = ChartUtils.getFont(graphPlot.getXAxis().getLegend().getFontFamily(), graphPlot.getXAxis().getLegend().getFontStyle(), graphPlot.getXAxis().getLegend().getFontWeight(), graphPlot.getXAxis().getLegend().getFontSize());
      } else {
        font = ChartUtils.getFont(graphPlot.getYAxis().getLegend().getFontFamily(), graphPlot.getYAxis().getLegend().getFontStyle(), graphPlot.getYAxis().getLegend().getFontWeight(), graphPlot.getYAxis().getLegend().getFontSize());
      }
      if (font != null) {
        categoryPlot.getRangeAxis().setLabelFont(font);
      }
    }
    
    if (domainAxisLabel.length() > 0) {
      Font font = null;
      if (graphPlot.getOrientation() == Orientation.HORIZONTAL) {
        font = ChartUtils.getFont(graphPlot.getYAxis().getLegend().getFontFamily(), graphPlot.getYAxis().getLegend().getFontStyle(), graphPlot.getYAxis().getLegend().getFontWeight(), graphPlot.getYAxis().getLegend().getFontSize());
      } else {
        font = ChartUtils.getFont(graphPlot.getXAxis().getLegend().getFontFamily(), graphPlot.getXAxis().getLegend().getFontStyle(), graphPlot.getXAxis().getLegend().getFontWeight(), graphPlot.getXAxis().getLegend().getFontSize());
      }
      
      if (font != null) {
        domainAxis.setLabelFont(font);
      }      
    }
    
    LabelOrientation labelOrientation = graphPlot.getXAxis().getLabelOrientation();
    if ((labelOrientation != null) && (labelOrientation != LabelOrientation.HORIZONTAL)) {
      if (graphPlot.getOrientation() == Orientation.HORIZONTAL) {
        if (labelOrientation == LabelOrientation.VERTICAL) {
          valueAxis.setVerticalTickLabels(true);
        }
      } else {
        switch (labelOrientation) {
          case VERTICAL:
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
            break;
          case DIAGONAL:
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            break;
        }
      }
    }

    Font yAxisFont = ChartUtils.getFont(graphPlot.getYAxis().getFontFamily(), graphPlot.getYAxis().getFontStyle(), graphPlot.getYAxis().getFontWeight(), graphPlot.getYAxis().getFontSize());
    Font xAxisFont = ChartUtils.getFont(graphPlot.getXAxis().getFontFamily(), graphPlot.getXAxis().getFontStyle(), graphPlot.getXAxis().getFontWeight(), graphPlot.getXAxis().getFontSize());
    Font domainAxisFont = null;
    Font rangeAxisFont = null;
    if (graphPlot.getOrientation() == Orientation.HORIZONTAL) {
      rangeAxisFont = xAxisFont;
      domainAxisFont = yAxisFont;
    } else {
      domainAxisFont = xAxisFont;
      rangeAxisFont = yAxisFont;
    }
    if (domainAxisFont != null) {
      domainAxis.setTickLabelFont(domainAxisFont);
    }
    if (rangeAxisFont != null) {
      valueAxis.setTickLabelFont(rangeAxisFont);
    }
    return chart;
  }
  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeBarChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  protected JFreeChart makeBarChart(ChartModel chartModel, ChartTableModel data) {
    return makeGraphChart(chartModel, data);
  }

  public IOutput makeChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext, final ChartResult chartResult) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final CSSConstant currentChartType = determineChartType(chartDocument);
    if (currentChartType == ChartSeriesType.UNDEFINED) {
      chartResult.setErrorCode(IChartPlugin.ERROR_INDETERMINATE_CHART_TYPE);
      chartResult.setDescription(Messages.getErrorString("JFreeChartPlugin.ERROR_0001_CHART_TYPE_INDETERMINABLE")); //$NON-NLS-1$
    }

    if (currentChartType == ChartSeriesType.BAR) {
      try {
        final JFreeChart chart = makeBarChart(data, chartDocumentContext);
        return new JFreeChartOutput(chart);
      } catch (Exception e) {
        logger.error("", e); //$NON-NLS-1$
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.LINE) {
      try {
        return new JFreeChartOutput(makeLineChart(data, chartDocumentContext));
      } catch (Exception e) {
        logger.error("", e); //$NON-NLS-1$
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.AREA) {
      try {
        return new JFreeChartOutput((makeAreaChart(data, chartDocumentContext)));
      } catch (Exception e) {
        logger.error("", e); //$NON-NLS-1$
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.PIE) {
      try {
        return new JFreeChartOutput((makePieChart(data, chartDocumentContext)));
      } catch (Exception e) {
        logger.error("", e); //$NON-NLS-1$
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.MULTI) {
      try {
        return new JFreeChartOutput((makeMultiChart(data, chartDocumentContext)));
      } catch (Exception e) {
        logger.error("", e); //$NON-NLS-1$
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    } else if (currentChartType == ChartSeriesType.DIAL) {
      try {
        return new JFreeChartOutput((makeDialChart(data, chartDocumentContext)));
      } catch (Exception e) {
        logger.error("", e); //$NON-NLS-1$
        chartResult.setErrorCode(IChartPlugin.RESULT_ERROR);
        chartResult.setDescription(e.getLocalizedMessage());
      }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeAreaChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createAreaChartSubtype(chartDocumentContext, data);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument);
    return chart;
  }

  private JFreeChart createAreaChartSubtype(final ChartDocumentContext chartDocumentContext, final ChartTableModel data) {
    final JFreeAreaChartGeneratorFactory chartFacEngine = new JFreeAreaChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makePieChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    final JFreePieChartGeneratorFactory chartFacEngine = new JFreePieChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }

  public JFreeChart makeDialChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    final JFreeDialChartGeneratorFactory chartFacEngine = new JFreeDialChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeBarChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeBarChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext)
      throws Exception {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createBarChartSubtype(chartDocumentContext, data);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument);

    return chart;
  }

  private JFreeChart createBarChartSubtype(final ChartDocumentContext chartDocumentContext, final ChartTableModel data) {
    final JFreeBarChartGeneratorFactory chartFacEngine = new JFreeBarChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeLineChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeLineChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext)
      throws Exception {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createLineChartSubtype(chartDocumentContext, data);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument);
    
    return chart;
  }

 /**
   * @param chartDocumentContext
   * @param data
   * @return the newly created JFreeChart object
   */
  private JFreeChart createLineChartSubtype(ChartDocumentContext chartDocumentContext, ChartTableModel data) {
    final JFreeLineChartGeneratorFactory chartFacEngine = new JFreeLineChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.chart.plugin.api.engine.ChartFactoryEngine#makeAreaChart(org.pentaho.chart.data.ChartTableModel, org.pentaho.chart.core.ChartDocument, org.pentaho.chart.plugin.api.IOutput)
   */
  public JFreeChart makeMultiChart(final ChartTableModel data, final ChartDocumentContext chartDocumentContext) {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final JFreeChart chart = createMultiChartSubtype(chartDocumentContext, data);
    JFreeChartUtils.setPlotAttributes(chart.getCategoryPlot(), chartDocument);
    return chart;
  }

  private JFreeChart createMultiChartSubtype(final ChartDocumentContext chartDocumentContext, final ChartTableModel data) {
    final JFreeMultiChartGeneratorFactory chartFacEngine = new JFreeMultiChartGeneratorFactory();
    final JFreeChart chart = chartFacEngine.createChart(chartDocumentContext, data);
    return chart;
  }
  /**
   * Determines what type of chart that should be rendered.  It is possible that this method
   * could somehow be moved up into the AbstractChartPlugin
   *
   * @param chartDocument that defines what type of chart to use
   * @return a ChartType that represents the type of chart the chartDocument is requesting.
   */
  public CSSConstant determineChartType(final ChartDocument chartDocument) {
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    for (final ChartElement element : elements) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.CHART_TYPE);
      if (value != null) {
        if (value.equals(ChartSeriesType.BAR)) {
          return ChartSeriesType.BAR;
        } else if (value.equals(ChartSeriesType.LINE)) {
          return ChartSeriesType.LINE;
        } else if (value.equals(ChartSeriesType.AREA)) {
          return ChartSeriesType.AREA;
        } else if (value.equals(ChartSeriesType.PIE)) {
          return ChartSeriesType.PIE;
        } else if (value.equals(ChartSeriesType.MULTI)) {
          return ChartSeriesType.MULTI;
        } else if (value.equals(ChartSeriesType.DIAL)) {
          return ChartSeriesType.DIAL;
        }
      }
    }
    return ChartSeriesType.UNDEFINED;
  }
}
