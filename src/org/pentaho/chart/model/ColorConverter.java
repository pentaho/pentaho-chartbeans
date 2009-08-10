package org.pentaho.chart.model;

public class ColorConverter {

  public static String toHexString(Object color){
    String returnColor = null;
    
    if (color instanceof String ){
      returnColor = (String)color;
    }
    if (color instanceof Integer){
      returnColor = Integer.toHexString(0x00FFFFFF & (Integer)color);
    }
    while (returnColor.length()<6){
      returnColor = "0".concat(returnColor);
    }
    returnColor = "#".concat(returnColor);
    return returnColor;
  }
  
  public static Integer toInteger(Object color){
    
    Integer returnColor = null;
    if (color instanceof Integer){
      return (Integer)color;
    }
    if (color instanceof String){
     returnColor = Integer.parseInt( (String)color , 16 ); 
    }
    return returnColor;
  }
  
}
