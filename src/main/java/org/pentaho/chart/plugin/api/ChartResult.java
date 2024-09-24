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
