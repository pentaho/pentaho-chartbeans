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

import org.pentaho.chart.model.Axis;
import org.pentaho.chart.model.NumericAxis;
import org.pentaho.chart.model.StyledText;
import org.pentaho.chart.model.Axis.LabelOrientation;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AxisConverter implements Converter {

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    Axis axis = (Axis)value;
    if (axis.getLabelOrientation() != LabelOrientation.HORIZONTAL) {
      writer.addAttribute("labelOrientation", axis.getLabelOrientation().toString());
    }
    if (axis.getStyle().size() > 0) {
      writer.addAttribute("style", axis.getStyle().getStyleString());
    }
    if (axis instanceof NumericAxis) {
      NumericAxis numericAxis = (NumericAxis)axis;
      if (numericAxis.getMinValue() != null) {
        writer.addAttribute("minValue", numericAxis.getMinValue().toString());
      }
      if (numericAxis.getMaxValue() != null) {
        writer.addAttribute("maxValue", numericAxis.getMaxValue().toString());
      }
    }
    if ((axis.getLegend().getText() != null) && (axis.getLegend().getText().length() > 0)) {
      ExtendedHierarchicalStreamWriterHelper.startNode(writer, "legend", axis.getLegend().getClass());
      context.convertAnother(axis.getLegend());
      writer.endNode();
    }
  }

  public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
    return null;
  }

  public boolean canConvert(Class clazz) {
    return clazz.equals(Axis.class) || clazz.equals(NumericAxis.class);
  }

}
