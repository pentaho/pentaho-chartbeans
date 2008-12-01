/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package org.pentaho.experimental.chart.plugin;

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
