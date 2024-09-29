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
