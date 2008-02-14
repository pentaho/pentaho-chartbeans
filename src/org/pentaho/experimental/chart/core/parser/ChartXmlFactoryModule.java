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

import org.jfree.xmlns.parser.XmlDocumentInfo;
import org.jfree.xmlns.parser.XmlFactoryModule;
import org.jfree.xmlns.parser.XmlReadHandler;

/**
 * The factory class that will return the correct document read handler to the XML document parsing system.
 *
 * @author David Kincade
 */
public class ChartXmlFactoryModule implements XmlFactoryModule {
  public static final String NAMESPACE = "http://reporting.pentaho.org/namespaces/charting/1.0";

  public ChartXmlFactoryModule() {
  }

  /**
   * Indicates how this factory recognizes the XML document. Since this factory will read any XML document
   * as a chart document, it will use the strongest recognition possible.
   *
   * @param documentInfo the document information used to recognize this document
   * @return an indicator of how this document is recogniszed
   */
  public int getDocumentSupport(final XmlDocumentInfo documentInfo) {
    return RECOGNIZED_BY_TAGNAME;
  }

  /**
   * Returns the default namespace to be used with this XML document based on the document information.
   */
  public String getDefaultNamespace(final XmlDocumentInfo documentInfo) {
    return NAMESPACE;
  }

  /**
   * Returns the XML parsing handler based on the document info. All chart XML documents will be parsed
   * with the <code>ChartDocumentReadHandler</code>.
   *
   * @param documentInfo the document inforation used to determine which read handlers to return
   * @return the read handler for the specified document type
   */
  public XmlReadHandler createReadHandler(final XmlDocumentInfo documentInfo) {
    return new ChartDocumentReadHandler();
  }
}
