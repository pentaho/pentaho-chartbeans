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

  public PersistenceException(String msg) {
    super(msg);
  }

  public PersistenceException(Throwable th) {
    super(th);
  }

  public PersistenceException(String msg, Throwable th) {
    super(msg, th);
  }

}
