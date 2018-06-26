/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2018 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.pentaho.chart;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.pentaho.chart.data.MultiSeriesDataModel;
import org.pentaho.chart.data.MultiSeriesXYDataModel;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.ScatterPlot;
import org.pentaho.chart.plugin.ChartDataOverflowException;
import org.pentaho.chart.plugin.NoChartDataException;
import org.pentaho.chart.plugin.jfreechart.JFreeChartPlugin;
import org.pentaho.commons.connection.IPentahoMetaData;
import org.pentaho.metadata.model.concept.types.DataType;

public class ChartBeanFactoryTest {

  private static final String ARG_NAME_MASK = "mask";

  private static final String ARG_NAME_TYPE = "datatype";

  private static final String SAMPLE_INTEGER_MASK = "###,###";

  private static final String SAMPLE_DATE_MASK = "yyyy-MM-dd";

  private static final int RANGE = 1;

  private static final int RANGE_COLUMN_INDEX = 2;

  private static final int SERIES_COLUMN_INDEX = 1;

  private static final int DOMAIN_COLUMN_INDEX = 0;

  private static final int ARG_NAME_INDEX = 2;

  private static final int SAMPLE_INTEGER = 123456;

  private static final Number SCALING_FACTOR = 1;

  private static final boolean CONVERTS_NULL_TO_ZERO = false;

  private Object[][] queryResults;

  private String expectedDate;

  private String expectedInteger;

  private Date sampleDate;

  private SimpleDateFormat format;

  private ChartModel chartModel;

  private IPentahoMetaData metadata;

  @Before
  public void initialize() {

    chartModel = new ChartModel();
    sampleDate = new Date( 0 );
    format = new SimpleDateFormat( SAMPLE_DATE_MASK );
    expectedDate = format.format( sampleDate );
    queryResults = new Object[][] { { "domain", sampleDate, RANGE } };
    metadata = mock( IPentahoMetaData.class );

    when( metadata.getAttribute( anyInt(), anyInt(), anyString() ) ).then( new Answer<Object>() {
      @Override
      public Object answer( InvocationOnMock invocation ) throws Throwable {
        if ( ARG_NAME_MASK.equalsIgnoreCase( (String) invocation.getArguments()[ ARG_NAME_INDEX ] ) ) {
          return SAMPLE_DATE_MASK;
        } else if ( ARG_NAME_TYPE.equalsIgnoreCase( (String) invocation.getArguments()[ ARG_NAME_INDEX ] ) ) {
          return DataType.DATE;
        }
        return (String) invocation.getArguments()[ ARG_NAME_INDEX ];
      }
    } );

  }

  @Test
  public void testCreateChartDataModelScatterPlot_Date_OpenFlash()
    throws ChartDataOverflowException, NoChartDataException {

    ScatterPlot scatterPlot = new ScatterPlot();
    chartModel.setPlot( scatterPlot );

    MultiSeriesXYDataModel model =
      (MultiSeriesXYDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( expectedDate, model.getSeries().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

    metadata = null;

    MultiSeriesXYDataModel modelWithNullMask =
      (MultiSeriesXYDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( sampleDate.toString(),
      modelWithNullMask.getSeries().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

  @Test
  public void testCreateChartDataModelScatterPlot_Date_NullMask_OpenFlash()
    throws ChartDataOverflowException, NoChartDataException {

    ScatterPlot scatterPlot = new ScatterPlot();
    chartModel.setPlot( scatterPlot );

    metadata = null;

    MultiSeriesXYDataModel modelWithNullMask =
      (MultiSeriesXYDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( sampleDate.toString(),
      modelWithNullMask.getSeries().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

  @Test
  public void testCreateChartDataModel_MultiSeriesDataModel_Date_OpenFlash() throws ChartDataOverflowException,
    NoChartDataException {

    MultiSeriesDataModel model =
      (MultiSeriesDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( expectedDate, model.getSeriesData().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

    metadata = null;

    MultiSeriesDataModel modelWithNullMask =
      (MultiSeriesDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( sampleDate.toString(),
      modelWithNullMask.getSeriesData().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

  @Test
  public void testCreateChartDataModel_MultiSeriesDataModel_Date_NullMask_OpenFlash() throws ChartDataOverflowException,
    NoChartDataException {

    metadata = null;

    MultiSeriesDataModel modelWithNullMask =
      (MultiSeriesDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( sampleDate.toString(),
      modelWithNullMask.getSeriesData().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

  @Test
  public void testCreateChartDataModel_MultiSeriesDataModel_Numeric_OpenFlash() throws ChartDataOverflowException,
    NoChartDataException {

    DecimalFormat decimalFormat = new DecimalFormat( SAMPLE_INTEGER_MASK );
    expectedInteger = decimalFormat.format( SAMPLE_INTEGER );
    queryResults = new Object[][] { { "domain", SAMPLE_INTEGER, RANGE } };

    when( metadata.getAttribute( anyInt(), anyInt(), anyString() ) ).then( new Answer<Object>() {
      @Override
      public Object answer( InvocationOnMock invocation ) throws Throwable {
        if ( ARG_NAME_MASK.equalsIgnoreCase( (String) invocation.getArguments()[ ARG_NAME_INDEX ] ) ) {
          return SAMPLE_INTEGER_MASK;
        } else if ( ARG_NAME_TYPE.equalsIgnoreCase( (String) invocation.getArguments()[ ARG_NAME_INDEX ] ) ) {
          return DataType.NUMERIC;
        }
        return (String) invocation.getArguments()[ ARG_NAME_INDEX ];
      }
    } );

    MultiSeriesDataModel model =
      (MultiSeriesDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( expectedInteger, model.getSeriesData().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

    metadata = null;

    MultiSeriesDataModel modelWithNullMask =
      (MultiSeriesDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( Integer.toString( SAMPLE_INTEGER ),
      modelWithNullMask.getSeriesData().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

  @Test
  public void testCreateChartDataModel_MultiSeriesDataModel_Numeric_NullMask_OpenFlash()
    throws ChartDataOverflowException,
    NoChartDataException {

    DecimalFormat decimalFormat = new DecimalFormat( SAMPLE_INTEGER_MASK );
    expectedInteger = decimalFormat.format( SAMPLE_INTEGER );
    queryResults = new Object[][] { { "domain", SAMPLE_INTEGER, RANGE } };

    metadata = null;

    MultiSeriesDataModel modelWithNullMask =
      (MultiSeriesDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( Integer.toString( SAMPLE_INTEGER ),
      modelWithNullMask.getSeriesData().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

  @Test
  public void testCreateChartDataModelScatterPlot_Date_JFree() throws ChartDataOverflowException, NoChartDataException {

    chartModel.setChartEngineId( JFreeChartPlugin.PLUGIN_ID );
    ScatterPlot scatterPlot = new ScatterPlot();
    chartModel.setPlot( scatterPlot );

    MultiSeriesXYDataModel model =
      (MultiSeriesXYDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( expectedDate, model.getSeries().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

  @Test
  public void testCreateChartDataModel_MultiSeriesDataModel_Date_JFree() throws ChartDataOverflowException,
    NoChartDataException {

    chartModel.setChartEngineId( JFreeChartPlugin.PLUGIN_ID );

    MultiSeriesDataModel model =
      (MultiSeriesDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( expectedDate, model.getSeriesData().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

  @Test
  public void testCreateChartDataModel_MultiSeriesDataModel_Numeric_JFree() throws ChartDataOverflowException,
    NoChartDataException {

    chartModel.setChartEngineId( JFreeChartPlugin.PLUGIN_ID );

    DecimalFormat decimalFormat = new DecimalFormat( SAMPLE_INTEGER_MASK );
    expectedInteger = decimalFormat.format( SAMPLE_INTEGER );
    queryResults = new Object[][] { { "domain", SAMPLE_INTEGER, RANGE } };

    when( metadata.getAttribute( anyInt(), anyInt(), anyString() ) ).then( new Answer<Object>() {
      @Override
      public Object answer( InvocationOnMock invocation ) throws Throwable {
        if ( ARG_NAME_MASK.equalsIgnoreCase( (String) invocation.getArguments()[ ARG_NAME_INDEX ] ) ) {
          return SAMPLE_INTEGER_MASK;
        } else if ( ARG_NAME_TYPE.equalsIgnoreCase( (String) invocation.getArguments()[ ARG_NAME_INDEX ] ) ) {
          return DataType.NUMERIC;
        }
        return (String) invocation.getArguments()[ ARG_NAME_INDEX ];
      }
    } );

    MultiSeriesDataModel model =
      (MultiSeriesDataModel) ChartBeanFactory
        .createChartDataModel( queryResults, SCALING_FACTOR, CONVERTS_NULL_TO_ZERO,
          RANGE_COLUMN_INDEX, SERIES_COLUMN_INDEX, DOMAIN_COLUMN_INDEX, chartModel, metadata );

    assertEquals( expectedInteger, model.getSeriesData().get( DOMAIN_COLUMN_INDEX ).getSeriesFormattedName() );

  }

}
