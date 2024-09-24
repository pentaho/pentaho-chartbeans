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

package org.pentaho.chart.core.parser;

import org.pentaho.chart.core.ChartElement;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.pentaho.reporting.libraries.xmlns.parser.StringReadHandler;
import org.pentaho.reporting.libraries.xmlns.parser.XmlReadHandler;

/**
 * Used in the parsing of the chart definition xml document. This class will perform the creation of a
 * <code>ChartElement</code> based on the information read from the xml tag.
 *
 * @author David Kincade
 */
public class ChartElementReadHandler extends StringReadHandler
{
  /**
   * The chart element to be created / initialized during the reading of this tag
   */
  private final ChartElement chartElement;

  /**
   * Constructs a new chart element parser that does not have a parent chart element
   */
  public ChartElementReadHandler() {
    chartElement = new ChartElement();
  }

  /**
   * Constructs a new chart element parser that has a parent chart element (so this will parse a nested xml tag).
   *
   * @param parentChartElement the parent <code>ChartElement</code> to which the <code>ChartElement</code> being parsed
   *                           will be a child.
   */
  public ChartElementReadHandler(final ChartElement parentChartElement) {
    this.chartElement = new ChartElement();
    parentChartElement.addChildElement(this.chartElement);
  }

  /**
   * Creates an object that will parse a child tag in the xml. Since all the elements in the chart xml will be parsed
   * as <code>ChartElements</code>s, we just need to create another parser like this one and let it know the resulting
   * <code>ChartElement</code> will be a child of our <code>ChartElement</code>
   *
   * @param uri     - uri used to help determine which child handler should be used (ignored in this case)
   * @param tagName - the child's tag name to help determine which child handler should be used (ignored in this case)
   * @param atts    - the child tag's attributes to help determine which child handler should be used (ignored in this case)
   * @return the newly created child tag parser
   */
  protected XmlReadHandler getHandlerForChild(final String uri, final String tagName, final Attributes atts) {
    return new ChartElementReadHandler(chartElement);
  }

  /**
   * Returns the <code>ChartElement</code> created by this element parser
   */
  public Object getObject() {
    return chartElement;
  }

  /**
   * Called by the parsing engine when the parsing has started on the tag. This will allow us to retrieve the
   * name of the tag being parsed and the attributed contained in that tag. This information will be retained and
   * stored in the <code>ChartElement</code> being created.
   *
   * @param attrs the attributes of the tag being parsed
   * @throws SAXException indicates an error during parsing of this tag
   */
  protected void startParsing(final Attributes attrs) throws SAXException {
    // Let the base class perform its operations
    super.startParsing(attrs);

    // Save the namespace and tag name of this xml tag
    chartElement.setNamespace(getUri());
    chartElement.setTagName(getTagName());

    // Save the attributes
    for (int i = 0; i < attrs.getLength(); ++i) {
      chartElement.setAttribute(attrs.getURI(i), attrs.getLocalName(i), attrs.getValue(i));
    }
  }

  /**
   * Called by the parsing engine when this tag has completed parsing. This is the only time at which we will know the
   * text contents of the tag. If the text content of the tag is not empty, we will need to retain that text
   * as part of the <code>ChartElement</code>.
   *
   * @throws SAXException indicates an error in the processing of this element
   */
  protected void doneParsing() throws SAXException {
    super.doneParsing();
    final String result = getResult();
    if (result.trim().length() > 0) {
      chartElement.setText(result);
    }
  }
}
