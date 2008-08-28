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
 * @created Feb 25, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin.api;

import org.pentaho.experimental.chart.plugin.IChartPlugin;

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
