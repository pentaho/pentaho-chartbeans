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

import org.pentaho.chart.plugin.jfreechart.utils.ChartMarkerFilledType;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-item-label-visible</code> style.
 *
 * @author William Seyler
 */
public class ChartMarkerFilledReadHandler extends OneOfConstantsReadHandler {
  public ChartMarkerFilledReadHandler() {
    super(false);
    addValue(ChartMarkerFilledType.EMPTY);
    addValue(ChartMarkerFilledType.FILLED);
  }
}
