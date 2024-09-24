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
