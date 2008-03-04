/*
 * Copyright 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * @created Mar 4, 2008 
 * @author wseyler
 */


package org.pentaho.experimental.chart.plugin.jfreechart.outputs;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.jfree.chart.ChartUtilities;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.PersistenceException;
import org.pentaho.experimental.chart.plugin.api.engine.Chart;
import org.pentaho.experimental.chart.plugin.jfreechart.beans.BaseJFreeChartBean;

  
/**
 * @author wseyler
 *
 */
public class JFreeChartOutput implements IOutput {
  Chart chart;
  int fileType;
  String filename;
  URL baseLocationURL;
  URL lastSaveLocation;
  
  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getAsStream()
   */
  public OutputStream getAsStream() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getChart()
   */
  public Chart getChart() {
    return chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getFileType()
   */
  public int getFileType() {
    return fileType;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getLastSaveLocation()
   */
  public URL getLastSaveLocation() {
    return lastSaveLocation;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#persist()
   */
  public void persist() throws PersistenceException {
    if (fileType == IOutput.FILE_TYPE_JPG) {
      try {
        ChartUtilities.saveChartAsJPEG(new File(filename), ((BaseJFreeChartBean)chart).getChart(), 400, 400);
      } catch (IOException e) {
        throw new PersistenceException(e);
      }
    } else if (fileType == IOutput.FILE_TYPE_PNG) {
      try {
        ChartUtilities.saveChartAsPNG(new File(filename), ((BaseJFreeChartBean)chart).getChart(), 400, 400);
      } catch (IOException e) {
        throw new PersistenceException(e);
      }
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#persist(byte[])
   */
  public void persist(byte[] data) throws PersistenceException {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#persist(java.net.URL, java.lang.String, int, byte[])
   */
  public void persist(URL baseLocationURL, String filename, int fileType, byte[] data) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setChart(org.pentaho.experimental.chart.plugin.api.engine.Chart)
   */
  public void setChart(Chart chart) {
    this.chart = chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setFileType(int)
   */
  public void setFileType(int fileType) {
    this.fileType = fileType;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setFilename(java.lang.String)
   */
  public void setFilename(String filename) {
    this.filename = filename;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setLocation(java.net.URL)
   */
  public void setLocation(URL baseLocationURL) {
    this.baseLocationURL = baseLocationURL;
  }

}
