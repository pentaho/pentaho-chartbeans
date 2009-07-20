package org.pentaho.chart.model;

import java.io.Serializable;

public class TwoAxisPlot extends Plot implements Serializable {
  Axis verticalAxis;
  Axis horizontalAxis;
  
  protected TwoAxisPlot(Axis horizontalAxis, NumericAxis verticalAxis) {
    this.verticalAxis = verticalAxis;
    this.horizontalAxis = horizontalAxis;
  }
  
  public Axis getHorizontalAxis() {
    return horizontalAxis;
  }
  
  public Axis getVerticalAxis() {
    return verticalAxis;
  }

  public Axis getDomainAxis() {
    return  getOrientation() == Orientation.HORIZONTAL ? verticalAxis : horizontalAxis;
  }
  
  public NumericAxis getRangeAxis() {
    return  (NumericAxis)(getOrientation() == Orientation.HORIZONTAL ? horizontalAxis : verticalAxis);
  }

  public void setOrientation(Orientation orientation) {
    if (this.orientation != orientation) {
      Axis tmpAxis = verticalAxis;
      verticalAxis = horizontalAxis;
      horizontalAxis = tmpAxis;
    }
    super.setOrientation(orientation);
  }

}
