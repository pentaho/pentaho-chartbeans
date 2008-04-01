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

import java.awt.Paint;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartOrientationStyle;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSValue;

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
        Comparable<?> rowName = (Comparable<?>) (data.getRowMetadata(row, "row-name") == null ? row : data.getRowMetadata(row, "row-name"));
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
    ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName("series");
    for (int i=0; i<seriesElements.length; i++) {
      ChartElement seriesElement = seriesElements[i];
      Paint paint = getPaintFromSeries(seriesElement);
      if (paint != null) {
        int column = getSeriesColumn(seriesElement, data, i);
        categoryPlot.getRenderer(0).setSeriesPaint(column, paint);
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
    if (gradientType != null && !gradientType.equalsIgnoreCase("none")) {
//      paint = getGradientPaint(seriesElement);
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
    Object positionAttr = seriesElement.getAttribute("column-pos");
    int column = 0;
    if (positionAttr != null) {
      column = Integer.parseInt(positionAttr.toString());
    } else {
      positionAttr = seriesElement.getAttribute("column-name");
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
    ChartElement[] children = chartDocument.getRootElement().findChildrenByName("title");
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
    return "Category Label";
  }

  /**
   * Returns the ValueAxisLabel of the chart.
   * 
   * @param chartDocument
   * @return String - the value axis label
   */
  public static String getValueAxisLabel(ChartDocument chartDocument) {
 // TODO determine this from the chartDocument
    return "Value Axis Label";
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

}
