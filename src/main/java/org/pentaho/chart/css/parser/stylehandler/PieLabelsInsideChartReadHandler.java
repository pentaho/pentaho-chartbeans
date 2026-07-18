/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.chart.css.parser.stylehandler;

import org.pentaho.chart.css.styles.ChartItemLabelVisibleType;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-pie-labels-inside-chart</code> style.
 *
 * @author Ravi Hasija
 */
public class PieLabelsInsideChartReadHandler extends OneOfConstantsReadHandler {
  public PieLabelsInsideChartReadHandler() {
    super(false);
    addValue(ChartItemLabelVisibleType.HIDDEN);
    addValue(ChartItemLabelVisibleType.VISIBLE);
  }
}
