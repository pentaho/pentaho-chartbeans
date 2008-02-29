package org.pentaho.experimental.chart.plugin.jfreechart.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueChangeClosure;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.pentaho.experimental.chart.plugin.api.engine.Chart;
import org.pentaho.experimental.chart.plugin.api.engine.Series;

public class CombinationChartBean extends BaseJFreeChartBean implements Chart {

  private static final long serialVersionUID = 5571955028933118201L;
  // TODO:
  // Datasets need to be wrapped as beans
  private CategoryDataset[] data;

  public CombinationChartBean() {

  }

  public void createDefaultChart() {

    NumberAxis linesAxis = new NumberAxis();

    CategoryPlot plot = new CategoryPlot(null, new CategoryAxis(), new NumberAxis(), new BarRenderer());

    plot.setRenderer(1, new LineAndShapeRenderer());
    plot.setRangeAxis(1, linesAxis);
    plot.mapDatasetToRangeAxis(1, 1);
    plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
    plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);

    chart = new JFreeChart(plot);
    
    axes = new AxisBean[3];
    series = new SeriesBean[4];
    data = new CategoryDataset[2];

    for (int i = 0; i < axes.length; i++) {
      axes[i] = new AxisBean();
    }

    ((AxisBean) axes[0]).setAxis(plot.getDomainAxis());
    ((AxisBean) axes[1]).setAxis(plot.getRangeAxis(0));
    ((AxisBean) axes[2]).setAxis(plot.getRangeAxis(1));

    series[0] = new SeriesBean();
    series[0].setType(SeriesBean.Type.BAR);
    series[0].setIndex(0);
    series[1] = new SeriesBean();
    series[1].setType(SeriesBean.Type.BAR);
    series[1].setIndex(1);
    series[2] = new SeriesBean();
    series[2].setType(SeriesBean.Type.LINE);
    series[2].setIndex(0);
    series[3] = new SeriesBean();
    series[3].setType(SeriesBean.Type.LINE);
    series[3].setIndex(1);

    // Filter series by BAR type
    BeanPropertyValueEqualsPredicate predicate = new BeanPropertyValueEqualsPredicate("type", SeriesBean.Type.BAR);

    List <Series> bars = new ArrayList <Series> (Arrays.asList(series));
    CollectionUtils.filter(bars, predicate);

    // Now set the BAR renderer on all BAR series
    BeanPropertyValueChangeClosure closure = new BeanPropertyValueChangeClosure("renderer", plot.getRenderer(0));

    CollectionUtils.forAllDo(bars, closure);

    // Filter series by LINE
    predicate = new BeanPropertyValueEqualsPredicate("type", SeriesBean.Type.LINE);

    List <Series> lines = new ArrayList <Series> (Arrays.asList(series));
    CollectionUtils.filter(lines, predicate);

    // Now set the LINE renderer on all LINE series
    closure = new BeanPropertyValueChangeClosure("renderer", plot.getRenderer(1));

    CollectionUtils.forAllDo(lines, closure);

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
