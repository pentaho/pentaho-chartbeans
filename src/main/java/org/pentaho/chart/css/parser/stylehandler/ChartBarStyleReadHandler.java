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

import org.pentaho.chart.css.styles.ChartBarStyle;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-bar-style</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartBarStyleReadHandler extends OneOfConstantsReadHandler {
  public ChartBarStyleReadHandler() {
    super(false);
    addValue(ChartBarStyle.BAR);
    addValue(ChartBarStyle.CYLINDER);
    addValue(ChartBarStyle.INTERVAL);
    addValue(ChartBarStyle.LAYERED);
    addValue(ChartBarStyle.STACKED);
    addValue(ChartBarStyle.STACK_PERCENT);
    addValue(ChartBarStyle.STACK_100_PERCENT);
    addValue(ChartBarStyle.WATERFALL);
  }
}
