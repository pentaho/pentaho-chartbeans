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

import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceKeyCreationException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;
import org.pentaho.reporting.libraries.base.config.Configuration;
import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.pentaho.reporting.libraries.css.namespace.NamespaceDefinition;

/**
 * Defines the namespace used with the charting API.
 *
 * @author David Kincade
 */
public class ChartNamespaceDefinition implements NamespaceDefinition {
  private ResourceKey chartStyleSheetKey;
  private String preferredPrefix;
  private String uri;
  private String[] classAttributeNames;
  private String[] styleAttributeNames;

  private static final String PREFERRED_PREFIX = "org.pentaho.chart.namespace.prefix"; //$NON-NLS-1$
  private static final String URI = "org.pentaho.chart.namespace.uri"; //$NON-NLS-1$
  private static final String CLASS_ATTRIBUTES = "org.pentaho.chart.namespace.class_attributes"; //$NON-NLS-1$
  private static final String STYLE_ATTRIBUTES = "org.pentaho.chart.namespace.style_attributes"; //$NON-NLS-1$
  private static final String CHART_STYLE_LOCATION = "org.pentaho.chart.namespace.style"; //$NON-NLS-1$

  private static final String DEFAULT_PREFERRED_PREFIX = "";
  private static final String DEFAULT_URI = "http://reporting.pentaho.org/namespaces/charting/1.0"; //$NON-NLS-1$
  private static final String DEFAULT_CLASS_ATTRIBUTE_NAMES = "class"; //$NON-NLS-1$
  private static final String DEFAULT_STYLE_ATTRIBUTE_NAMES = "style"; //$NON-NLS-1$
  private static final String DEFAULT_CHART_STYLE_LOCATION = "res://org/pentaho/chart/css/chart.css"; //$NON-NLS-1$

  public ChartNamespaceDefinition(final ResourceManager resourceManager) throws ResourceKeyCreationException {
    // Save the resource manager
    if (resourceManager == null) {
      throw new IllegalArgumentException();
    }

    // Load the information from the chart properties file
    final Configuration config = ChartBoot.getInstance().getGlobalConfig();
    preferredPrefix = config.getConfigProperty(ChartNamespaceDefinition.PREFERRED_PREFIX, ChartNamespaceDefinition.DEFAULT_PREFERRED_PREFIX);
    uri = config.getConfigProperty(ChartNamespaceDefinition.URI, ChartNamespaceDefinition.DEFAULT_URI);
    classAttributeNames = StringUtils.split(config.getConfigProperty(ChartNamespaceDefinition.CLASS_ATTRIBUTES, ChartNamespaceDefinition.DEFAULT_CLASS_ATTRIBUTE_NAMES));
    styleAttributeNames = StringUtils.split(config.getConfigProperty(ChartNamespaceDefinition.STYLE_ATTRIBUTES, ChartNamespaceDefinition.DEFAULT_STYLE_ATTRIBUTE_NAMES));

    // Load the chart style information and retain the key
    final String chartStyleLocation = config.getConfigProperty(ChartNamespaceDefinition.CHART_STYLE_LOCATION, ChartNamespaceDefinition.DEFAULT_CHART_STYLE_LOCATION);
    chartStyleSheetKey = resourceManager.createKey(chartStyleLocation);
  }

  public String getPreferredPrefix() {
    return preferredPrefix;
  }

  public String getURI() {
    return uri;
  }

  public String[] getClassAttribute(final String element) {
    return classAttributeNames.clone();
  }

  public String[] getStyleAttribute(final String element) {
    return styleAttributeNames.clone();
  }

  public ResourceKey getDefaultStyleSheetLocation() {
    return chartStyleSheetKey;
  }
}
