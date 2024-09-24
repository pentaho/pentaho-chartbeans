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

import org.pentaho.chart.css.styles.ChartLineStyle;
import org.pentaho.reporting.libraries.css.parser.stylehandler.OneOfConstantsReadHandler;

/**
 * The style parser for the <code>-x-pentaho-chart-line-style</code> style.
 *
 * @author David Kincade
 */
public class LineStyleReadHandler extends OneOfConstantsReadHandler {
  public LineStyleReadHandler() {
    super(false);
    addValue(ChartLineStyle.SOLID);
    addValue(ChartLineStyle.DASHED);
    addValue(ChartLineStyle.DOTTED);
    addValue(ChartLineStyle.DOT_DASH);
    addValue(ChartLineStyle.DOT_DOT_DASH);
  }  
}
