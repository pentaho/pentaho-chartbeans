package org.pentaho.chart.api.exceptions;

public class PersistenceException extends Exception {

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
