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
