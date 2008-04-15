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

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.ImageMapUtilities;
import org.pentaho.experimental.chart.plugin.api.IOutput;
import org.pentaho.experimental.chart.plugin.api.PersistenceException;

  
/**
 * @author wseyler
 *
 */
public class JFreeChartOutput implements IOutput {
  
  JFreeChart chart;
  ChartRenderingInfo info;
  
  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#persistChart(java.lang.String, int)
   */
  public void persistChart(String filePath, IOutput.OutputTypes fileType) throws PersistenceException {
    info = new ChartRenderingInfo(new StandardEntityCollection());
    if (filePath != null && filePath.length() > 0) {
      if (fileType == IOutput.OutputTypes.FILE_TYPE_JPEG) {
        try {
          ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 400, 400, info);
        } catch (IOException e) {
          throw new PersistenceException(e);
        }
      } else if (fileType == IOutput.OutputTypes.FILE_TYPE_PNG) {
        try {
          ChartUtilities.saveChartAsPNG(new File(filePath), chart, 400, 400, info);
        } catch (IOException e) {
          throw new PersistenceException(e);
        }
      }
    }
    
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#persistChart(java.io.OutputStream, int)
   */
  public OutputStream persistChart(OutputStream outputStream, IOutput.OutputTypes fileType) throws PersistenceException {
    info = new ChartRenderingInfo(new StandardEntityCollection());
    if (outputStream == null) {
      outputStream = new ByteArrayOutputStream();
    }
    try {
      outputStream.flush();
    } catch (IOException e1) {
      throw new PersistenceException(e1);
    }
    if (fileType == IOutput.OutputTypes.FILE_TYPE_JPEG) {
      try {
        ChartUtilities.writeChartAsJPEG(outputStream, chart, 400, 400, info);
      } catch (IOException e) {
        throw new PersistenceException(e);
      }
    } else if (fileType == IOutput.OutputTypes.FILE_TYPE_PNG) {
      try {
        ChartUtilities.writeChartAsPNG(outputStream, chart, 400, 400, info);
      } catch (IOException e) {
        throw new PersistenceException(e);
      }
    }
    return outputStream;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#persistMap(java.lang.String)
   */
  public void persistMap(String filePath) throws PersistenceException {
    if (chart.getCategoryPlot().getRenderer().getBaseItemURLGenerator() != null) {
      final String mapFileName = filePath + MAP_EXTENSION;
      BufferedOutputStream outputStream;
      try {
        outputStream = new BufferedOutputStream(new FileOutputStream(mapFileName));
        persistMap(outputStream, mapFileName);
      } catch (FileNotFoundException e) {
        throw new PersistenceException(e);
      }
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#persistMap(java.io.OutputStream, java.lang.String)
   */
  public OutputStream persistMap(OutputStream outputStream, String mapName) throws PersistenceException {
    if (outputStream == null) {
      outputStream = new ByteArrayOutputStream();
    }
    try {
      outputStream.flush();
    } catch (IOException e1) {
      throw new PersistenceException(e1);
    }
    final String mapString = getMap(mapName);
    try {
      outputStream.write(mapString.getBytes());
      outputStream.flush();
      outputStream.close();
    } catch (IOException e) {
      throw new PersistenceException(e);
    }
    return outputStream;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#getMap(java.lang.String)
   */
  public String getMap(String mapName) {
    return ImageMapUtilities.getImageMap(mapName, info);
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#setChart(java.lang.Object)
   */
  public void setChart(final Object chart) {
    this.chart = (JFreeChart) chart;
  }

  /* (non-Javadoc)
   * @see org.pentaho.experimental.chart.plugin.api.IOutput#draw(java.awt.Graphics2D, java.awt.geom.Rectangle2D)
   */
  public void draw(Graphics2D graphics, Rectangle2D rectangle) {
    info = new ChartRenderingInfo(new StandardEntityCollection());
    chart.draw(graphics, rectangle, info);
  }

  
}
