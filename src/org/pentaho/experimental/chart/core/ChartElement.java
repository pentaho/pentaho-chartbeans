/*
 * Copyright 2008 Pentaho Corporation.  All rights reserved. 
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
 * Created  
 * @author David Kincade 
 */
package org.pentaho.experimental.chart.core;

import org.jfree.xmlns.LibXmlInfo;
import org.jfree.xmlns.common.AttributeMap;
import org.pentaho.reporting.libraries.css.dom.DefaultLayoutStyle;
import org.pentaho.reporting.libraries.css.dom.LayoutElement;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.util.Empty;
import org.pentaho.util.collections.HeirarchicalLinkedListItem;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

/**
 * Defines the element information for the elements in the chart definition file
 */
public class ChartElement extends HeirarchicalLinkedListItem implements Cloneable, LayoutElement {
  public static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

  public static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

  /**
   * See XML-Namespaces for the idea of that one ...
   */
  public static final String NAMESPACE_ATTRIBUTE = "namespace"; //$NON-NLS-1$


  public static final String NAMESPACE = "charting-rulez"; //$NON-NLS-1$

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
  private DefaultLayoutStyle layoutStyle;

  /**
   * Constant used when generating the deep <code>toString</code> representation
   */
  private static final String TO_STRING_PREFIX = "  ";
  public static final String STYLESHEET_TAGNAME = "stylesheet";
  public static final String CHART_TAGNAME = "chart";

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
    setNamespace(NAMESPACE);
    this.layoutStyle = new DefaultLayoutStyle();
  }

  /**
   * Indicates if style information has been set on the <code>ChartElement</code>
   *
   * @return <code>true</code> if this <code>ChartElement</code> contains style information,
   *         <code>false</code> otherwise
   */
  public boolean containsStyleInformation() {
    return !(this.layoutStyle.isClean());
  }

  /**
   */
  public String getNamespace() {
    return (String) getAttribute(NAMESPACE, NAMESPACE_ATTRIBUTE);
  }

  /**
   */
  public void setNamespace(final String id) {
    setAttribute(NAMESPACE, NAMESPACE_ATTRIBUTE, id);
  }

  /**
   * Returns the id for this chart element
   */
  public String getId() {
    return (String) getAttribute(NAMESPACE, ID_ATTRIBUTE);
  }

  /**
   * Sets the id for this chart elements
   */
  public void setId(final String id) {
    setAttribute(LibXmlInfo.XML_NAMESPACE, ID_ATTRIBUTE, id);
  }

  /**
   * Returns the tag name that this element represents
   */
  public String getTagName() {
    return tagName;
  }

  /**
   * Sets the tagname for this element
   */
  public void setTagName(String tagName) {
    this.tagName = tagName;
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
    setAttribute(LibXmlInfo.XML_NAMESPACE, NAME_ATTRIBUTE, name);
  }

  /**
   * Returns the name of the Element. The name of the Element is never null.
   *
   * @return the name.
   */
  public String getName() {
    return (String) getAttribute(LibXmlInfo.XML_NAMESPACE, NAME_ATTRIBUTE);
  }

  /**
   * @param name
   * @param value
   */
  public void setAttribute(final String name, final Object value) {
    setAttribute(getNamespace(), name, value);
  }

  /**
   * @param namespace
   * @param name
   * @param value
   */
  public void setAttribute(final String namespace, final String name, final Object value) {
    if (name == null) {
      throw new NullPointerException();
    }
    if (attributes == null) {
      this.attributes = new AttributeMap();
    }
    this.attributes.setAttribute(namespace, name, value);
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
    // TODO Auto-generated method stub
    return null;
  }

  public Locale getLanguage() {
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub
    return null;
  }

  public LayoutElement getParentLayoutElement() {
    // TODO Auto-generated method stub
    return null;
  }

  public void addChildElement(ChartElement chartElement) {
    addChildItem(chartElement);
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {

    this.text = text;
  }

  /**
   * Dumps the content of this ChartElement
   */
  public String toString() {
    final StringBuffer sb = new StringBuffer();

    // Dump this object
    sb.append(getClass().getName()).append(" {").append("tagName=[").append(tagName).append("]");
    sb.append(" attributes=[").append(attributes).append("]");
    if (text != null) {
      sb.append(" text=[").append(text).append("]");
    }
    sb.append("}");
    return sb.toString();
  }

  /**
   * Performs a "deep" toString() where this element and all the children of this element are included
   *
   * @param prefix used for indenting the output
   */
  public String toString(final String prefix) {
    final StringBuffer sb = new StringBuffer();
    sb.append(prefix).append(toString()).append("\n");
    ChartElement child = (ChartElement) getFirstChildItem();
    while (child != null) {
      sb.append("\n").append(child.toString(prefix + TO_STRING_PREFIX));
      child = (ChartElement) child.getNextItem();
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
   * @param tagName the name of the tag used in searching
   * @return an array of <code>ChartElement</code> objects that match the search criteria
   */
  public ChartElement[] findChildrenByName(String tagName) {
    // Holder for the elements that match
    final ArrayList<ChartElement> matchList = new ArrayList<ChartElement>();

    // If there is no tag name specified, then nothing will match
    if (tagName != null) {
      // "Have you checked the children lately"
      for (ChartElement item = getFirstChildItem(); item != null; item = item.getNextItem()) {
        // See if this item matches
        if (tagName.equals(item.getTagName())) {
          matchList.add(item);
        }
      }
    }

    // Return the list as an array
    return matchList.toArray(new ChartElement[matchList.size()]);
  }
}

