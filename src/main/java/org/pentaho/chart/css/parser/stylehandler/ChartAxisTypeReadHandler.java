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

import java.util.HashMap;
import java.util.Map;

import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.parser.CSSCompoundValueReadHandler;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.w3c.css.sac.LexicalUnit;

/**
 * The style parser for the <code>-x-pentaho-chart-axis-type compound type</code> style.
 * This style comprises of three values (in given order): 
 *   -x-pentaho-chart-axis-type-dimension
 *   -x-pentaho-chart-axis-type-position
 *   -x-pentaho-chart-axis-type-order
 *   
 *   For eg: -x-pentaho-chart-axis-type: domain primary 1
 *           -x-pentaho-chart-axis-type: range secondary 2
 *           
 * @author Ravi Hasija
 */
public class ChartAxisTypeReadHandler implements CSSCompoundValueReadHandler {

    private final ChartAxisDimensionReadHandler axisDimension;
    private final ChartAxisPositionReadHandler axisPosition;
    private final ChartAxisOrderReadHandler axisOrder;

    public ChartAxisTypeReadHandler() {
      axisDimension = new ChartAxisDimensionReadHandler();
      axisPosition = new ChartAxisPositionReadHandler();
      axisOrder    = new ChartAxisOrderReadHandler();
    }

    public StyleKey[] getAffectedKeys() {
      return new StyleKey[]{
          ChartStyleKeys.AXIS_DIMENSION,
          ChartStyleKeys.AXIS_POSITION,
          ChartStyleKeys.AXIS_ORDER
      };      
    }
    /**
     * Parses the LexicalUnit and returns a map of (StyleKey, CSSValue) pairs.
     *
     * @param unit
     * @return
     */
    public Map createValues(final LexicalUnit unit) {
      CSSValue dimension = null;
      LexicalUnit positionUnit = null;
      if (unit != null) {
        dimension = axisDimension.createValue(null, unit);
        if (dimension != null) {
          positionUnit = unit.getNextLexicalUnit();
        } 
      }  
      
      //System.out.println("" + dimension);
      LexicalUnit orderUnit = null;
      CSSValue position = null;
      if (positionUnit != null) {
        position = axisPosition.createValue(null, positionUnit);
        
        if (position != null) {
          orderUnit = positionUnit.getNextLexicalUnit();
        } 
      }

      //System.out.println("," + position);
      
      CSSValue order = null;
      if (orderUnit != null) {
        order = axisOrder.createValue(null, orderUnit);
      }
      
      //System.out.println("," + order);
      final Map<StyleKey, CSSValue> map;
      if (dimension != null && position != null && order != null) {
        map = new HashMap<StyleKey, CSSValue>();
        map.put(ChartStyleKeys.AXIS_DIMENSION, dimension);      
        map.put(ChartStyleKeys.AXIS_POSITION, position);
        map.put(ChartStyleKeys.AXIS_ORDER, order);
      } else {
        map = null;
      }
      return map;
    }
  }
