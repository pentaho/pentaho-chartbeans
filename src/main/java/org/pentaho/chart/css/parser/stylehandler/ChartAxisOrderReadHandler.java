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
import org.pentaho.reporting.libraries.css.parser.CSSValueReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.w3c.css.sac.LexicalUnit;

/**
 * The style parser for the <code>-x-pentaho-chart-axis-type-order</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartAxisOrderReadHandler implements CSSValueReadHandler
{
  public ChartAxisOrderReadHandler() {    
  }

  /**
   * Parses the LexicalUnit and returns the CSSValue 
   *
   * @param value
   * @return
   */
  public CSSValue createValue(final StyleKey name, final LexicalUnit value)
  {
    CSSNumericValue numValue = null;
    
    if (value.getLexicalUnitType() == LexicalUnit.SAC_INTEGER) {
      numValue = CSSNumericValue.createValue(CSSNumericType.NUMBER, value.getIntegerValue());
    } 
    
    return numValue;
  }
}
