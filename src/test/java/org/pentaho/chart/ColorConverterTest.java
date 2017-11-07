/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

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
