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
package org.pentaho.chart.core.parser;

import org.pentaho.reporting.libraries.xmlns.parser.AbstractXmlResourceFactory;
import org.pentaho.chart.ChartBoot;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.reporting.libraries.base.config.Configuration;

/**
 * Returns resource information used in the parsing of the chart XML document.
 *
 * @author David Kincade
 */
public class ChartXmlResourceFactory extends AbstractXmlResourceFactory {

  /**
   * Default constructor
   */
  public ChartXmlResourceFactory() {
  }

  /**
   * Returns the configuration information that contains the
   * list of classes that will be used for parsing the chart XML file.
   */
  protected Configuration getConfiguration() {
    return ChartBoot.getInstance().getGlobalConfig();
  }

  /**
   * Returns the class whose fully qualified name will be used as a key
   * in the configuration file to pull out the class name of the factory module
   */
  public Class getFactoryType() {
    return ChartDocument.class;
  }
}
