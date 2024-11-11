/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart.plugin;

public class ChartDataOverflowException extends ChartProcessingException
{
  int numberOfDataPoints;
  int maxAllowedDataPoints;
  
  public ChartDataOverflowException(int numberOfDataPoints, int maxAllowedDataPoints)
  {
    this.numberOfDataPoints = numberOfDataPoints;
    this.maxAllowedDataPoints = maxAllowedDataPoints;
  }

  public int getNumberOfDataPoints() {
    return numberOfDataPoints;
  }

  public int getMaxAllowedDataPoints() {
    return maxAllowedDataPoints;
  }

}
