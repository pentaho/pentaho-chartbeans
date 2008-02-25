/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
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
 * Created 2/12/2008 
 * @author David Kincade 
 */
package org.pentaho.experimental.chart;

import org.jfree.resourceloader.ResourceKey;
import org.jfree.resourceloader.ResourceKeyCreationException;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.reporting.libraries.base.config.Configuration;
import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.pentaho.reporting.libraries.css.namespace.NamespaceDefinition;

/**
 * Defines the namespace used with the charting API.
 *
 * @author David Kincade
 */
public class ChartNamespaceDefinition implements NamespaceDefinition {
  private final ResourceManager resourceManager;
  private ResourceKey chartStyleSheetKey;
  private String preferredPrefix;
  private String uri;
  private String[] classAttributeNames;
  private String[] styleAttributeNames;

  private static final String PREFERRED_PREFIX = "org.pentaho.experimental.chart.namespace.prefix";
  private static final String URI = "org.pentaho.experimental.chart.namespace.uri";
  private static final String CLASS_ATTRIBUTES = "org.pentaho.experimental.chart.namespace.class_attributes";
  private static final String STYLE_ATTRIBUTES = "org.pentaho.experimental.chart.namespace.style_attributes";
  private static final String CHART_STYLE_LOCATION = "org.pentaho.experimental.chart.namespace.style";

  private static final String DEFAULT_PREFERRED_PREFIX = "";
  private static final String DEFAULT_URI = "http://reporting.pentaho.org/namespaces/charting/1.0";
  private static final String DEFAULT_CLASS_ATTRIBUTE_NAMES = "class";
  private static final String DEFAULT_STYLE_ATTRIBUTE_NAMES = "style";
  private static final String DEFAULT_CHART_STYLE_LOCATION = "res://org/pentaho/experimental/chart/css/chart.css";

  public ChartNamespaceDefinition(final ResourceManager resourceManager) throws ResourceKeyCreationException {
    // Save the resource manager
    if (resourceManager == null) {
      throw new IllegalArgumentException();
    }
    this.resourceManager = resourceManager;

    // Load the information from the chart properties file
    Configuration config = ChartBoot.getInstance().getGlobalConfig();
    preferredPrefix = config.getConfigProperty(PREFERRED_PREFIX, DEFAULT_PREFERRED_PREFIX);
    uri = config.getConfigProperty(URI, DEFAULT_URI);
    classAttributeNames = StringUtils.split(config.getConfigProperty(CLASS_ATTRIBUTES, DEFAULT_CLASS_ATTRIBUTE_NAMES));
    styleAttributeNames = StringUtils.split(config.getConfigProperty(STYLE_ATTRIBUTES, DEFAULT_STYLE_ATTRIBUTE_NAMES));

    // Load the chart style information and retain the key
    String chartStyleLocation = config.getConfigProperty(CHART_STYLE_LOCATION, DEFAULT_CHART_STYLE_LOCATION);
    chartStyleSheetKey = resourceManager.createKey(chartStyleLocation);
  }

  public String getPreferredPrefix() {
    return preferredPrefix;
  }

  public String getURI() {
    return uri;
  }

  public String[] getClassAttribute(String element) {
    return classAttributeNames;
  }

  public String[] getStyleAttribute(String element) {
    return styleAttributeNames;
  }

  public ResourceKey getDefaultStyleSheetLocation() {
    return chartStyleSheetKey;
  }
}
