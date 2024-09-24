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

package org.pentaho.chart.css.parser.stylehandler;

import org.pentaho.chart.css.styles.ChartGradientType;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-bar-style</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartGradientTypeReadHandler extends OneOfConstantsReadHandler {
  public ChartGradientTypeReadHandler() {
    super(false);
    addValue(ChartGradientType.NONE);
    addValue(ChartGradientType.HORIZONTAL);
    addValue(ChartGradientType.VERTICAL);
    addValue(ChartGradientType.CENTER_HORIZONTAL);
    addValue(ChartGradientType.CENTER_VERTICAL);
    addValue(ChartGradientType.POINTS);
  }
}
