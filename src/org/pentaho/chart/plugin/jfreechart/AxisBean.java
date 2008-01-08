package org.pentaho.chart.plugin.jfreechart;

import org.jfree.chart.axis.Axis;

public class AxisBean extends AbstractBean implements org.pentaho.chart.api.engine.Axis{
  
  private static final long serialVersionUID = -5245978368873701196L;
  
  private Axis axis; 

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
  
  public boolean isVisible(){
    if (axis != null){
      return axis.isVisible();
    }
    return false;
  }
  
  public void setVisible(boolean vis){
    if(axis != null){
      boolean old = isVisible();
      axis.setVisible(vis);
      firePropertyChange("visible", old, vis);
    }
  }
  
  
  
  


}
