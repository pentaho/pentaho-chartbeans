/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * Created 3/27/2008 
 * @author David Kincade 
 */
package org.pentaho.experimental.chart;

import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs validation on the <code>ChartDocument</code> and ensures it is proper
 * for rendering a chart.
 * @author David Kincade
 */
public class ChartDocumentValidator {

  /**
   * The ChartDocument being validated
   */
  private final ChartDocument chartDoc;

  /**
   * The list of messages that indicate problems with the chart document
   */
  private final List<String> messages = new ArrayList<String>(); 

  /**
   * Constructs a validator for the specified ChartDocument
   * @param chartDoc
   */
  public ChartDocumentValidator(final ChartDocument chartDoc) {
    this.chartDoc = chartDoc;
    revalidate();
  }

  /**
   * Performs the validation process on the chart document
   */
  public void revalidate() {
    // Reset the problem messages
    messages.clear();

    // Perform all the tests (a failure at one level will prevent subsequence levels of test
    // from being performed)

    // TEST LEVEL 1
    if (messages.isEmpty()) {
      validateChartTag();
    }

    // TEST LEVEL 2
    if (messages.isEmpty()) {
      validateSeriesTags();
    }
  }

  private void validateSeriesTags() {
    // Make sure there are one or more series elements
    final List seriesElements = chartDoc.getSeriesChartElements();
    if (seriesElements.size() == 0) {
      messages.add("Could not find any <series> tags as children of the <chart> tag");
    }

  }

  private void validateChartTag() {
    if (chartDoc == null || chartDoc.getRootElement() == null) {
      messages.add("Chart Document is empty");
    } else if (!ChartElement.TAG_NAME_CHART.equals(chartDoc.getRootElement().getTagName())) {
      messages.add("<chart> must be the root chart element");
    }
  }

  /**
   * Returns the number of messages generated from the last validation test
   */
  public int getMessageCount() {
    return messages.size();
  }
}
