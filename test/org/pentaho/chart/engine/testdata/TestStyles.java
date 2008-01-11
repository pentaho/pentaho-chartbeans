package org.pentaho.chart.engine.testdata;

import java.awt.Color;
import java.util.HashMap;

import org.jfree.data.category.DefaultCategoryDataset;

public final class TestStyles {

  public static final HashMap <String, Object> COMBINATION_CHART_STYLES = new HashMap <String, Object>();
  public static final HashMap <String, Object> BAR_CHART_STYLES = new HashMap <String, Object>();
  
  public static void setUp(){
    setUpCombinationChartStyles();
    setUpBarChartStyles();
  }
  
  @SuppressWarnings("nls")
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

    COMBINATION_CHART_STYLES.put("axes[0].axis.visible", "true");
    COMBINATION_CHART_STYLES.put("axes[1].axis.visible", "true");
    COMBINATION_CHART_STYLES.put("axes[2].axis.visible", "true");

    COMBINATION_CHART_STYLES.put("axes[0].axis.axisLinePaint", Color.CYAN);
    COMBINATION_CHART_STYLES.put("axes[0].axis.axisLineVisible", "false");
    COMBINATION_CHART_STYLES.put("axes[0].axis.tickLabelPaint", Color.CYAN);
    COMBINATION_CHART_STYLES.put("axes[0].labelPaint", Color.CYAN);

    COMBINATION_CHART_STYLES.put("data[0]", c);
    COMBINATION_CHART_STYLES.put("data[1]", c2);

    COMBINATION_CHART_STYLES.put("title", "Test Setting the Chart Title");

    COMBINATION_CHART_STYLES.put("series[0].renderer.seriesPaint[0]", Color.green);
    COMBINATION_CHART_STYLES.put("series[1].renderer.seriesPaint[1]", Color.red);
    COMBINATION_CHART_STYLES.put("series[2].renderer.seriesPaint[0]", Color.orange);
    COMBINATION_CHART_STYLES.put("series[3].renderer.seriesPaint[1]", Color.pink);


    // This is a bar series
    COMBINATION_CHART_STYLES.put("series[1].renderer.drawBarOutline", "false");
    // This is a line series
    COMBINATION_CHART_STYLES.put("series[1].renderer.drawBarOutline", "false");

    // noProperty doesn't exist
    COMBINATION_CHART_STYLES.put("series[3].noProperty", "true");
    
    // axes[4] doesn't exist
    //  COMBINATION_CHART_STYLES.put("axes[4].doesntexist", "true");
  }
  
  @SuppressWarnings("nls")
  private static void setUpBarChartStyles(){
    
    DefaultCategoryDataset c = new DefaultCategoryDataset();
    c.setValue(25, "Series0", "Category1");
    c.setValue(31, "Series0", "Category2");
    c.setValue(32, "Series0", "Category3");
    c.setValue(12, "Series0", "Category4");

    c.setValue(14, "Series1", "Category1");
    c.setValue(21, "Series1", "Category2");
    c.setValue(11, "Series1", "Category3");
    c.setValue(30, "Series1", "Category4");

    c.setValue(100, "Series2", "Category1");
    c.setValue(131, "Series2", "Category2");
    c.setValue(132, "Series2", "Category3");
    c.setValue(112, "Series2", "Category4");
    
    c.setValue(114, "Series3", "Category1");
    c.setValue(121, "Series3", "Category2");
    c.setValue(111, "Series3", "Category3");
    c.setValue(130, "Series3", "Category4");

    // There is no physical bean structure defined for a 5th series...
    c.setValue(5, "Series4", "Category1");
    c.setValue(7, "Series4", "Category2");
    c.setValue(8, "Series4", "Category3");
    c.setValue(1, "Series4", "Category4");

    BAR_CHART_STYLES.put("axes[0].axis.visible", "true");
    BAR_CHART_STYLES.put("axes[0].tickMarkLabelAngle",  90);
    //BAR_CHART_STYLES.put("axes[1].visible", "true");
    BAR_CHART_STYLES.put("axes[1].tickMarkLabelAngle", 90);
       
    BAR_CHART_STYLES.put("data[0]", c);

    BAR_CHART_STYLES.put("title", "Test Setting the Chart Title");

    BAR_CHART_STYLES.put("series[0].renderer.seriesPaint[0]", Color.MAGENTA);
    BAR_CHART_STYLES.put("series[1].renderer.seriesPaint[1]", Color.BLUE);
    BAR_CHART_STYLES.put("series[2].renderer.seriesPaint[2]", Color.PINK);
    BAR_CHART_STYLES.put("series[3].renderer.seriesPaint[3]", Color.YELLOW);

    BAR_CHART_STYLES.put("series[1].renderer.drawBarOutline", "false");
  }
  
}
