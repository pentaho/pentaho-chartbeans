/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

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
