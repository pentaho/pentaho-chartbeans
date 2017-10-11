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

import org.pentaho.reporting.libraries.base.boot.AbstractBoot;
import org.pentaho.reporting.libraries.base.config.Configuration;
import org.pentaho.reporting.libraries.base.versioning.ProjectInformation;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;

@SuppressWarnings({"StaticNonFinalField"})
public class ChartBoot extends AbstractBoot {
  private static ChartBoot instance=null;

  public static synchronized ChartBoot getInstance() {
    if (ChartBoot.instance == null) {
      ChartBoot.instance = new ChartBoot();
    }
    return ChartBoot.instance;
  }

  private ChartBoot() {
  }

  @SuppressWarnings("nls")
  public Configuration loadConfiguration() {
    return createDefaultHierarchicalConfiguration(
        "/org/pentaho/chart/chart.properties", // Hard coded default values that shoulnd't be changed by user
        "/chart.properties", // User changable values (these override above)
        true,
        ChartBoot.class);
  }

  protected void performBoot() {
    // nothing required. Just gather the configuration.
  }

  protected ProjectInformation getProjectInfo() {
    return ChartInfo.getInstance();
  }
}
