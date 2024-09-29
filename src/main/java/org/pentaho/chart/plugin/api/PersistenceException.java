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


package org.pentaho.chart.plugin.api;

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
