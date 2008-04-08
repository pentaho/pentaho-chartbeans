package org.pentaho.experimental.chart.css.parser.stylehandler;

import org.pentaho.reporting.libraries.css.parser.stylehandler.AbstractWidthReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.w3c.css.sac.LexicalUnit;

/**
 * Creation-Date: 27.11.2005, 19:07:11
 *
 * @author Ravi Hasija
 */
public class MaxBarWidthReadHandler extends AbstractWidthReadHandler
{
  public MaxBarWidthReadHandler()
  {
    super (true, false);
  }

  /**
   * Parses the LexicalUnit and returns the CSSValue 
   *
   * @param value
   * @return
   */
  public CSSValue createValue(final LexicalUnit value)
  {
    // TODO: Please uncomment the code below if we do not want to extend the abstact class 
    // and implement a simple one ourself
    /*
     CSSValue maxWidth = null;
    if (value.getLexicalUnitType() == LexicalUnit.SAC_PERCENTAGE)
    {
      return CSSNumericValue.createValue(CSSNumericType.PERCENTAGE,
              value.getFloatValue());
    }
    return maxWidth;*/
    
    final CSSValue maxWidth = parseWidth(value);
    return maxWidth;
  }
}
