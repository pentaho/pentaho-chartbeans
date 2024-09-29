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


package org.pentaho.chart.model.util;

import org.pentaho.chart.model.CssStyle;

import com.thoughtworks.xstream.converters.SingleValueConverter;

public class CssStylesConverter implements SingleValueConverter {

  public Object fromString(String arg0) {
    return null;
  }

  public String toString(Object arg0) {
    CssStyle cssStyle = (CssStyle) arg0;
    String str = cssStyle.getStyleString();
    return str.length() > 0 ? str : null;
  }

  public boolean canConvert(Class type) {
    return type.equals(CssStyle.class);
  }

}
