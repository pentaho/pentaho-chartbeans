package org.pentaho.chart.plugin.jfreechart;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;

public class CategoryAxisBean extends AxisBean {
  
  private static final long serialVersionUID = -5758760973627048077L;
  private double degrees = 0;
  
  public CategoryAxisBean(){
    super();
  }

  public double getTickMarkLabelAngle(){
    return degrees;
    
  }
  
  public void setTickMarkLabelAngle(double degrees){
    double old = getTickMarkLabelAngle();
    this.degrees = degrees;
    ((CategoryAxis)axis).setCategoryLabelPositions(
        CategoryLabelPositions.createDownRotationLabelPositions(degrees * (Math.PI / 180)));
    
    firePropertyChange("tickMarkLabelAngle", old, degrees);
  }
  
}
