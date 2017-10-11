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

package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Theme implements Serializable {
  public enum ChartTheme{THEME1, THEME2, THEME3, THEME4, THEME5, THEME6, THEME7, THEME8};
  
  String id;
  List<Integer> colors = new ArrayList<Integer>();
  
  public void applyTo(ChartModel chartModel) {
    applyTo(chartModel.getPlot());
  }

  private void applyTo(Plot graph) {
    if (getColors().size() > 0) {
      graph.getPalette().clear();
      graph.getPalette().addAll(getColors());
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String file) {
    this.id = file;
  }

  public List<Integer> getColors() {
    return colors;
  }


}
