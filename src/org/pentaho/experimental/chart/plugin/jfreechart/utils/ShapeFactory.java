package org.pentaho.experimental.chart.plugin.jfreechart.utils;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.pentaho.experimental.chart.core.ChartElement;

/**
 * This class provides a Shape object base on the element data.
 * Author: William Seyler
 * Date: May 15, 2008
 * Time: 3:18:40 PM
 */
public class ShapeFactory {
  private static ShapeFactory shapeFacObj;

  private ShapeFactory() {
  }

  /**
   * Returns a singleton StrokeFactory object.
   * @return StrokeFactory Returns a singleton object of this class.
   */
  public static synchronized ShapeFactory getInstance() {
    if (ShapeFactory.shapeFacObj == null) {
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
  public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }

  public Shape getShape(ChartElement element) {
//    if (element == null) {
//      return null;
//    }
//    return null;
    return new Ellipse2D.Float(-10, -10, 20, 20); 
  }
}