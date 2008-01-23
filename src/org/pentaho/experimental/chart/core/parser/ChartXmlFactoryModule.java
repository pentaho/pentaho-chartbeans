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

public class ChartXmlFactoryModule implements XmlFactoryModule {
  public static final String NAMESPACE = "http://reporting.pentaho.org/namespaces/charting/1.0";

  public ChartXmlFactoryModule() {
  }

  public int getDocumentSupport(final XmlDocumentInfo documentInfo) {
    return RECOGNIZED_BY_TAGNAME;
  }

  public String getDefaultNamespace(final XmlDocumentInfo documentInfo) {
    return NAMESPACE;
  }

  public XmlReadHandler createReadHandler(final XmlDocumentInfo documentInfo) {
    return new ChartDocumentReadHandler();
  }
}
