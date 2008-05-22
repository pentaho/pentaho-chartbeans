package org.pentaho.experimental.chart.plugin.jfreechart.utils;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

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

  public synchronized Shape getShape(ChartElement element) {
    if (element == null) {
      return null;
    }
    String shapeStr = element.getLayoutStyle().getValue(ChartStyleKeys.MARKER_SHAPE).getCSSText();
    double height = Double.parseDouble(element.getLayoutStyle().getValue(ChartStyleKeys.MARKER_HEIGHT).getCSSText());
    double width = Double.parseDouble(element.getLayoutStyle().getValue(ChartStyleKeys.MARKER_WIDTH).getCSSText());

    if (ChartMarkerShapeType.RECTANGLE.getCSSText().equalsIgnoreCase(shapeStr)) {
      return new Rectangle2D.Double(-(width/2), -(height/2), width, height);
    } else if (ChartMarkerShapeType.ELLIPSE.getCSSText().equalsIgnoreCase(shapeStr)) {
      return new Ellipse2D.Double(-(width/2), -(height/2), width, height);
    }
    return null;
  }

}