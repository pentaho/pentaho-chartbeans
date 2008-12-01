/*
 * Copyright 2008 Pentaho Corporation.  All rights reserved. 
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
 * Created  
 * @author David Kincade
 */
package org.pentaho.experimental.chart.core.parser;

import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;

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
