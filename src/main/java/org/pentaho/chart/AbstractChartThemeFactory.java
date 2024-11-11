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
