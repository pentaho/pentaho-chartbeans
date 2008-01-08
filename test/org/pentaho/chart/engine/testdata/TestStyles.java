package org.pentaho.chart.engine.testdata;

import java.awt.Color;
import java.util.HashMap;

import org.jfree.data.category.DefaultCategoryDataset;

public final class TestStyles {

  public static final HashMap COMBINATION_CHART_STYLES = new HashMap();
  
  public static void setUp(){
    setUpCombinationChartStyles();
  }
  
  private static void setUpCombinationChartStyles(){
    
    DefaultCategoryDataset c = new DefaultCategoryDataset();
    c.setValue(25, "Series0", "Category1");
    c.setValue(31, "Series0", "Category2");
    c.setValue(32, "Series0", "Category3");
    c.setValue(12, "Series0", "Category4");

    c.setValue(14, "Series1", "Category1");
    c.setValue(21, "Series1", "Category2");
    c.setValue(11, "Series1", "Category3");
    c.setValue(30, "Series1", "Category4");

    DefaultCategoryDataset c2 = new DefaultCategoryDataset();
    c2.setValue(100, "Series2", "Category1");
    c2.setValue(131, "Series2", "Category2");
    c2.setValue(132, "Series2", "Category3");
    c2.setValue(112, "Series2", "Category4");

    c2.setValue(114, "Series3", "Category1");
    c2.setValue(121, "Series3", "Category2");
    c2.setValue(111, "Series3", "Category3");
    c2.setValue(130, "Series3", "Category4");

    // There is no physical bean structure defined for a 5th series...
    c2.setValue(5, "Series4", "Category1");
    c2.setValue(7, "Series4", "Category2");
    c2.setValue(8, "Series4", "Category3");
    c2.setValue(1, "Series4", "Category4");

    COMBINATION_CHART_STYLES.put("axes[0].visible", "true");
    COMBINATION_CHART_STYLES.put("axes[1].visible", "true");
    COMBINATION_CHART_STYLES.put("axes[2].visible", "true");

    COMBINATION_CHART_STYLES.put("data[0]", c);
    COMBINATION_CHART_STYLES.put("data[1]", c2);

    COMBINATION_CHART_STYLES.put("title", "Test Setting the Chart Title");

    COMBINATION_CHART_STYLES.put("series[0].color", Color.green);
    COMBINATION_CHART_STYLES.put("series[1].color", Color.red);
    COMBINATION_CHART_STYLES.put("series[2].color", Color.orange);
    COMBINATION_CHART_STYLES.put("series[3].color", Color.pink);

    // This is a bar series: it has no shapesVisible setter or getter
    COMBINATION_CHART_STYLES.put("series[1].shapesVisible", "true");
    
    // This series could legitimately have its shapesVisible property set
    COMBINATION_CHART_STYLES.put("series[2].shapesVisible", "false");

    // noProperty doesn't exist
    COMBINATION_CHART_STYLES.put("series[3].noProperty", "true");
    
    // axes[4] doesn't exist
  //  COMBINATION_CHART_STYLES.put("axes[4].doesntexist", "true");

  }
  
}
