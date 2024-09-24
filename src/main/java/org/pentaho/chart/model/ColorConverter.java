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
/*
    if (color instanceof Color){
      returnColor = toHtmlColorString((Color)color);
      return returnColor;
    }
*/    
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
      if (((String) color).contains("#")){
        color = ((String) color).replace("#", "");
      }
      returnColor = Integer.parseInt( (String)color , 16 ); 
    }
    /*
    if (color instanceof Color){
      returnColor = 0xFFFFFF & ((Color) color).getRGB();
    }
    */
    return returnColor;
  }
 /* 
  public static String toHtmlColorString(Color c) {
    if (c == null) {
      throw new IllegalArgumentException();
    }
    return new StringBuilder()
        .append('#')
        .append( toPaddedHex(c.getRed() ) ) 
        .append(toPaddedHex(c.getGreen() ) ) 
        .append( toPaddedHex(c.getBlue() ) )
        .toString();
  }
   
  private static String toPaddedHex(int i) {
    String s = Integer.toString(i & 0xff, 16); // makes it a hex string - the '& 0xff' makes it unsigned
    return (s.length() > 1 ? s : '0' + s);
  }
*/
  
}
