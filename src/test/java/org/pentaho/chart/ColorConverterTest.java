/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.chart;

import org.junit.Assert;
import org.junit.Test;
import org.pentaho.chart.model.ColorConverter;

public class ColorConverterTest {

  @Test
  public void testToHexString() {
    System.out.println("Integer to Hex String:");
    System.out.println("Integer: " + 65280);
    System.out.print("Hex String: ");
    System.out.println( ColorConverter.toHexString(65280));
    Assert.assertEquals("#00ff00", ColorConverter.toHexString(65280)); 

    System.out.println("Integer to Hex String:");
    System.out.println("Integer: " +  16711680);
    System.out.print("Hex String: ");
    System.out.println( ColorConverter.toHexString(16711680));
    Assert.assertEquals("#ff0000", ColorConverter.toHexString(16711680)); 
    
  }

  @Test
  public void testToInteger() {
    System.out.println("Hex String to Integer:");
    System.out.println("Hex String: #00ff00");
    System.out.print("Integer: ");
    System.out.println(ColorConverter.toInteger("#00ff00"));
    Assert.assertEquals(65280, ColorConverter.toInteger("#00ff00")); 

    System.out.println("Hex String to Integer:");
    System.out.println("Hex String: #ff0000");
    System.out.print("Integer: ");
    System.out.println(ColorConverter.toInteger("#ff0000"));
    Assert.assertEquals(16711680, ColorConverter.toInteger("#ff0000")); 
}

}
