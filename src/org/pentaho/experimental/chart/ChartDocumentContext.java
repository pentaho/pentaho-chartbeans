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
 * Created 2/8/2008 
 * @author David Kincade 
 */
package org.pentaho.experimental.chart;

import org.jfree.resourceloader.ResourceKey;
import org.jfree.resourceloader.ResourceKeyCreationException;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.core.ChartSeriesDataLinkInfo;
import org.pentaho.reporting.libraries.css.dom.DocumentContext;
import org.pentaho.reporting.libraries.css.dom.StyleReference;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;
import org.pentaho.reporting.libraries.css.namespace.DefaultNamespaceCollection;
import org.pentaho.reporting.libraries.css.namespace.NamespaceCollection;

import java.awt.*;
import java.util.ArrayList;

/**
 * The <code>DocumentContext</code> for the Charting systen.
 *
 * @author David Kincade
 */
public class ChartDocumentContext implements DocumentContext {
  private final ResourceManager resourceManager;
  private final ChartDocument chartDocument;
  private final ResourceKey resourceKey;
  private final DefaultNamespaceCollection namespaceCollection;

  private ChartSeriesDataLinkInfo dataLinkInfo;

  /**
   * List of classes that can be loaded as resources from the style information
   */
  private static final Class[] SUPPORTED_TYPES = new Class[]{Image.class};

  /**
   * Constructs a chart document context based on a chart document
   */
  public ChartDocumentContext(final ChartDocument chart) throws ResourceKeyCreationException {
    // Save the information from the chart
    this.chartDocument = chart;
    this.resourceManager = chart.getResourceManager();
    this.resourceKey = chart.getResourceKey();

    // Setup the namespace collection
    namespaceCollection = new DefaultNamespaceCollection();
    namespaceCollection.addDefinition(new ChartNamespaceDefinition(this.resourceManager));
  }

  /**
   * Returns the ChartDocument
   */
  public ChartDocument getChartDocument() {
    return chartDocument;
  }

  /**
   * Returns the style references for the chart.
   *
   * @return an array of <code>StyleReferences</code> which contain the style information extracted
   *         from the chart definition. The objects are in the same order as they are encountered in the
   *         chart definition file.
   */
  public StyleReference[] getStyleReferences() {
    return createStyleReferences(chartDocument);
  }

  /**
   * Returns the resource manager that is used to load externally referenced resources.
   * Such resources can be either images, drawable or other stylesheets. In some cases, this might even
   * reference whole documents.
   * <p/>
   * The implementation should indicate which document types can be loaded using the
   * {@link ChartDocumentContext#getSupportedResourceTypes()} method.
   *
   * @return the resource manager.
   * @see ChartDocumentContext#getSupportedResourceTypes()
   */
  public ResourceManager getResourceManager() {
    return resourceManager;
  }

  /**
   * Returns the context key provides the base-key for resolving relative
   * URLs. Usually it is the key that was used to parse the document. Without this key, it would be
   * impossible to resolve non-absolute URLs/paths into a usable URL or path.
   *
   * @return the context key
   * @see ResourceManager#deriveKey(ResourceKey, String)
   */
  public ResourceKey getContextKey() {
    return resourceKey;
  }

  /**
   * Returns the list of supported resource types that can be loaded as external resources.
   *
   * @return the supported resource types.
   * @see ResourceManager#create(ResourceKey, ResourceKey, Class[])
   */
  public Class[] getSupportedResourceTypes() {
    return (Class[]) SUPPORTED_TYPES.clone();
  }

  /**
   * Returns information about the known namespaces. This allows the system to recognize 'class' and 'style' attributes
   * for each defined namespace.
   *
   * @return the defines namespaces.
   * @see org.pentaho.reporting.libraries.css.namespace.NamespaceDefinition
   * @see NamespaceCollection#getDefinition(String)
   */
  public NamespaceCollection getNamespaces() {
    return namespaceCollection;
  }

  /**
   * Returns the style-key registry that holds all known stylekeys that might be encountered during the
   * parsing.
   *
   * @return the stylekey registry to use.
   */
  public StyleKeyRegistry getStyleKeyRegistry() {
    return StyleKeyRegistry.getRegistry();
  }

  /**
   * Creats a list of <code>StyleReference</code> objects based off the information in the chart document
   * NOTE: this method is protected for unit testing only
   *
   * @param chartDocument the chart document from which the style references will be extracted
   * @return a list of ordered <code>StyleReferences</code>. The order in the list will be the same as the
   *         order the style information was encountered in the chart document.
   */
  protected StyleReference[] createStyleReferences(final ChartDocument chartDocument) {
    // Get the set of top-level document items which contain style sheet information
    final ChartElement[] styleSheetElements = chartDocument.getRootElement().findChildrenByName(ChartElement.TAG_NAME_STYLESHEET);

    // The list of StyleReferences created from the styleSheetElements
    final ArrayList<StyleReference> styleReferenceList = new ArrayList<StyleReference>(styleSheetElements.length);

    // Process the list of style sheet chart definitions
    for (int i = 0; i < styleSheetElements.length; ++i) {
      // Get the URL from the href attributes
      final String hrefText = (String) styleSheetElements[i].getAttribute("href");
      if (hrefText != null) {
        final StyleReference linkStyleReference = new StyleReference(StyleReference.LINK, hrefText);
        styleReferenceList.add(linkStyleReference);
      }

      // Get the style sheet information that was contained in the tag itself as text
      final String styleSheetText = styleSheetElements[i].getText();
      if (styleSheetText != null) {
        final StyleReference inlineStyleReference = new StyleReference(StyleReference.INLINE, styleSheetText);
        styleReferenceList.add(inlineStyleReference);
      }
    }

    // Return the list of StyleReferences
    return styleReferenceList.toArray(new StyleReference[styleReferenceList.size()]);
  }

  public ChartSeriesDataLinkInfo getDataLinkInfo() {
    return dataLinkInfo;
  }

  public void setDataLinkInfo(final ChartSeriesDataLinkInfo dataLinkInfo) {
    this.dataLinkInfo = dataLinkInfo;
  }

}
