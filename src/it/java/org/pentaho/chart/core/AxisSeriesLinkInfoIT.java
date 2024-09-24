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

package org.pentaho.chart.core;

import java.net.URL;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.chart.core.AxisSeriesLinkInfo;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.core.parser.ChartXMLParser;

/**
 * Tests the AxisSeriesLinkInfo class
 *
 * @author Ravi Hasija
 *
 */
public class AxisSeriesLinkInfoIT extends TestCase {
  /**
   *
   * @param chartDocFileName
   * @return
   */
  private ChartDocument getChartDoc(final String chartDocFileName) {
    ChartDocument chartDoc = null;
    // Read the XML document and prepare for the test
    try {
      final ChartXMLParser chartParser = new ChartXMLParser();
      final URL chartXmlDocument = this.getClass().getResource(chartDocFileName);
      chartDoc = chartParser.parseChartDocument(chartXmlDocument);
      if (chartDoc == null) {
        fail("A null document should never be returned");//$NON-NLS-1$
      }
    } catch (ResourceException re) {
      System.out.println(re.getLocalizedMessage());
    }

    return chartDoc;
  }

  /*
   * No range or domain axis element present at all.
   */
  public final void testNoAxisElement() {
    ChartDocument chartDoc = getChartDoc("AxisSeriesLinkInfoTest1.xml"); //$NON-NLS-1$

    AxisSeriesLinkInfo axisLinkInfo = chartDoc.getAxisSeriesLinkInfo();
    assertNotNull(axisLinkInfo);

    ArrayList<ChartElement> domainAxisElem = axisLinkInfo.getDomainAxisElements();
    assertEquals(0, domainAxisElem.size());
    ArrayList<ChartElement> rangeAxisElem = axisLinkInfo.getRangeAxisElements();
    assertEquals(0, rangeAxisElem.size());

    assertEquals(null, axisLinkInfo.getAxisElement("junk")); //$NON-NLS-1$

    ChartElement dummyElement = new ChartElement();
    axisLinkInfo.setAxisElement("axisElement1", dummyElement); //$NON-NLS-1$
    assertEquals(dummyElement, axisLinkInfo.getAxisElement("axisElement1")); //$NON-NLS-1$

    ArrayList<ChartElement> seriesElements = axisLinkInfo.getSeriesElements("axis_range1"); //$NON-NLS-1$
    assertEquals(null, seriesElements);
    seriesElements = axisLinkInfo.getSeriesElements("axis_range2"); //$NON-NLS-1$
    assertEquals(null, seriesElements);
  }

  /*
   * One Range and One Domain axis element, one required range axis element is missing
   */
  public final void test_OneRange_OneDomain() {
    ChartDocument chartDoc = getChartDoc("AxisSeriesLinkInfoTest2.xml"); //$NON-NLS-1$

    AxisSeriesLinkInfo axisLinkInfo = chartDoc.getAxisSeriesLinkInfo();
    assertNotNull(axisLinkInfo);

    ArrayList<ChartElement> domainAxisElem = axisLinkInfo.getDomainAxisElements();
    assertEquals(1, domainAxisElem.size());
    ArrayList<ChartElement> rangeAxisElem = axisLinkInfo.getRangeAxisElements();
    assertEquals(1, rangeAxisElem.size());

    assertNotNull(axisLinkInfo.getAxisElement("axis_range1")); //$NON-NLS-1$
    assertEquals(null, axisLinkInfo.getAxisElement("axis_range2")); //$NON-NLS-1$

    ArrayList<ChartElement> seriesElements = axisLinkInfo.getSeriesElements("axis_range1"); //$NON-NLS-1$
    assertEquals(2, seriesElements.size());
    seriesElements = axisLinkInfo.getSeriesElements("axis_range2"); //$NON-NLS-1$
    assertEquals(1, seriesElements.size());
  }

  /*
   * Requisiste range and domain axis are present
   */
  public final void test_TwoRange_OneDomain() {
    ChartDocument chartDoc = getChartDoc("AxisSeriesLinkInfoTest3.xml"); //$NON-NLS-1$

    AxisSeriesLinkInfo axisLinkInfo = chartDoc.getAxisSeriesLinkInfo();
    assertNotNull(axisLinkInfo);

    // Check domain axis elements array list
    ArrayList<ChartElement> domainAxisElemList = axisLinkInfo.getDomainAxisElements();
    assertEquals(1, domainAxisElemList.size());
    assertNotNull(domainAxisElemList.get(0));

    // Check range axis elements array list
    ArrayList<ChartElement> rangeAxisElemList = axisLinkInfo.getRangeAxisElements();
    assertEquals(2, rangeAxisElemList.size());
    assertNotNull(rangeAxisElemList.get(0));
    assertNotNull(rangeAxisElemList.get(1));

    // Check if you can retrieve axis elements by id
    ChartElement rangeAxisElem = axisLinkInfo.getAxisElement("axis_range1"); //$NON-NLS-1$
    assertNotNull(rangeAxisElem);
    assertEquals("Country", rangeAxisElem.getAttribute("label")); //$NON-NLS-1$ //$NON-NLS-2$

    // Check if you can retrieve axis elements by id
    rangeAxisElem = axisLinkInfo.getAxisElement("axis_range2"); //$NON-NLS-1$
    assertNotNull(rangeAxisElem);
    assertEquals("State", rangeAxisElem.getAttribute("label")); //$NON-NLS-1$ //$NON-NLS-2$

    // Check if you can retrieve series elements for each axis element
    ArrayList<ChartElement> seriesElements = axisLinkInfo.getSeriesElements("axis_range1"); //$NON-NLS-1$
    assertEquals(2, seriesElements.size());
    Integer obj = Integer.parseInt((String)seriesElements.get(0).getAttribute("column-pos"));//$NON-NLS-1$
    assertEquals(new Integer(2), obj);
    assertEquals("sales", seriesElements.get(1).getAttribute("column-name")); //$NON-NLS-1$ //$NON-NLS-2$

    // Check if you can retrieve series elements for each axis element
    seriesElements = axisLinkInfo.getSeriesElements("axis_range2"); //$NON-NLS-1$
    assertEquals(1, seriesElements.size());
  }
}
