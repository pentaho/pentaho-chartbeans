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

import org.pentaho.chart.model.Theme.ChartTheme;

/**
 * The documentation for this class is from the perspective
 * of the ChartModel file. The valid property parameters may
 * not match the property parameter type, but instead reflects
 * the values that may be present in the file.
 * 
 * Properties which are classes have their own configurable
 * properties and are therefore linked. Please reference the
 * respective class for its attributes. 
 * 
 */
public class ChartModel implements Serializable {
  public static final String DEFAULT_FAMILY = "serif";
  public static final int DEFAULT_SIZE = 14;
  
  ChartTitle title = new ChartTitle();
  ArrayList<StyledText> subtitles = new ArrayList<StyledText>();
  ChartLegend legend = new ChartLegend();
  String backgroundImageLocation;
  Texture backgroundTexture;
  Gradient backgroundGradient;
  Plot plot;
  String chartEngineId;
  ChartTheme theme;
  CssStyle style = new CssStyle();
  
  public String getChartEngineId() {
    return chartEngineId;
  }

  /**
   * Desired renderer of the chart.
   * <p> 
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>JFreeChart</td></tr>
   *   <tr><td>OpenFlashChart</td></tr>
   * </table>
   * </p>
   * 
   * @param id
   */
  public void setChartEngineId(String id) {
    this.chartEngineId = id;
  }

  public ChartTheme getTheme() {
    return theme;
  }

  /**
   * 
   * Set an overall color and style scheme for the chart.
   * <p>
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>THEME1</td></tr>
   *   <tr><td>THEME2</td></tr>
   *   <tr><td>THEME3</td></tr>
   *   <tr><td>THEME4</td></tr>
   *   <tr><td>THEME5</td></tr>
   *   <tr><td>THEME6</td></tr>
   * </table>
   * </p>
   * 
   * @param theme
   */
  public void setTheme(ChartTheme theme) {
    this.theme = theme;
  }

  public List<StyledText> getSubtitles() {
    return subtitles;
  }
  
  /**
   * @see StyledText
   * @param subtitles
   */
  public void setSubtitles(List<StyledText> subtitles) {
    subtitles.clear();
    if (subtitles != null) {
      subtitles.addAll(subtitles);
    }
  }
  
  public StyledText getSubtitle() {
    return subtitles.size() > 0 ? subtitles.get(0) : null;
  }

  /**
   * @see StyledText
   * @param title
   */
  public void setSubtitle(StyledText title) {
    subtitles.clear();
    if (title != null) {
      subtitles.add(title);
    }
  }

  public ChartLegend getLegend() {
    return legend;
  }

  /**
   * @see ChartLegend
   * @param chartLegend
   */
  public void setLegend(ChartLegend chartLegend) {
    this.legend = chartLegend;
  }

  /**
   * Get the currently set background
   * @return The currently set background
   */
  public Object getBackground() {
    Object background = null;
    if (style.getBackgroundColor() != null) {
      background = style.getBackgroundColor();
    } else if (backgroundGradient != null) {
      background = backgroundGradient;
    } else if (backgroundImageLocation != null) {
      background = backgroundImageLocation;
    } else if (backgroundTexture != null) {
      background = backgroundTexture;
    }
    return background;
  }
  
  /**
   * {@link org.pentaho.chart.model.StyledText#setBackgroundColor(Integer)}
   * 
   * @param backgroundColor
   */
  public void setBackground(Integer backgroundColor) {
    style.setBackgroundColor(backgroundColor);
    if (backgroundColor != null) {
      backgroundGradient = null;
      backgroundImageLocation = null;
      backgroundTexture = null;
    }
  }

  /**
   * Not implemented
   * @param backgroundImageLocation
   */
  public void setBackground(String backgroundImageLocation) {
    this.backgroundImageLocation = backgroundImageLocation;
    if (backgroundImageLocation != null) {
      backgroundGradient = null;
      setBackground((Integer)null);
      backgroundTexture = null;
    }
  }
  
  
  /**
   * Not implemented
   * @see Gradient
   * @param backgroundGradient
   */
  public void setBackground(Gradient backgroundGradient) {
    this.backgroundGradient = backgroundGradient;
    if (backgroundGradient != null) {
      setBackground((Integer)null);
      backgroundImageLocation = null;
      backgroundTexture = null;
    }
  }
  
  /**
   * Not implemented
   * @param backgroundTexture
   */
  public void setBackground(Texture backgroundTexture) {
    this.backgroundTexture = backgroundTexture;
    if (backgroundTexture != null) {
      backgroundGradient = null;
      setBackground((Integer)null);
      backgroundImageLocation = null;
    }
  }
  
  /**
   * 
   * @return Instance of plot implementation
   */
  public Plot getPlot() {
    return plot;
  }

  /**
   * The plot is the area on which the chart will be rendered.
   * <p>
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>barPlot</td></tr>
   *   <tr><td>areaPlot</td></tr>
   *   <tr><td>linePlot</td></tr>
   *   <tr><td>piePlot</td></tr>
   *   <tr><td>dialPlot</td></tr>
   * </table>
   * </p>
   * 
   * @param plot
   */
  public void setPlot(Plot plot) {
    this.plot = plot;
  }

  public ChartTitle getTitle() {
    return title;
  }

  /**
   * @see ChartTitle
   * @param title
   */
  public void setTitle(ChartTitle title) {
    this.title = title;
  }

  public Integer getBorderColor() {
    return style.getBorderColor();
  }

  public boolean getBorderVisible() {
    return style.getBorderVisible();
  }

  public Integer getBorderWidth() {
    return style.getBorderWidth();
  }

  /**
   * Hex RGB notation of the desired border color
   * <p>
   * <table>
   *   <tr><th colspan="2">Basic colors</th></tr>
   *   <tr><td>White: </td><td>#FFFFFF</td></tr>
   *   <tr><td>Black: </td><td>#000000</td></tr>
   *   <tr><td>Grey:  </td><td>#888888</td></tr>
   *   <tr><td>Red:   </td><td>#FF0000</td></tr>
   *   <tr><td>Green: </td><td>#00FF00</td></tr>
   *   <tr><td>Blue:  </td><td>#0000FF</td></tr>
   * </table>
   * </p>
   * 
   * @param color
   */
  public void setBorderColor(Integer color) {
    style.setBorderColor(color);
  }

  /**
   * Not implemented
   * @param visible
   */
  public void setBorderVisible(boolean visible) {
    style.setBorderVisible(visible);
  }

  /**
   * Width of the entire chart area in pixels
   * @param width
   */
  public void setBorderWidth(Integer width) {
    style.setBorderWidth(width);
  }

  public CssStyle getStyle() {
    return style;
  }

}
