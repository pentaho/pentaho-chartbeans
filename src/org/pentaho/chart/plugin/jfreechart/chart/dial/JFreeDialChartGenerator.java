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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
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
import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.plugin.jfreechart.chart.JFreeChartGenerator;
import org.pentaho.chart.plugin.jfreechart.utils.ColorFactory;
import org.pentaho.chart.plugin.jfreechart.utils.JFreeChartUtils;
import org.pentaho.chart.plugin.jfreechart.utils.StrokeFactory;
import org.pentaho.reporting.libraries.css.keys.border.BorderStyleKeys;
import org.pentaho.reporting.libraries.css.keys.box.BoxStyleKeys;
import org.pentaho.reporting.libraries.css.values.CSSValue;

public class JFreeDialChartGenerator extends JFreeChartGenerator {

  private static final Log logger = LogFactory.getLog(JFreeDialChartGenerator.class);

  public JFreeChart createChart(final ChartDocumentContext chartDocContext, final ChartTableModel data) {
    final ChartDocument chartDocument = chartDocContext.getChartDocument();

    // ~ plot ======================================================================================================== 

    DialPlot dialPlot = new DialPlot();

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

    SingleLineDialRange standarddialrange = new SingleLineDialRange(40D, 60D, Color.red);
    standarddialrange.setInnerRadius(0.52000000000000002D);
    dialPlot.addLayer(standarddialrange);

    SingleLineDialRange standarddialrange1 = new SingleLineDialRange(10D, 40D, Color.orange);
    standarddialrange1.setInnerRadius(0.52000000000000002D);
    dialPlot.addLayer(standarddialrange1);

    SingleLineDialRange standarddialrange2 = new SingleLineDialRange(-40D, 10D, Color.green);
    standarddialrange2.setInnerRadius(0.52000000000000002D);
    dialPlot.addLayer(standarddialrange2);

    // ~ background ==================================================================================================

    GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170,
        170, 220));
    DialBackground dialbackground = new DialBackground(gradientpaint); // specify Color here for no gradient
    dialbackground
        .setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
    dialPlot.setBackground(dialbackground);

    // ~ pointer: either pin or pointer ==============================================================================

    // ~ params begin
    double pointerRadius = 0.9; // length of pointer
    double pointerWidthRadius = 0.05; // width of base of pointer
    Color pointerFillPaint = Color.gray;
    Color pointerOutlinePaint = Color.black;
    // ~ params end

    DialPointer.Pointer pointer = new DialPointer.Pointer();
    pointer.setRadius(pointerRadius);
    pointer.setWidthRadius(pointerWidthRadius);
    pointer.setFillPaint(pointerFillPaint);
    pointer.setOutlinePaint(pointerOutlinePaint);
    // DialPointer pointer = new DialPointer.Pin();
    dialPlot.addPointer(pointer);

    return new JFreeChart(getTitle(chartDocument), dialPlot);

  }

  protected void setDialFrame(ChartDocument chartDocument, DialPlot dialPlot) {
    // ~ params begin
    Color frameForegroundPaint = Color.black;
    Stroke frameStroke = new BasicStroke(2.0f);
    // ~ params end

    final StrokeFactory strokeFacObj = StrokeFactory.getInstance();

    ChartElement plotElement = getUniqueElement(chartDocument, ChartElement.TAG_NAME_PLOT);

    final BasicStroke borderStyleStroke = strokeFacObj.getBorderStroke(plotElement);
    if (borderStyleStroke != null) {
      frameStroke = borderStyleStroke;
    }

    final Color borderColor = ColorFactory.getInstance().getColor(plotElement, BorderStyleKeys.BORDER_TOP_COLOR);
    if (borderColor != null) {
      frameForegroundPaint = borderColor;
    }

    final SingleLineDialFrame dialFrame = new SingleLineDialFrame();
    dialFrame.setForegroundPaint(frameForegroundPaint);
    dialFrame.setStroke(frameStroke);

    dialPlot.setDialFrame(dialFrame);
  }

  protected void setDialTextAnnotation(ChartDocument chartDocument, DialPlot dialPlot) {
    // ~ params begin
    Font textAnnotationFont = new Font("Dialog", Font.BOLD, 14); //$NON-NLS-1$
    Color textAnnotationPaint = Color.black;
    final double textAnnotationRadius = 0.69999999999999996D; // hard-coded for now
    // ~ params end

    ChartElement annotationElement = getUniqueElement(chartDocument, "annotation");
    String annotation = null;
    if (annotationElement != null && annotationElement.getText() != null) {
      annotation = annotationElement.getText();
    }

    Color annotationColorTmp = ColorFactory.getInstance().getColor(annotationElement);
    if (annotationColorTmp != null) {
      textAnnotationPaint = annotationColorTmp;
    }

    Font annotationFontTmp = JFreeChartUtils.getFont(annotationElement);
    if (annotationFontTmp != null) {
      textAnnotationFont = annotationFontTmp;
    }

    if (annotation != null) {
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

    ChartElement dialCapElement = getUniqueElement(chartDocument, "dialcap");

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

    // TODO mlowery cap radius can only be a percentage; add support for other units?
    final CSSValue capRadiusValue = dialCapElement.getLayoutStyle().getValue(BoxStyleKeys.WIDTH);
    final String capRadiusPercent = (capRadiusValue.getCSSText().substring(0, capRadiusValue.getCSSText().indexOf("%"))).trim(); //$NON-NLS-1$
    capRadius = Double.parseDouble(capRadiusPercent) / 100;

    DialCap dialCap = new DialCap();
    dialCap.setRadius(capRadius);
    dialCap.setFillPaint(capFillPaint);
    dialCap.setOutlinePaint(capOutlinePaint);
    dialCap.setOutlineStroke(capOutlineStroke);
    dialPlot.setCap(dialCap);
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
    final double scaleMajorTickLength = 0.04;
    Color scaleMajorTickPaint = Color.black;
    final Stroke scaleMajorTickStroke = new BasicStroke(3.0f);
    final double scaleMinorTickLength = 0.02;
    Color scaleMinorTickPaint = Color.black;
    final Stroke scaleMinorTickStroke = new BasicStroke(1.0f);
    Font scaleTickLabelFont = new Font("Dialog", 0, 14); //$NON-NLS-1$
    Color scaleTickLabelPaint = Color.blue;
    // ~ params end

    ChartElement scaleElement = getUniqueElement(chartDocument, "scale");

    scaleUpperBound = Double.parseDouble((String) scaleElement.getAttribute("upperbound"));
    scaleLowerBound = Double.parseDouble((String) scaleElement.getAttribute("lowerbound"));
    scaleStartAngle = Double.parseDouble((String) scaleElement.getAttribute("startangle"));
    scaleExtent = Double.parseDouble((String) scaleElement.getAttribute("extent"));

    ChartElement tickLabelElement = getUniqueElement(chartDocument, "ticklabel");
    Color tickLabelColorTmp = ColorFactory.getInstance().getColor(tickLabelElement);
    if (tickLabelColorTmp != null) {
      scaleTickLabelPaint = tickLabelColorTmp;
    }

    Font tickLabelFontTmp = JFreeChartUtils.getFont(tickLabelElement);
    if (tickLabelFontTmp != null) {
      scaleTickLabelFont = tickLabelFontTmp;
    }

    ChartElement majorTickElement = getUniqueElement(chartDocument, "majortick");
    scaleMajorTickIncrement = Double.parseDouble((String) majorTickElement.getAttribute("increment"));

    ChartElement minorTickElement = getUniqueElement(chartDocument, "minortick");
    scaleMinorTickCount = Integer.parseInt((String) minorTickElement.getAttribute("count"));

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
    } else if ("ticklabel".equals(name) || "majortick".equals(name) || "minortick".equals(name)) {
      return doc.getPlotElement().findChildrenByName("scale")[0].findChildrenByName(name);
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

    ChartElement valIndicatorElement = getUniqueElement(chartDocument, "dialvalueindicator");
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
    return getElements(doc, name)[0];
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

      g2.setPaint(getPaint());
      g2.setStroke(new BasicStroke(25.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
      g2.draw(arcInner);
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

}
