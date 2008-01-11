package org.pentaho.chart.plugin.jfreechart;

import org.jfree.chart.axis.Axis;

public class AxisBean extends AbstractBean implements org.pentaho.chart.api.engine.Axis{
  
  private static final long serialVersionUID = -5245978368873701196L;
  
  protected Axis axis; 

  public AxisBean() {
  }

  /**
   * @return the axis
   */
  public final Axis getAxis() {
    return axis;
  }

  /**
   * @param axis the axis to set
   */
  public final void setAxis(Axis axis) {
    this.axis = axis;
  }
  
}
