package org.pentaho.chart;

import org.junit.Assert;
import org.junit.Test;
import org.pentaho.chart.model.ColorConverter;

public class ColorConverterTest {

  @Test
  public void testToHexString() {
    //System.out.println("Color to Hex String:");
    //System.out.println("Color: " + Color.BLUE);
    //System.out.print("Hex String: ");
    //System.out.println( ColorConverter.toHexString(Color.BLUE));
    
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
    //System.out.println("Color to Integer:");
    //System.out.println("Color: Green");
    //System.out.print("Integer: ");
    //System.out.println(ColorConverter.toInteger(Color.GREEN));

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
