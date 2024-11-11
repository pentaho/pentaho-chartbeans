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

import org.pentaho.chart.css.styles.ChartLineVisibleType;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-line-visible</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartLineVisibleReadHandler extends OneOfConstantsReadHandler {
  public ChartLineVisibleReadHandler() {
    super(false);
    addValue(ChartLineVisibleType.HIDDEN);
    addValue(ChartLineVisibleType.VISIBLE);
  }
}
