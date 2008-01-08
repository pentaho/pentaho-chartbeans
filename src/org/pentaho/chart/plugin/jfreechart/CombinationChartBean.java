package org.pentaho.chart.plugin.jfreechart;

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
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.pentaho.chart.api.engine.Axis;
import org.pentaho.chart.api.engine.Chart;
import org.pentaho.chart.api.engine.Series;

public class CombinationChartBean extends BaseJFreeChartBean implements Chart {

  private Axis[] axes;

  private Series[] series;

  // TODO:
  // Datasets need to be wrapped as beans
  private CategoryDataset[] data;

  public CombinationChartBean() {
    chart = new JFreeChart(new CategoryPlot());
    axes = new AxisBean[3];
    series = new SeriesBean[4];
    data = new CategoryDataset[2];

    CategoryAxis catAxis = new CategoryAxis();
    NumberAxis barsAxis = new NumberAxis();
    NumberAxis linesAxis = new NumberAxis();

    for (int i = 0; i < axes.length; i++) {
      axes[i] = new AxisBean();
    }

    ((AxisBean)axes[0]).setAxis(catAxis);
    ((AxisBean)axes[1]).setAxis(barsAxis);
    ((AxisBean)axes[2]).setAxis(linesAxis);

    series[0] = new BarSeriesBean();
    series[0].setIndex(0);
    series[1] = new BarSeriesBean();
    series[1].setIndex(1);
    series[2] = new LineSeriesBean();
    series[2].setIndex(0);
    series[3] = new LineSeriesBean();
    series[3].setIndex(1);

    
    // Filter series by BAR type
    BeanPropertyValueEqualsPredicate predicate = new BeanPropertyValueEqualsPredicate("type", SeriesBean.Type.BAR);

    List bars = new ArrayList(Arrays.asList(series));
    CollectionUtils.filter(bars, predicate);

    // Now set the BAR renderer on all BAR series
    BarRenderer br = new BarRenderer();
    BeanPropertyValueChangeClosure closure = new BeanPropertyValueChangeClosure("renderer", br);

    CollectionUtils.forAllDo(bars, closure);

    // Filter series by LINE
    predicate = new BeanPropertyValueEqualsPredicate("type", SeriesBean.Type.LINE);

    List lines = new ArrayList(Arrays.asList(series));
    CollectionUtils.filter(lines, predicate);

    // Now set the LINE renderer on all LINE series
    LineAndShapeRenderer lr = new LineAndShapeRenderer();
    closure = new BeanPropertyValueChangeClosure("renderer", lr);

    CollectionUtils.forAllDo(lines, closure);

    //Map axes and renderers
    CategoryPlot plot = (CategoryPlot) chart.getPlot();

    plot.setDomainAxis(catAxis);
    plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

    plot.setRangeAxis(0, barsAxis);
    plot.setRenderer(0, br);
    
    plot.setRangeAxis(1, linesAxis);
    plot.mapDatasetToRangeAxis(1, 1);
    plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
    plot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_RIGHT);
    plot.setRenderer(1, lr);

  }

  public String getTitle() {
    return chart.getTitle().getText();
  }

  public void setTitle(String title) {
    if (chart.getTitle() == null) {
      TextTitle tt = new TextTitle();
      chart.setTitle(tt);
    }
    chart.getTitle().setText(title);
  }

  /**
   * @return the axes
   */
  public final Axis[] getAxes() {
    return axes;
  }

  public final Axis getAxes(int index) {
    return axes[index];
  }

  /**
   * @param axes the axes to set
   */
  public final void setAxes(Axis[] axes) {
    this.axes = axes;
  }

  public final void setAxes(int index, Axis axis) {
    axes[index] = axis;
  }

  /**
   * @return the series
   */
  public final Series[] getSeries() {
    return series;
  }

  public final Series getSeries(int index) {
    return series[index];
  }

  /**
   * @param series the series to set
   */
  public final void setSeries(Series[] series) {
    this.series = series;
  }

  public final void setSeries(int index, Series series) {
    this.series[index] = series;
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
