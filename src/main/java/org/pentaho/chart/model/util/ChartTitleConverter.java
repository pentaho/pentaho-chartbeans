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

import org.pentaho.chart.model.ChartTitle;
import org.pentaho.chart.model.ChartTitle.TitleLocation;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ChartTitleConverter extends StyledTextConverter {

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    ChartTitle chartTitle = (ChartTitle)value;
    if ((chartTitle.getText() != null) && (chartTitle.getText().length() > 0)) {
      if ((chartTitle.getLocation() != null) && (chartTitle.getLocation() != TitleLocation.TOP)) {
        writer.addAttribute("location", chartTitle.getLocation().toString());
      }
    }
    super.marshal(value, writer, context);
  }

  public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
    return null;
  }

  public boolean canConvert(Class clazz) {
    return clazz.equals(ChartTitle.class);
  }

}
