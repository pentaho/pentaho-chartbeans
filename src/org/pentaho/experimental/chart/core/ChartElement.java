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
 * @author 
 */
package org.pentaho.experimental.chart.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jfree.xmlns.LibXmlInfo;
import org.jfree.xmlns.common.AttributeMap;
import org.pentaho.reporting.libraries.css.dom.LayoutElement;
import org.pentaho.reporting.libraries.css.dom.LayoutStyle;
import org.pentaho.reporting.libraries.css.keys.box.BoxStyleKeys;
import org.pentaho.reporting.libraries.css.model.CSSStyleRule;
import org.pentaho.reporting.libraries.css.values.CSSConstant;

/**
 * Defines the element information for the elements in the chart definition file
 */
public class ChartElement extends HeirarchicalLinkedListItem implements Cloneable, LayoutElement {
  private static final String[] EMPTY_STRINGS = new String[0];

  private static final Map EMPTY_MAP = Collections.unmodifiableMap(new HashMap<Object, Object>());

  public static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

  public static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

  /**
   * The type corresponds (somewhat) to the tagname of HTML.
   */
  public static final String TYPE_ATTRIBUTE = "type"; //$NON-NLS-1$

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
   * The style information of this element
   */
  private CSSStyleRule style;

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
    this.style = new CSSStyleRule(null, null);
    this.attributes = new AttributeMap();
    setNamespace(NAMESPACE);
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
   */
  public String getTagName() {
    return (String) getAttribute(NAMESPACE, TYPE_ATTRIBUTE);
  }

  /**
   */
  public void setType(final String type) {
    setAttribute(NAMESPACE, TYPE_ATTRIBUTE, type);
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
   * @return the name.
   */
  public String getName() {
    return (String) getAttribute(LibXmlInfo.XML_NAMESPACE, NAME_ATTRIBUTE);
  }

  /**
   * 
   * @param name
   * @param value
   */
  public void setAttribute(final String name, final Object value) {
    setAttribute(getNamespace(), name, value);
  }

  /**
   * 
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
   * 
   * @param name
   * @return
   */
  public Object getAttribute(final String name) {
    return getAttribute(getNamespace(), name);
  }

  /**
   * 
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
   * 
   * @param namespace
   * @return
   */
  public Map getAttributes(final String namespace) {
    if (this.attributes == null) {
      return EMPTY_MAP;
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
      return EMPTY_STRINGS;
    }
    return this.attributes.getNameSpaces();
  }

  /**
   * Returns this elements private stylesheet. This sheet can be used to
   * override the default values set in one of the parent-stylesheets.
   *
   * @return the Element's stylesheet
   */
  public CSSStyleRule getStyle() {
    return style;
  }

  /**
   * 
   * @param v
   */
  public void setVisibility(final CSSConstant v) {
    getStyle().setPropertyValue(BoxStyleKeys.VISIBILITY, v);
  }

  /**
   * 
   * @return
   */
  public CSSConstant getVisibility() {
    return (CSSConstant) getStyle().getPropertyCSSValue(BoxStyleKeys.VISIBILITY);
  }

  /**
   * 
   */
  public Object clone() throws CloneNotSupportedException {
    final ChartElement element = (ChartElement) super.clone();
    element.style = (CSSStyleRule) style.clone();
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
    // TODO Auto-generated method stub
    return null;
  }

  public String getPseudoElement() {
    return null;
  }

  public Map getStrings() {
    return EMPTY_MAP;
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
}
