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
import org.pentaho.reporting.libraries.css.values.CSSNumericValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;
import org.w3c.css.sac.LexicalUnit;

/**
 * The style parser for the <code>-x-pentaho-chart-gradient-start</code> style.
 *
 * @author Ravi Hasija
 */
public class ChartGradientPositionReadHandler implements CSSValueReadHandler {
  public ChartGradientPositionReadHandler() {
  }
  
  public CSSValue createValue(final StyleKey name, LexicalUnit value)
  {
    CSSNumericValue firstValue = null;

    if (value != null) {
      firstValue = CSSValueFactory.createNumericValue(value);  
    }
    
    if (firstValue != null) {
      // Parse the comma and move to next value
      value = CSSValueFactory.parseComma(value);  
    }

    CSSNumericValue secondValue = null;
    if (value != null) {
      secondValue = CSSValueFactory.createNumericValue(value);
    }


    CSSValuePair result = null;
    if (firstValue != null && secondValue != null) {
      result = new CSSValuePair(firstValue, secondValue);
      //System.out.println("Values: " + firstValue.getCSSText() + ", "+ secondValue.getCSSText());
    }
    
    return result; 
  }
}
