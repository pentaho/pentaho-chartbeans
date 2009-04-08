package org.pentaho.chart.model.util;

import org.pentaho.chart.model.CssStyle;

import com.thoughtworks.xstream.converters.SingleValueConverter;

public class CssStylesConverter implements SingleValueConverter {

  public Object fromString(String arg0) {
    return null;
  }

  public String toString(Object arg0) {
    String str = arg0.toString();
    return str.length() > 0 ? str : null;
  }

  public boolean canConvert(Class type) {
    return type.equals(CssStyle.class);
  }

}
