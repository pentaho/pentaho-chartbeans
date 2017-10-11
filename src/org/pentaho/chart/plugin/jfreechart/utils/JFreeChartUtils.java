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

package org.pentaho.chart.plugin.jfreechart.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.pentaho.chart.ChartUtils;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartCSSFontSizeMappingConstants;
import org.pentaho.chart.css.styles.ChartGradientType;
import org.pentaho.chart.css.styles.ChartItemLabelVisibleType;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.keys.font.FontSizeConstant;
import org.pentaho.reporting.libraries.css.keys.font.FontStyle;
import org.pentaho.reporting.libraries.css.keys.font.FontStyleKeys;
import org.pentaho.reporting.libraries.css.keys.font.FontWeight;
import org.pentaho.reporting.libraries.css.keys.font.RelativeFontSize;
import org.pentaho.reporting.libraries.css.values.CSSColorValue;
import org.pentaho.reporting.libraries.css.values.CSSFunctionValue;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;

/**
 * @author wseyler
 */
public class JFreeChartUtils extends ChartUtils {

  private static final Log logger = LogFactory.getLog(JFreeChartUtils.class);
  

  private static final char SEPERATOR = '/';

  
  private JFreeChartUtils() {
  }

  public static Object getRawRowName(final ChartTableModel data, final ChartDocument chartDocument, final int row) {
    final StringBuffer syntheticColumnName = new StringBuffer();
    if (getIsStackedGrouped(chartDocument)) {
        ChartElement currentGroup = getBaseStackedGroupElement(chartDocument);
        while (currentGroup != null) {
          final String columnName = currentGroup.getAttribute(ChartElement.COLUMN_NAME).toString();
          final int columnIndex = data.findColumn(columnName);
          syntheticColumnName.append(data.getValueAt(row, columnIndex)).append(SEPERATOR);
          currentGroup = getChildGroup(currentGroup);
        }
    }

    return syntheticColumnName.append(data.getRowMetadata(row, ChartTableModel.ROW_NAME)).toString();
  }
  /**
   * @param data
   * @param column
   * @return
   */
  public static String getColumnName(final ChartTableModel data, final int column) {
    return data.getColumnName(column) == null ? Integer.toBinaryString(column) : data.getColumnName(column);
  }

  /**
   * Get the scale of the chart from the ChartTableModel.
   *
   * @param chartDocument Contains actual chart definition
   * @return Returns the scale for the current plot
   */
  public static double getScale(final ChartDocument chartDocument) {
    return ((CSSNumericValue)chartDocument.getPlotElement().getLayoutStyle().getValue(ChartStyleKeys.SCALE_NUM)).getValue();
  }

 /**
   * @param seriesElement - series definition that has column-pos or column-name style
   * @param data          - the actual data (needed to locate the correct columns)
   * @param columnDefault - default column to return if either column-pos or column-name are
   *                      not defined or not found
   * @return int value of the real column in the data.
   */
  public static int getSeriesColumn(final ChartElement seriesElement, final ChartTableModel data, final int columnDefault) {
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
   * @param seriesElement - series definition that has column-pos or column-name style
   * @param rowDefault - default column to return if either column-pos or column-name are
   *                      not defined or not found
   * @return int value of the real column in the data.
   */
  public static int getSeriesRow(final ChartElement seriesElement, final int rowDefault) {
    final Object positionAttr = seriesElement.getAttribute(ChartElement.ROW_POSITION);
    final int row;
    if (positionAttr != null) {
      row = Integer.parseInt(positionAttr.toString());
    } else {
      row = rowDefault;
    }
    return row;
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
   * Main method for setting ALL the plot attributes.  This method is a staging
   * method for calling all the other helper methods.
   *
   * @param categoryPlot  - a CategoryPlot to manipulate
   * @param chartDocument - ChartDocument that contains the information for manipulating the plot
   */
  public static void setPlotAttributes(final CategoryPlot categoryPlot, final ChartDocument chartDocument) {
    JFreeChartUtils.setURLGeneration(categoryPlot.getRenderer(), chartDocument);
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
      } else if (!gradType.equals(ChartGradientType.NONE)) {
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
  public static Color getColorFromCSSValue(final CSSValue value) {
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

      if (ChartGradientType.HORIZONTAL.equals(gradType)) {
        trans = new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL);
      } else if (ChartGradientType.VERTICAL.equals(gradType)) {
        trans = new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL);
      } else if (ChartGradientType.CENTER_HORIZONTAL.equals(gradType)) {
        trans = new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL);
      } else if (ChartGradientType.CENTER_VERTICAL.equals(gradType)) {
        trans = new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_VERTICAL);
      }
    }
    return trans;
  }

  /**
   * Returns true if the item label visibility is set to true for the given element
   *
   * @param element Current series element.
   * @return true/false
   */
  public static boolean showItemLabel(final ChartElement element) {
    boolean showItemLabel = false;

    final CSSValue itemLabelVisible = element.getLayoutStyle().getValue(ChartStyleKeys.ITEM_LABEL_VISIBLE);

    if (ChartItemLabelVisibleType.VISIBLE.equals(itemLabelVisible)) {
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
  public static float getMaximumBarWidth(final ChartElement seriesElement) {
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
  
  public static ChartElement getChildGroup(final ChartElement parentGroup) {
    final ChartElement[] groupElements = parentGroup.findChildrenByName(ChartElement.TAG_NAME_GROUP);
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
    final Set matchSet = new HashSet();
    final int rowCount = data.getRowCount();
    for (int row = 0; row < rowCount; row++) {
      final StringBuffer keyStr = new StringBuffer();
      final int groupDepth = getGroupDepth(groupElement);
      for (int i=0; i<groupDepth; i++) {
        final String columnName = groupElement.getAttribute(ChartElement.COLUMN_NAME).toString();
        final int columnNum = data.findColumn(columnName);
        keyStr.append(data.getValueAt(row, columnNum)).append(SEPERATOR);
        groupElement = getChildGroup(groupElement);
      }
      matchSet.add(keyStr.toString());
      groupElement = getBaseStackedGroupElement(chartDocument);
    }
    
    // Now we match them and add then to an appropriate group
    final KeyToGroupMap keyToGroupMap = new KeyToGroupMap();

    for (final Object aMatchSet : matchSet) {
      final String matchStr = aMatchSet.toString();
      final Iterator rowHeaderIterator = dataSet.getRowKeys().iterator();
      while (rowHeaderIterator.hasNext()) {
        final String rowHeader = aMatchSet.toString();
        if (rowHeader.startsWith(matchStr)) {
          keyToGroupMap.mapKeyToGroup(rowHeader, matchStr);
        }
      }
    }
    
    return keyToGroupMap;
  }

  /**
   * @param groupElementParam
   * @return
   */
  private static int getGroupDepth(final ChartElement groupElementParam) {
    int depth = 0;

    if (groupElementParam != null) {
      ChartElement groupElement = groupElementParam;
      while(groupElement != null) {
        depth ++;
        groupElement = getChildGroup(groupElement);
      }
    }

    return depth;
  }
}
