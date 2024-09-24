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

import org.pentaho.chart.model.CssStyle;
import org.pentaho.chart.model.Palette;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PaletteConverter implements Converter {

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    Palette palette = (Palette)value;
    for (Integer rgb : palette) {
      if (rgb != null) {
        CssStyle style = new CssStyle();
        style.setColor(rgb);
        writer.startNode("paint");
        writer.addAttribute("style", style.getStyleString());
        writer.endNode();
      }
    }
  }

  public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean canConvert(Class clazz) {
    return clazz.equals(Palette.class);
  }

}
