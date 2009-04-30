package org.pentaho.chart.model.util;

import org.pentaho.chart.model.Axis;
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
    return clazz.equals(Axis.class);
  }

}
