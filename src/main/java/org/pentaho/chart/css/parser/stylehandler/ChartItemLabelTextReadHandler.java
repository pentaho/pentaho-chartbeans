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
import org.pentaho.reporting.libraries.css.values.CSSStringType;
import org.pentaho.reporting.libraries.css.values.CSSStringValue;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.w3c.css.sac.LexicalUnit;

/**
 *  The style parser for the <code>-x-pentaho-chart-item-label-text</code> style.
 * 
 * @author Ravi Hasija
 */
public class ChartItemLabelTextReadHandler implements CSSValueReadHandler
{
  public ChartItemLabelTextReadHandler()
  {
  }

  /**
   * Parses the LexicalUnit and returns the CSSValue 
   *
   * @param value
   * @return
   */
  public CSSValue createValue(final StyleKey name, final LexicalUnit value)
  {
    CSSValue labelText = null;
    
    if (value.getLexicalUnitType() == LexicalUnit.SAC_STRING_VALUE) {
      labelText = new CSSStringValue (CSSStringType.STRING, value.getStringValue());
    } 
    
    return labelText;
  }
}
