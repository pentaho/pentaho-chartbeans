package org.pentaho.experimental.chart.plugin.api;

/**
 * @author wseyler
 * 
 * Defines a Persistence Exception to be used by IOutput
 */
public class PersistenceException extends Exception {

  private static final long serialVersionUID = -5922555902251829661L;

  public PersistenceException() {
  }

  public PersistenceException(final String msg) {
    super(msg);
  }

  public PersistenceException(final Throwable th) {
    super(th);
  }

  public PersistenceException(final String msg, final Throwable th) {
    super(msg, th);
  }

}
