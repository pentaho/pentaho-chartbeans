/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * @created Mar 25, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin.jfreechart.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartCSSFontSizeMappingConstants;
import org.pentaho.experimental.chart.css.styles.ChartGradientType;
import org.pentaho.experimental.chart.css.styles.ChartItemLabelVisibleType;
import org.pentaho.experimental.chart.css.styles.ChartOrientationStyle;
import org.pentaho.experimental.chart.css.styles.ChartSeriesType;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.ChartItemLabelGenerator;
import org.pentaho.reporting.libraries.base.config.Configuration;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyle;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.keys.border.BorderWidth;
import org.pentaho.reporting.libraries.css.keys.font.FontSizeConstant;
import org.pentaho.reporting.libraries.css.keys.font.FontStyle;
import org.pentaho.reporting.libraries.css.keys.font.FontStyleKeys;
import org.pentaho.reporting.libraries.css.keys.font.RelativeFontSize;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;
import org.pentaho.reporting.libraries.css.values.CSSConstant;
import org.pentaho.reporting.libraries.css.values.CSSFunctionValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;

/**
 * @author wseyler
 */
public class JFreeChartUtils {

  private static final Log logger = LogFactory.getLog(JFreeChartUtils.class);

  private static final char SEPERATOR = '/';
  private static final String DOMAIN_AXIS="domain";
  private JFreeChartUtils() {
  }

  /**
   * This method iterates through the rows and columns to populate a DefaultCategoryDataset.
   * Since a CategoryDataset stores values based on a multikey hash we supply as the keys
   * either the metadata column name or the column number and the metadata row name or row number
   * as the keys.
   * <p/>
   * As it's processing the data from the ChartTableModel into the DefaultCategoryDataset it
   * applies the scale specified in the
   *
   * @param data          - ChartTablemodel that represents the data that will be charted
   * @param chartDocument - Contains actual chart definition
   * @return DefaultCategoryDataset that can be used as a source for JFreeChart
   */
  public static DefaultCategoryDataset createDefaultCategoryDataset(final ChartTableModel data, final ChartDocument chartDocument, final Integer[] columnIndexArr) {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    final int rowCount = data.getRowCount();
    final Configuration config = ChartBoot.getInstance().getGlobalConfig();
    final String noRowNameSpecified = config.getConfigProperty("org.pentaho.experimental.chart.namespace.row_name_not_defined"); //$NON-NLS-1$
    final int colCount = data.getColumnCount();
    final String noColumnName = config.getConfigProperty("org.pentaho.experimental.chart.namespace.column_name_not_defined"); //$NON-NLS-1$
    final double scale = JFreeChartUtils.getScale(chartDocument);

    // Only if we have to separate datasets then do we do some column processing in the given data
    // else we simply process all rows and all columns
    if (columnIndexArr != null) {
      final int columnIndexArrLength = columnIndexArr.length;
      for (int row = 0; row < rowCount; row++) {
        int columnIndexArrCounter = 0;
        for (int column = 0; column < colCount; column++) {
          if (columnIndexArrCounter < columnIndexArrLength) {
            // if the current column is what we want in the dataset (based on column indexes in columnIndexArr),
            // then process the data
            // Else move to the next column
            if (column == columnIndexArr[columnIndexArrCounter]) {
              setDataset(dataset, chartDocument, data, row, column, noRowNameSpecified, noColumnName, scale);
              // Increment the counter so that we can process the next column in the columnIndexArr
              columnIndexArrCounter++;
            }
          } else {
            // if we have reached beyond the last element in the column index array then simply start processing
            // the next row of data.
            break;
          }
        }
      }      
    }
    // If we do not want to process entire data as is (without dividing the dataset)
    // then simply process all the dataset
    else {
    for (int row = 0; row < rowCount; row++) {
      for (int column = 0; column < colCount; column++) {
          setDataset(dataset, chartDocument, data, row, column, noRowNameSpecified, noColumnName, scale);
        }
      }
    }
    return dataset;
  }

  /**
   * 
   * @param dataset
   * @param data
   * @param row
   * @param column
   * @param noRowNameSpecified
   * @param noColumnName
   * @param scale
   */
  private static void setDataset(DefaultCategoryDataset dataset,
                                 ChartDocument chartDocument,
                                 ChartTableModel data,
                                 int row,
                                 int column,
                                 String noRowNameSpecified,
                                 String noColumnName,
                                 double scale) {
        final String rawColumnName = getColumnName(data, chartDocument, row, column);
        final String columnName = rawColumnName != null ? rawColumnName : noColumnName + column ;
        final Object rawRowName = getRawRowName(data, chartDocument, row);
        final String rowName = rawRowName != null ? String.valueOf(rawRowName): (noRowNameSpecified + row); 
        final Object rawValue = data.getValueAt(row, column);
        if (rawValue instanceof Number) {
        final Number number = (Number) rawValue;
          double value = number.doubleValue();
          value *= scale;
          dataset.setValue(value, rowName, columnName);
      }
    }

  private static Object getRawRowName(final ChartTableModel data, final ChartDocument chartDocument, int row) {
    StringBuffer syntheticColumnName = new StringBuffer();
    if (getIsStackedGrouped(chartDocument)) {
        ChartElement currentGroup = getBaseStackedGroupElement(chartDocument);
        while (currentGroup != null) {
          final String columnName = currentGroup.getAttribute(ChartElement.COLUMN_NAME).toString();
          final int columnIndex = data.findColumn(columnName);
          syntheticColumnName.append(data.getValueAt(row, columnIndex)).append(SEPERATOR);
          currentGroup = getChildGroup(currentGroup);
        }
    }
    
    return syntheticColumnName.append(data.getRowMetadata(row, "row-name")).toString(); //$NON-NLS-1$
  }
  /**
   * @param data
   * @param chartDocument
   * @param row
   * @return
   */
  private static String getColumnName(final ChartTableModel data, final ChartDocument chartDocument, final int row, final int column) {
    return data.getColumnName(column) == null ? Integer.toBinaryString(column) : data.getColumnName(column).toString();
  }

  /**
   * Get the scale of the chart from the ChartTableModel.
   *
   * @param chartDocument Contains actual chart definition
   * @return Returns the scale for the current plot
   */
  private static double getScale(final ChartDocument chartDocument) {
    return ((CSSNumericValue)chartDocument.getPlotElement().getLayoutStyle().getValue(ChartStyleKeys.SCALE_NUM)).getValue();
  }

  /**
   * @param data          - Data for the chart
   * @param chartDocument - Actual chart definiton
   * @return
   */
  public static DefaultIntervalCategoryDataset createDefaultIntervalCategoryDataset(final ChartTableModel data, final ChartDocument chartDocument) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Determines what type of chart that should be rendered.  It is possible that this method
   * could somehow be moved up into the AbstractChartPlugin
   *
   * @param chartDocument that defines what type of chart to use
   * @return a ChartType that represents the type of chart the chartDocument is requesting.
   */
  public static CSSConstant determineChartType(final ChartDocument chartDocument) {
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    for (final ChartElement element : elements) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.CHART_TYPE);
      if (value != null) {
        if (value.equals(ChartSeriesType.BAR)) {
          return ChartSeriesType.BAR;
        } else if (value.equals(ChartSeriesType.LINE)) {
          return ChartSeriesType.LINE;
        }
      }
    }
    return ChartSeriesType.UNDEFINED;
  }

  /**
   * Paints the series border/outline.
   *
   * @param categoryPlot   The plot that has the renderer object
   * @param seriesElements The series elements from the chart document
   */
  private static void setSeriesBarOutline(final CategoryPlot categoryPlot, final ChartElement[] seriesElements) {
    final int length = seriesElements.length;
    for (int i = 0; i < length; i++) {
      final ChartElement currElement = seriesElements[i];

      if (categoryPlot.getRenderer() instanceof BarRenderer) {
        final BarRenderer barRender = (BarRenderer) categoryPlot.getRenderer();
        final BasicStroke borderStyle = JFreeChartUtils.getBorderStyle(currElement);
        if (borderStyle != null) {
          final CSSValue borderColorValue = currElement.getLayoutStyle().getValue(BorderStyleKeys.BORDER_TOP_COLOR);
          final Color borderColor = JFreeChartUtils.getColorFromCSSValue(borderColorValue);
          if (borderColor != null) {
            barRender.setSeriesOutlinePaint(i, borderColor, true);
          }
          barRender.setSeriesOutlineStroke(i, borderStyle, true);
          barRender.setDrawBarOutline(true);
        }
      }
    }
  }

  /**
   * This method creates a BasicStroke object for border-style like dotted, solid etc
   * It also incorporates border-width for the border.
   * <p/>
   * Currently we only support NONE, HIDDEN, SOLID, DASHED, DOT-DASH and DOTTED.
   * The border-width: thin, medium and thick have been mapped to static widths.
   * We do not support separate borders for top, bottom, left and right side of
   * the bar. So we accept the first value and implement it for border-style and
   * border-width.
   *
   * @param element The current series element
   * @return BasicStroke  The basic stroke object that would implement border-style and border-width
   */
  private static BasicStroke getBorderStyle(final ChartElement element) {
    final String borderWidth = element.getLayoutStyle().getValue(BorderStyleKeys.BORDER_TOP_WIDTH).getCSSText();

    float width = 0f;
    if (borderWidth != null) {
      if (borderWidth.equalsIgnoreCase(BorderWidth.THIN.toString())) {
        width = 1f;
      } else if (borderWidth.equalsIgnoreCase(BorderWidth.MEDIUM.toString())) {
        width = 2f;
      } else if (borderWidth.equalsIgnoreCase(BorderWidth.THICK.toString())) {
        width = 4f;
      }
    }

    final CSSValue borderStyle = element.getLayoutStyle().getValue(BorderStyleKeys.BORDER_TOP_STYLE);
    BasicStroke stroke = null;

    if (borderStyle.equals(BorderStyle.SOLID)) {
      stroke = new BasicStroke(width);
    } else if (borderStyle.equals(BorderStyle.DASHED)) {
      stroke = new BasicStroke(width, BasicStroke.CAP_BUTT,
              BasicStroke.JOIN_MITER,
              10.0F,
              new float[]{10.0F, 3.0F},
              0.F);
    } else if (borderStyle.equals(BorderStyle.DOT_DASH)) {
      stroke = new BasicStroke(width, BasicStroke.CAP_BUTT,
              BasicStroke.JOIN_MITER,
              10.0F,
              new float[]{10.0F, 3.0F, 2.0F, 2.0F},
              0.F);
    } else if (borderStyle.equals(BorderStyle.DOTTED)) {
      stroke = new BasicStroke(width, BasicStroke.CAP_ROUND,
              BasicStroke.JOIN_ROUND,
              0,
              new float[]{0, 6, 0, 6},
              0);
    }


    return stroke;
  }

  /**
   * Sets the series item label(s) defined in the chartDocument
   *
   * @param categoryPlot   - Plot for the current chart
   * @param seriesElements - Array of Series elements
   * @param data           - the data
   */
  private static void setSeriesItemLabel(final CategoryPlot categoryPlot, final ChartElement[] seriesElements, final ChartTableModel data) {
    final int numOfDatasets = categoryPlot.getDatasetCount();
    for (int datasetCounter =0 ; datasetCounter < numOfDatasets; datasetCounter++) {
      categoryPlot.getRenderer(datasetCounter).setBaseItemLabelGenerator(new ChartItemLabelGenerator(seriesElements, data));

      final int numOfSeriesElements = seriesElements.length;
      for (int seriesCounter = 0; seriesCounter < numOfSeriesElements; seriesCounter++) {
      // Get and set font information only if the item label's visibility is set to true 
        if (JFreeChartUtils.showItemLabel(seriesElements[seriesCounter])) {
          final BarRenderer barRender = (BarRenderer) categoryPlot.getRenderer(datasetCounter);
          final Font font = JFreeChartUtils.getFont(seriesElements[seriesCounter]);
          barRender.setSeriesItemLabelFont(seriesCounter, font, true);
          barRender.setSeriesItemLabelsVisible(seriesCounter, Boolean.TRUE, true);
        }
      }
    }
  }

  /**
   * Sets the paint(gradient color) on all the series listed by the
   * chartDocument.
   *
   * @param categoryPlot   - the active plot
   * @param seriesElements - Array of series elements that contains series tags
   * @param data           - The actual chart data
   */
  private static void setSeriesPaint(final CategoryPlot categoryPlot, final ChartElement[] seriesElements, final ChartTableModel data) {
    StandardGradientPaintTransformer st = null;

    final int length = seriesElements.length;
    for (int i = 0; i < length; i++) {
      final ChartElement seriesElement = seriesElements[i];
      final Paint paint = JFreeChartUtils.getPaintFromSeries(seriesElement);
      if (paint != null) {
        final int column = JFreeChartUtils.getSeriesColumn(seriesElement, data, i);
        final int datasetCount = categoryPlot.getDatasetCount();
        for (int datasetCounter=0; datasetCounter<datasetCount; datasetCounter++) {
          categoryPlot.getRenderer(datasetCounter).setSeriesPaint(column, paint);
        }
        /*
        * JFreeChart engine cannot currently implement more than one gradient type except
        * when it's none. That is we cannot have one gradient as CENTER_VERTICAL and another
        * one as CENTER_HORIZONTAL.
        *
        * For example:
        *    series1: none
        *    series2: VERTICAL
        *    series3: none
        *    series4: HORIZONTAL
        * JfreeChart can render none as none, but can implement only one of the gradient
        * styles defined for bar2 and bar4. In our implementation we are accepting the first
        * not-none gradient type and then rendering bar2 and bar4 with VERTICAL.
        *
        *  Check for specific renderer instances since only certain specific renderers allow
        *  setting gradient paint transform.
        */
        if (st == null) {
          st = JFreeChartUtils.getStandardGradientPaintTrans(seriesElements[i]);
          final float barWidthPercent = JFreeChartUtils.getMaximumBarWidth(seriesElements[i]);
          for (int datasetCounter = 0; datasetCounter < datasetCount; datasetCounter++) {
            final CategoryItemRenderer itemRenderer = categoryPlot.getRenderer(datasetCounter);

            // If the renderer is of type BarRenderer then set the gradient paint transform
            // and also set the maximum bar width
            if (itemRenderer instanceof BarRenderer) {
              final BarRenderer barRender = (BarRenderer) categoryPlot.getRenderer(datasetCounter);
          barRender.setGradientPaintTransformer(st);

          if (barWidthPercent > 0) {
            barRender.setMaximumBarWidth(barWidthPercent);
          }
        }
      }
    }
  }
    }
  }

  /**
   * This method checks to see if there is a gradient type other than "none" set on seriesElement.
   * If the series element has a gradient set on it then it returns that.  Otherwise if a color
   * is defined then that is returned.  If no color or gradient is set then this method returns
   * a null
   *
   * @param seriesElement - Current series element
   * @return a Paint object defined by the seriesElement
   */
  private static Paint getPaintFromSeries(final ChartElement seriesElement) {
    final String gradientType = seriesElement.getLayoutStyle().getValue(ChartStyleKeys.GRADIENT_TYPE).getCSSText();
    final Paint paint;
    if (gradientType != null && !gradientType.equalsIgnoreCase("none")) { //$NON-NLS-1$ 
      paint = JFreeChartUtils.getGradientPaint(seriesElement);
    } else {
      paint = (Paint) seriesElement.getLayoutStyle().getValue(ChartStyleKeys.CSS_COLOR);
    }
    return paint;
  }

  /**
   * @param seriesElement - series definition that has column-pos or column-name style
   * @param data          - the actual data (needed to locate the correct columns)
   * @param columnDefault - default column to return if either column-pos or column-name are
   *                      not defined or not found
   * @return int value of the real column in the data.
   */
  @SuppressWarnings({"ReuseOfLocalVariable"})
  private static int getSeriesColumn(final ChartElement seriesElement, final ChartTableModel data, final int columnDefault) {
    Object positionAttr = seriesElement.getAttribute(ChartElement.COLUMN_POSITION);
    final int column;
    if (positionAttr != null) {
      column = Integer.parseInt(positionAttr.toString());
    } else {
      positionAttr = seriesElement.getAttribute(ChartElement.COLUMN_NAME);
      if (positionAttr != null) {
        column = JFreeChartUtils.lookupPosition(data, positionAttr.toString());
      } else {
        column = columnDefault;
      }
    }
    return column;
  }

  /**
   * @param data       data that contains the column
   * @param columnName - Name of the column to look for
   * @return and integer that represent the column indicated by columnName.
   *         Returns -1 if columnName not found
   */
  private static int lookupPosition(final ChartTableModel data, final String columnName) {
    final int colCount = data.getColumnCount();
    for (int i = 0; i < colCount; i++) {
      if (data.getColumnName(i).equalsIgnoreCase(columnName)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * @param chartDocument that contains a orientation on the Plot element
   * @return PlotOrientation.VERTICAL or .HORIZONTAL or Null if not defined.
   */
  public static PlotOrientation getPlotOrientation(final ChartDocument chartDocument) {
    PlotOrientation plotOrient = null;
    final ChartElement plotElement = chartDocument.getPlotElement();

    if (plotElement != null) {
      final LayoutStyle layoutStyle = plotElement.getLayoutStyle();
      final CSSValue value = layoutStyle.getValue(ChartStyleKeys.ORIENTATION);

      if (value != null) {
        final String orientatValue = value.toString();

        if (orientatValue.equalsIgnoreCase(ChartOrientationStyle.VERTICAL.getCSSText())) {
          plotOrient = PlotOrientation.VERTICAL;
        } else if (orientatValue.equalsIgnoreCase(ChartOrientationStyle.HORIZONTAL.getCSSText())) {
          plotOrient = PlotOrientation.HORIZONTAL;
        }
      }
    }
    return plotOrient;
  }

  /**
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return a boolean that indicates of if a legend should be included in the chart
   */
  public static boolean getShowLegend(final ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return true;
  }

  /**
   * If the chart URL template is defined in the plot tag with url value, then return true. False otherwise.
   *
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return true if chart url templates are defined in the plot tag with url value.
   */
  public static boolean getShowUrls(final ChartDocument chartDocument) {
    final ChartElement plotElement = chartDocument.getPlotElement();
    boolean showURL = false;

    if (plotElement != null) {
      final LayoutStyle layoutStyle = plotElement.getLayoutStyle();
      final CSSValue value = layoutStyle.getValue(ChartStyleKeys.DRILL_URL);

      if (value != null && !value.getCSSText().equalsIgnoreCase("none")) { //$NON-NLS-1$
        showURL = true;
      }
    }
    return showURL;
  }

  /**
   * Returns a boolean value that indicates if the chart should generate tooltips
   *
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return true if we want to show tool tips
   */
  public static boolean getShowToolTips(final ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return true;
  }

  /**
   * Gets the title of the chart defined in the chartDocument
   *
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return String - the title
   */
  public static String getTitle(final ChartDocument chartDocument) {
    final ChartElement[] children = chartDocument.getRootElement().findChildrenByName("title"); //$NON-NLS-1$
    if (children != null && children.length > 0) {
      return children[0].getText();
    }
    return null;
  }

  /**
   * Returns the ValueCategoryLabel of the chart.
   *
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return String - the value category label
   */
  public static String getValueCategoryLabel(final ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return "Category Label"; //$NON-NLS-1$ 
  }

  /**
   * Returns the ValueAxisLabel of the chart.
   *
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return String - the value axis label
   */
  public static String getValueAxisLabel(final ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return "Value Axis Label"; //$NON-NLS-1$ 
  }

  /**
   * Main method for setting ALL the plot attributes.  This method is a staging
   * method for calling all the other helper methods.
   *
   * @param categoryPlot  - a CategoryPlot to manipulate
   * @param chartDocument - ChartDocument that contains the information for manipulating the plot
   * @param data          - The actual data
   */
  public static void setPlotAttributes(final CategoryPlot categoryPlot, final ChartDocument chartDocument, final ChartTableModel data) {
    JFreeChartUtils.setURLGeneration(categoryPlot.getRenderer(), chartDocument);
    JFreeChartUtils.setAxisMargins(categoryPlot, chartDocument);
    JFreeChartUtils.setSeriesAttributes(categoryPlot, chartDocument, data);
  }

  /**
   * This method allows to manipulate bar width indirectly by sepecifying percentages for lower margin,
   * upper margin, category margin and item margin. Definitions of these margins and how they effect bar width
   * are available in the JFreeChart documentation.
   *
   * @param categoryPlot  The plot object for the current chart
   * @param chartDocument Current chart defintion
   */
  private static void setAxisMargins(final CategoryPlot categoryPlot, final ChartDocument chartDocument) {
    final ArrayList<ChartElement> axisElementsList = chartDocument.getAxisSeriesLinkInfo().getDomainAxisElements();
    if (axisElementsList != null) {
      for(ChartElement axisElement : axisElementsList) {
        if (axisElement != null) {
          final String axisType = (String)axisElement.getAttribute("type");
          if (axisType != null &&
              DOMAIN_AXIS.equalsIgnoreCase(axisType)) {
            final LayoutStyle layoutStyle = axisElement.getLayoutStyle();
      final CSSValue lowerMarginValue = layoutStyle.getValue(ChartStyleKeys.MARGIN_LOWER);
      final CSSValue upperMarginValue = layoutStyle.getValue(ChartStyleKeys.MARGIN_UPPER);
      final CSSValue itemMarginValue = layoutStyle.getValue(ChartStyleKeys.MARGIN_ITEM);
      final CSSValue categoryMarginValue = layoutStyle.getValue(ChartStyleKeys.MARGIN_CATEGORY);

      // The lower, upper and category margins can be controlled through category axis
      final CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
      if (lowerMarginValue != null) {
        final double lowerMargin = ((CSSNumericValue) lowerMarginValue).getValue() / 100;
        categoryAxis.setLowerMargin(lowerMargin);
      }
      if (upperMarginValue != null) {
        final double upperMargin = ((CSSNumericValue) upperMarginValue).getValue() / 100;
        categoryAxis.setUpperMargin(upperMargin);
      }
      if (categoryMarginValue != null) {
        final double categoryMargin = ((CSSNumericValue) categoryMarginValue).getValue() / 100;
        categoryAxis.setCategoryMargin(categoryMargin);
      }

      if (itemMarginValue != null) {
        final double itemMargin = ((CSSNumericValue) itemMarginValue).getValue() / 100;
              final int datasetCount = categoryPlot.getDatasetCount();
              for(int i=0; i< datasetCount; i++) {
        if (categoryPlot.getRenderer() instanceof BarRenderer) {
                  final BarRenderer barRenderer = (BarRenderer) categoryPlot.getRenderer(i);
          barRenderer.setItemMargin(itemMargin);
                }
              }
        }
      }
    }
  }
    }
  }

  /**
   * @param renderer      - Renderer for the current chart
   * @param chartDocument - ChartDocument that defines what the series should look like
   */
  public static void setURLGeneration(final CategoryItemRenderer renderer, final ChartDocument chartDocument) {
    if (JFreeChartUtils.getShowUrls(chartDocument)) {
      final String URLPrefix = JFreeChartUtils.getURLText(chartDocument);
      renderer.setBaseItemURLGenerator(new StandardCategoryURLGenerator(URLPrefix));
    }
  }

  /**
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return URL text
   */
  public static String getURLText(final ChartDocument chartDocument) {
    final ChartElement plotElement = chartDocument.getPlotElement();

    if (plotElement != null) {
      final LayoutStyle layoutStyle = plotElement.getLayoutStyle();
      final CSSValue value = layoutStyle.getValue(ChartStyleKeys.DRILL_URL);

      if (value != null && !value.getCSSText().equalsIgnoreCase("none")) { //$NON-NLS-1$
        return value.getCSSText();
      }
    }
    return null;
  }

  /**
   * Main method for setting ALL the series attributes.  This method is a stating
   * method for calling all the other helper methods.
   *
   * @param categoryPlot  - Plot for the current chart
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @param data          - Actual data
   */
  private static void setSeriesAttributes(final CategoryPlot categoryPlot, final ChartDocument chartDocument, final ChartTableModel data) {
    final ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    JFreeChartUtils.setSeriesItemLabel(categoryPlot, seriesElements, data);
    JFreeChartUtils.setSeriesPaint(categoryPlot, seriesElements, data);
    JFreeChartUtils.setSeriesBarOutline(categoryPlot, seriesElements);
  }

  /**
   * Creates a GradientPaint object from the current series element using
   * the gradient pertinent information.
   * <p/>
   * The gradient paint contains color and start and end co-ordinates for the
   * gradient. If the gradient type is not none and not points, then the
   * gradient paint simply contains color information.
   * <p/>
   * If the required information from the chart element was not available then returns a null.
   *
   * @param ce The ChartElement to be used to create the GradientPaint object.
   * @return GradientPaint Returns the newly created GradientPaint object.
   */
  public static GradientPaint getGradientPaint(final ChartElement ce) {
    GradientPaint gradPaint = null;
    final LayoutStyle layoutStyle = ce.getLayoutStyle();

    if (layoutStyle != null) {
      final CSSValue gradType = layoutStyle.getValue(ChartStyleKeys.GRADIENT_TYPE);
      final Color[] gradColors = JFreeChartUtils.getGradientColors(ce);
      if (ChartGradientType.POINTS.equals(gradType)) {
        final CSSValuePair gradStart = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_START);
        final CSSValuePair gradEnd = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_END);
        // Get the start and end co-ordinates for the gradient start and end.

        final float x1 = (float) ((CSSNumericValue) gradStart.getFirstValue()).getValue();
        final float y1 = (float) ((CSSNumericValue) gradStart.getSecondValue()).getValue();
        final float x2 = (float) ((CSSNumericValue) gradEnd.getFirstValue()).getValue();
        final float y2 = (float) ((CSSNumericValue) gradEnd.getSecondValue()).getValue();

        gradPaint = new GradientPaint(x1, y1, gradColors[0], x2, y2, gradColors[1]);
      } else if (!gradType.getCSSText().equalsIgnoreCase((ChartGradientType.NONE).getCSSText())) {
        /*
         * For gradient types like HORIZONTAL, VERTICAL, etc we do not consider x1, y1 
         * and x2, y2 as start and end points since the renderer would figure that out
         * on it's own. So we have static 0's for start and end co-ordinates.
         */
        gradPaint = new GradientPaint(0f, 0f, gradColors[0], 0f, 0f, gradColors[1]);
      }
    }
    return gradPaint;
  }

  /**
   * Returns an array that contains two colors; color1 and color2 for the gradient
   *
   * @param element Current series element.
   * @return Color[] Contains 2 elements: color1 and color2 for the GradientPaint class.
   */
  private static Color[] getGradientColors(final ChartElement element) {
    Color[] gradientColor = null;
    final LayoutStyle layoutStyle = element.getLayoutStyle();

    if (layoutStyle != null) {
      final CSSValuePair valuePair = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_COLOR);
      final CSSValue colorValue1 = valuePair.getFirstValue();
      final CSSValue colorValue2 = valuePair.getSecondValue();
      final Color color1 = JFreeChartUtils.getColorFromCSSValue(colorValue1);
      final Color color2 = JFreeChartUtils.getColorFromCSSValue(colorValue2);

      gradientColor = new Color[]{color1, color2};
    }

    return gradientColor;
  }

  /**
   * Retrieves the color information from the CSSValue parameter, creates a Color object and returns the same.
   *
   * @param value CSSValue that has the color information.
   * @return Color  Returns a Color object created from the color information in the value parameter
   *         If the CSSValue does not contain any color information then returns a null.
   */
  private static Color getColorFromCSSValue(final CSSValue value) {
    Color gradientColor = null;

    if (value instanceof CSSFunctionValue) {
      final CSSFunctionValue func1 = (CSSFunctionValue) value;
      final CSSValue[] rgbArr = func1.getParameters();
      try {
        final int red = (int) ((CSSNumericValue)rgbArr[0]).getValue();
        final int green = (int) ((CSSNumericValue)rgbArr[1]).getValue();
        final int blue = (int) ((CSSNumericValue)rgbArr[2]).getValue();

        gradientColor = new Color(red, green, blue);
      } catch (NumberFormatException ne) {
        JFreeChartUtils.logger.info("Color values defined were incorrect.", ne); //$NON-NLS-1$
      }

    } else if (value instanceof CSSColorValue) {
      final CSSColorValue colorValue = (CSSColorValue) value;
      gradientColor = new Color(colorValue.getRed(), colorValue.getGreen(), colorValue.getBlue());
    }

    return gradientColor;
  }

  /**
   * Returns a new StandardGradientPaintTransformer object if the series element has gradient type
   * of horizontal, vertical, center-horizontal and center-vertical.
   *
   * @param ce Current series element
   * @return StandardGradientPaintTransformer  New StandardGradientPaintTransformer with
   *         appropriate gradient paint transform type.
   */
  public static StandardGradientPaintTransformer getStandardGradientPaintTrans(final ChartElement ce) {
    StandardGradientPaintTransformer trans = null;

    final LayoutStyle layoutStyle = ce.getLayoutStyle();
    if (layoutStyle != null) {
      final CSSValue gradType = layoutStyle.getValue(ChartStyleKeys.GRADIENT_TYPE);

      if (gradType.equals((ChartGradientType.HORIZONTAL))) {
        trans = new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL);
      } else if (gradType.equals((ChartGradientType.VERTICAL))) {
        trans = new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL);
      } else if (gradType.equals((ChartGradientType.CENTER_HORIZONTAL))) {
        trans = new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL);
      } else if (gradType.equals((ChartGradientType.CENTER_VERTICAL))) {
        trans = new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_VERTICAL);
      }
    }
    return trans;
  }

  /**
   * This method creates a Font object based on the font-family, font-size, and
   * font-style defined in the chart xml doc for the current series
   *
   * @param currentSeries Current series element
   * @return Font  The font object created based on the current series font css style
   */
  private static Font getFont(final ChartElement currentSeries) {
    Font font = null;
    if (currentSeries != null) {
      final LayoutStyle layoutStyle = currentSeries.getLayoutStyle();
      final String fontFamily = layoutStyle.getValue(FontStyleKeys.FONT_FAMILY).getCSSText();
      final int fontStyle = JFreeChartUtils.getFontStyle(layoutStyle);
      // Creating the requisite font and setting the default size of 10. This will be overwritten below.
      font = new Font(fontFamily, fontStyle, 10);

      // Modifying the size of the font since we cannot create a Font with size of type float. 
      // We can only modify it's size to float value and not create it with float value.
      font = font.deriveFont(JFreeChartUtils.getFontSize(currentSeries));
    }
    return font;
  }

  /**
   * This method maps the CSS font-style to Font styles allowed by Java Font Style.
   * We default to css font style value "oblique".
   *
   * @param layoutStyle The layout style for the current series element.
   * @return int  Represents Java based font style.
   */
  private static int getFontStyle(final LayoutStyle layoutStyle) {
    final String fontStyleStr = layoutStyle.getValue(FontStyleKeys.FONT_STYLE).getCSSText();
    // Font Style default
    int fontStyle = Font.PLAIN;
    if (fontStyleStr.equalsIgnoreCase((FontStyle.ITALIC).getCSSText())) {
      fontStyle = Font.ITALIC;
    } else if (fontStyleStr.equalsIgnoreCase((FontStyle.NORMAL).getCSSText())) {
      fontStyle = Font.BOLD;
    }
    return fontStyle;
  }

  /**
   * CSSFontSize values: xx-small, x-small, small, medium, large, x-large, xx-large
   * are mapped to certain integer values based on ChartCSSFontSizeMappingConstants file.
   * CSSFontSize values: smaller, larger, and % are based on parent values. Hence for that
   * we establish the current series font size based on the parent font size value.
   *
   * @param element The current series element that would be looked for the font style
   * @return float   The font size for the current series element.
   */
  private static float getFontSize(final ChartElement element) {
    final LayoutStyle layoutStyle = element.getLayoutStyle();
    final CSSValue fontSizeValue = layoutStyle.getValue(FontStyleKeys.FONT_SIZE);

    if (FontSizeConstant.XX_SMALL.equals(fontSizeValue)) {
      return ChartCSSFontSizeMappingConstants.XX_SMALL;
    }
    if (FontSizeConstant.X_SMALL.equals(fontSizeValue)) {
      return ChartCSSFontSizeMappingConstants.X_SMALL;
    }
    if (FontSizeConstant.SMALL.equals(fontSizeValue)) {
      return ChartCSSFontSizeMappingConstants.SMALL;
    }
    if (FontSizeConstant.MEDIUM.equals(fontSizeValue)) {
      return ChartCSSFontSizeMappingConstants.MEDIUM;
    }
    if (FontSizeConstant.LARGE.equals(fontSizeValue)) {
      return ChartCSSFontSizeMappingConstants.LARGE;
    }
    if (FontSizeConstant.X_LARGE.equals(fontSizeValue)) {
      return ChartCSSFontSizeMappingConstants.X_LARGE;
    }
    if (FontSizeConstant.XX_LARGE.equals(fontSizeValue)) {
      return ChartCSSFontSizeMappingConstants.XX_LARGE;
    }

    /*
    * If you encounter SMALLER/LARGER/PERCENT value for the parent
    * then that is an error because at this point the parent defined
    * in the layout style should have absolute values. To overcome that
    * we are returning static value here
    */
    if (fontSizeValue.equals(RelativeFontSize.SMALLER)) {
      final ChartElement parentElement = element.getParentItem();
      final float parentSize = JFreeChartUtils.getFontSize(parentElement);
      if (parentSize >= ChartCSSFontSizeMappingConstants.XX_SMALL &&
              parentSize <= ChartCSSFontSizeMappingConstants.XX_LARGE) {
        return (parentSize - 2);
      } else {
        return ChartCSSFontSizeMappingConstants.SMALLER;
      }
    }

    if (fontSizeValue.equals(RelativeFontSize.LARGER)) {
      final ChartElement parentElement = element.getParentItem();
      final float parentSize = JFreeChartUtils.getFontSize(parentElement);
      if (parentSize >= ChartCSSFontSizeMappingConstants.XX_SMALL &&
              parentSize <= ChartCSSFontSizeMappingConstants.XX_LARGE) {
        return (parentSize + 2);
      } else {
        return ChartCSSFontSizeMappingConstants.LARGER;
      }
    }
    // We will hit the code below only in case of percent or pt
    if (fontSizeValue instanceof CSSNumericValue) {
      final CSSNumericValue fontSize = (CSSNumericValue) fontSizeValue;
      final CSSNumericType fontSizeType = fontSize.getType();
      if (CSSNumericType.PERCENTAGE.equals(fontSizeType)) {
        final ChartElement parentElement = element.getParentItem();
        final float parentSize = JFreeChartUtils.getFontSize(parentElement);
        if (parentSize >= ChartCSSFontSizeMappingConstants.XX_SMALL &&
                parentSize <= ChartCSSFontSizeMappingConstants.XX_LARGE) {
          return (parentSize * ((float) fontSize.getValue()) / 100);
        }
      }
      if (CSSNumericType.PT.equals(fontSizeType)) {
        return (float) fontSize.getValue();
      }
    }

    //If we do not have a font defined at current level 
    // then we return the parent font size
    final ChartElement parentElement = element.getParentItem();
    return JFreeChartUtils.getFontSize(parentElement);
  }

  /**
   * Returns true if the item label visibility is set to true for the given element
   *
   * @param element Current series element.
   * @return true/false
   */
  private static boolean showItemLabel(final ChartElement element) {
    boolean showItemLabel = false;

    final CSSValue itemLabelVisible = element.getLayoutStyle().getValue(ChartStyleKeys.ITEM_LABEL_VISIBLE);

    if (ChartItemLabelVisibleType.YES.equals(itemLabelVisible)) {
      showItemLabel = true;
    }
    return showItemLabel;
  }

  /**
   * Controls what the maximum bar width can be.
   *
   * @param seriesElement Maximum bar width setting for current series element
   * @return Returns the current maximum bar width setting
   */
  private static float getMaximumBarWidth(final ChartElement seriesElement) {
    float maxWidth = 0;
    final LayoutStyle layoutStyle = seriesElement.getLayoutStyle();
    final CSSValue maxWidthValue = layoutStyle.getValue(ChartStyleKeys.BAR_MAX_WIDTH);

    //TODO: need to handle auto and length value probably
    if (maxWidthValue instanceof CSSNumericValue) {
      final CSSNumericValue maxWidthNumValue = (CSSNumericValue) maxWidthValue;
      if (CSSNumericType.PERCENTAGE.equals(maxWidthNumValue.getType())) {
        maxWidth = (float) (maxWidthNumValue.getValue() / 100);
      }
    }
    return maxWidth;
  }

  public static ChartElement getBaseStackedGroupElement(final ChartDocument chartDocument) {
    final ChartElement[] groupElements = chartDocument.getGroupChartElements();
    if (groupElements.length > 0) {
      return groupElements[0];
    } 
    
    return null;
  }
  
  public static ChartElement getChildGroup(ChartElement parentGroup) {
    ChartElement[] groupElements = parentGroup.findChildrenByName(ChartElement.TAG_NAME_GROUP);
    if (groupElements.length > 0) {
      return groupElements[0];
    }
    
    return null;
  }
  /**
   * @param chartDocument
   * @return
   */
  public static boolean getIsStackedGrouped(final ChartDocument chartDocument) {
    return getBaseStackedGroupElement(chartDocument) != null;
  }

  /**
   * @param chartDocument
   * @param data
   * @return
   */
  public static KeyToGroupMap createKeyToGroupMap(final ChartDocument chartDocument, final ChartTableModel data, final CategoryDataset dataSet) {
    ChartElement groupElement = getBaseStackedGroupElement(chartDocument);
    
    // First build the set of keys to match against
    Set matchSet = new HashSet();
    for (int row = 0; row<data.getRowCount(); row++) {
      StringBuffer keyStr = new StringBuffer();
      for (int i=0; i<getGroupDepth(groupElement); i++) {
        String columnName = groupElement.getAttribute(ChartElement.COLUMN_NAME).toString();
        int columnNum = data.findColumn(columnName);
        keyStr.append(data.getValueAt(row, columnNum)).append(SEPERATOR);
        groupElement = getChildGroup(groupElement);
      }
      matchSet.add(keyStr.toString());
      groupElement = getBaseStackedGroupElement(chartDocument);
    }
    
    // Now we match them and add then to an appropriate group
    KeyToGroupMap keyToGroupMap = new KeyToGroupMap();

    Iterator matchSetIterator = matchSet.iterator();
    while (matchSetIterator.hasNext()) {
      String matchStr = matchSetIterator.next().toString();
      Iterator rowHeaderIterator = dataSet.getRowKeys().iterator();
      while (rowHeaderIterator.hasNext()) {
        String rowHeader = rowHeaderIterator.next().toString();
        if (rowHeader.startsWith(matchStr)) {
          keyToGroupMap.mapKeyToGroup(rowHeader, matchStr);
        }
      }
    }
    
    return keyToGroupMap;
  }

  /**
   * @param groupElement
   * @return
   */
  private static int getGroupDepth(ChartElement groupElement) {
    int depth = 0;
    
    while(groupElement != null) {
      depth ++;
      groupElement = getChildGroup(groupElement);
    }
    
    return depth;
  }
  
  private static String getInnermostGroupName(ChartDocument chartDocument) {
    ChartElement group = getBaseStackedGroupElement(chartDocument);
    while (getChildGroup(group) != null) {
      group = getChildGroup(group);
    }
    if (group != null) {
      return group.getAttribute(ChartElement.COLUMN_NAME).toString();
    }
    return null;
  }

}
