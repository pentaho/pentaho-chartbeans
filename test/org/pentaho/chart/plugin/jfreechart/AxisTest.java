package org.pentaho.chart.plugin.jfreechart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.axis.NumberAxis;
import org.junit.Before;
import org.junit.Test;

public class AxisTest extends TestCase implements PropertyChangeListener{
  
  private Log log = LogFactory.getLog(AxisTest.class);
  
  private AxisBean bean = new AxisBean();

  @Before
  public void setUp() throws Exception {
    bean.addPropertyChangeListener(this);
    NumberAxis axis = new NumberAxis();
    bean.setAxis(axis);
  }

  @Test
  public final void testSetAxis() {
    NumberAxis axis = new NumberAxis();
    bean.setAxis(axis);
    assertEquals(axis, bean.getAxis()); // TODO
  }

  @Test
  public final void testSetVisible() {
    bean.setVisible(false);
    assertFalse(bean.isVisible());
  }

  public void propertyChange(PropertyChangeEvent event) {
    
    log.info("Property Name: " +  event.getPropertyName());
    log.info("New Value: " +      event.getNewValue());
    log.info("Old Value: " +      event.getOldValue());
    log.info("Propagation ID: " + event.getPropagationId());
    log.info("Source: " +         event.getSource());
    log.info("Class: " +          event.getClass());
    
  }

}
