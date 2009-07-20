package org.pentaho.chart.plugin.xml;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.pentaho.chart.ChartDocumentContext;
import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.data.ChartTableModel;
import org.pentaho.chart.data.IChartDataModel;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.util.ChartSerializer;
import org.pentaho.chart.model.util.ChartSerializer.ChartSerializationFormat;
import org.pentaho.chart.plugin.AbstractChartPlugin;
import org.pentaho.chart.plugin.IChartPlugin;
import org.pentaho.chart.plugin.api.ChartResult;
import org.pentaho.chart.plugin.api.IOutput;

/**
 * Todo: Document Me
 *
 * @author Thomas Morgner
 */
public class XmlChartPlugin extends AbstractChartPlugin
{
  public static final String PLUGIN_ID = "XmlChart";
  
  private static final Set<IOutput.OutputTypes> supportedOutputs =
      Collections.unmodifiableSet(EnumSet.of(IOutput.OutputTypes.FILE_TYPE_JPEG, IOutput.OutputTypes.FILE_TYPE_PNG));

  public XmlChartPlugin()
  {
  }

  public IOutput renderChartDocument(ChartModel chartModel, IChartDataModel data) {
    //TODO finish this.
    return null;
  }

  public IOutput renderChartDocument(final ChartDocumentContext chartDocumentContext, final ChartTableModel data)
  {
    final ChartDocument chartDocument = chartDocumentContext.getChartDocument();
    final ChartResult chartResult = validateChartDocument(chartDocument);
    if (chartResult.getErrorCode() == IChartPlugin.RESULT_VALIDATED)
    {  // The superclass so now we'll render
      return new XmlChartOutput(chartDocumentContext.getChartDocument());
    }
    return null;
  }

  public Set<IOutput.OutputTypes> getSupportedOutputs()
  {
    return supportedOutputs;
  }
  
  public String getPluginId() {
    return PLUGIN_ID;
  }
}
