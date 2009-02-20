/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package org.pentaho.chart.css.parser.stylehandler;

import org.pentaho.reporting.libraries.css.parser.stylehandler.color.ColorReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSAutoValue;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.w3c.css.sac.LexicalUnit;

/**
 * Todo: Document Me
 *
 * @author Thomas Morgner
 */
public class AutoColorReadHandler extends ColorReadHandler
{
  public AutoColorReadHandler()
  {
  }

  public CSSValue createValue(final StyleKey name, final LexicalUnit value)
  {
    if (value.getLexicalUnitType() == LexicalUnit.SAC_IDENT)
    {
      if ("auto".equalsIgnoreCase(value.getStringValue()))
      {
        return CSSAutoValue.getInstance();
      }
    }
    return super.createValue(name, value);
  }
}
