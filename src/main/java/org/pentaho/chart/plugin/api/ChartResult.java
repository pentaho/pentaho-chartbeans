/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.chart.plugin.api;

import org.pentaho.chart.plugin.IChartPlugin;

/**
 * @author wseyler
 *
 */
public class ChartResult {
  protected int errorCode = IChartPlugin.RESULT_VALIDATED;
  protected String missingOrInvalidTagName = null;
  protected String description = null;
  
  public ChartResult() {
    super();
  }

  public ChartResult(final int errorCode, final String missingOrInvalidTagName, final String description) {
    this();
    this.errorCode = errorCode;
    this.missingOrInvalidTagName = missingOrInvalidTagName;
    this.description = description;
  }
  
  public ChartResult(final int errorCode, final String missingOrInvalidTagName) {
    this(errorCode, missingOrInvalidTagName, null);
  }
  
  public ChartResult(final int errorCode) {
    this(errorCode, null);
  }
  

  public int getErrorCode() {
    return errorCode;
  }
  
  public void setErrorCode(final int errorCode) {
    this.errorCode = errorCode;
  }
  
  public String getMissingOrInvalidTagName() {
    return missingOrInvalidTagName;
  }
  
  public void setMissingOrInvalidTagName(final String missingOrInvalidTagName) {
    this.missingOrInvalidTagName = missingOrInvalidTagName;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(final String description) {
    this.description = description;
  }

}
