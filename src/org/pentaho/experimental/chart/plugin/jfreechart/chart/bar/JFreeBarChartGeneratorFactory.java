package org.pentaho.experimental.chart.plugin.jfreechart.chart.bar;

import org.jfree.chart.JFreeChart;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartBarStyle;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.reporting.libraries.css.values.CSSValue;

/**
 * Generate a particular type of bar chart based on the bar style mentioned in the current chart document. 
 * </p>
 * Author: Ravi Hasija
 * Date: May 14, 2008
 * Time: 1:18:42 PM 
 */
public class JFreeBarChartGeneratorFactory {
  public JFreeChart createChart(final ChartDocumentContext chartDocContext,
                                final ChartTableModel data) {
    boolean stacked = false;
    boolean stackedPct = false;
    boolean cylinder = false;
    boolean interval = false;
    boolean layered = false;
    boolean stacked100Pct = false;

    final ChartDocument chartDocument = chartDocContext.getChartDocument();
    final ChartElement[] elements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_SERIES);
    for (final ChartElement element : elements) {
      final CSSValue value = element.getLayoutStyle().getValue(ChartStyleKeys.BAR_STYLE);
      stacked |= value.equals(ChartBarStyle.STACKED);
      stackedPct |= value.equals(ChartBarStyle.STACK_PERCENT);
      cylinder |= value.equals(ChartBarStyle.CYLINDER);
      interval |= value.equals(ChartBarStyle.INTERVAL);
      layered |= value.equals(ChartBarStyle.LAYERED);
      stacked100Pct |= value.equals(ChartBarStyle.STACK_100_PERCENT);

      // Pick the first one that is set.
      if (stacked || stackedPct || stacked100Pct || cylinder || interval || layered) {
        break;
      }
    }

    final JFreeBarChartGenerator barChartGenerator;

    if (cylinder) {
      barChartGenerator = new JFreeCylinderBarChartGenerator();
    } else if (layered) {
      barChartGenerator = new JFreeLayeredBarChartGenerator();
    } else if (stacked) {
      barChartGenerator = new JFreeStackedBarChartGenerator();
    } else if (stackedPct) {
      barChartGenerator = new JFreeStackedPercentBarChartGenerator();
    } else if (stacked100Pct) {
      barChartGenerator = new JFreeStacked100PercentBarChartGenerator();
    } else {
      barChartGenerator = new JFreeDefaultBarChartGenerator();
    }

    return barChartGenerator.createChart(chartDocContext, data);
  }
}
