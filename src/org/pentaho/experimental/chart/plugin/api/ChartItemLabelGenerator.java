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
 * @created Apr 2, 2008 
 * @author rhasija
 */
package org.pentaho.experimental.chart.plugin.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.font.FontStyleKeys;
import org.pentaho.reporting.libraries.css.keys.font.FontVariant;
import org.pentaho.reporting.libraries.css.keys.font.FontWeight;
import org.pentaho.reporting.libraries.css.values.CSSStringValue;

/** 
 * This class handles the ItemLabel generation and numeric formatting.
 * @author Ravi Hasija
 *
 */
public class ChartItemLabelGenerator extends StandardCategoryItemLabelGenerator {
  
  ChartElement[] chartElements = null;
  ChartTableModel chartData = null;
  Map<Integer, ChartElement> map = new HashMap<Integer, ChartElement>();
  
  public ChartItemLabelGenerator(ChartElement[] chartElements, ChartTableModel chartData) {
    this.chartElements = chartElements;
    this.chartData = chartData;
    // Get the column number for a particular series and store the information in the map
    mapSeriesToColumnNumber();    
  }
  /**
   * 
   */
  private static final long serialVersionUID = -1787452968861210575L;

  /**
   * This method is over-ridden to get the actual data value into the item label along with 
   * custom formatting defined for the series in the chart definition document. 
   * {0} maps data value
   * {1} maps column name
   * @param dataset   category dataset
   *        row       Row number
   *        column    Column number
   * @return String Final label string for the given series
   */
  public String generateLabel(CategoryDataset dataset, int row, int column) {
    String result = null;
    String messageFormat = null;
    
    if (dataset != null) {
      try {
        Object data = dataset.getValue(row, column);
        if (data != null) {
          ChartElement ce = map.get(Integer.valueOf(column));
          if (ce != null) {
            LayoutStyle layoutStyle = ce.getLayoutStyle();
            messageFormat = ((CSSStringValue)layoutStyle.getValue(ChartStyleKeys.LABEL_TEXT)).getValue();
            result = MessageFormat.format(messageFormat, data, dataset.getColumnKey(column));
            
            // Get the font variant to convert the label text to upper case if the font variant is set to small-caps
            String variant = layoutStyle.getValue(FontStyleKeys.FONT_VARIANT).getCSSText();
            if (variant.equals(FontVariant.SMALL_CAPS.getCSSText())) {
              result = result.toUpperCase();
            }
          }
        }      
      } catch (IndexOutOfBoundsException ignore) {
        //Ignore this exception as we are not directly causing it.
      }
    }
    return result;
  }
  
  /*
   * TODO: mapSeriesToColumnNumber(),
   *       getSeriesColumn(ChartElement seriesElement, int columnDefault),
   *       and lookupPosition methods are from JFreeChartUtils class.
   *  To not make this class dependent on JFreeChartUtils, the methods above 
   *  were copied to this class. We need to move these methods to a commons class.
   */    
  private void mapSeriesToColumnNumber() {
    int i = 0;
    if (chartElements != null) {
      for (ChartElement seriesElement : chartElements) {
        int columnNum = getSeriesColumn(seriesElement, i);      
        map.put(Integer.valueOf(columnNum), seriesElement);
        i++;
      }
    }
  }
  
  private int getSeriesColumn(ChartElement seriesElement, int columnDefault) {
    int column = columnDefault;

    if (seriesElement != null) {
      Object positionAttr = seriesElement.getAttribute("column-pos"); //$NON-NLS-1$ 
      if (positionAttr != null) {
        column = Integer.parseInt(positionAttr.toString());
      } else {
        positionAttr = seriesElement.getAttribute("column-name"); //$NON-NLS-1$ 
        if (positionAttr != null) {
          column = lookupPosition(positionAttr.toString());
        } 
      }
    }
    return column;
  }
  
  private int lookupPosition(String columnName) {
    if (chartData != null) {
      for (int i=0; i<chartData.getColumnCount(); i++) {
        if (chartData.getColumnName(i).equalsIgnoreCase(columnName)) {
          return i;
        }
      }
    }
    return -1;
  }
}
