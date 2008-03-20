package org.pentaho.experimental.chart.plugin.jfreechart;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.PersistenceException;
import org.pentaho.experimental.chart.plugin.api.engine.Chart;
import org.pentaho.experimental.chart.plugin.api.engine.ChartFactoryEngine;
import org.pentaho.experimental.chart.plugin.api.engine.Series;
import org.pentaho.experimental.chart.plugin.jfreechart.beans.BarChartBean;
import org.pentaho.experimental.chart.plugin.jfreechart.beans.CombinationChartBean;
import org.pentaho.experimental.chart.plugin.jfreechart.beans.SeriesBean;

public class JFreeChartFactoryEngine implements ChartFactoryEngine, Serializable {
  
  private static final long serialVersionUID = -1079376910255750394L;

  public JFreeChartFactoryEngine(){
  }
  
  public ChartFactoryEngine getInstance() {
    return this;
  }

  public void makeAreaChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {
    // TODO Auto-generated method stub

  }

  public void makeBarChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {
    BarChartBean chartBean = new BarChartBean();
    chartBean.createDefaultChart();
//    Series series[] = createSeriesBeans(chartDocument);
//    chartBean.setSeries(series);
    
    try {
      
      BeanUtils.populate(chartBean, null);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    DefaultCategoryDataset[] categoryDataSet = createCategoryDataset(data, chartDocument);
    chartBean.setData(categoryDataSet);

    outHandler.setChart(chartBean);
    try {
      outHandler.persist();
    } catch (PersistenceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


  public void makeBarLineChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

    Chart chartBean = new CombinationChartBean();
    chartBean.createDefaultChart();
    
    // TODO: figure out data loading
    
    try {
      // TODO populate styles from chartDocument
      BeanUtils.populate(chartBean, null);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    
    outHandler.setChart(chartBean);
    try {
      outHandler.persist();
    } catch (PersistenceException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void makeBubbleChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makeDialChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makeDifferenceChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makeLineChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makeMultiPieChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makePieChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makeScatterPlotChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makeStepAreaChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makeStepChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  public void makeWaterfallChart(ChartTableModel data, ChartDocument chartDocument, IOutput outHandler) {

  }

  /**
   * @return
   */
  private DefaultCategoryDataset[] createCategoryDataset(ChartTableModel data, ChartDocument chartDoc) {
    DefaultCategoryDataset[] value = new DefaultCategoryDataset[1];
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for(int row=0; row<data.getRowCount(); row++) {
      for(int column=0; column<data.getColumnCount(); column++) {
        Comparable<?> columnName = data.getColumnName(column) == null ? column : data.getColumnName(column);
        Comparable<?> rowName = (Comparable<?>) (data.getRowMetadata(row, "row-name") == null ? row : data.getRowMetadata(row, "row-name"));
        dataset.setValue((Number) data.getValueAt(row, column), rowName, columnName);
      }
    }
    
    value[0] = dataset;
    
    return value;
  }

  /**
   * @param chartDocument
   * @return
   */
  private Series[] createSeriesBeans(ChartDocument chartDocument) {
    ChartElement[] seriesElements = chartDocument.getRootElement().findChildrenByName("series");
    Series[] values = new Series[seriesElements.length];
    for (int i=0; i<seriesElements.length; i++) {
      SeriesBean seriesBean = new SeriesBean();
      
      values[i] = seriesBean;
    }
    return values;
  }


}
