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
