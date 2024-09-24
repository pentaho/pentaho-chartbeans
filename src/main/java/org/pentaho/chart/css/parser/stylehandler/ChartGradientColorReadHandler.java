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
