package org.pentaho.experimental.chart.plugin;

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
