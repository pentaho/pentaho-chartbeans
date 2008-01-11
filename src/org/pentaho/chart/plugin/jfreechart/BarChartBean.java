package org.pentaho.chart.plugin.jfreechart;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanPropertyValueChangeClosure;
import org.apache.commons.collections.CollectionUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.pentaho.chart.api.engine.Chart;
import org.pentaho.chart.api.engine.Series;

public class BarChartBean extends BaseJFreeChartBean implements Chart {
  
  private static final long serialVersionUID = 8494570750310363779L;
  // TODO:
  // Datasets need to be wrapped as beans
  private CategoryDataset[] data;


  public BarChartBean() {
  }


  public void createDefaultChart(){

    CategoryPlot plot = new CategoryPlot(null, new CategoryAxis(), new NumberAxis(), new BarRenderer());

    chart = new JFreeChart(plot);
    
    axes = new AxisBean[2];
    series = new SeriesBean[4];
    data = new CategoryDataset[1];

    axes[0] = new CategoryAxisBean();
    axes[1] = new AxisBean();

    ((AxisBean) axes[0]).setAxis(plot.getDomainAxis());
    ((AxisBean) axes[1]).setAxis(plot.getRangeAxis());

    series[0] = new SeriesBean();
    series[0].setIndex(0);
    series[1] = new SeriesBean();
    series[1].setIndex(1);
    series[2] = new SeriesBean();
    series[2].setIndex(2);
    series[3] = new SeriesBean();
    series[3].setIndex(3);

    // Now set the BAR renderer on all BAR series
    BeanPropertyValueChangeClosure closure = new BeanPropertyValueChangeClosure("renderer", plot.getRenderer()); //$NON-NLS-1$

    // Now set the BAR type on all BAR series
    BeanPropertyValueChangeClosure typeClosure = new BeanPropertyValueChangeClosure("type", SeriesBean.Type.BAR); //$NON-NLS-1$

    ArrayList <Series> operationalList = new ArrayList <Series>(Arrays.asList(series));
    CollectionUtils.forAllDo(operationalList, closure);
    CollectionUtils.forAllDo(operationalList, typeClosure);
  }
  /**
   * @return the data
   */
  public final CategoryDataset[] getData() {
    return data;
  }

  public final CategoryDataset getData(int index) {
    return data[index];
  }

  /**
   * @param data the data to set
   */
  public final void setData(CategoryDataset[] data) {
    this.data = data;
  }

  public final void setData(int index, CategoryDataset data) {
    this.data[index] = data;
    ((CategoryPlot) chart.getPlot()).setDataset(index, data);
  }

}
