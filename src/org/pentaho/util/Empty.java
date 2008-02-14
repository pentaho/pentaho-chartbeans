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
 * Created 25 January 2008
 * @author David Kincade 
 */
package org.pentaho.util;

import java.util.*;

/**
 * Class which holds a static reference to a set of empty objects. This is created for performance
 * reasons. Using this class will prevent creating duplicated "empty" object.
 *
 * @author David Kincade
 */
public final class Empty {

  /**
   * No reason to create an instance of this class
   */
  private Empty() {
  }

  /**
   * The empty string
   */
  public static final String STRING = "";

  /**
   * An empty array of Strings
   */
  public static final String[] STRING_ARRAY = new String[0];

  /**
   * An empty Map
   */
  public static final Map MAP = Collections.unmodifiableMap(new HashMap<Object, Object>());

  /**
   * An empty List
   */
  public static final List LIST = Collections.unmodifiableList(new ArrayList<Object>());
}
