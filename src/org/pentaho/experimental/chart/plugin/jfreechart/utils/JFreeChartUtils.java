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

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartGradientType;
import org.pentaho.experimental.chart.css.styles.ChartOrientationStyle;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;
import org.pentaho.reporting.libraries.css.values.CSSFunctionValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;

/**
 * @author wseyler
 *
 */
public class JFreeChartUtils {
  /**
   * This method iterates through the rows and columns to populate a DefaultCategoryDataset.
   * Since a CategoryDataset stores values based on a multikey hash we supply as the keys
   * either the metadata column name or the column number and the metadata row name or row number
   * as the keys.
   *
   * @param data - ChartTablemodel that represents the data that will be charted
   * @return DefaultCategoryDataset that can be used as a source for JFreeChart
   * 
   */
  public static DefaultCategoryDataset createCategoryDataset(ChartTableModel data) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for(int row=0; row<data.getRowCount(); row++) {
      for(int column=0; column<data.getColumnCount(); column++) {
        Comparable<?> columnName = data.getColumnName(column) == null ? column : data.getColumnName(column);
        Comparable<?> rowName = (Comparable<?>) (data.getRowMetadata(row, "row-name") == null ? row : data.getRowMetadata(row, "row-name")); //$NON-NLS-1$  //$NON-NLS-2$ 
        dataset.setValue((Number) data.getValueAt(row, column), rowName, columnName);
      }
    }
    return dataset;
  }

  /**
   * This method sets the paint (color or gradient) on all the series listed by the 
   * chartDocument.
   * 
   * @param categoryPlot - the active plot
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @param data - The actual chart data
   */
  public static void setSeriesPaint(CategoryPlot categoryPlot, ChartDocument chartDocument, ChartTableModel data) {
    ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName("series"); //$NON-NLS-1$
    StandardGradientPaintTransformer st = null;
    for (int i=0; i<seriesElements.length; i++) {
      ChartElement seriesElement = seriesElements[i];
      Paint paint = getPaintFromSeries(seriesElement);
      if (paint != null) {
        int column = getSeriesColumn(seriesElement, data, i);
        categoryPlot.getRenderer(0).setSeriesPaint(column, paint);
        
        /*
         * JFreeChart engine cannot currently implement more than one gradient type except 
         * when it's none. For example: 
         *    series1: none
         *    series2: VERTICAL
         *    series3: none
         *    series4: HORIZONTAL
         * JfreeChart can render none as none, but can implement only one of the gradient 
         * styles defined for bar2 and bar4. In our implementation we are accepting the first 
         * not-none gradient type and then rendering bar2 and bar4 with VERTICAL. 
         */
        if (st == null) {
          st = getStandardGradientPaintTrans(seriesElements[i]);
        }
        /*
         * If the renderer is BarRenderer and the StandardGradientPaintTransformer is 
         * horizontal/vertical/center-horizontal/center-vertical then render the series
         * bar using specific renderer (since only specific renderers implement 
         * StandardGradientPaintTransformer types correctly.
         */
        if (st != null && categoryPlot.getRenderer(0) instanceof BarRenderer) {
          BarRenderer barRender = (BarRenderer)categoryPlot.getRenderer(0);  
          barRender.setGradientPaintTransformer(st);  
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
   * @param seriesElement
   * @return a Paint object defined by the seriesElement
   */
  private static Paint getPaintFromSeries(ChartElement seriesElement) {
    String gradientType = seriesElement.getLayoutStyle().getValue(ChartStyleKeys.GRADIENT_TYPE).getCSSText();
    Paint paint = null;
    if (gradientType != null && !gradientType.equalsIgnoreCase("none")) { //$NON-NLS-1$ 
      paint = getGradientPaint(seriesElement);
    } else {
      paint = (Paint) seriesElement.getLayoutStyle().getValue(ChartStyleKeys.CSS_COLOR);
    }
    return paint;
  }
  
  /**
   * @param seriesElement - series definition that has column-pos or column-name style
   * @param data - the actual data (needed to locate the correct columns)
   * @param columnDefault - default column to return if either column-pos or column-name are
   * not defined or not found
   * @return int value of the real column in the data.
   */
  private static int getSeriesColumn(ChartElement seriesElement, ChartTableModel data, int columnDefault) {
      Object positionAttr = seriesElement.getAttribute("column-pos"); //$NON-NLS-1$ 
      int column = 0;
      if (positionAttr != null) {
        column = Integer.parseInt(positionAttr.toString());
      } else {
        positionAttr = seriesElement.getAttribute("column-name"); //$NON-NLS-1$ 
        if (positionAttr != null) {
          column = lookupPosition(data, positionAttr.toString());
        } else {
        column = columnDefault;
        }
      }
    return column;
      }    

  /**
   * @param columnName - Name of the column to look for
   * @param ChartTableModel - data that contains the column
   * @return and integer that represent the column indicated by columnName.
   * Returns -1 if columnName not found
   */
  private static int lookupPosition(ChartTableModel data, String columnName) {
    for (int i=0; i<data.getColumnCount(); i++) {
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
  public static PlotOrientation getPlotOrientation(ChartDocument chartDocument) {
    PlotOrientation plotOrient = null;
    ChartElement plotElement   = chartDocument.getPlotElement();
    
    if (plotElement != null) {
      LayoutStyle layoutStyle  = plotElement.getLayoutStyle();
      CSSValue value = layoutStyle.getValue(ChartStyleKeys.ORIENTATION);
      
    if (value != null) {
      String orientatValue = value.toString();
      
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
   * @param chartDocument
   * @return a boolean that indicates of if a legend should be included in the chart
   */
  public static boolean getShowLegend(ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return true;
  }

  /** 
   * If the chart URL template is defined in the plot tag with url value, then return true. False otherwise.
   * @param chartDocument
   * @return true if chart url templates are defined in the plot tag with url value.
   */
  public static boolean getShowUrls(ChartDocument chartDocument) {
    ChartElement plotElement = chartDocument.getPlotElement();
    boolean showURL = false;
    
    if (plotElement != null) {
      LayoutStyle layoutStyle = plotElement.getLayoutStyle();
      CSSValue value = layoutStyle.getValue(ChartStyleKeys.DRILL_URL);
      
      if (value != null && !value.getCSSText().equalsIgnoreCase("none")) { //$NON-NLS-1$
        showURL = true;
      }
    }    
    return showURL;
  }

  /**
   * Returns a boolean value that indicates if the chart should generate tooltips
   * 
   * @param chartDocument
   * @return
   */
  public static boolean getShowToolTips(ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return true;
  }

  /**
   * Gets the title of the chart defined in the chartDocument
   * 
   * @param chartDocument
   * @return String - the title
   */
  public static String getTitle(ChartDocument chartDocument) {
    ChartElement[] children = chartDocument.getRootElement().findChildrenByName("title"); //$NON-NLS-1$ 
    if (children != null && children.length > 0) {
      return children[0].getText();
    }
    return null;
  }

  /**
   * Returns the ValueCategoryLabel of the chart.
   * 
   * @param chartDocument
   * @return String - the value category label
   */
  public static String getValueCategoryLabel(ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return "Category Label"; //$NON-NLS-1$ 
  }

  /**
   * Returns the ValueAxisLabel of the chart.
   * 
   * @param chartDocument
   * @return String - the value axis label
   */
  public static String getValueAxisLabel(ChartDocument chartDocument) {
 // TODO determine this from the chartDocument
    return "Value Axis Label"; //$NON-NLS-1$ 
  }

  /**
   * Main method for setting ALL the plot attributes.  This method is a staging
   * method for calling all the other helper methods.
   * 
   * @param categoryPlot - a CategoryPlot to manipulate
   * @param chartDocument - ChartDocument that contains the information for manipulating the plot
   * @param data - The actual data
   */
  public static void setPlotAttributes(CategoryPlot categoryPlot, ChartDocument chartDocument, ChartTableModel data) {
    // TODO set other stuff beside the series stuff
    setSeriesAttributes(categoryPlot, chartDocument, data);
  }

  /**
   * Main method for setting ALL the series attributes.  This method is a stating
   * method for calling all the other helper methods.
   * 
   * @param chart
   * @param chartDocument
   * @param data
   */
  public static void setSeriesAttributes(CategoryPlot categoryPlot, ChartDocument chartDocument, ChartTableModel data) {
    // TODO set other stuff about the series.
    setSeriesPaint(categoryPlot, chartDocument, data);
  }

  /**
   * Creates a GradientPaint object from the current series element using 
   * the gradient pertinent information. 
   * 
   * The gradient paint contains color and start and end co-ordinates for the 
   * gradient. If the gradient type is not none and not points, then the 
   * gradient paint simply contains color information.
   * 
   * If the required information from the chart element was not available then returns a null.
   * @param ce  The ChartElement to be used to create the GradientPaint object.
   * @return GradientPaint Returns the newly created GradientPaint object. 
   */
  public static GradientPaint getGradientPaint(ChartElement ce) {
    GradientPaint gradPaint = null;
    LayoutStyle layoutStyle = ce.getLayoutStyle();
    
    if (layoutStyle != null) {
      CSSValue gradType = layoutStyle.getValue(ChartStyleKeys.GRADIENT_TYPE);
      Color[] gradColors = getGradientColors(ce);
      if (gradType.getCSSText().equalsIgnoreCase((ChartGradientType.POINTS).getCSSText())) {
        CSSValuePair gradStart = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_START);
        CSSValuePair gradEnd = (CSSValuePair) layoutStyle.getValue(ChartStyleKeys.GRADIENT_END);
        // Get the start and end co-ordinates for the gradient start and end.
        float x1 = Float.valueOf(gradStart.getFirstValue().getCSSText()).floatValue();
        float y1 = Float.valueOf(gradStart.getSecondValue().getCSSText()).floatValue();
        float x2 = Float.valueOf(gradEnd.getFirstValue().getCSSText()).floatValue();
        float y2 = Float.valueOf(gradEnd.getSecondValue().getCSSText()).floatValue();

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
   * @param element Current series element.
   * @return Color[] Contains 2 elements: color1 and color2 for the GradientPaint class.
   */
  private static Color[] getGradientColors(ChartElement element) {
    Color[] gradientColor = null;
    LayoutStyle layoutStyle = element.getLayoutStyle();
    
    if (layoutStyle != null) {
      CSSValuePair valuePair = (CSSValuePair)layoutStyle.getValue(ChartStyleKeys.GRADIENT_COLOR);
      CSSValue colorValue1 = valuePair.getFirstValue();
      CSSValue colorValue2 = valuePair.getSecondValue();
      Color color1 = getColorFromCSSValue(colorValue1);
      Color color2 = getColorFromCSSValue(colorValue2);
      
      gradientColor = new Color[] { color1, color2 };
    }
    
    return gradientColor;
  }
  
  /**
   * Retrieves the color information from the CSSValue parameter, creates a Color object and returns the same.
   * @param value   CSSValue that has the color information.
   * @return Color  Returns a Color object created from the color information in the value parameter
   *                If the CSSValue does not contain any color information then returns a null.
   */
  private static Color getColorFromCSSValue(CSSValue value) {
    Color gradientColor = null;
    
    if (value instanceof CSSFunctionValue) {
      CSSFunctionValue func1 = (CSSFunctionValue)value;
      CSSValue[] rgbArr = func1.getParameters();
      int red   = Integer.valueOf(rgbArr[0].toString()).intValue();
      int green = Integer.valueOf(rgbArr[1].toString()).intValue();
      int blue  = Integer.valueOf(rgbArr[2].toString()).intValue();
      gradientColor = new Color(red, green, blue);
    } else if (value instanceof CSSColorValue) {
      CSSColorValue colorValue = (CSSColorValue)value;
      gradientColor = new Color(colorValue.getRed(), colorValue.getGreen(), colorValue.getBlue());
    }

    return gradientColor;
  }

  /**
   * Returns a new StandardGradientPaintTransformer object if the series element has gradient type
   * of horizontal, vertical, center-horizontal and center-vertical.
   * @param   ce  Current series element
   * @return  StandardGradientPaintTransformer  New StandardGradientPaintTransformer with 
   *                                            appropriate gradient paint transform type. 
   */
  public static StandardGradientPaintTransformer getStandardGradientPaintTrans(ChartElement ce) {
    StandardGradientPaintTransformer trans = null;

    LayoutStyle layoutStyle = ce.getLayoutStyle();
    if (layoutStyle != null) {
      String gradType = layoutStyle.getValue(ChartStyleKeys.GRADIENT_TYPE).getCSSText();

      if (!gradType.equalsIgnoreCase((ChartGradientType.NONE).getCSSText()) &&
          !gradType.equalsIgnoreCase((ChartGradientType.POINTS).getCSSText())) {
        if (gradType.equalsIgnoreCase((ChartGradientType.HORIZONTAL).getCSSText())) {
          trans = new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL);  
        } else if (gradType.equalsIgnoreCase((ChartGradientType.VERTICAL).getCSSText())) {
          trans = new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL);  
        } else if (gradType.equalsIgnoreCase((ChartGradientType.CENTER_HORIZONTAL).getCSSText())) {
          trans = new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL);  
        } else if (gradType.equalsIgnoreCase((ChartGradientType.CENTER_VERTICAL).getCSSText())) {
          trans = new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_VERTICAL);  
        }      
      }
    }
    return trans;
  }
}
