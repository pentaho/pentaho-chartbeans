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

package org.pentaho.chart.plugin.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.chart.plugin.api.IOutput;
import org.pentaho.chart.plugin.api.PersistenceException;
import org.pentaho.reporting.libraries.xmlns.common.AttributeList;
import org.pentaho.reporting.libraries.xmlns.common.AttributeMap;
import org.pentaho.reporting.libraries.xmlns.writer.XmlWriter;

/**
 * Todo: Document Me
 *
 * @author Thomas Morgner
 */
public class XmlChartOutput implements IOutput
{
  private ChartDocument document;

  public XmlChartOutput(final ChartDocument document)
  {
    if (document == null)
    {
      throw new NullPointerException();
    }
    this.document = document;
  }

  public OutputStream persistChart(final OutputStream outputStream,
                                   final OutputTypes fileType, int width, int height) throws PersistenceException
  {
    try
    {
      final XmlWriter writer = new XmlWriter(new OutputStreamWriter(outputStream, "UTF-8"));
      writer.writeXmlDeclaration("UTF-8");
      final ChartElement chartElement = document.getRootElement();
      writeElement(writer, chartElement);
      writer.flush();
    }
    catch (IOException e)
    {
      throw new PersistenceException(e);
    }

    return outputStream;
  }

  private void writeElement(final XmlWriter writer, final ChartElement chartElement)
      throws IOException
  {
    final AttributeList list = createMainAttributes(chartElement, writer, new AttributeList());
    writer.writeTag(chartElement.getNamespace(), chartElement.getTagName(), list, XmlWriter.OPEN);
    ChartElement child = chartElement.getFirstChildItem();
    while (child != null)
    {
      writeElement(writer, child);
      child = child.getNextItem();
    }
    if (isEmpty(chartElement.getText(), true) == false)
    {
      writer.writeTextNormalized(chartElement.getText(), false);
    }
    writer.writeCloseTag();
  }

  public static boolean isEmpty(final String source, final boolean trim)
  {
    if (source == null)
    {
      return true;
    }
    if (source.length() == 0)
    {
      return true;
    }
    if (trim == false)
    {
      return false;
    }
    final char[] chars = source.toCharArray();
    for (int i = 0; i < chars.length; i++)
    {
      final char c = chars[i];
      if (c > ' ')
      {
        return false;
      }
    }
    return true;
  }

  protected AttributeList createMainAttributes(final ChartElement element,
                                               final XmlWriter writer,
                                               final AttributeList attList)
  {
    if (element == null)
    {
      throw new NullPointerException();
    }
    if (writer == null)
    {
      throw new NullPointerException();
    }
    if (attList == null)
    {
      throw new NullPointerException();
    }
    final AttributeMap map = element.getAttributeMap();
    final String[] attributeNamespaces = map.getNameSpaces();
    for (int i = 0; i < attributeNamespaces.length; i++)
    {
      final String namespace = attributeNamespaces[i];
      final String[] attributeNames = map.getNames(namespace);
      for (int j = 0; j < attributeNames.length; j++)
      {
        final String name = attributeNames[j];
        final Object value = element.getAttribute(namespace, name);
        if (value == null)
        {
          continue;
        }

        ensureNamespaceDefined(writer, attList, namespace);
        attList.setAttribute(namespace, name, String.valueOf(value));
      }
    }
    return attList;
  }

  private void ensureNamespaceDefined(final XmlWriter writer, final AttributeList attList, final String namespace)
  {
    if (writer.isNamespaceDefined(namespace) == false &&
        attList.isNamespaceUriDefined(namespace) == false)
    {
      attList.addNamespaceDeclaration("autoGenNs", namespace);
    }
  }

  public Writer persistMap(final Writer outputStream, final String mapName) throws PersistenceException
  {
    return outputStream;
  }

  public Object getDrawable()
  {
    return null;
  }
}
