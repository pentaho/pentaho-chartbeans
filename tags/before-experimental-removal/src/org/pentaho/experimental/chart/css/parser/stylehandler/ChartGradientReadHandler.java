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
 * Created 3/28/2008 
 * @author Ravi Hasija 
 */
package org.pentaho.experimental.chart.css.parser.stylehandler;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.parser.CSSCompoundValueReadHandler;
import org.pentaho.reporting.libraries.css.parser.CSSValueFactory;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.w3c.css.sac.LexicalUnit;

/**
 * TODO: change comments
 * The style parser for the style: "-x-pentaho-chart-gradient" compound type.
 * This style comprises of three values (in given order): 
 *   -x-pentaho-chart-gradient-color (color1, color2) eg: red, yellow
 *   -x-pentaho-chart-gradient-type  (none, horizontal, etc)
 *   -x-pentaho-chart-gradient-start start_pos1,start_pos2
 *   -x-pentaho-chart-gradient-end   end_pos1,end_pos2
 *   
 *   For eg: -x-pentaho-chart-gradient: red, blue VERTICAL 1,2 2,20
 *           -x-pentaho-chart-gradient: red, yellow HORIZONTAL 2,2 5,20
 *           
 * @author Ravi Hasija
 */
public class ChartGradientReadHandler implements CSSCompoundValueReadHandler {

    private final ChartGradientColorReadHandler gradientColor;
    private final ChartGradientTypeReadHandler gradientType;
    private final ChartGradientPositionReadHandler gradientPos;
    
    public ChartGradientReadHandler() {
      gradientColor = new ChartGradientColorReadHandler();
      gradientType = new ChartGradientTypeReadHandler();
      gradientPos = new ChartGradientPositionReadHandler();      
    }

    public StyleKey[] getAffectedKeys() {
      return new StyleKey[]{
          ChartStyleKeys.GRADIENT_COLOR,
          ChartStyleKeys.GRADIENT_TYPE,
          ChartStyleKeys.GRADIENT_START,
          ChartStyleKeys.GRADIENT_END
      };      
    }
    /**
     * Parses the LexicalUnit and returns a map of (StyleKey, CSSValue) pairs.
     *
     * @param unit
     * @return
     */
    public Map createValues(LexicalUnit unit) {
      // Gradient Color
      CSSValue color = null;
      if (unit != null) {
        color = gradientColor.createValue(null, unit);
        if (color != null) {
          /*
           * The color is specified as color1, color2 for eg: red, yellow
           * When we createValue using the first unit (red in our example), 
           * we also process color #2 (yellow in our example). 
           * The unit though, is still pointing to the first color (red), so 
           * we need to advance the unit by 2 (i.e. comma and second color) to 
           * get to the next correct unit.
           */
          unit = CSSValueFactory.parseComma(unit).getNextLexicalUnit();
        } 
      }  

      // Gradient Type
      CSSValue type = null;
      if (unit != null) {
        type = gradientType.createValue(null, unit);
        
        if (type != null) {
          unit = unit.getNextLexicalUnit();
        } 
      }

      // Gradient start position
      CSSValue start = null;
      if (unit != null) {
        start = gradientPos.createValue(null, unit);
        unit = CSSValueFactory.parseComma(unit).getNextLexicalUnit();
      }

      // Gradient end position
      CSSValue end = null;
      if (unit != null) {
        end = gradientPos.createValue(null, unit);
      }

      final Map<StyleKey, CSSValue> map;
      if (color != null && type != null && start != null && end != null) {
        map = new HashMap<StyleKey, CSSValue>();
        map.put(ChartStyleKeys.GRADIENT_COLOR, color);      
        map.put(ChartStyleKeys.GRADIENT_TYPE, type);
        map.put(ChartStyleKeys.GRADIENT_START, start);
        map.put(ChartStyleKeys.GRADIENT_END, end);        
      } else {
        map = null;
      }
    
      return map;
    }
  }