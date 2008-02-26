package org.pentaho.util.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.pentaho.util.messages.MessageUtil;
import org.pentaho.util.messages.LocaleHelper;

public class Messages {
  private static final String BUNDLE_NAME = "org.pentaho.experimental.chart.locale.messages"; //$NON-NLS-1$
  private static final Map locales = Collections.synchronizedMap(new HashMap());
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private Messages() {
  }
  

  private static ResourceBundle getBundle() {
    Locale locale = LocaleHelper.getLocale();
    ResourceBundle bundle = (ResourceBundle) locales.get(locale);
    if (bundle == null) {
      bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
      locales.put(locale, bundle);
    }
    return bundle;
  }
  
  public static String getString(String key, String param1, String param2) {
    return MessageUtil.getString(getBundle(), key, param1, param2);
  }
  
  public static String getString(String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
  
  public static String getErrorString(String key) {
    return MessageUtil.formatErrorMessage(key, getString(key));
  }

  public static String getErrorString(String key, String param1) {
    return MessageUtil.getErrorString(getBundle(), key, param1);
  }

  public static String getErrorString(String key, String param1, String param2) {
    return MessageUtil.getErrorString(getBundle(), key, param1, param2);
  }

  public static String getErrorString(String key, String param1, String param2, String param3) {
    return MessageUtil.getErrorString(getBundle(), key, param1, param2, param3);
  }
}
