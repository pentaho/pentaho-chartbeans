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

import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.parser.CSSValueReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSNumericType;
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.w3c.css.sac.LexicalUnit;

/**
 * Handles all Margin types
 *
 * @author Ravi Hasija
 */
public class ChartMarginReadHandler implements CSSValueReadHandler
{
  public ChartMarginReadHandler() {    
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
    
    if (value.getLexicalUnitType() == LexicalUnit.SAC_PERCENTAGE) {
      numValue = CSSNumericValue.createValue(CSSNumericType.PERCENTAGE, value.getFloatValue());
    } 
    
    return numValue;
  }
}
