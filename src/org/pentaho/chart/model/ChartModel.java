package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.chart.model.Theme.ChartTheme;

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

  public void setChartEngineId(String id) {
    this.chartEngineId = id;
  }

  public ChartTheme getTheme() {
    return theme;
  }

  public void setTheme(ChartTheme theme) {
    this.theme = theme;
  }

  public List<StyledText> getSubtitles() {
    return subtitles;
  }
  
  public void setSubtitles(List<StyledText> subtitles) {
    subtitles.clear();
    if (subtitles != null) {
      subtitles.addAll(subtitles);
    }
  }
  
  public StyledText getSubtitle() {
    return subtitles.size() > 0 ? subtitles.get(0) : null;
  }

  public void setSubtitle(StyledText title) {
    subtitles.clear();
    if (title != null) {
      subtitles.add(title);
    }
  }

  public ChartLegend getLegend() {
    return legend;
  }

  public void setLegend(ChartLegend chartLegend) {
    this.legend = chartLegend;
  }

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
  
  public Plot getPlot() {
    return plot;
  }

  public void setPlot(Plot plot) {
    this.plot = plot;
  }

  public ChartTitle getTitle() {
    return title;
  }

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

  public void setBorderColor(Integer color) {
    style.setBorderColor(color);
  }

  public void setBorderVisible(boolean visible) {
    style.setBorderVisible(visible);
  }

  public void setBorderWidth(Integer width) {
    style.setBorderWidth(width);
  }

  public CssStyle getStyle() {
    return style;
  }

}
