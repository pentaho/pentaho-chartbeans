/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
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
import java.util.Date;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.pentaho.chart.data.MultiSeriesDataModel;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.plugin.ChartDataOverflowException;
import org.pentaho.chart.plugin.NoChartDataException;
import org.pentaho.commons.connection.IPentahoMetaData;
import org.pentaho.metadata.model.concept.types.DataType;

public class ChartBeanFactoryTest {

  private static final int ARG_NAME_INDEX = 2;

  private static final String ARG_NAME_MASK = "mask";

  private static final String ARG_NAME_TYPE = "datatype";

  private static final int SAMPLE_INTEGER = 123456;

  private static final String SAMPLE_INTEGER_MASK = "###,###";

  private static final String EXPECTED_INTEGER = "123,456";

  private Date sampleDate;

  private static final String SAMPLE_DATE_MASK = "yyyy-MM-dd";

  private String expectedDate;

  @Test
  public void testCreateChartDataModel_MultiSeriesDataModel_Date() throws ChartDataOverflowException,
    NoChartDataException {
    sampleDate = new Date( 0 );
    SimpleDateFormat format = new SimpleDateFormat( SAMPLE_DATE_MASK );
    expectedDate = format.format( sampleDate );

    IPentahoMetaData metadata = mock( IPentahoMetaData.class );
    when( metadata.getAttribute( anyInt(), anyInt(), anyString() ) ).then( new Answer<Object>() {
      @Override
      public Object answer( InvocationOnMock invocation ) throws Throwable {
        if ( ARG_NAME_MASK.equalsIgnoreCase( (String) invocation.getArguments()[ARG_NAME_INDEX] ) ) {
          return SAMPLE_DATE_MASK;
        } else if ( ARG_NAME_TYPE.equalsIgnoreCase( (String) invocation.getArguments()[ARG_NAME_INDEX] ) ) {
          return DataType.DATE;
        }
        return (String) invocation.getArguments()[ARG_NAME_INDEX];
      }
    } );
    testCreateChartDataModel_MultiSeriesDataModel( expectedDate, sampleDate, metadata );
  }

  @Test
  public void testCreateChartDataModel_MultiSeriesDataModel_Numeric() throws ChartDataOverflowException,
    NoChartDataException {
    IPentahoMetaData metadata = mock( IPentahoMetaData.class );
    when( metadata.getAttribute( anyInt(), anyInt(), anyString() ) ).then( new Answer<Object>() {
      @Override
      public Object answer( InvocationOnMock invocation ) throws Throwable {
        if ( ARG_NAME_MASK.equalsIgnoreCase( (String) invocation.getArguments()[ARG_NAME_INDEX] ) ) {
          return SAMPLE_INTEGER_MASK;
        } else if ( ARG_NAME_TYPE.equalsIgnoreCase( (String) invocation.getArguments()[ARG_NAME_INDEX] ) ) {
          return DataType.NUMERIC;
        }
        return (String) invocation.getArguments()[ARG_NAME_INDEX];
      }
    } );
    testCreateChartDataModel_MultiSeriesDataModel( EXPECTED_INTEGER, SAMPLE_INTEGER, metadata );
  }

  public void testCreateChartDataModel_MultiSeriesDataModel( String expected, Object sample, IPentahoMetaData metadata )
    throws ChartDataOverflowException, NoChartDataException {
    int range = 1;
    Object[][] queryResults = new Object[][] { { "domain", sample, range } };
    Number scalingFactor = 1;
    boolean convertNullsToZero = false;
    int rangeColumnIndex = 2;
    int seriesColumnIdx = 1;
    int domainColumnIdx = 0;
    ChartModel chartModel = mock( ChartModel.class );

    MultiSeriesDataModel model =
        (MultiSeriesDataModel) ChartBeanFactory.createChartDataModel( queryResults, scalingFactor, convertNullsToZero,
            rangeColumnIndex, seriesColumnIdx, domainColumnIdx, chartModel, metadata );
    assertEquals( expected, model.getSeriesData( expected ).getSeriesName() );
  }

}
