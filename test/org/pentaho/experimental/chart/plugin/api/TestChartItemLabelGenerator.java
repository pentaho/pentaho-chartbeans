package org.pentaho.experimental.chart.plugin.api;


import junit.framework.TestCase;

import org.jfree.data.category.CategoryDataset;
import org.jfree.resourceloader.ResourceException;
import org.junit.After;
import org.junit.Before;
import org.pentaho.experimental.chart.ChartDocumentContext;
import org.pentaho.experimental.chart.ChartFactory;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.data.ChartTableModel;
import org.pentaho.experimental.chart.plugin.jfreechart.utils.JFreeChartUtils;

public class TestChartItemLabelGenerator extends TestCase {

  private static final String ROW_NAME = "row-name"; //$NON-NLS-1$
  
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }
  
  public void testNulls() throws ResourceException {
    final ChartDocumentContext cdc = ChartFactory.generateChart(getClass().getResource("ItemLabelTest1.xml")); //$NON-NLS-1$
    final ChartDocument cd = cdc.getChartDocument();
    assertNotNull(cd);
    if (cd == null) {
      fail("A null document should never be returned"); //$NON-NLS-1$
    }

    final ChartElement[] seriesElements = cd.getRootElement().findChildrenByName("series"); //$NON-NLS-1$
    ChartTableModel chartData = createChartTableModel(null);
    ChartItemLabelGenerator labelGen = new ChartItemLabelGenerator(seriesElements, chartData);
    assertNotNull(labelGen);
    
    final Object[][] dataArray = {{null, null, null}, {null, 40, 35}, {null, 35, 86}};
    chartData = createChartTableModel(dataArray);
    labelGen = new ChartItemLabelGenerator(seriesElements, chartData);
    assertNotNull(labelGen);
    
    final CategoryDataset categoryDataset = JFreeChartUtils.createDefaultCategoryDataset(chartData, cd);
    
    for(int i=0; i< dataArray.length; i++) {
      for (int j=0; j<dataArray[i].length; j++) {
        labelGen.generateLabel(categoryDataset, i, j);    
      }
    }
  }
  
  private ChartTableModel createChartTableModel(final Object[][] dataArray) {
    final ChartTableModel data = new ChartTableModel();
    data.setData(dataArray);
    data.setColumnName(0, "budget"); //$NON-NLS-1$
    data.setColumnName(1, "sales"); //$NON-NLS-1$
    data.setColumnName(2, "forecast"); //$NON-NLS-1$

    data.setRowMetadata(0, ROW_NAME, "Jan"); //$NON-NLS-1$
    data.setRowMetadata(1, ROW_NAME, "Feb"); //$NON-NLS-1$
    data.setRowMetadata(2, ROW_NAME, "Mar"); //$NON-NLS-1$

    return data;
  }
}
