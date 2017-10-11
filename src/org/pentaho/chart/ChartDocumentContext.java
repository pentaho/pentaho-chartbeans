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

import java.awt.Image;
import java.util.ArrayList;

import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.core.ChartSeriesDataLinkInfo;
import org.pentaho.reporting.libraries.css.dom.DocumentContext;
import org.pentaho.reporting.libraries.css.dom.StyleReference;
import org.pentaho.reporting.libraries.css.dom.LayoutOutputMetaData;
import org.pentaho.reporting.libraries.css.model.StyleKeyRegistry;
import org.pentaho.reporting.libraries.css.namespace.DefaultNamespaceCollection;
import org.pentaho.reporting.libraries.css.namespace.NamespaceCollection;
import org.pentaho.reporting.libraries.resourceloader.ResourceKey;
import org.pentaho.reporting.libraries.resourceloader.ResourceKeyCreationException;
import org.pentaho.reporting.libraries.resourceloader.ResourceManager;

/**
 * The <code>DocumentContext</code> for the Charting systen.
 *
 * @author David Kincade
 */
public class ChartDocumentContext implements DocumentContext
{
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
  public ChartDocumentContext(final ChartDocument chart) throws ResourceKeyCreationException
  {
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
  public ChartDocument getChartDocument()
  {
    return chartDocument;
  }

  /**
   * Returns the style references for the chart.
   *
   * @return an array of <code>StyleReferences</code> which contain the style information extracted
   *         from the chart definition. The objects are in the same order as they are encountered in the
   *         chart definition file.
   */
  public StyleReference[] getStyleReferences()
  {
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
  public ResourceManager getResourceManager()
  {
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
  public ResourceKey getContextKey()
  {
    return resourceKey;
  }

  /**
   * Returns the list of supported resource types that can be loaded as external resources.
   *
   * @return the supported resource types.
   * @see ResourceManager#create(ResourceKey, ResourceKey, Class[])
   */
  public Class[] getSupportedResourceTypes()
  {
    return ChartDocumentContext.SUPPORTED_TYPES.clone();
  }

  /**
   * Returns information about the known namespaces. This allows the system to recognize 'class' and 'style' attributes
   * for each defined namespace.
   *
   * @return the defines namespaces.
   * @see org.pentaho.reporting.libraries.css.namespace.NamespaceDefinition
   * @see NamespaceCollection#getDefinition(String)
   */
  public NamespaceCollection getNamespaces()
  {
    return namespaceCollection;
  }

  /**
   * Returns the style-key registry that holds all known stylekeys that might be encountered during the
   * parsing.
   *
   * @return the stylekey registry to use.
   */
  public StyleKeyRegistry getStyleKeyRegistry()
  {
    return StyleKeyRegistry.getRegistry();
  }

  public LayoutOutputMetaData getOutputMetaData()
  {
    return null;
  }

  /**
   * Creats a list of <code>StyleReference</code> objects based off the information in the chart document
   * NOTE: this method is protected for unit testing only
   *
   * @param chartDoc the chart document from which the style references will be extracted
   * @return a list of ordered <code>StyleReferences</code>. The order in the list will be the same as the
   *         order the style information was encountered in the chart document.
   */
  //TODO: Do we really need chart document as a parameter here?
  protected StyleReference[] createStyleReferences(final ChartDocument chartDoc)
  {
    // Get the set of top-level document items which contain style sheet information
    final ChartElement[] styleSheetElements = chartDoc.getRootElement().findChildrenByName(ChartElement.TAG_NAME_STYLESHEET);

    // The list of StyleReferences created from the styleSheetElements
    final ArrayList<StyleReference> styleReferenceList = new ArrayList<StyleReference>(styleSheetElements.length);

    // Process the list of style sheet chart definitions
    final int elementsLength = styleSheetElements.length;
    for (int i = 0; i < elementsLength; ++i)
    {
      // Get the URL from the href attributes
      final String hrefText = (String) styleSheetElements[i].getAttribute("href");//$NON-NLS-1$
      if (hrefText != null)
      {
        final StyleReference linkStyleReference = new StyleReference(StyleReference.LINK, hrefText);
        styleReferenceList.add(linkStyleReference);
      }

      // Get the style sheet information that was contained in the tag itself as text
      final String styleSheetText = styleSheetElements[i].getText();
      if (styleSheetText != null)
      {
        final StyleReference inlineStyleReference = new StyleReference(StyleReference.INLINE, styleSheetText);
        styleReferenceList.add(inlineStyleReference);
      }
    }

    // Return the list of StyleReferences
    return styleReferenceList.toArray(new StyleReference[styleReferenceList.size()]);
  }

  public ChartSeriesDataLinkInfo getDataLinkInfo()
  {
    return dataLinkInfo;
  }

  public void setDataLinkInfo(final ChartSeriesDataLinkInfo dataLinkInfo)
  {
    this.dataLinkInfo = dataLinkInfo;
  }

}
