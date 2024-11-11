/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.chart.plugin.api;


import junit.framework.TestCase;
import org.jfree.data.category.DefaultCategoryDataset;
import org.pentaho.chart.ChartBoot;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.junit.After;
import org.junit.Before;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.ChartFactory;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.api.ChartItemLabelGenerator;
import org.pentaho.chart.plugin.jfreechart.dataset.DatasetGeneratorFactory;

public class ChartItemLabelGeneratorIT extends TestCase {

  @Before
  public void setUp() throws Exception {
    super.setUp();

    // Boot the charting library - required for parsing configuration
    ChartBoot.getInstance().start();
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

    DatasetGeneratorFactory datasetGenFac = new DatasetGeneratorFactory();
    DefaultCategoryDataset categoryDataset = datasetGenFac.createDefaultCategoryDataset(cdc, chartData);
    assertNotNull(categoryDataset);


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

    data.setRowName(0, "Jan"); //$NON-NLS-1$
    data.setRowName(1, "Feb"); //$NON-NLS-1$
    data.setRowName(2, "Mar"); //$NON-NLS-1$

    return data;
  }
}
