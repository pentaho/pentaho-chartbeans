package org.pentaho.experimental.chart.plugin.jfreechart.utils;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import org.jfree.chart.renderer.AbstractRenderer;
import org.pentaho.experimental.chart.core.ChartElement;
import org.pentaho.experimental.chart.css.keys.ChartStyleKeys;
import org.pentaho.experimental.chart.css.styles.ChartMarkerShapeType;

/**
 * This class provides a Shape object base on the element data.
 * Author: William Seyler
 * Date: May 15, 2008
 * Time: 3:18:40 PM
 */
public class ShapeFactory {
  private static ShapeFactory shapeFacObj;
  public static final Shape DEFAULT_RECTANGLE_SHAPE = AbstractRenderer.DEFAULT_SHAPE;
  public static final Shape DEFAULT_ELLIPSE_SHAPE = new Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0);

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
    if (element == null) {
      return null;
    }
    String shapeStr = element.getLayoutStyle().getValue(ChartStyleKeys.MARKER_SHAPE).getCSSText();
    if (ChartMarkerShapeType.RECTANGLE.getCSSText().equalsIgnoreCase(shapeStr)) {
      return DEFAULT_RECTANGLE_SHAPE;
    } else if (ChartMarkerShapeType.ELLIPSE.getCSSText().equalsIgnoreCase(shapeStr)) {
      return DEFAULT_ELLIPSE_SHAPE;
    }
    return null;
  }
}