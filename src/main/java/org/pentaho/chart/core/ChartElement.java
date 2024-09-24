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

package org.pentaho.chart.core;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.pentaho.reporting.libraries.base.util.Empty;
import org.pentaho.reporting.libraries.base.util.StringUtils;
import org.pentaho.reporting.libraries.css.dom.DefaultLayoutStyle;
import org.pentaho.reporting.libraries.css.dom.LayoutElement;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.model.StyleKey;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.util.collections.HeirarchicalLinkedListItem;
import org.pentaho.reporting.libraries.xmlns.common.AttributeMap;
import org.pentaho.reporting.libraries.xmlns.LibXmlInfo;

/**
 * Defines the element information for the elements in the chart definition file
 */
public class ChartElement extends HeirarchicalLinkedListItem implements Cloneable, LayoutElement {
  /**
   * The name attribute which defines the element name (if specified)
   */
  public static final String NAME_ATTRIBUTE = "name";//$NON-NLS-1$

  /**
   * The ID attribute which defines an element's id (in the xml namespace)
   */
  public static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

  /**
   * See XML-Namespaces for the idea of that one ...
   */
  public static final String NAMESPACE_ATTRIBUTE = "namespace"; //$NON-NLS-1$

  /**
   * The namespace for all the charting items
   */
  public static final String NAMESPACE = "http://reporting.pentaho.org/namespaces/charting/1.0"; //$NON-NLS-1$

  /**
   * Chart Definition tag for the base chart
   */
  public static final String TAG_NAME_CHART = "chart"; //$NON-NLS-1$

  /**
   * Chart Definition tag for the range label
   */
  public static final String TAG_NAME_RANGE_LABEL = "rangeLabel"; //$NON-NLS-1$
  
  /**
   * Chart Definition tag for the range label
   */
  public static final String TAG_NAME_DOMAIN_LABEL = "domainLabel"; //$NON-NLS-1$
  
  /**
   * The name of the XML tag which represents the CSS information
   */
  public static final String TAG_NAME_STYLESHEET = "stylesheet"; //$NON-NLS-1$

  /**
   * The name of the XML tag which represents the chart title
   */
  public static final String TAG_NAME_TITLE = "title"; //$NON-NLS-1$

  /**
   * The name of the XML tag which represents the chart legend
   */
  public static final String TAG_NAME_LEGEND = "legend"; //$NON-NLS-1$

  /**
   * The name of the XML tag which represents the chart axis
   */
  public static final String TAG_NAME_AXIS = "axis"; //$NON-NLS-1$

  /**
   * The name of the XML tag which represents the chart plot
   */
  public static final String TAG_NAME_PLOT = "plot"; //$NON-NLS-1$

  /**
   * The name of the XML tag which represents a series in the chart
   */
  public static final String TAG_NAME_SERIES = "series"; //$NON-NLS-1$

  /**
   * The name of the XML tag which represents a data grouping for the chart
   */
  public static final String TAG_NAME_GROUP = "group"; //$NON-NLS-1$

  /**
   * The name of the XML tag which represents a tick label in the chart
   */
  public static final String TAG_NAME_LABEL = "label"; //$NON-NLS-1$

  /**
    * The name of the XML tag which represents a label in the chart
    */
   public static final String TAG_NAME_TICK_LABEL = "ticklabel"; //$NON-NLS-1$
  
  /**
   * Attribute name for specifying a chart's data should be represented categorically
   */
  public static final String CATEGORICAL = "categorical"; //$NON-NLS-1$

  /**
   * Attribute name for specifying a chart's data should be interpreted "byrow"
   */
  public static final String BYROW = "byrow"; //$NON-NLS-1$

  /**
   * Attributes nam for specifying a taq's user specified id (used in referencing one tag from another tag)
   */
  public static final String ID = "id"; //$NON-NLS-1$

  /**
   * Attribute name for making an external reference
   */
  public static final String HREF = "href"; //$NON-NLS-1$

  /**
   * Attribute name used to refer to a column in the data positionally (starting with column 0)
   */
  public static final String COLUMN_POSITION = "column-pos"; //$NON-NLS-1$

  /**
   * Attribute name used to refer to a row in the data positionally (starting with row 0) (Used for Pie Charts)
   */
  public static final String ROW_POSITION = "row-pos"; //$NON-NLS-1$

  /**
   * Attribute name used to refer to a column in the data by column name
   */
  public static final String COLUMN_NAME = "column-name"; //$NON-NLS-1$

  /**
   * Attribute name used to mark a group as stacked instead of a normal groupingmv 
   */
  public static final String STACKED = "stacked"; //$NON-NLS-1$

  /**
   * The attributes of this element. These attributes are namespaced, therefore
   */
  private AttributeMap attributes;

  /**
   * The name of the tag that this element represents
   */
  private String tagName;

  /**
   * The text contents of this element.
   */
  private String text;

  /**
   * The layout style for this element
   */
  private final DefaultLayoutStyle layoutStyle;

  /**
   * Constant used when generating the deep <code>toString</code> representation
   */
  private static final String TO_STRING_PREFIX = "  "; //$NON-NLS-1$

  /**
   * Constructs an element.
   * <p/>
   * The element inherits the element's defined default ElementStyleSheet to
   * provide reasonable default values for common stylekeys. When the element is
   * added to the band, the bands stylesheet is set as parent to the element's
   * stylesheet.
   * <p/>
   * A datasource is assigned with this element is set to a default source,
   * which always returns null.
   */
  public ChartElement() {
    this.attributes = new AttributeMap();
    setNamespace(ChartElement.NAMESPACE);
    this.layoutStyle = new DefaultLayoutStyle();

    // Mark this item as modified
    markModified();
  }

  /**
   * Indicates if style information contained in the attributes has been resolved into 
   *
   * @return <code>true</code> if this <code>ChartElement</code> contains style information,
   *         <code>false</code> otherwise
   */
  public boolean isStyleResolved() {
    return this.layoutStyle.isEmpty() == false;
  }

  /**
   */
  public String getNamespace() {
    return (String) getAttribute(ChartElement.NAMESPACE, ChartElement.NAMESPACE_ATTRIBUTE);
  }

  /**
   */
  public void setNamespace(final String id) {
    setAttribute(ChartElement.NAMESPACE, ChartElement.NAMESPACE_ATTRIBUTE, id);

    // Mark this item as modified
    markModified();
  }

  /**
   * Returns the id for this chart element
   */
  public String getId() {
    return (String) getAttribute(ChartElement.NAMESPACE, ChartElement.ID_ATTRIBUTE);
  }

  /**
   * Sets the id for this chart elements
   */
  public void setId(final String id) {
    setAttribute(LibXmlInfo.XML_NAMESPACE, ChartElement.ID_ATTRIBUTE, id);

    // Mark this item as modified
    markModified();
  }

  /**
   * Returns the tag name that this element represents
   */
  public String getTagName() {
    return tagName;
  }

  /**
   * Sets the tagname for this element.
   */
  public void setTagName(final String tagName) {
    if (StringUtils.isEmpty(tagName)) {
      throw new NullPointerException();
    }
    this.tagName = tagName.trim();

    // Mark this item as modified
    markModified();
  }

  /**
   * Defines the name for this Element. The name must not be empty, or a
   * NullPointerException is thrown.
   * <p/>
   * Names can be used to lookup an element. There is no requirement for element names to be unique.
   *
   * @param name the name of this element
   */
  public void setName(final String name) {
    setAttribute(LibXmlInfo.XML_NAMESPACE, ChartElement.NAME_ATTRIBUTE, name);

    // Mark this item as modified
    markModified();
  }

  /**
   * Returns the name of the Element. The name of the Element is never null.
   *
   * @return the name.
   */
  public String getName() {
    return (String) getAttribute(LibXmlInfo.XML_NAMESPACE, ChartElement.NAME_ATTRIBUTE);
  }

  /**
   * @param name
   * @param value
   */
  public void setAttribute(final String name, final Object value) {
    setAttribute(getNamespace(), name, value);

    // Mark this item as modified
    markModified();
  }

  /**
   * @param namespace
   * @param name
   * @param value
   */
  public void setAttribute(final String namespace, final String name, final Object value) {
    if (StringUtils.isEmpty(name)) {
      throw new NullPointerException();
    }
    if (attributes == null) {
      this.attributes = new AttributeMap();
    }
    this.attributes.setAttribute(namespace, name.trim(), value);

    // Mark this item as modified
    markModified();
  }

  /**
   * @param name
   * @return
   */
  public Object getAttribute(final String name) {
    return getAttribute(getNamespace(), name);
  }

  /**
   * @param namespace
   * @param name
   * @return
   */
  public Object getAttribute(final String namespace, final String name) {
    if (this.attributes == null) {
      return null;
    }
    return this.attributes.getAttribute(namespace, name);
  }

  /**
   * @param namespace
   * @return
   */
  public Map getAttributes(final String namespace) {
    if (this.attributes == null) {
      return Empty.MAP;
    }
    return this.attributes.getAttributes(namespace);
  }

  /**
   * Returns a copy of the attributes for this chart element
   */
  public AttributeMap getAttributeMap() {
    return new AttributeMap(this.attributes);
  }

  /**
   * Returns a set of namespaces used in the attributes for this chart elements
   */
  public String[] getAttributeNameSpaces() {
    if (this.attributes == null) {
      return Empty.STRING_ARRAY;
    }
    return this.attributes.getNameSpaces();
  }

  /**
   *
   */
  public Object clone() throws CloneNotSupportedException {
    final ChartElement element = (ChartElement) super.clone();
//    element.style = (CSSStyleRule) style.clone();
    if (attributes != null) {
      element.attributes = (AttributeMap) attributes.clone();
    }
    return element;
  }

  public Map getCounters() {
    return Empty.MAP;
  }

  public Locale getLanguage() {
    return Locale.getDefault();
  }

  public LayoutStyle getLayoutStyle() {
    return layoutStyle;
  }

  public String getPseudoElement() {
    return null;
  }

  public Map getStrings() {
    return Empty.MAP;
  }

  public boolean isPseudoElement() {
    return false;
  }

  public LayoutElement getPreviousLayoutElement() {
    return getPreviousItem();
  }

  public LayoutElement getParentLayoutElement() {
    return getParentItem();
  }

  public void addChildElement(final ChartElement chartElement) {
    addChildItem(chartElement); // sets modification flag
  }

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;

    // Mark this item as modified
    markModified();
  }

  /**
   * Dumps the content of this ChartElement
   */
  public String toString() {
    final StringBuffer sb = new StringBuffer();

    // Dump this object
    sb.append(getClass().getName()).append(" {").append("tagName=[").append(tagName).append("]");//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
    sb.append(" attributes=[").append(attributes).append("]");//$NON-NLS-1$//$NON-NLS-2$
    if (text != null) {
      sb.append(" text=[").append(text).append("]");//$NON-NLS-1$//$NON-NLS-2$
    }
    sb.append("}");//$NON-NLS-1$
    return sb.toString();
  }

  /**
   * Performs a "deep" toString() where this element and all the children of this element are included
   *
   * @param prefix used for indenting the output
   */
  public String toString(final String prefix) {
    final StringBuffer sb = new StringBuffer();
    sb.append(prefix).append(toString()).append("\n");//$NON-NLS-1$
    ChartElement child = getFirstChildItem();
    while (child != null) {
      sb.append("\n").append(child.toString(prefix + ChartElement.TO_STRING_PREFIX));//$NON-NLS-1$
      child = child.getNextItem();
    }
    return sb.toString();
  }

  /**
   * Returns the parent <code>ChartElement</code> for the current <code>ChartElement</code>.
   * If this <code>ChartElement</code> is the root element of the document, this method
   * will return <code>null</code>
   */
  public ChartElement getParentItem() {
    return (ChartElement) super.getParentItem();
  }

  /**
   * Returns the <code>ChartElement</code> that is previous to this element. If there is none,
   * this method will return <code>null</code>
   */
  public ChartElement getPreviousItem() {
    return (ChartElement) super.getPreviousItem();
  }

  /**
   * Returns the <code>ChartElement</code> this follows this element. If there is none,
   * this method will return <code>null</code>
   */
  public ChartElement getNextItem() {
    return (ChartElement) super.getNextItem();
  }

  /**
   * Returns the first <code>ChartElement</code> that is a child of this <code>ChartElement</code>.
   * If this <code>ChartElement</code> does not have any children, this method will return <code>null</code>.
   */
  public ChartElement getFirstChildItem() {
    return (ChartElement) super.getFirstChildItem();
  }

  /**
   * Returns the last <code>ChartElement</code> that is a child of this <code>ChartElement</code>.
   * If this <code>ChartElement</code> does not have any children, this method will return <code>null</code>.
   */
  public ChartElement getLastChildItem() {
    return (ChartElement) super.getLastChildItem();
  }

  /**
   * Returns the next <code>ChartElement</code> based on a "depth-first" search. This will return
   * (in the following order)
   * <ol>
   * <li>the first child of this <code>ChartElement</code>
   * <li>the "next" sibling of this <code>ChartElement</code>
   * <li>the "next" sibling of the closest parent of this <code>ChartElement</code>
   * <li><code>null</code> (indicates that this is the last <code>ChartElements</code> from a depth-first point of view
   * </ol>
   */
  public ChartElement getNextDepthFirstItem() {
    return (ChartElement) super.getNextDepthFirstItem();
  }

  /**
   * Returns all child <code>ChartElements</code> to this <code>ChartElement</code> which
   * has the specified <code>tagName</code>.
   *
   * @param tagNameToMatch the name of the tag used in searching
   * @return an array of <code>ChartElement</code> objects that match the search criteria
   */
  public ChartElement[] findChildrenByName(final String tagNameToMatch) {
    // Holder for the elements that match
    final ArrayList<ChartElement> matchList = new ArrayList<ChartElement>();

    // If there is no tag name specified, then nothing will match
    if (tagNameToMatch != null) {
      // "Have you checked the children lately"
      for (ChartElement item = getFirstChildItem(); item != null; item = item.getNextItem()) {
        // See if this item matches
        if (tagNameToMatch.equals(item.getTagName())) {
          matchList.add(item);
        }
      }
    }

    // Return the list as an array
    return matchList.toArray(new ChartElement[matchList.size()]);
  }

  /**
   * Quick accessor method to return the style value for the style key for this chart element
   */
  public CSSValue getStyle(final StyleKey key) {
    CSSValue result = null;
    if (key != null) {
      result = getLayoutStyle().getValue(key);
    }
    return result;
  }
}

