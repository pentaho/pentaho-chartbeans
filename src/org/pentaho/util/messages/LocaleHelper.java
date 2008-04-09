
/*
 * Copyright 2006 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 */
package org.pentaho.util.messages;


import java.text.DateFormat;
import java.util.Locale;

public class LocaleHelper {

  private static final ThreadLocal threadLocales = new ThreadLocal();

  public static final int FORMAT_SHORT = DateFormat.SHORT;

  public static final int FORMAT_MEDIUM = DateFormat.MEDIUM;

  public static final int FORMAT_LONG = DateFormat.LONG;

  public static final int FORMAT_FULL = DateFormat.FULL;

  public static final int FORMAT_IGNORE = -1;

  private static Locale defaultLocale;

  public static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

  private static String encoding = UTF_8;

  public static final String LEFT_TO_RIGHT = "LTR"; //$NON-NLS-1$

  private static String textDirection = LEFT_TO_RIGHT;

  public static void setDefaultLocale(final Locale newLocale) {
    defaultLocale = newLocale;
  }

  public static Locale getDefaultLocale() {
    return defaultLocale;
  }

  public static void setLocale(final Locale newLocale) {
    threadLocales.set(newLocale);
  }

  public static Locale getLocale() {
    final Locale rtn = (Locale) threadLocales.get();
    if (rtn != null) {
      return rtn;
    }
    defaultLocale = Locale.getDefault();
    setLocale(defaultLocale);
    return defaultLocale;
  }

  public static void setSystemEncoding(final String encoding) {
    LocaleHelper.encoding = encoding;
  }

  public static void setTextDirection(final String textDirection) {
    // TODO make this ThreadLocal
    LocaleHelper.textDirection = textDirection;
  }

  public static String getSystemEncoding() {
    return encoding;
  }

  public static String getTextDirection() {
    // TODO make this ThreadLocal
    return textDirection;
  }

  public static DateFormat getDateFormat(final int dateFormat, final int timeFormat) {

    if (dateFormat != FORMAT_IGNORE && timeFormat != FORMAT_IGNORE) {
      return DateFormat.getDateTimeInstance(dateFormat, timeFormat, getLocale());
    } else if (dateFormat != FORMAT_IGNORE) {
      return DateFormat.getDateInstance(dateFormat, getLocale());
    } else if (timeFormat != FORMAT_IGNORE) {
      return DateFormat.getTimeInstance(timeFormat, getLocale());
    } else {
      return null;
    }

  }

  public static DateFormat getShortDateFormat(final boolean date, final boolean time) {
    if (date && time) {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, getLocale());
    } else if (date) {
      return DateFormat.getDateInstance(DateFormat.SHORT, getLocale());
    } else if (time) {
      return DateFormat.getTimeInstance(DateFormat.SHORT, getLocale());
    } else {
      return null;
    }
  }

  public static DateFormat getMediumDateFormat(final boolean date, final boolean time) {
    if (date && time) {
      return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, getLocale());
    } else if (date) {
      return DateFormat.getDateInstance(DateFormat.MEDIUM, getLocale());
    } else if (time) {
      return DateFormat.getTimeInstance(DateFormat.MEDIUM, getLocale());
    } else {
      return null;
    }
  }

  public static DateFormat getLongDateFormat(final boolean date, final boolean time) {
    if (date && time) {
      return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, getLocale());
    } else if (date) {
      return DateFormat.getDateInstance(DateFormat.LONG, getLocale());
    } else if (time) {
      return DateFormat.getTimeInstance(DateFormat.LONG, getLocale());
    } else {
      return null;
    }
  }

  public static DateFormat getFullDateFormat(final boolean date, final boolean time) {
    if (date && time) {
      return DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, getLocale());
    } else if (date) {
      return DateFormat.getDateInstance(DateFormat.FULL, getLocale());
    } else if (time) {
      return DateFormat.getTimeInstance(DateFormat.FULL, getLocale());
    } else {
      return null;
    }
  }

}
