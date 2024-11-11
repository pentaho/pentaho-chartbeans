/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart.core.parser;

import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;

/**
 * Used in the parsing of the chart structure xml document. This is the first document read handler that is
 * called when the parsing starts. It will create a new <code>ChartDocument</code> and initialize it with
 * the top-most <code>ChartElement</code>,
 *
 * @author David Kincade
 */
public class ChartDocumentReadHandler extends ChartElementReadHandler {

  /**
   * Creates a default <code>ChartDocumentReadHandler</code>.
   */
  public ChartDocumentReadHandler() {
  }

  /**
   * Returns the <code>ChartDocument</code> which contains all the parsed <code>ChartElements</code>
   */
  public Object getObject() {
    return new ChartDocument((ChartElement) super.getObject());
  }

}
