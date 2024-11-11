/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart.plugin;

/**
 * Todo: Document me!
 *
 * @author : Thomas Morgner
 */
public class ChartProcessingException extends Exception
{
  public ChartProcessingException()
  {
  }

  public ChartProcessingException(final String message)
  {
    super(message);
  }

  public ChartProcessingException(final String message, final Throwable cause)
  {
    super(message, cause);
  }

  public ChartProcessingException(final Throwable cause)
  {
    super(cause);
  }
}
