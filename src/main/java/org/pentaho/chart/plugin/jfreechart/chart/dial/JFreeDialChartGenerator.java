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

package org.pentaho.chart.plugin.jfreechart.chart.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialLayerChangeEvent;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialScale;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.ValueDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PaintUtilities;
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.css.keys.ChartStyleKeys;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.chart.plugin.jfreechart.utils.ColorFactory;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.chart.plugin.jfreechart.utils.StrokeFactory;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.keys.box.BoxStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSValue;
import org.pentaho.reporting.libraries.css.values.CSSValuePair;

public class JFreeDialChartGenerator extends JFreeChartGenerator {

  private static final String DIALVALUEINDICATOR = "dialvalueindicator"; //$NON-NLS-1$

  private static final String DIALRANGES = "dialranges"; //$NON-NLS-1$

  private static final String COUNT = "count"; //$NON-NLS-1$

  private static final String MINORTICK = "minortick"; //$NON-NLS-1$

  private static final String INCREMENT = "increment"; //$NON-NLS-1$

  private static final String MAJORTICK = "majortick"; //$NON-NLS-1$

  private static final String TICKLABEL = "ticklabel"; //$NON-NLS-1$

  private static final String EXTENT = "extent"; //$NON-NLS-1$

  private static final String STARTANGLE = "startangle"; //$NON-NLS-1$

  private static final String SCALE = "scale"; //$NON-NLS-1$

  private static final String DIALCAP = "dialcap"; //$NON-NLS-1$

  private static final String ANNOTATION = "annotation"; //$NON-NLS-1$

  private static final String DIALPOINTER = "dialpointer"; //$NON-NLS-1$

  private static final String UPPERBOUND = "upperbound"; //$NON-NLS-1$

  private static final String LOWERBOUND = "lowerbound"; //$NON-NLS-1$

  private static final String DIALRANGE = "dialrange"; //$NON-NLS-1$

  protected JFreeChart doCreateChart(final ChartDocumentContext chartDocContext, final ChartTableModel data) {
    final ChartDocument chartDocument = chartDocContext.getChartDocument();

    // ~ plot ======================================================================================================== 

    DialPlot dialPlot = new SquareDialPlot();

    // ~ data ========================================================================================================

    // ~ params begin
    final ValueDataset dataset = (ValueDataset) datasetGeneratorFactory.createDataset(chartDocContext, data);
    // ~ params end

    dialPlot.setDataset(dataset);

    // ~ frame type: either circular or arc ==========================================================================

    setDialFrame(chartDocument, dialPlot);

    // ~ annotation ==================================================================================================

    setDialTextAnnotation(chartDocument, dialPlot);

    // ~ value indicator: prints value of dial as text ===============================================================

    setDialValueIndicator(chartDocument, dialPlot);

    // ~ ticks =======================================================================================================

    setDialScale(chartDocument, dialPlot);

    // ~ cap over pointer ============================================================================================

    setDialCap(chartDocument, dialPlot);

    // ~ ranges ======================================================================================================

    setDialRange(chartDocument, dialPlot);

    // ~ background ==================================================================================================

    setDialBackground(chartDocument, dialPlot);

    // ~ pointer: either pin or pointer ==============================================================================

    setDialPointer(chartDocument, dialPlot);

    JFreeChart chart = new JFreeChart(getTitle(chartDocument), dialPlot);
    return chart;

  }

  protected void setDialFrame(ChartDocument chartDocument, DialPlot dialPlot) {
    // ~ params begin
    Color frameForegroundPaint = Color.black;
    Color frameInnerForegroundPaint = Color.black;
    Color frameBackgroundPaint = Color.white;
    Stroke frameStroke = new BasicStroke(2.0f);
    // ~ params end

    final StrokeFactory strokeFacObj = StrokeFactory.getInstance();

    ChartElement plotElement = getUniqueElement(chartDocument, ChartElement.TAG_NAME_PLOT);

    final BasicStroke borderStyleStroke = strokeFacObj.getBorderStroke(plotElement);
    if (borderStyleStroke != null) {
      frameStroke = borderStyleStroke;
    }

    final Color outerBorderColor = ColorFactory.getInstance().getColor(plotElement, BorderStyleKeys.BORDER_TOP_COLOR);
    if (outerBorderColor != null) {
      frameForegroundPaint = outerBorderColor;
    }

    final Color innerBorderColorTmp = ColorFactory.getInstance().getColor(plotElement,
        BorderStyleKeys.BORDER_BOTTOM_COLOR);
    if (innerBorderColorTmp != null) {
      frameInnerForegroundPaint = innerBorderColorTmp;
    }

    final Color borderBackgroundColorTmp = ColorFactory.getInstance().getColor(plotElement);
    if (borderBackgroundColorTmp != null) {
      frameBackgroundPaint = borderBackgroundColorTmp;
    }

    final DoubleLineDialFrame dialFrame = new DoubleLineDialFrame();
    dialFrame.setForegroundPaint(frameForegroundPaint);
    dialFrame.setInnerForegroundPaint(frameInnerForegroundPaint);
    dialFrame.setStroke(frameStroke);
    dialFrame.setBackgroundPaint(frameBackgroundPaint);

    dialPlot.setDialFrame(dialFrame);
  }

  protected void setDialBackground(ChartDocument chartDocument, DialPlot dialPlot) {
    ChartElement plotElement = getUniqueElement(chartDocument, ChartElement.TAG_NAME_PLOT);

    CSSValuePair cssValue = (CSSValuePair) plotElement.getLayoutStyle().getValue(ChartStyleKeys.GRADIENT_COLOR);
    Color beginColor = JFreeChartUtils.getColorFromCSSValue(cssValue.getFirstValue());
    Color endColor = JFreeChartUtils.getColorFromCSSValue(cssValue.getSecondValue());

    GradientPaint gradientpaint = new GradientPaint(new Point(), beginColor, new Point(), endColor);
    DialBackground dialbackground = new DialBackground(gradientpaint); // specify Color here for no gradient
    dialbackground
        .setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
    dialPlot.setBackground(dialbackground);
  }

  protected void setDialRange(ChartDocument chartDocument, DialPlot dialPlot) {

    final double rangeInnerRadius = 0.4D;
    ChartElement[] rangeElements = getElements(chartDocument, DIALRANGE);

    for (int i = 0; i < rangeElements.length; i++) {

      double lowerBound = Double.parseDouble(rangeElements[i].getAttribute(LOWERBOUND).toString());
      double upperBound = Double.parseDouble(rangeElements[i].getAttribute(UPPERBOUND).toString());

      final Color rangeColorTmp = ColorFactory.getInstance().getColor(rangeElements[i]);
      Color rangeColor = Color.BLACK;
      if (rangeColorTmp != null) {
        rangeColor = rangeColorTmp;
      }

      SingleLineDialRange standarddialrange = new SingleLineDialRange(lowerBound, upperBound, rangeColor);
      standarddialrange.setInnerRadius(rangeInnerRadius);
      dialPlot.addLayer(standarddialrange);
    }
  }

  protected void setDialPointer(ChartDocument chartDocument, DialPlot dialPlot) {
    // ~ params begin
    double pointerRadius = 0.9; // length of pointer
    double pointerWidthRadius = 0.05; // width of base of pointer
    Color pointerFillPaint = Color.gray;
    Color pointerOutlinePaint = Color.black;
    Stroke pointerOutlineStroke = new BasicStroke(2.0f);
    // ~ params end

    VariableStrokePointer pointer = new VariableStrokePointer();

    ChartElement pointerElement = getUniqueElement(chartDocument, DIALPOINTER);

    final Color pointerColorTmp = ColorFactory.getInstance().getColor(pointerElement);
    if (pointerColorTmp != null) {
      pointerFillPaint = pointerColorTmp;
    }

    final Color pointerBorderColorTmp = ColorFactory.getInstance().getColor(pointerElement,
        BorderStyleKeys.BORDER_TOP_COLOR);
    if (pointerBorderColorTmp != null) {
      pointerOutlinePaint = pointerBorderColorTmp;
    }

    double pointerWidthRadiusTmp = parseDouble(pointerElement.getLayoutStyle().getValue(BoxStyleKeys.WIDTH)) / 100;
    if (pointerWidthRadiusTmp != 0) {
      pointerWidthRadius = pointerWidthRadiusTmp;
    }

    double pointerRadiusTmp = parseDouble(pointerElement.getLayoutStyle().getValue(BoxStyleKeys.HEIGHT)) / 100;
    if (pointerRadiusTmp != 0) {
      pointerRadius = pointerRadiusTmp;
    }

    final BasicStroke borderStyleStroke = StrokeFactory.getInstance().getBorderStroke(pointerElement);
    if (borderStyleStroke != null) {
      pointerOutlineStroke = borderStyleStroke;
    }

    pointer.setRadius(pointerRadius);
    pointer.setOutlineStroke(pointerOutlineStroke);
    pointer.setWidthRadius(pointerWidthRadius);
    pointer.setFillPaint(pointerFillPaint);
    pointer.setOutlinePaint(pointerOutlinePaint);
    // DialPointer pointer = new DialPointer.Pin();
    dialPlot.addPointer(pointer);
  }

  protected void setDialTextAnnotation(ChartDocument chartDocument, DialPlot dialPlot) {
    // ~ params begin
    Font textAnnotationFont = new Font("Dialog", Font.BOLD, 14); //$NON-NLS-1$
    Color textAnnotationPaint = Color.black;
    final double textAnnotationRadius = 0.69999999999999996D; // hard-coded for now
    // ~ params end

    ChartElement annotationElement = getUniqueElement(chartDocument, ANNOTATION);
    if (annotationElement != null && annotationElement.getText() != null) {
      String annotation = annotationElement.getText();

      Color annotationColorTmp = ColorFactory.getInstance().getColor(annotationElement);
      if (annotationColorTmp != null) {
        textAnnotationPaint = annotationColorTmp;
      }

      Font annotationFontTmp = JFreeChartUtils.getFont(annotationElement);
      if (annotationFontTmp != null) {
        textAnnotationFont = annotationFontTmp;
      }

      DialTextAnnotation dialtextannotation = new DialTextAnnotation(annotation);
      dialtextannotation.setFont(textAnnotationFont);
      dialtextannotation.setPaint(textAnnotationPaint);
      dialtextannotation.setRadius(textAnnotationRadius);
      dialPlot.addLayer(dialtextannotation);
    }

  }

  protected void setDialCap(ChartDocument chartDocument, DialPlot dialPlot) {
    // ~ params begin
    double capRadius = 0.05; // expressed as percentage of dial's framing rectangle 
    Color capFillPaint = Color.white;
    Color capOutlinePaint = Color.black;
    Stroke capOutlineStroke = new BasicStroke(2.0f);
    // ~ params end

    final StrokeFactory strokeFacObj = StrokeFactory.getInstance();

    ChartElement dialCapElement = getUniqueElement(chartDocument, DIALCAP);

    final BasicStroke borderStyleStroke = strokeFacObj.getBorderStroke(dialCapElement);
    if (borderStyleStroke != null) {
      capOutlineStroke = borderStyleStroke;
    }

    final Color borderColor = ColorFactory.getInstance().getColor(dialCapElement, BorderStyleKeys.BORDER_TOP_COLOR);
    if (borderColor != null) {
      capOutlinePaint = borderColor;
    }

    final Color capColor = ColorFactory.getInstance().getColor(dialCapElement);
    if (capColor != null) {
      capFillPaint = capColor;
    }

    capRadius = parseDouble(dialCapElement.getLayoutStyle().getValue(BoxStyleKeys.WIDTH)) / 100;

    DialCap dialCap = new DialCap();
    dialCap.setRadius(capRadius);
    dialCap.setFillPaint(capFillPaint);
    dialCap.setOutlinePaint(capOutlinePaint);
    dialCap.setOutlineStroke(capOutlineStroke);
    dialPlot.setCap(dialCap);
  }

  /**
   * Gets the numeric value inside the CSS value; ignores units.
   */
  protected double parseDouble(final CSSValue value) {
    String trimmedString = value.getCSSText().trim();
    for (int i = 0; i < trimmedString.length(); i++) {
      char c = trimmedString.charAt(i);
      if (!Character.isDigit(c)) {
        try {
          double d = Double.parseDouble(trimmedString.substring(0, i));
          return d;
        } catch (NumberFormatException e) {
          return 0;
        }
      }
    }
    return 0;
  }

  protected void setDialScale(ChartDocument chartDocument, DialPlot dialPlot) {
    // ~ params begin
    double scaleLowerBound = -40D;
    double scaleUpperBound = 60D;
    double scaleStartAngle = -120D;
    double scaleExtent = -300D;
    double scaleMajorTickIncrement = 10D;
    int scaleMinorTickCount = 4;
    final double scaleTickRadius = 0.88D;
    final double scaleTickLabelOffset = 0.14999999999999999D;
    double scaleMajorTickLength = 0.04;
    Color scaleMajorTickPaint = Color.black;
    Stroke scaleMajorTickStroke = new BasicStroke(3.0f);
    double scaleMinorTickLength = 0.02;
    Color scaleMinorTickPaint = Color.black;
    Stroke scaleMinorTickStroke = new BasicStroke(1.0f);
    Font scaleTickLabelFont = new Font("Dialog", 0, 14); //$NON-NLS-1$
    Color scaleTickLabelPaint = Color.blue;
    // ~ params end

    ChartElement scaleElement = getUniqueElement(chartDocument, SCALE);

    scaleUpperBound = Double.parseDouble(scaleElement.getAttribute(UPPERBOUND).toString());
    scaleLowerBound = Double.parseDouble(scaleElement.getAttribute(LOWERBOUND).toString());
    scaleStartAngle = Double.parseDouble(scaleElement.getAttribute(STARTANGLE).toString());
    scaleExtent = Double.parseDouble(scaleElement.getAttribute(EXTENT).toString());

    ChartElement tickLabelElement = getUniqueElement(chartDocument, TICKLABEL);
    Color tickLabelColorTmp = ColorFactory.getInstance().getColor(tickLabelElement);
    if (tickLabelColorTmp != null) {
      scaleTickLabelPaint = tickLabelColorTmp;
    }

    Font tickLabelFontTmp = JFreeChartUtils.getFont(tickLabelElement);
    if (tickLabelFontTmp != null) {
      scaleTickLabelFont = tickLabelFontTmp;
    }

    ChartElement majorTickElement = getUniqueElement(chartDocument, MAJORTICK);
    scaleMajorTickIncrement = Double.parseDouble((String) majorTickElement.getAttribute(INCREMENT));

    float majorTickWidthTmp = (float) parseDouble(majorTickElement.getLayoutStyle().getValue(BoxStyleKeys.WIDTH));
    if (majorTickWidthTmp != 0) {
      scaleMajorTickStroke = new BasicStroke(majorTickWidthTmp);
    }

    double majorTickLengthTmp = parseDouble(majorTickElement.getLayoutStyle().getValue(BoxStyleKeys.HEIGHT)) / 100;
    if (majorTickLengthTmp != 0) {
      scaleMajorTickLength = majorTickLengthTmp;
    }

    ChartElement minorTickElement = getUniqueElement(chartDocument, MINORTICK);
    scaleMinorTickCount = Integer.parseInt((String) minorTickElement.getAttribute(COUNT));

    float minorTickWidthTmp = (float) parseDouble(minorTickElement.getLayoutStyle().getValue(BoxStyleKeys.WIDTH));
    if (minorTickWidthTmp != 0) {
      scaleMinorTickStroke = new BasicStroke(minorTickWidthTmp);
    }

    double minorTickLengthTmp = parseDouble(minorTickElement.getLayoutStyle().getValue(BoxStyleKeys.HEIGHT)) / 100;
    if (minorTickLengthTmp != 0) {
      scaleMinorTickLength = minorTickLengthTmp;
    }

    Color majorTickColorTmp = ColorFactory.getInstance().getColor(majorTickElement);
    if (majorTickColorTmp != null) {
      scaleMajorTickPaint = majorTickColorTmp;
    }

    Color minorTickColorTmp = ColorFactory.getInstance().getColor(minorTickElement);
    if (minorTickColorTmp != null) {
      scaleMinorTickPaint = minorTickColorTmp;
    }

    FixedStandardDialScale standardDialScale = new FixedStandardDialScale(scaleLowerBound, scaleUpperBound,
        scaleStartAngle, scaleExtent, scaleMajorTickIncrement, scaleMinorTickCount);
    standardDialScale.setTickRadius(scaleTickRadius);
    standardDialScale.setTickLabelOffset(scaleTickLabelOffset);
    standardDialScale.setTickLabelFont(scaleTickLabelFont);
    standardDialScale.setTickLabelPaint(scaleTickLabelPaint);
    standardDialScale.setMajorTickLength(scaleMajorTickLength);
    standardDialScale.setMajorTickPaint(scaleMajorTickPaint);
    standardDialScale.setMajorTickStroke(scaleMajorTickStroke);
    standardDialScale.setMinorTickLength(scaleMinorTickLength);
    standardDialScale.setMinorTickPaint(scaleMinorTickPaint);
    standardDialScale.setMinorTickStroke(scaleMinorTickStroke);
    dialPlot.addScale(0, standardDialScale);
  }

  /**
   * Very ugly but there is no stinking XPath support.
   */
  protected ChartElement[] getElements(ChartDocument doc, String name) {
    if (ChartElement.TAG_NAME_PLOT.equals(name)) {
      return new ChartElement[] { doc.getPlotElement() };
    } else if (DIALRANGE.equals(name)) {
      ChartElement plotElem = doc.getPlotElement();
      if (plotElem != null) {
        ChartElement[] dialRanges = plotElem.findChildrenByName(DIALRANGES);
        if (dialRanges.length > 0) {
          return dialRanges[0].findChildrenByName(name);
        } else {
          return null;
        }
      } else {
        return null;
      }
    } else if (TICKLABEL.equals(name) || MAJORTICK.equals(name) || MINORTICK.equals(name)) {
      return doc.getPlotElement().findChildrenByName(SCALE)[0].findChildrenByName(name);
    } else {
      ChartElement[] elems = doc.getPlotElement().findChildrenByName(name);
      return elems;
    }
  }

  public void setDialValueIndicator(ChartDocument chartDocument, DialPlot dialPlot) {

    // ~ params begin
    Font valueIndicatorFont = new Font("Dialog", Font.BOLD, 14); //$NON-NLS-1$
    Color valueIndicatorPaint = Color.black;
    Color valueIndicatorBackgroundPaint = Color.white;
    Stroke valueIndicatorOutlineStroke = new BasicStroke(1.0f);
    Color valueIndicatorOutlinePaint = Color.blue;
    // ~ params end

    ChartElement valIndicatorElement = getUniqueElement(chartDocument, DIALVALUEINDICATOR);
    Color valIndicatorColorTmp = ColorFactory.getInstance().getColor(valIndicatorElement);
    if (valIndicatorColorTmp != null) {
      valueIndicatorPaint = valIndicatorColorTmp;
    }

    Color valIndicatorBgColorTmp = ColorFactory.getInstance().getColor(valIndicatorElement,
        BorderStyleKeys.BACKGROUND_COLOR);
    if (valIndicatorBgColorTmp != null) {
      valueIndicatorBackgroundPaint = valIndicatorBgColorTmp;
    }

    final BasicStroke borderStyleStroke = StrokeFactory.getInstance().getBorderStroke(valIndicatorElement);
    if (borderStyleStroke != null) {
      valueIndicatorOutlineStroke = borderStyleStroke;
    }

    Color valIndicatorBorderColorTmp = ColorFactory.getInstance().getColor(valIndicatorElement,
        BorderStyleKeys.BORDER_TOP_COLOR);
    if (valIndicatorBorderColorTmp != null) {
      valueIndicatorOutlinePaint = valIndicatorBorderColorTmp;
    }

    Font valIndicatorFontTmp = JFreeChartUtils.getFont(valIndicatorElement);
    if (valIndicatorFontTmp != null) {
      valueIndicatorFont = valIndicatorFontTmp;
    }

    DialValueIndicator dialValueIndicator = new DialValueIndicator(0);

    // begin code to determine the size of the value indicator box 
    ChartElement scaleElement = getUniqueElement(chartDocument, SCALE);
    if (scaleElement != null) {

      double scaleUpperBound = Double.parseDouble(scaleElement.getAttribute(UPPERBOUND).toString());
      double scaleLowerBound = Double.parseDouble(scaleElement.getAttribute(LOWERBOUND).toString());

      if (Math.abs(scaleUpperBound) > Math.abs(scaleLowerBound)) {
        dialValueIndicator.setTemplateValue(scaleUpperBound);
      } else {
        dialValueIndicator.setTemplateValue(scaleLowerBound);
      }
    }
    // end code to determine the size of the value indicator box

    dialValueIndicator.setFont(valueIndicatorFont);
    dialValueIndicator.setPaint(valueIndicatorPaint);
    dialValueIndicator.setBackgroundPaint(valueIndicatorBackgroundPaint);
    dialValueIndicator.setOutlineStroke(valueIndicatorOutlineStroke);
    dialValueIndicator.setOutlinePaint(valueIndicatorOutlinePaint);
    dialPlot.addLayer(dialValueIndicator);
  }

  /**
   * Very ugly but there is no stinking XPath support.
   */
  protected ChartElement getUniqueElement(ChartDocument doc, String name) {
    ChartElement[] chartElements = getElements(doc, name);
    return chartElements.length > 0 ? chartElements[0] : null;
  }

  /**
   * Resizes the dial plot so that the smallest side of the plot is the length of all sides--a square plot.
   */
  public static class SquareDialPlot extends DialPlot {

    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
      Rectangle2D squareArea = new Rectangle2D.Double();
      double sideLength = Math.min(area.getWidth(), area.getHeight());

      double distToShiftToCenter = (area.getWidth() - sideLength) / 2;

      squareArea.setRect(area.getX() + distToShiftToCenter, area.getY(), sideLength, sideLength);
      super.draw(g2, squareArea, anchor, parentState, info);
    }

  }

  /**
   * Instead of the double line drawn by <code>StandardDialFrame</code>, this class draws a single line.
   */
  public static class SingleLineDialFrame extends StandardDialFrame {

    private static final long serialVersionUID = 1L;

    @Override
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {

      Shape window = getWindow(frame);

      g2.setPaint(getBackgroundPaint());

      g2.setStroke(getStroke());
      g2.setPaint(getForegroundPaint());
      g2.draw(window);
    }

  }

  /**
   * Instead of the double line drawn by <code>StandardDialRange</code>, this class draws a single line.
   */
  public static class SingleLineDialRange extends StandardDialRange {

    private static final long serialVersionUID = 1L;

    public SingleLineDialRange(double lower, double upper, Paint paint) {
      super(lower, upper, paint);
    }

    @Override
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {

      Rectangle2D arcRectInner = DialPlot.rectangleByRadius(frame, getInnerRadius(), getInnerRadius());

      DialScale scale = plot.getScale(getScaleIndex());
      if (scale == null) {
        throw new RuntimeException("No scale for scaleIndex = " + getScaleIndex()); //$NON-NLS-1$
      }
      double angleMin = scale.valueToAngle(getLowerBound());
      double angleMax = scale.valueToAngle(getUpperBound());

      Arc2D arcInner = new Arc2D.Double(arcRectInner, angleMin, angleMax - angleMin, Arc2D.OPEN);

      // make the stroke a percentage of the width of the frame
      double frameWidth = frame.getWidth();
      float strokeWidth = (float) frameWidth * 0.125f;

      g2.setPaint(getPaint());
      g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
      g2.draw(arcInner);
    }

  }

  /**
   * Same as <code>StandardDialFrame</code>, but allows the line colors to vary between the two lines.
   */
  public static class DoubleLineDialFrame extends StandardDialFrame {

    private static final long serialVersionUID = 1L;

    /**
     * The color used for the inner border around the window. This field is transient
     * because it requires special handling for serialization.
     */
    private transient Paint innerForegroundPaint;

    public DoubleLineDialFrame() {
      super();
      this.innerForegroundPaint = Color.black;
    }

    public Paint getInnerForegroundPaint() {
      return this.innerForegroundPaint;
    }

    public void setInnerForegroundPaint(Paint paint) {
      if (paint == null) {
        throw new IllegalArgumentException("Null 'paint' argument."); //$NON-NLS-1$
      }
      this.innerForegroundPaint = paint;
      notifyListeners(new DialLayerChangeEvent(this));
    }

    @Override
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
      Shape window = getWindow(frame);

      Rectangle2D f = DialPlot.rectangleByRadius(frame, getRadius() + 0.02, getRadius() + 0.02);
      Ellipse2D e = new Ellipse2D.Double(f.getX(), f.getY(), f.getWidth(), f.getHeight());

      Area area = new Area(e);
      Area area2 = new Area(window);
      area.subtract(area2);
      g2.setPaint(getBackgroundPaint());
      g2.fill(area);

      g2.setStroke(getStroke());
      g2.setPaint(getInnerForegroundPaint());
      g2.draw(window);
      g2.setPaint(getForegroundPaint());
      g2.draw(e);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof DoubleLineDialFrame)) {
        return false;
      }
      DoubleLineDialFrame that = (DoubleLineDialFrame) obj;
      if (!PaintUtilities.equal(getBackgroundPaint(), that.getBackgroundPaint())) {
        return false;
      }
      if (!PaintUtilities.equal(getForegroundPaint(), that.getForegroundPaint())) {
        return false;
      }
      if (!PaintUtilities.equal(getInnerForegroundPaint(), that.getInnerForegroundPaint())) {
        return false;
      }
      if (getRadius() != that.getRadius()) {
        return false;
      }
      if (!this.getStroke().equals(that.getStroke())) {
        return false;
      }
      return super.equals(obj);
    }

  }

  public static class ImageDialBackground extends DialBackground {

    private static final long serialVersionUID = 1L;

    private Image image;

    public ImageDialBackground(Image image) {
      super();
      this.image = image;
    }

    @Override
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
      g2.drawImage(image, 20, 20, new ImageObserver() {

        public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {

          return false;

        }

      });
    }

  }

  /**
   * Fixes a bug in JFreeChart where color for tick label is ignored. Bug ID: 2617557
   */
  public static class FixedStandardDialScale extends StandardDialScale {

    private static final long serialVersionUID = 1L;

    public FixedStandardDialScale(double lowerBound, double upperBound, double startAngle, double extent,
        double majorTickIncrement, int minorTickCount) {

      super(lowerBound, upperBound, startAngle, extent, majorTickIncrement, minorTickCount); // TODO Auto-generated constructor stub

    }

    @Override
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {

      Rectangle2D arcRect = DialPlot.rectangleByRadius(frame, getTickRadius(), getTickRadius());
      Rectangle2D arcRectMajor = DialPlot.rectangleByRadius(frame, getTickRadius() - getMajorTickLength(),
          getTickRadius() - getMajorTickLength());
      Rectangle2D arcRectMinor = arcRect;
      if (getMinorTickCount() > 0 && getMinorTickLength() > 0.0) {
        arcRectMinor = DialPlot.rectangleByRadius(frame, getTickRadius() - getMinorTickLength(), getTickRadius()
            - getMinorTickLength());
      }
      Rectangle2D arcRectForLabels = DialPlot.rectangleByRadius(frame, getTickRadius() - getTickLabelOffset(),
          getTickRadius() - getTickLabelOffset());

      boolean firstLabel = true;

      Arc2D arc = new Arc2D.Double();
      Line2D workingLine = new Line2D.Double();
      for (double v = getLowerBound(); v <= getUpperBound(); v += getMajorTickIncrement()) {
        arc.setArc(arcRect, getStartAngle(), valueToAngle(v) - getStartAngle(), Arc2D.OPEN);
        Point2D pt0 = arc.getEndPoint();
        arc.setArc(arcRectMajor, getStartAngle(), valueToAngle(v) - getStartAngle(), Arc2D.OPEN);
        Point2D pt1 = arc.getEndPoint();
        g2.setPaint(getMajorTickPaint());
        g2.setStroke(getMajorTickStroke());
        workingLine.setLine(pt0, pt1);
        g2.draw(workingLine);
        arc.setArc(arcRectForLabels, getStartAngle(), valueToAngle(v) - getStartAngle(), Arc2D.OPEN);
        Point2D pt2 = arc.getEndPoint();

        if (getTickLabelsVisible()) {
          if (!firstLabel || getFirstTickLabelVisible()) {
            g2.setFont(getTickLabelFont());
            g2.setPaint(getTickLabelPaint());
            TextUtilities.drawAlignedString(getTickLabelFormatter().format(v), g2, (float) pt2.getX(), (float) pt2
                .getY(), TextAnchor.CENTER);
          }
        }
        firstLabel = false;

        // now do the minor tick marks
        if (getMinorTickCount() > 0 && getMinorTickLength() > 0.0) {
          double minorTickIncrement = getMajorTickIncrement() / (getMinorTickCount() + 1);
          for (int i = 0; i < getMinorTickCount(); i++) {
            double vv = v + ((i + 1) * minorTickIncrement);
            if (vv >= getUpperBound()) {
              break;
            }
            double angle = valueToAngle(vv);

            arc.setArc(arcRect, getStartAngle(), angle - getStartAngle(), Arc2D.OPEN);
            pt0 = arc.getEndPoint();
            arc.setArc(arcRectMinor, getStartAngle(), angle - getStartAngle(), Arc2D.OPEN);
            Point2D pt3 = arc.getEndPoint();
            g2.setStroke(getMinorTickStroke());
            g2.setPaint(getMinorTickPaint());
            workingLine.setLine(pt0, pt3);
            g2.draw(workingLine);
          }
        }

      }
    }
  }

  /**
   * Enhances <code>DialPointer.Pointer</code> to allow any size stroke for the outline.
   */
  public static class VariableStrokePointer extends DialPointer.Pointer {

    private static final long serialVersionUID = 1L;

    private transient Stroke outlineStroke;

    public Stroke getOutlineStroke() {
      return this.outlineStroke;
    }

    public void setOutlineStroke(Stroke outlineStroke) {
      if (outlineStroke == null) {
        throw new IllegalArgumentException("Null 'stroke' argument."); //$NON-NLS-1$
      }
      this.outlineStroke = outlineStroke;
      notifyListeners(new DialLayerChangeEvent(this));
    }

    @Override
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view) {
      g2.setPaint(Color.blue);
      g2.setStroke(this.outlineStroke);
      Rectangle2D lengthRect = DialPlot.rectangleByRadius(frame, getRadius(), getRadius());
      Rectangle2D widthRect = DialPlot.rectangleByRadius(frame, getWidthRadius(), getWidthRadius());
      double value = plot.getValue(getDatasetIndex());
      DialScale scale = plot.getScaleForDataset(getDatasetIndex());
      double angle = scale.valueToAngle(value);

      Arc2D arc1 = new Arc2D.Double(lengthRect, angle, 0, Arc2D.OPEN);
      Point2D pt1 = arc1.getEndPoint();
      Arc2D arc2 = new Arc2D.Double(widthRect, angle - 90.0, 180.0, Arc2D.OPEN);
      Point2D pt2 = arc2.getStartPoint();
      Point2D pt3 = arc2.getEndPoint();
      Arc2D arc3 = new Arc2D.Double(widthRect, angle - 180.0, 0.0, Arc2D.OPEN);
      Point2D pt4 = arc3.getStartPoint();

      GeneralPath gp = new GeneralPath();
      gp.moveTo((float) pt1.getX(), (float) pt1.getY());
      gp.lineTo((float) pt2.getX(), (float) pt2.getY());
      gp.lineTo((float) pt4.getX(), (float) pt4.getY());
      gp.lineTo((float) pt3.getX(), (float) pt3.getY());
      gp.closePath();
      g2.setPaint(getFillPaint());
      g2.fill(gp);

      g2.setPaint(getOutlinePaint());
      Line2D line = new Line2D.Double(frame.getCenterX(), frame.getCenterY(), pt1.getX(), pt1.getY());
      g2.draw(line);

      line.setLine(pt2, pt3);
      g2.draw(line);

      line.setLine(pt3, pt1);
      g2.draw(line);

      line.setLine(pt2, pt1);
      g2.draw(line);

      line.setLine(pt2, pt4);
      g2.draw(line);

      line.setLine(pt3, pt4);
      g2.draw(line);
    }

  }

}
