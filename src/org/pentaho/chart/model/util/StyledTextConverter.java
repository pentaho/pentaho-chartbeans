package org.pentaho.chart.model.util;

import org.pentaho.chart.model.StyledText;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class StyledTextConverter implements Converter {

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    StyledText text = (StyledText)value;
    if ((text.getText() != null) && (text.getText().length() > 0)) {
      if (text.getStyle().size() > 0) {
        writer.addAttribute("style", text.getStyle().getStyleString());
      }
      writer.setValue(text.getText());
    }
  }

  public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
    return null;
  }

  public boolean canConvert(Class clazz) {
    return clazz.equals(StyledText.class);
  }

}
