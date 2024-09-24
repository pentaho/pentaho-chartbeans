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

import java.net.URL;

import org.pentaho.reporting.libraries.resourceloader.Resource;
import org.pentaho.reporting.libraries.resourceloader.ResourceException;
import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.chart.core.ChartDocument;

/**
 * Parses the chart XML document into the internal representation of the chart document.
 *
 * @author David Kincade
 */
public class ChartXMLParser {
  /**
   * The resource manager used to parse chart definitions
   */
  private final ResourceManager resourceManager;

  /**
   * Initializes the chart parser by creating a default <code>ResourceManager</code>
   */
  public ChartXMLParser() {
    resourceManager = new ResourceManager();
    resourceManager.registerDefaults();
  }

  /**
   * Initializes the chart parser with a <code>ResourceManager</code>
   *
   * @param resourceManager the <code>ResourceManager</code> to use while parsing a chart definition
   */
  public ChartXMLParser(final ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }

  /**
   * Parses the chart definition file into a <code>ChartDocument</code>.
   * NOTE: this method is protected for unit testing
   *
   * @param chartDefinitionURL the location of the chart definition file
   * @return a <code>ChartDocument</code> that was created from the chart definition
   * @throws org.pentaho.reporting.libraries.resourceloader.ResourceException
   *          indeicates an error occurred loading the chart defintion
   */
  public ChartDocument parseChartDocument(final URL chartDefinitionURL) throws ResourceException {
    final Resource res = resourceManager.createDirectly(chartDefinitionURL, ChartDocument.class);
    final ResourceKey key = res.getSource();
    final ChartDocument chart = (ChartDocument) res.getResource();
    chart.setResourceManager(resourceManager);
    chart.setResourceKey(key);
    return chart;
  }

  /**
   * Returns the <code>ResourceManager</code> used by this instance of the chart parser
   */
  public ResourceManager getResourceManager() {
    return resourceManager;
  }
}
