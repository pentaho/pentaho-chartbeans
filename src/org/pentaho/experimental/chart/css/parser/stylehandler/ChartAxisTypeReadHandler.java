/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created 3/25/2008 
 * @author Ravi Hasija 
 */
package org.pentaho.experimental.chart.css.parser.stylehandler;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
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

    private ChartAxisDimensionReadHandler axisDimension;
    private ChartAxisPositionReadHandler axisPosition;
    private ChartAxisOrderReadHandler axisOrder;

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
     * @param value
     * @return
     */
    public Map createValues(LexicalUnit unit) 
    throws IllegalStateException
    {
      final Map<StyleKey, CSSValue> map = new HashMap<StyleKey, CSSValue>();

      CSSValue dimension = null;
      if (unit != null) {
        dimension = axisDimension.createValue(null, unit);
        if (dimension != null) {
          unit = unit.getNextLexicalUnit(); 
        } else {
          throw new IllegalStateException("Dimension value should never be null."); //$NON-NLS-1$ 
        }
      }  
      
      //System.out.println("" + dimension);
      CSSValue position = null;
      if (unit != null) {
        position = axisPosition.createValue(null, unit);
        
        if (position != null) {
          unit = unit.getNextLexicalUnit();
        } else {
          throw new IllegalStateException("Axis Position value should never be null."); //$NON-NLS-1$ 
        }
      }

      //System.out.println("," + position);
      
      CSSValue order = null;
      if (unit != null) {
        order = axisOrder.createValue(null, unit);
        
        if (order == null) {
          throw new IllegalStateException("Axis Order should never come out be null."); //$NON-NLS-1$ 
        }
      }
      
      //System.out.println("," + order);
      
      map.put(ChartStyleKeys.AXIS_DIMENSION, dimension);      
      map.put(ChartStyleKeys.AXIS_POSITION, position);
      map.put(ChartStyleKeys.AXIS_ORDER, order);
      
      return map;
    }
  }