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

import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.parser.CSSValueFactory;
import org.pentaho.reporting.libraries.css.parser.CSSValueReadHandler;
import org.pentaho.reporting.libraries.css.parser.stylehandler.color.ColorReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;
import org.w3c.css.sac.LexicalUnit;

/**
 * The style parser for the <code>-x-pentaho-chart-gradient-color</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartGradientColorReadHandler implements CSSValueReadHandler
{
  public ChartGradientColorReadHandler()
  {
  }

  public CSSValue createValue(final StyleKey name, final LexicalUnit unit)
  {
    final CSSValue firstValue = ColorReadHandler.createColorValue(unit);
    CSSValue secondValue = null;

    if (firstValue != null)
    {
      // Parse the comma and move to the next field
      final LexicalUnit secondUnit = CSSValueFactory.parseComma(unit);
      if (secondUnit != null)
      {
        secondValue = ColorReadHandler.createColorValue(secondUnit);
      }
    }

    if (firstValue != null && secondValue != null)
    {
      return new CSSValuePair(firstValue, secondValue);
    }

    return null;
  }
}
