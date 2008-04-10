package org.pentaho.util.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
  private static final String BUNDLE_NAME = "org.pentaho.experimental.chart.locale.messages"; //$NON-NLS-1$
  private static final Map locales = Collections.synchronizedMap(new HashMap());
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Messages.BUNDLE_NAME);

  private Messages() {
  }
  

  private static ResourceBundle getBundle() {
    final Locale locale = LocaleHelper.getLocale();
    ResourceBundle bundle = (ResourceBundle) Messages.locales.get(locale);
    if (bundle == null) {
      bundle = ResourceBundle.getBundle(Messages.BUNDLE_NAME, locale);
      Messages.locales.put(locale, bundle);
    }
    return bundle;
  }
  
  public static String getString(final String key, final String param1, final String param2) {
    return MessageUtil.getString(Messages.getBundle(), key, param1, param2);
  }
  
  public static String getString(final String key) {
    try {
      return Messages.RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }
  
  public static String getErrorString(final String key) {
    return MessageUtil.formatErrorMessage(key, Messages.getString(key));
  }

  public static String getErrorString(final String key, final String param1) {
    return MessageUtil.getErrorString(Messages.getBundle(), key, param1);
  }

  public static String getErrorString(final String key, final String param1, final String param2) {
    return MessageUtil.getErrorString(Messages.getBundle(), key, param1, param2);
  }

  public static String getErrorString(final String key, final String param1, final String param2, final String param3) {
    return MessageUtil.getErrorString(Messages.getBundle(), key, param1, param2, param3);
  }
}
