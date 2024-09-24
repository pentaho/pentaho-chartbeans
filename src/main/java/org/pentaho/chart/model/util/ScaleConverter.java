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

package org.pentaho.chart.model.util;

import org.pentaho.chart.model.DialPlot.DialRange;
import org.pentaho.chart.model.DialPlot.Scale;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ScaleConverter implements Converter {

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    Scale scale = (Scale)value;
    for (DialRange dialRange : scale) {
      writer.startNode("range");
      if (dialRange.getMinValue() != null) {
        writer.addAttribute("min", dialRange.getMinValue().toString());
      }
      if (dialRange.getMinValue() != null) {
        writer.addAttribute("max", dialRange.getMaxValue().toString());
      }
      if (dialRange.getStyle().size() > 0) {
        writer.addAttribute("style", dialRange.getStyle().getStyleString());
      }
      writer.endNode();
    }
  }

  public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
    return null;
  }

  public boolean canConvert(Class clazz) {
    return clazz.equals(Scale.class);
  }

}
