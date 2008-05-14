package org.pentaho.experimental.chart.plugin.jfreechart.dataset;

import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.AxisSeriesLinkInfo;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.ChartSeriesDataLinkInfo;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.reporting.libraries.base.config.Configuration;

/**
 * This class generates default category dataset generator.
 * 
 * Author: Ravi Hasija
 * Date: May 12, 2008
 * Time: 4:00:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCategoryDatasetGenerator implements IDatasetGenerator, Dataset {
  private final ChartDocumentContext chartDocumentContext;
  private final ChartDocument chartDocument;
  private final ChartTableModel data;

  /**
   * Creates an object of JFreeDefaultCategoryDatasetCreator type. 
   * @param chartDocContext Current chart document info.
   * @param data Data for the current chart.
   */
  public DefaultCategoryDatasetGenerator(final ChartDocumentContext chartDocContext,
                                         final ChartTableModel data) {
    this.chartDocumentContext = chartDocContext;
    this.chartDocument = chartDocContext.getChartDocument();
    this.data = data;
  }

   /**
   * Returns custom dataset based on certain column positions. The column positions are retrieved by iterating
   * over series elements looking for a specific/given axis id.
   * @param axisElement Current axis element.
   * @param axisSeriesLinkInfo Holds information that links the axis id to series element(s).  
   * @return DefaultCategoryDataset that has information from specific column positions.
   */
  public DefaultCategoryDataset createDataset(final ChartElement axisElement,
                                           final AxisSeriesLinkInfo axisSeriesLinkInfo) {
    /*
     * First we get the column pos information for each range axis from the columns array list.
     * And then we create the default category dataset based on the columns positions retrieved above.
     */
    // Get current axis element's axis id.
    final Object axisID = axisElement.getAttribute("id");//$NON-NLS-1$
    // Get the column positions for current axis element by looking into each series for given axis id.
    final Integer[] columnPosArr = getColumnPositions(axisSeriesLinkInfo, axisID);
    // Create custom dataset based on the given column positions.
    return createDefaultCategoryDataset(columnPosArr);
  }

  /**
   * The default method that creates a dataset of DefaultCategoryDataset type.
   *
   * @return DefaultCategoryDataset type dataset object.
   */
  public DefaultCategoryDataset createDataset() {
   return createDefaultCategoryDataset(null); 
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
   * @param columnIndexArr - Contains column position information. When not null, we would get data from specified columns.
   * @return DefaultCategoryDataset that can be used as a source for JFreeChart
   */
  private DefaultCategoryDataset createDefaultCategoryDataset(final Integer[] columnIndexArr) {
    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    final int rowCount = data.getRowCount();
    final int colCount = data.getColumnCount();
    final Configuration config = ChartBoot.getInstance().getGlobalConfig();
    final String noRowNameSpecified = config.getConfigProperty("org.pentaho.experimental.chart.namespace.row_name_not_defined"); //$NON-NLS-1$
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
              updateDatasetBasedOnScale(dataset, row, column, noRowNameSpecified, noColumnName, scale);
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
          updateDatasetBasedOnScale(dataset, row, column, noRowNameSpecified, noColumnName, scale);
        }
      }
    }
    return dataset;
  }

  /**
   * Gets the column number that the current axis element applies to (as defined in the series tag in the chart
   * document).
   * </p>
   * @param axisId Current axis element id.
   * @param axisSeriesLinkInfo Holds information that links the axis id to series element(s). 
   * @return Integer array that holds all the column positions (in sorted order) that the current axis
   *         element applies to.
   */
  private Integer[] getColumnPositions(final AxisSeriesLinkInfo axisSeriesLinkInfo,
                                       final Object axisId) {
    Integer[] columnPosArr = null;

    if (axisSeriesLinkInfo != null && axisId != null) {
      final ChartSeriesDataLinkInfo seriesDataLinkInfo = chartDocumentContext.getDataLinkInfo();      

      if (seriesDataLinkInfo != null) {
        final ArrayList<ChartElement> seriesElementsList = axisSeriesLinkInfo.getSeriesElements(axisId);

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

  public void addChangeListener(final DatasetChangeListener datasetChangeListener) {
  }

  /**
   * Updates the dataset values based on the scale info.
   * </p>
   * @param dataset Current Dataset.
   * @param row  Curent row nuber in the data.
   * @param column Current column number in the data.
   * @param noRowNameSpecified Default row name if row name is not specified.
   * @param noColumnName Default column name if column name is not specified.
   * @param scale Scale to be used to modify the dataset.
   */
  private void updateDatasetBasedOnScale(final DefaultCategoryDataset dataset,
                                         final int row,
                                         final int column,
                                         final String noRowNameSpecified,
                                         final String noColumnName,
                                         final double scale) {
    final String rawColumnName = JFreeChartUtils.getColumnName(data, column);
    final String columnName = rawColumnName != null ? rawColumnName : noColumnName + column ;
    final Object rawRowName = JFreeChartUtils.getRawRowName(data, chartDocument, row);
    final String rowName = rawRowName != null ? String.valueOf(rawRowName): (noRowNameSpecified + row);
    final Object rawValue = data.getValueAt(row, column);
    if (rawValue instanceof Number) {
      final Number number = (Number) rawValue;
      double value = number.doubleValue();
      value *= scale;
      dataset.setValue(value, rowName, columnName);
    }
  }

  public void removeChangeListener(final DatasetChangeListener datasetChangeListener) {
  }

  public DatasetGroup getGroup() {
    return null;
  }

  public void setGroup(final DatasetGroup datasetGroup) {    
  }
}
