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

package org.pentaho.chart.plugin.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.font.FontStyleKeys;
import org.pentaho.reporting.libraries.css.keys.font.FontVariant;
import org.pentaho.reporting.libraries.css.values.CSSStringValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * This class handles the ItemLabel generation and numeric formatting.
 *
 * @author Ravi Hasija
 */
public class ChartItemLabelGenerator extends StandardCategoryItemLabelGenerator
{
  private ChartElement[] chartElements;
  private Map<Integer, ChartElement> map;
  private Map<String,Integer> columnPositions;
  
  public ChartItemLabelGenerator(final ChartElement[] chartElements,
                                 final ChartTableModel chartData)
  {
    this.chartElements = chartElements;
    this.map = new HashMap<Integer, ChartElement>();
    this.columnPositions = new HashMap<String,Integer>();

    // Get the column number for a particular series and store the information in the map
    mapSeriesToColumnNumber();

    if (chartData != null)
    {
      for (int i = 0; i < chartData.getColumnCount(); i++)
      {
        final String columnName = chartData.getColumnName(i);
        columnPositions.put (columnName, Integer.valueOf(i));
      }
    }
  }

  /**
   *
   */
  private static final long serialVersionUID = -1787452968861210575L;

  /**
   * This method is over-ridden to get the actual data value into the item label along with
   * custom formatting defined for the series in the chart definition document.
   * {0} maps to data value
   * {1} maps to column name
   *
   * @param dataset category dataset
   * @param row     Row number
   * @param column  Column number
   * @return String Final label string for the given series
   */
  public String generateLabel(final CategoryDataset dataset, final int row, final int column)
  {
    if (dataset == null)
    {
      return null;
    }
    try
    {
      final Object data = dataset.getValue(row, column);
      if (data != null)
      {
        final ChartElement ce = map.get(Integer.valueOf(column));
        if (ce != null)
        {
          final LayoutStyle layoutStyle = ce.getLayoutStyle();

          //Format the item label text
          final String messageFormat = ((CSSStringValue) layoutStyle.getValue(ChartStyleKeys.ITEM_LABEL_TEXT)).getValue();//$NON-NLS-1$
          String result = MessageFormat.format(messageFormat, data, dataset.getColumnKey(column));

          //Get the font variant to convert the label text to upper case if the
          //font variant is set to small-caps
          final CSSValue variant = layoutStyle.getValue(FontStyleKeys.FONT_VARIANT);
          if (FontVariant.SMALL_CAPS.equals(variant))
          {
            result = result.toUpperCase();
          }
          return result;
        }
      }
    }
    catch (IndexOutOfBoundsException ignore)
    {
      //Ignore this exception as we are not directly causing it.
    }

    return null;
  }

  /*
  * TODO: mapSeriesToColumnNumber(),
  *       getSeriesColumn(ChartElement seriesElement, int columnDefault),
  *       and lookupPosition methods are from JFreeChartUtils class.
  *  To not make this class dependent on JFreeChartUtils, the methods above
  *  were copied to this class. We need to move these methods to a commons class.
  */
  private void mapSeriesToColumnNumber()
  {
    if (chartElements != null)
    {
      for (int i = 0; i < chartElements.length; i++)
      {
        ChartElement element = chartElements[i];
        final int columnNum = getSeriesColumn(element, i);
        map.put(Integer.valueOf(columnNum), element);
      }
    }
  }

  private int getSeriesColumn(final ChartElement seriesElement, final int columnDefault)
  {
    int column = columnDefault;

    if (seriesElement != null)
    {
      Object positionAttr = seriesElement.getAttribute("column-pos"); //$NON-NLS-1$
      if (positionAttr != null)
      {
        column = Integer.parseInt(positionAttr.toString());
      }
      else
      {
        positionAttr = seriesElement.getAttribute("column-name"); //$NON-NLS-1$
        if (positionAttr != null)
        {
          column = lookupPosition(positionAttr.toString());
        }
      }
    }
    return column;
  }

  private int lookupPosition(final String columnName)
  {
    final Integer integer = columnPositions.get(columnName.toLowerCase());
    if (integer == null)
    {
      return -1;
    }
    return integer.intValue();
  }
}
