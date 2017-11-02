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

  private static String encoding = LocaleHelper.UTF_8;

  public static final String LEFT_TO_RIGHT = "LTR"; //$NON-NLS-1$

  private static String textDirection = LocaleHelper.LEFT_TO_RIGHT;

  private LocaleHelper() {
  }

  public static void setDefaultLocale(final Locale newLocale) {
    LocaleHelper.defaultLocale = newLocale;
  }

  public static Locale getDefaultLocale() {
    return LocaleHelper.defaultLocale;
  }

  public static void setLocale(final Locale newLocale) {
    LocaleHelper.threadLocales.set(newLocale);
  }

  public static Locale getLocale() {
    final Locale rtn = (Locale) LocaleHelper.threadLocales.get();
    if (rtn != null) {
      return rtn;
    }
    LocaleHelper.defaultLocale = Locale.getDefault();
    LocaleHelper.setLocale(LocaleHelper.defaultLocale);
    return LocaleHelper.defaultLocale;
  }

  public static void setSystemEncoding(final String encoding) {
    LocaleHelper.encoding = encoding;
  }

  public static void setTextDirection(final String textDirection) {
    // TODO make this ThreadLocal
    LocaleHelper.textDirection = textDirection;
  }

  public static String getSystemEncoding() {
    return LocaleHelper.encoding;
  }

  public static String getTextDirection() {
    // TODO make this ThreadLocal
    return LocaleHelper.textDirection;
  }

  public static DateFormat getDateFormat(final int dateFormat, final int timeFormat) {

    if (dateFormat != LocaleHelper.FORMAT_IGNORE && timeFormat != LocaleHelper.FORMAT_IGNORE) {
      return DateFormat.getDateTimeInstance(dateFormat, timeFormat, LocaleHelper.getLocale());
    } else if (dateFormat != LocaleHelper.FORMAT_IGNORE) {
      return DateFormat.getDateInstance(dateFormat, LocaleHelper.getLocale());
    } else if (timeFormat != LocaleHelper.FORMAT_IGNORE) {
      return DateFormat.getTimeInstance(timeFormat, LocaleHelper.getLocale());
    } else {
      return null;
    }

  }

  public static DateFormat getShortDateFormat(final boolean date, final boolean time) {
    if (date && time) {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, LocaleHelper.getLocale());
    } else if (date) {
      return DateFormat.getDateInstance(DateFormat.SHORT, LocaleHelper.getLocale());
    } else if (time) {
      return DateFormat.getTimeInstance(DateFormat.SHORT, LocaleHelper.getLocale());
    } else {
      return null;
    }
  }

  public static DateFormat getMediumDateFormat(final boolean date, final boolean time) {
    if (date && time) {
      return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, LocaleHelper.getLocale());
    } else if (date) {
      return DateFormat.getDateInstance(DateFormat.MEDIUM, LocaleHelper.getLocale());
    } else if (time) {
      return DateFormat.getTimeInstance(DateFormat.MEDIUM, LocaleHelper.getLocale());
    } else {
      return null;
    }
  }

  public static DateFormat getLongDateFormat(final boolean date, final boolean time) {
    if (date && time) {
      return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, LocaleHelper.getLocale());
    } else if (date) {
      return DateFormat.getDateInstance(DateFormat.LONG, LocaleHelper.getLocale());
    } else if (time) {
      return DateFormat.getTimeInstance(DateFormat.LONG, LocaleHelper.getLocale());
    } else {
      return null;
    }
  }

  public static DateFormat getFullDateFormat(final boolean date, final boolean time) {
    if (date && time) {
      return DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, LocaleHelper.getLocale());
    } else if (date) {
      return DateFormat.getDateInstance(DateFormat.FULL, LocaleHelper.getLocale());
    } else if (time) {
      return DateFormat.getTimeInstance(DateFormat.FULL, LocaleHelper.getLocale());
    } else {
      return null;
    }
  }

}
