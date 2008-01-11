/**
 * 
 */
package org.pentaho.chart.engine;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.chart.api.engine.Output;
import org.pentaho.chart.engine.testdata.TestStyles;
import org.pentaho.chart.plugin.jfreechart.outputs.JFreeChartPngChartOutput;

/**
 * @author gretchen moran
 *
 */
public class ChartUtilTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    TestStyles.setUp();
  }

  @Test
  public final void testMakeBarLineChart() {

    Output outHandler = new JFreeChartPngChartOutput("chart.png");
    ChartUtil.makeBarLineChart(null, TestStyles.COMBINATION_CHART_STYLES, outHandler);

    try {
      outHandler.persist();
    } catch (Exception e) {
      fail();
    }
 }

  @Test
  public final void testMakeAreaChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeBarChart() {
    Output outHandler = new JFreeChartPngChartOutput("barchart.png");
    ChartUtil.makeBarChart(null, TestStyles.BAR_CHART_STYLES, outHandler);

    try {
      outHandler.persist();
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public final void testMakeBubbleChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeDialChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeDifferenceChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeLineChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeMultiPieChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakePieChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeScatterPlotChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeStepAreaChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeStepChart() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public final void testMakeWaterfallChart() {
    fail("Not yet implemented"); // TODO
  }

}
