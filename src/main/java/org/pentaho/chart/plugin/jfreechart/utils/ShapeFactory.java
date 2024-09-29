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


package org.pentaho.chart.plugin.jfreechart.utils;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.css.styles.ChartMarkerShapeType;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.StyleSheetUtility;

/**
 * This class provides a Shape object base on the element data.
 * Author: William Seyler
 * Date: May 15, 2008
 * Time: 3:18:40 PM
 */
public class ShapeFactory
{
  private static ShapeFactory shapeFacObj;

  private ShapeFactory()
  {
  }

  /**
   * Returns a singleton StrokeFactory object.
   *
   * @return StrokeFactory Returns a singleton object of this class.
   */
  public static synchronized ShapeFactory getInstance()
  {
    if (ShapeFactory.shapeFacObj == null)
    {
      ShapeFactory.shapeFacObj = new ShapeFactory();
    }
    return ShapeFactory.shapeFacObj;
  }

  /**
   * Overriding Object class's clone method so that clone() is not supported.
   *
   * @return Throws CloneNotSupportedException and returns nothing.
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }

  public synchronized Shape getShape(ChartElement element)
  {
    if (element == null)
    {
      return null;
    }
    final CSSValue shapeValue = element.getLayoutStyle().getValue(ChartStyleKeys.MARKER_SHAPE);

    final double height = StyleSheetUtility.convertLengthToDouble
        (element.getLayoutStyle().getValue(ChartStyleKeys.MARKER_HEIGHT), 72);
    final double width = StyleSheetUtility.convertLengthToDouble
        (element.getLayoutStyle().getValue(ChartStyleKeys.MARKER_WIDTH), 72);

    if (ChartMarkerShapeType.RECTANGLE.equals(shapeValue))
    {
      return new Rectangle2D.Double(-(width / 2), -(height / 2), width, height);
    }
    else if (ChartMarkerShapeType.ELLIPSE.equals(shapeValue))
    {
      return new Ellipse2D.Double(-(width / 2), -(height / 2), width, height);
    }
    return null;
  }

}
