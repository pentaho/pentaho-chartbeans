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

public abstract class Plot implements Serializable {
  
  public static final Palette DEFAULT_PALETTE = new Palette(
    0x006666, 0x0066CC, 0x009999, 0x336699, 0x339966, 0x3399FF,
    0x663366, 0x666666, 0x666699, 0x669999, 0x6699CC, 0x66CCCC,
    0x993300, 0x990033, 0x999966, 0x999999, 0x9999CC, 0x9999FF,
    0x99CC33, 0x99CCCC, 0x99CCFF, 0xCC6600, 0xCC9933, 0xCCCC33,
    0xCCCC66, 0xCCCC99, 0xCCCCCC, 0xFF9900, 0xFFCC00, 0xFFCC66);
  
  Palette palette = new Palette(DEFAULT_PALETTE);
  
  public enum Orientation {VERTICAL, HORIZONTAL};
  
  protected Orientation orientation = Orientation.VERTICAL;
  protected String backgroundImageLocation;
  protected Texture backgroundTexture;
  protected Gradient backgroundGradient;
  protected CssStyle style = new CssStyle();
  
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

  public Orientation getOrientation() {
    return orientation;
  }

  /**
   * Orientation of the chart.
   * <p>
   * <table>
   *   <tr><th>Possible values</th></tr>
   *   <tr><td>vertical</td></tr>
   *   <tr><td>horizontal</td></tr>
   * </table>
   * </p>
   * @param orientation
   */
  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  public Palette getPalette() {
    return palette;
  }

  /**
   * A Palette is a list of RGB colors in Hex format (i.e. - #ffffff)
   * @param colorPalette
   */
  public void setPalette(Palette colorPalette) {
    this.palette = colorPalette;
  }

  public Float getOpacity() {
    return style.getOpacity();
  }

  /**
   * {@link org.pentaho.chart.model.CssStyle#setOpacity(Float)}
   * @param opacity
   */
  public void setOpacity(Float opacity) {
    style.setOpacity(opacity);
  }
  
  public CssStyle getStyle() {
    return style;
  }

}
