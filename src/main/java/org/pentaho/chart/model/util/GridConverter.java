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
