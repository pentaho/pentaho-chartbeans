package org.pentaho.experimental.chart.plugin.jfreechart.chart;

import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.chart.plot.PlotOrientation;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.AxisSeriesLinkInfo;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.ChartSeriesDataLinkInfo;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartOrientationStyle;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * Top Level class that is extended by different chart type creators.
 *
 *                            JFreeChartGenerator
 *                                   |
 *                     -----------------------------------------------------------------------------
 *                     |                                                            |               |
 *                JFreeBarChartGenerator                                           ....            ...
 *                     |
 *       -------------------------------------------------------
 *       |                 |                |                   |
 *   JFreeStackedGen   JFreeLayeredGen    JFreeCylinderGen   JFreeDefault
 *       |
 * ---------------------------
 * |                         |
 * JFreeStackedPercent    JFreeStacked100Percent
 * </p>
 *
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 4:24:46 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class JFreeChartGenerator implements IJfreeChartGenerator {

  /**
   * Gets the title of the chart defined in the chartDocument
   * </p>
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
   * Returns a boolean value that indicates if the chart should generate tooltips
   * </p>
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return true if we want to show tool tips
   */
  public static boolean getShowToolTips(final ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return true;
  }

  /**
   * Returns the ValueCategoryLabel of the chart.
   * </p>
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
   * @param chartDocument - ChartDocument that defines what the series should look like
   * @return a boolean that indicates of if a legend should be included in the chart
   */
  public static boolean getShowLegend(final ChartDocument chartDocument) {
    // TODO determine this from the chartDocument
    return true;
  }

  /**
   * Returns the plot orientation (horizontal or vertical) for the current chart.
   * </p> 
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
   * Returns custom dataset based on certain column positions. The column positions are retrieved by iterating
   * over series elements looking for a specific/given axis id.
   * </p>
   * @param chartDocContext Chart document context object for the current chart document.
   * @param axisElement Current axis element.
   * @param axisSeriesLinkInfo Holds information that links the axis id to series element(s).
   * @return DefaultCategoryDataset that has information from specific column positions.
  */
 public Integer[] getColumPositions(final ChartDocumentContext chartDocContext,
                                    final ChartElement axisElement,
                                    final AxisSeriesLinkInfo axisSeriesLinkInfo) {
   /*
    * First we get the column pos information for each range axis from the columns array list.
    * And then we create the default category dataset based on the columns positions retrieved above.
    */
   // Get current axis element's axis id.
   final Object axisID = axisElement.getAttribute("id");//$NON-NLS-1$
   // Get the column positions for current axis element by looking into each series for given axis id.
   Integer[] columnPosArr = null;

   if (axisSeriesLinkInfo != null && axisID != null) {
     final ChartSeriesDataLinkInfo seriesDataLinkInfo = chartDocContext.getDataLinkInfo();

     if (seriesDataLinkInfo != null) {
       final ArrayList<ChartElement> seriesElementsList = axisSeriesLinkInfo.getSeriesElements(axisID);

         if (seriesElementsList != null) {
           final int size = seriesElementsList.size();
           final ArrayList<Integer> columnPosList = new ArrayList<Integer>();
           for (int i=0; i<size; i++) {
             final ChartElement seriesElement = seriesElementsList.get(i);
             final Integer columnPos = seriesDataLinkInfo.getColumnNum(seriesElement);
             columnPosList.add(columnPos);
           }
           final int listLength = columnPosList.size();
           columnPosArr = new Integer[listLength];
           System.arraycopy(columnPosList.toArray(),0, columnPosArr, 0, listLength);
           Arrays.sort(columnPosArr);
         }
       }
     }
    return columnPosArr;
  }
}
