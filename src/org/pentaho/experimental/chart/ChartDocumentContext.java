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

import org.jfree.resourceloader.ResourceException;
import org.jfree.resourceloader.ResourceKey;
import org.jfree.resourceloader.ResourceManager;
import org.pentaho.experimental.chart.core.ChartDocument;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.reporting.libraries.css.dom.DocumentContext;
import org.pentaho.reporting.libraries.css.dom.StyleReference;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;
import org.pentaho.reporting.libraries.css.namespace.NamespaceCollection;

import java.util.ArrayList;

/**
 * The <code>DocumentContext</code> for the Charting systen.
 *
 * @author David Kincade
 */
public class ChartDocumentContext implements DocumentContext {
  private final ResourceManager resourceManager;
  private final ChartDocument chartDocument;

  /**
   * Constructs a chart document context based on a chart document
   */
  public ChartDocumentContext(final ResourceManager resourceManager, final ChartDocument chartDocument) throws ResourceException {
    // Setup the resource manager
    this.resourceManager = resourceManager;

    // Parse the chart definition
    this.chartDocument = chartDocument;
  }

  /**
   * Returns the style references for the chart
   *
   * @return an array of <code>StyleReferences</code> which contain the style information extracted
   *         from the chart definition. The objects are in the same order as they are encountered in the
   *         chart definition file.
   */
  public StyleReference[] getStyleReferences() {
      return createStyleReferences(chartDocument);
  }

  /**
   * Returns the <code>ResourceManager</code> used by this document context.
   */
  public ResourceManager getResourceManager() {
    return resourceManager;
  }

  public ResourceKey getContextKey() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public StyleKeyRegistry getStyleKeyRegistry() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public Class[] getSupportedResourceTypes() {
    return new Class[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  public NamespaceCollection getNamespaces() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  /**
   * Creats a list of <code>StyleReference</code> objects based off the information in the chart document
   * NOTE: this method is protected for unit testing only
   *
   * @param chartDocument the chart document from which the style references will be extracted
   * @return a list of ordered <code>StyleReferences</code>. The order in the list will be the same as the
   *         order the style information was encountered in the chart document.
   */
  protected StyleReference[] createStyleReferences(ChartDocument chartDocument) {
    // Get the set of top-level document items which contain style sheet information
    final ChartElement[] styleSheetElements = chartDocument.getRootElement().findChildrenByName(ChartElement.STYLESHEET_TAGNAME);

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
}
