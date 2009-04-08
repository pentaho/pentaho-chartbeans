package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Theme implements Serializable {
  public enum ChartTheme{THEME1, THEME2, THEME3, THEME4, THEME5, THEME6};
  
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
