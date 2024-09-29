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

import org.pentaho.chart.css.styles.ChartSeriesType;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-series-type</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartSeriesTypeReadHandler extends OneOfConstantsReadHandler {
  public ChartSeriesTypeReadHandler() {
    super(false);
    addValue(ChartSeriesType.BAR);
    addValue(ChartSeriesType.LINE);
    addValue(ChartSeriesType.PIE);
    addValue(ChartSeriesType.AREA);
    addValue(ChartSeriesType.MULTI);
    addValue(ChartSeriesType.DIAL);
   
  }
}
