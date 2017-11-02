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

import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.reporting.libraries.base.config.Configuration;
import org.pentaho.reporting.libraries.resourceloader.ResourceCreationException;
import org.pentaho.reporting.libraries.resourceloader.ResourceData;
import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceLoadingException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.reporting.libraries.xmlns.parser.AbstractXmlResourceFactory;

/**
 * Returns resource information used in the parsing of the chart XML document.
 *
 * @author David Kincade
 */
public class ChartXmlResourceFactory extends AbstractXmlResourceFactory
{

  /**
   * Default constructor
   */
  public ChartXmlResourceFactory()
  {
  }

  /**
   * Returns the configuration information that contains the
   * list of classes that will be used for parsing the chart XML file.
   */
  protected Configuration getConfiguration()
  {
    return ChartBoot.getInstance().getGlobalConfig();
  }

  /**
   * Returns the class whose fully qualified name will be used as a key
   * in the configuration file to pull out the class name of the factory module
   */
  public Class getFactoryType()
  {
    return ChartDocument.class;
  }

  protected Object finishResult(final Object res,
                                final ResourceManager manager,
                                final ResourceData data,
                                final ResourceKey context) throws ResourceCreationException, ResourceLoadingException
  {
    final ChartDocument doc = (ChartDocument) res;
    doc.setResourceManager(manager);
    if (context != null)
    {
      doc.setResourceKey(context);
    }
    else
    {
      doc.setResourceKey(data.getKey());
    }
    return doc;
  }
}
