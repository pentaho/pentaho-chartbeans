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

package org.pentaho.chart;

/**
 * Exception indicating an error with the chart definition
 *
 * @author David Kincade
 */
public class InvalidChartDefinition extends Exception {
  public InvalidChartDefinition() {
  }

  public InvalidChartDefinition(final String message) {
    super(message);
  }

  public InvalidChartDefinition(final String message, final Throwable cause) {
    super(message, cause);
  }

  public InvalidChartDefinition(final Throwable cause) {
    super(cause);
  }
}
