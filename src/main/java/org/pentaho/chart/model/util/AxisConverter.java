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
