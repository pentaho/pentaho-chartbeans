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

package org.pentaho.chart;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.model.Theme;
import org.pentaho.chart.model.Theme.ChartTheme;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.color.ColorStyleKeys;

public abstract class AbstractChartThemeFactory implements IChartThemeFactory {

  protected List<Theme> themes = null;
  
  protected AbstractChartThemeFactory() {    
  }
  
  public Theme getTheme(ChartTheme chartTheme) {
    Theme theme = null;
    if ((chartTheme != null) && (chartTheme.ordinal() < getThemes().size())) {
      theme = getThemes().get(chartTheme.ordinal());
    }
    return theme;
  }
  
  public List<Theme> getThemes() {
    if (themes == null) {
      synchronized(this) {
        if (themes == null) {
          themes = new ArrayList<Theme>();
          for (File themeFile : getThemeFiles()) {
            try {
              ChartDocument themeDocument = org.pentaho.chart.ChartFactory.getChartDocument(themeFile.toURL(), true);
              Theme chartTheme = new Theme();
              chartTheme.setId(themeFile.getAbsolutePath());
              for (ChartElement seriesTheme : themeDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES)) {
                LayoutStyle seriesStyle = seriesTheme.getLayoutStyle();
                Color color = seriesStyle != null ? (Color) seriesStyle.getValue(ColorStyleKeys.COLOR) : null;
                if (color != null) {
                  // For now get rid of the alpha value.
                  chartTheme.getColors().add(0x00FFFFFF & color.getRGB());
                }
              }
              themes.add(chartTheme);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
    return themes;
  }
  
  protected abstract List<File> getThemeFiles();
}
