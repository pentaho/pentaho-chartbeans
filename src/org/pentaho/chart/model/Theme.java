package org.pentaho.chart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.Graph;
import org.pentaho.chart.model.PiePlot;
import org.pentaho.chart.model.Series;
import org.pentaho.chart.model.PiePlot.Wedge;

public class Theme implements Serializable {
  public enum ChartTheme{THEME1, THEME2};
  
  String id;
  List<Integer> colors = new ArrayList<Integer>();
  
  public void applyTo(ChartModel chartModel) {
    if (chartModel.getPlot() instanceof Graph) {
      applyTo((Graph) chartModel.getPlot());
    } else if (chartModel.getPlot() instanceof PiePlot) {
      applyTo((PiePlot) chartModel.getPlot());
    }
  }

  private void applyTo(Graph graph) {
    int i = 0;
    for (Integer color : getColors()) {
        if (i < graph.getSeries().size()) {
          graph.getSeries().get(i).setForegroundColor(color);
        } else {
          Series series = new Series();
          series.setForegroundColor(color);
          graph.getSeries().add(series);
        }
      i++;
    }
  }

  private void applyTo(PiePlot piePlot) {
    int i = 0;
    for (Integer color : getColors()) {
        if (i < piePlot.getWedges().size()) {
          piePlot.getWedges().get(i).setForegroundColor(color);
        } else {
          Wedge wedge = new Wedge();
          wedge.setForegroundColor(color);
          piePlot.getWedges().add(wedge);
        }
      i++;
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
