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


package org.pentaho.chart;

import org.pentaho.reporting.libraries.resourceloader.LibLoaderInfo;
import org.pentaho.reporting.libraries.xmlns.LibXmlInfo;
import org.pentaho.reporting.libraries.base.LibBaseInfo;
import org.pentaho.reporting.libraries.base.versioning.ProjectInformation;
import org.pentaho.reporting.libraries.css.LibCssInfo;

public class ChartInfo extends ProjectInformation {

  private static ChartInfo info;

  public static ProjectInformation getInstance()
  {
    if (info == null)
    {
      info = new ChartInfo();
      info.initialize();
    }
    return info;
  }

  @SuppressWarnings("nls")
  private ChartInfo() {
    super("chartbundle", "ChartBundle");
  }

  private ChartInfo initialize() {

    setBootClass(ChartBoot.class.getName());
    setLicenseName("LGPL");
    setInfo("http://pentaho.org/chartbundle/");
    setCopyright("(C)opyright 2008-2011, by Pentaho Corporation and Contributors");

    addLibrary(LibBaseInfo.getInstance());
    addLibrary(LibLoaderInfo.getInstance());
    addLibrary(LibXmlInfo.getInstance());
    addLibrary(LibCssInfo.getInstance());

    return this;
  }
}
