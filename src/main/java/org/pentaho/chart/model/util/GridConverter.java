/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart.model.util;

import org.pentaho.chart.model.Grid;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class GridConverter implements Converter {

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    Grid grid = (Grid)value;
    if (grid.getVerticalLineStyle().size() > 0) {
      ExtendedHierarchicalStreamWriterHelper.startNode(writer, "verticalLines", null);
      writer.addAttribute("style", grid.getVerticalLineStyle().getStyleString());
      writer.endNode();
    }
    if (grid.getHorizontalLineStyle().size() > 0) {
      ExtendedHierarchicalStreamWriterHelper.startNode(writer, "horizontalLines", null);
      writer.addAttribute("style", grid.getHorizontalLineStyle().getStyleString());
      writer.endNode();
    }
    
  }

  public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
    return null;
  }

  public boolean canConvert(Class clazz) {
    return clazz.equals(Grid.class);
  }

}
