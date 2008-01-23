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
package org.pentaho.experimental.chart.core;

import java.net.URL;

import junit.framework.TestCase;

import org.jfree.resourceloader.Resource;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.ChartBoot;

public class ParseTest extends TestCase {
  @SuppressWarnings("nls")
  public void testBoot() throws Exception {
    // Boot the charting library
    ChartBoot.getInstance().start();
    
    URL xmlDocument = ParseTest.class.getResource("sample1.xml");
    ResourceManager resourceManager = new ResourceManager();
    resourceManager.registerDefaults();
    Resource res = resourceManager.createDirectly(xmlDocument, ChartDocument.class);
    ChartDocument doc = (ChartDocument)res.getResource();
    System.out.println(doc);
  }
}
