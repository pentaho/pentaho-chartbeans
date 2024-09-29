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

import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.parser.CSSValueFactory;
import org.pentaho.reporting.libraries.css.parser.CSSValueReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.w3c.css.sac.LexicalUnit;

/**
 * The style parser for the <code>-x-pentaho-chart-pie-start-angle</code> style.
 *
 * @author Angelo Rodriguez
 */
public class PieStartAngleReadHandler implements CSSValueReadHandler {
  public PieStartAngleReadHandler() {
  }

  public CSSValue createValue(final StyleKey name, final LexicalUnit value) {
    CSSNumericValue result = null;

    if (value != null) {
      result = CSSValueFactory.createNumericValue(value);

    }
    return result;
  }
}
