/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.chart.core.parser;

import org.pentaho.reporting.libraries.xmlns.parser.XmlDocumentInfo;
import org.pentaho.reporting.libraries.xmlns.parser.XmlFactoryModule;
import org.pentaho.reporting.libraries.xmlns.parser.XmlReadHandler;

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
    return XmlFactoryModule.RECOGNIZED_BY_TAGNAME;
  }

  /**
   * Returns the default namespace to be used with this XML document based on the document information.
   */
  public String getDefaultNamespace(final XmlDocumentInfo documentInfo) {
    return ChartXmlFactoryModule.NAMESPACE;
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
