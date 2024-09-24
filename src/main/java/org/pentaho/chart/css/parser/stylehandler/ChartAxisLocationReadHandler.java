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

import org.pentaho.chart.css.styles.ChartAxisLocationType;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-axis-location</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartAxisLocationReadHandler extends OneOfConstantsReadHandler {
  public ChartAxisLocationReadHandler () {
    super(false);
    addValue(ChartAxisLocationType.PRIMARY);
    addValue(ChartAxisLocationType.SECONDARY);    
  }
}
