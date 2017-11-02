/* ---------------------
* CylinderRenderer.java
* ---------------------
* (C) Copyright 2005-2007, by Object Refinery Limited.
*
*/

package org.pentaho.chart.plugin.jfreechart.utils;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;

/**
 * A custom renderer that draws cylinders to represent data from a 
 * CategoryDataset in a CategoryPlot.
 */
public class CylinderRenderer extends BarRenderer3D {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public CylinderRenderer() {
        super();   
    }
    
    /**
     * Creates a new renderer.
     * 
     * @param xOffset  the x-offset for the 3D effect.
     * @param yOffset  the y-offset for the 3D effect.
     */
    public CylinderRenderer(final double xOffset, final double yOffset) {
        super(xOffset, yOffset);   
    }
    
    /**
     * Draws a cylinder to represent one data item.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the area for plotting the data.
     * @param plot  the plot.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the dataset.
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param pass  the pass index.
     */
    public void drawItem(final Graphics2D g2,
                         final CategoryItemRendererState state,
                         final Rectangle2D dataArea,
                         final CategoryPlot plot,
                         final CategoryAxis domainAxis,
                         final ValueAxis rangeAxis,
                         final CategoryDataset dataset,
                         final int row,
                         final int column,
                         final int pass) {
    
        // check the value we are plotting...
        final Number dataValue = dataset.getValue(row, column);
        if (dataValue == null) {
            return;
        }
        
        final double value = dataValue.doubleValue();
        
        final Rectangle2D adjusted = new Rectangle2D.Double(dataArea.getX(),
                dataArea.getY() + getYOffset(), 
                dataArea.getWidth() - getXOffset(),
                dataArea.getHeight() - getYOffset());

        final PlotOrientation orientation = plot.getOrientation();
        
        final double barW0 = calculateBarW0(plot, orientation, adjusted, domainAxis,
                state, row, column);
        final double[] barL0L1 = calculateBarL0L1(value);
        if (barL0L1 == null) {
            return;  // the bar is not visible
        }

        final RectangleEdge edge = plot.getRangeAxisEdge();
        final float transL0 = (float) rangeAxis.valueToJava2D(barL0L1[0], adjusted,
                edge);
        final float transL1 = (float) rangeAxis.valueToJava2D(barL0L1[1], adjusted,
                edge);
        final float barL0 = Math.min(transL0, transL1);
        final float barLength = Math.abs(transL1 - transL0);
        
        // draw the bar...
        final GeneralPath bar = new GeneralPath();
        final Shape top;
        if (orientation == PlotOrientation.HORIZONTAL) {
            bar.moveTo((float) (barL0 + getXOffset() / 2), (float) barW0);
            bar.lineTo((float) (barL0 + barLength + getXOffset() / 2), 
                    (float) barW0);
            Arc2D arc = new Arc2D.Double(barL0 + barLength, barW0, 
                    getXOffset(), state.getBarWidth(), 90, 180, Arc2D.OPEN);
            bar.append(arc, true);
            bar.lineTo((float) (barL0 + getXOffset() / 2), 
                    (float) (barW0 + state.getBarWidth()));
            arc = new Arc2D.Double(barL0, barW0, 
                    getXOffset(), state.getBarWidth(), 270, -180, Arc2D.OPEN);
            bar.append(arc, true);
            bar.closePath();            
            top = new Ellipse2D.Double(barL0 + barLength, 
                    barW0, getXOffset(), state.getBarWidth());

        }
        else {
            bar.moveTo((float) barW0, (float) (barL0 - getYOffset() / 2));
            bar.lineTo((float) barW0, (float) (barL0 + barLength - getYOffset()
                    / 2));
            Arc2D arc = new Arc2D.Double(barW0, (barL0 + barLength 
                    - getYOffset()), state.getBarWidth(), getYOffset(), 180, 
                    180, Arc2D.OPEN);
            bar.append(arc, true);
            bar.lineTo((float) (barW0 + state.getBarWidth()), (float) (barL0 
                    - getYOffset() / 2));
            arc = new Arc2D.Double(barW0, (barL0 - getYOffset()), 
                    state.getBarWidth(), getYOffset(), 0, -180, Arc2D.OPEN);
            bar.append(arc, true);
            bar.closePath();

            top = new Ellipse2D.Double(barW0, barL0 - getYOffset(), 
                    state.getBarWidth(), getYOffset());
        }
        Paint itemPaint = getItemPaint(row, column);
        if (getGradientPaintTransformer() != null 
                && itemPaint instanceof GradientPaint) {
            final GradientPaint gp = (GradientPaint) itemPaint;
            itemPaint = getGradientPaintTransformer().transform(gp, bar);
        }
        g2.setPaint(itemPaint);
        g2.fill(bar);

        if (itemPaint instanceof GradientPaint) {
            g2.setPaint(((GradientPaint) itemPaint).getColor2());   
        }
        if (top != null) {
            g2.fill(top);
        }
        
        if (isDrawBarOutline() 
                && state.getBarWidth() > BarRenderer.BAR_OUTLINE_WIDTH_THRESHOLD) {
            g2.setStroke(getItemOutlineStroke(row, column));
            g2.setPaint(getItemOutlinePaint(row, column));
            g2.draw(bar);
            if (top != null) {
                g2.draw(top);
            }
        }

        final CategoryItemLabelGenerator generator
                = getItemLabelGenerator(row, column);
        if (generator != null && isItemLabelVisible(row, column)) {
            drawItemLabel(g2, dataset, row, column, plot, generator, 
                    bar.getBounds2D(), (value < 0.0));
        }        

        // collect entity and tool tip information...
        if (state.getInfo() != null) {
            final EntityCollection entities = state.getEntityCollection();
            if (entities != null) {
                String tip = null;
                final CategoryToolTipGenerator tipster
                        = getToolTipGenerator(row, column);
                if (tipster != null) {
                    tip = tipster.generateToolTip(dataset, row, column);
                }
                String url = null;
                if (getItemURLGenerator(row, column) != null) {
                    url = getItemURLGenerator(row, column).generateURL(
                            dataset, row, column);
                }
                final CategoryItemEntity entity = new CategoryItemEntity(
                        bar.getBounds2D(), tip, url, dataset, 
                        dataset.getRowKey(row), dataset.getColumnKey(column));
                entities.add(entity);
            }
        }

    }

}
