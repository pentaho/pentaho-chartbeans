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


package org.pentaho.chart.css.parser.stylehandler;

import org.pentaho.chart.css.styles.ChartMarkerVisibleType;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-item-label-visible</code> style.
 *
 * @author William Seyler
 */
public class ChartMarkerVisibleReadHandler extends OneOfConstantsReadHandler {
  public ChartMarkerVisibleReadHandler() {
    super(false);
    addValue(ChartMarkerVisibleType.HIDDEN);
    addValue(ChartMarkerVisibleType.VISIBLE);
  }
}
