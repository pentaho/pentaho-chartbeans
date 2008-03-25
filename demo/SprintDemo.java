import junit.framework.TestCase;
import org.pentaho.experimental.chart.ChartBoot;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.ChartPluginFactory;
import org.pentaho.experimental.chart.plugin.IChartPlugin;
import org.pentaho.experimental.chart.plugin.api.IOutput;

import java.net.URL;

/**
 * Demonstrates a simple chart creation
 */
public class SprintDemo extends TestCase {

  public void testCreateChart() throws Exception {
    // Boot the charting API
    ChartBoot.getInstance().start();

    // Generate sample data
    ChartTableModel data = createSampleData();

    // Parse the chart definition
    URL url = this.getClass().getResource("sample.xml"); //$NON-NLS-1$
    ChartDocumentContext cdc = ChartFactory.generateChart(url, data);

    // Get the charting engine to render the chart
    IChartPlugin plugin = ChartPluginFactory.getChartPlugin("org.pentaho.experimental.chart.plugin.jfreechart.JFreeChartPlugin"); //$NON-NLS-1$

    // Specify the output information
    IOutput output = ChartPluginFactory.getChartOutput();
    output.setFilename("demo-output/sample.png"); //$NON-NLS-1$
    output.setFileType(IOutput.FILE_TYPE_PNG);

    // Generate the chart
    plugin.renderChartDocument(cdc.getChartDocument(), data, output);
  }

  // Sample Report Data
  private static final Object [][] SAMPLE_DATA = new Object[][] {
    new Object[] { 27, 18, 35, 46 }, // january
    new Object[] { 14, 42, 17, 23 }, // february
    new Object[] { 37, 44, 28, 36 }, // march
  } ;

  /**
   * Creates and returns sample data
   */
  private ChartTableModel createSampleData()  {
    ChartTableModel data = new ChartTableModel();
    data.setData(SAMPLE_DATA);
    data.setColumnName(0, "North");  //$NON-NLS-1$
    data.setColumnName(1, "South");  //$NON-NLS-1$
    data.setColumnName(2, "East"); //$NON-NLS-1$
    data.setColumnName(3, "West"); //$NON-NLS-1$

    data.setRowMetadata(0, "row-name", "January"); //$NON-NLS-1$ //$NON-NLS-2$
    data.setRowMetadata(1, "row-name", "February"); //$NON-NLS-1$ //$NON-NLS-2$
    data.setRowMetadata(2, "row-name", "March"); //$NON-NLS-1$ //$NON-NLS-2$

    return data;
  }
}
