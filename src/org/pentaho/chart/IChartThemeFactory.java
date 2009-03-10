package org.pentaho.chart;

import java.util.List;

import org.pentaho.chart.model.Theme;
import org.pentaho.chart.model.Theme.ChartTheme;

public interface IChartThemeFactory {
  Theme getTheme(ChartTheme chartTheme);
  List<Theme> getThemes();
}
