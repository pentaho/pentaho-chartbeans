package org.pentaho.chart.plugin.jfreechart;

import java.awt.Paint;

import org.jfree.chart.title.TextTitle;
import org.jfree.ui.HorizontalAlignment;
import org.pentaho.chart.api.engine.Text;


public class TextBean extends AbstractBean implements Text{
  
  private TextTitle text;

  public TextBean() {
    text = new TextTitle();
  }

  public final String getText(){
    return text.getText();
  }
  
  public final void setText(String str){
    String old = text.getText();
    text.setText(str);
    firePropertyChange("text", old, str);
  }
  
  public final Paint getColor(){
    return text.getPaint();
  }
  
  public final void setColor(Paint color){
    Paint old = getColor();
    text.setPaint(color);
    firePropertyChange("color", old, color);
  }
  
  public final Paint getBackgroundColor(){
    return text.getBackgroundPaint();
  }
  
  public final void setBackgroundColor(Paint color){
    Paint old = getBackgroundColor();
    text.setBackgroundPaint(color);
    firePropertyChange("backgroundColor", old, color);
  }
  
  public final String getTextAlignment(){
    return text.getTextAlignment().toString();
  }
  
  /**
   * String values taken from jfree.org commons. 
   * @param align Valid values are "HorizontalAlignment.LEFT", "HorizontalAlignment.RIGHT", "HorizontalAlignment.CENTER"
   */
  public final void setTextAlignment(String align){
    String old = getTextAlignment();
    String newAlign = HorizontalAlignment.LEFT.toString();
    
    if (align.equalsIgnoreCase("HorizontalAlignment.RIGHT")){
      text.setTextAlignment(HorizontalAlignment.RIGHT);
      newAlign = HorizontalAlignment.RIGHT.toString();
    }else if (align.equalsIgnoreCase("HorizontalAlignment.CENTER")){
      text.setTextAlignment(HorizontalAlignment.CENTER);
      newAlign = HorizontalAlignment.CENTER.toString();
    }else{
      text.setTextAlignment(HorizontalAlignment.LEFT);
    }
    
    firePropertyChange("textAlignment", old, newAlign);
  }
  
}
