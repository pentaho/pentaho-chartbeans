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

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;

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

  public static void setSeriesColor(JFreeChart chart, ChartDocument chartDocument, ChartTableModel data) {
    StyleKey colorKey = StyleKeyRegistry.getRegistry().createKey("color", false, true, StyleKey.All_ELEMENTS);
    ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName("series");
    for (int i=0; i<seriesElements.length; i++) {
      ChartElement seriesElement = seriesElements[i];
      Object positionAttr = seriesElement.getAttribute("column-pos");
      int column = 0;
      if (positionAttr != null) {
        column = Integer.parseInt(positionAttr.toString());
      } else {
        positionAttr = seriesElement.getAttribute("column-name");
        if (positionAttr != null) {
          column = lookupPosition(data, positionAttr.toString());
        } else {
          column = i;
        }
      }
      Color color = (Color) seriesElement.getLayoutStyle().getValue(colorKey);
      if (color != null) {
        chart.getCategoryPlot().getRenderer(0).setSeriesPaint(column, color);
      }    
    }
  }

  /**
   * @param string
   * @return
   */
  public static int lookupPosition(ChartTableModel data, String columnName) {
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
    // TODO determine this from the chartDocument
    return PlotOrientation.VERTICAL;
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
   * @param chartDocument
   * @return
   */
  public static boolean getShowUrls(ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return false;
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
  public static String getTitle() {
    // TODO determine this from the chartDocument
    return "Chart Title";
  }

  /**
   * @return
   */
  public static String getValueCategoryLabel() {
    // TODO determine this from the chartDocument
    return "Category Label";
  }

  /**
   * @return
   */
  public static String getValueAxisLabel() {
    // TODO determine this from the chartDocument
    return "Axis Label";
  }

}
