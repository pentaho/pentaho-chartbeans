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
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * @author wseyler
 *
 */
public class JFreeChartUtils {
  /**
   * @return
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
  
  private static Paint getPaintFromSeries(ChartElement seriesElement) {
    String gradientType = seriesElement.getLayoutStyle().getValue(ChartStyleKeys.GRADIENT_TYPE).getCSSText();
    Paint paint = null;
    if (gradientType != null && !gradientType.equalsIgnoreCase("none")) {
      
    } else {
      paint = (Paint) seriesElement.getLayoutStyle().getValue(ChartStyleKeys.CSS_COLOR);
    }
    return paint;
  }
  
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
   * @param string
   * @return
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
   * @param chartDocument
   * @return
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
   * @return
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
   * @param chartDocument
   * @return
   */
  public static boolean getShowToolTips(ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return true;
  }

  /**
   * @return
   */
  public static String getTitle(ChartDocument chartDocument) {
    ChartElement[] children = chartDocument.getRootElement().findChildrenByName("title");
    if (children != null && children.length > 0) {
      return children[0].getText();
    }
    return null;
  }

  /**
   * @return
   */
  public static String getValueCategoryLabel(ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return "Category Label";
  }

  /**
   * @return
   */
  public static String getValueAxisLabel(ChartDocument chartDocument) {
 // TODO determine this from the chartDocument
    return "Value Axis Label";
  }

  /**
   * @param categoryPlot
   * @param chartDocument
   * @param data
   */
  public static void setPlotAttributes(CategoryPlot categoryPlot, ChartDocument chartDocument, ChartTableModel data) {
    // TODO set other stuff beside the series stuff
    setSeriesAttributes(categoryPlot, chartDocument, data);
  }

  /**
   * @param chart
   * @param chartDocument
   * @param data
   */
  public static void setSeriesAttributes(CategoryPlot categoryPlot, ChartDocument chartDocument, ChartTableModel data) {
    // TODO set other stuff about the series.
    setSeriesPaint(categoryPlot, chartDocument, data);
  }

}
