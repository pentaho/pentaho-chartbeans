package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Plot implements Serializable {
  
  static final Palette DEFAULT_PALETTE = new Palette(
    0x006666, 0x0066CC, 0x009999, 0x336699, 0x339966, 0x3399FF,
    0x663366, 0x666666, 0x666699, 0x669999, 0x6699CC, 0x66CCCC,
    0x993300, 0x990033, 0x999966, 0x999999, 0x9999CC, 0x9999FF,
    0x99CC33, 0x99CCCC, 0x99CCFF, 0xCC6600, 0xCC9933, 0xCCCC33,
    0xCCCC66, 0xCCCC99, 0xCCCCCC, 0xFF9900, 0xFFCC00, 0xFFCC66);
  
  Palette palette = new Palette(DEFAULT_PALETTE);
  
  public enum Orientation {VERTICAL, HORIZONTAL};
  
  Orientation orientation = Orientation.VERTICAL;
  String backgroundImageLocation;
  Texture backgroundTexture;
  Gradient backgroundGradient;
  CssStyle style = new CssStyle();
  
  public Object getBackground() {
    Object background = null;
    if (getBackground() != null) {
      background = getBackground();
    } else if (backgroundGradient != null) {
      background = backgroundGradient;
    } else if (backgroundImageLocation != null) {
      background = backgroundImageLocation;
    } else if (backgroundTexture != null) {
      background = backgroundTexture;
    }
    return background;
  }

  public void setBackground(Integer backgroundColor) {
    style.setBackgroundColor(backgroundColor);
    if (backgroundColor != null) {
      backgroundGradient = null;
      backgroundImageLocation = null;
      backgroundTexture = null;
    }
  }

  public void setBackground(String backgroundImageLocation) {
    this.backgroundImageLocation = backgroundImageLocation;
    if (backgroundImageLocation != null) {
      backgroundGradient = null;
      setBackground((Integer)null);
      backgroundTexture = null;
    }
  }
  
  public void setBackground(Gradient backgroundGradient) {
    this.backgroundGradient = backgroundGradient;
    if (backgroundGradient != null) {
      setBackground((Integer)null);
      backgroundImageLocation = null;
      backgroundTexture = null;
    }
  }
  
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

  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }

  public Palette getPalette() {
    return palette;
  }

  public void setPalette(Palette colorPalette) {
    this.palette = colorPalette;
  }

  public Float getOpacity() {
    return style.getOpacity();
  }

  public void setOpacity(Float opacity) {
    style.setOpacity(opacity);
  }
  
  public CssStyle getStyle() {
    return style;
  }

}
