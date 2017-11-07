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

import java.util.ArrayList;
import java.util.List;

import org.pentaho.chart.core.ChartDocument;
import org.pentaho.chart.core.ChartElement;
import org.pentaho.reporting.libraries.base.util.StringUtils;

/**
 * Performs validation on the <code>ChartDocument</code> and ensures it is proper
 * for rendering a chart.
 * @author David Kincade
 */
public class ChartDocumentValidator {

  /**
   * The ChartDocument being validated
   */
  private ChartDocument chartDoc;

  /**
   * The list of messages that indicate problems with the chart document
   */
  private final List<String> messages;

  /**
   * Constructs a validator for the specified ChartDocument
   * @param chartDoc
   */
  public ChartDocumentValidator(final ChartDocument chartDoc) {
    this();
    this.chartDoc = chartDoc;
    revalidate();
  }

  /**
   * Package level constructor for unit testing only
   */
  ChartDocumentValidator() {
    messages = new ArrayList<String>();
  }

  /**
   * Performs the validation process on the chart document
   */
  public void revalidate() {
    // Reset the problem messages
    messages.clear();

    // Perform all the tests (a failure at one level will prevent subsequence levels of test
    // from being performed)

    // TEST LEVEL 1
    if (messages.isEmpty()) {
      validateChartTag();
    }

    // TEST LEVEL 2
    if (messages.isEmpty()) {
      validateSeriesTags();
    }

    // TEST LEVEL 3
    if (messages.isEmpty()) {
      validateGroupTags(chartDoc.getGroupChartElements());
    }
  }

  /**
   * Validates the series tags. If any errors are found, entries will be
   * added to the messages list.
   */
  private void validateSeriesTags() {
    // Make sure there are one or more series elements
    final ChartElement[] seriesElements = chartDoc.getSeriesChartElements();
    if (seriesElements.length == 0) {
      messages.add("Could not find any <series> tags as children of the <chart> tag");
    }
  }

  /**
   * Validates the chart tag of the document. If any errors are found, entries will be
   * added to the messages list.
   */
  private void validateChartTag() {
    if (chartDoc == null || chartDoc.getRootElement() == null) {
      messages.add("Chart Document is empty");
    } else if (!ChartElement.TAG_NAME_CHART.equals(chartDoc.getRootElement().getTagName())) {
      messages.add("<chart> must be the root chart element"); 
    }
  }

  /**
   * Validates the group tags. If an errors are found, entries will be
   * added to the messages list.
   *
   * The Group tag should only have other group tags and any group can be
   * marked as "stacked".
   *
   * NOTE: this method is package protected for testing purposes only
   */
  void validateGroupTags(final ChartElement[] groupElements) {
    if (groupElements.length > 0) {
      // Only process the 1st element
      ChartElement groupElement = groupElements[0];
      ChartElement parentGroupElement = null;
      boolean isStacked = false;
      while (groupElement != null) {
        // See if the stacked flag is set correctly
        final String stacked = (String)groupElement.getAttribute(ChartElement.STACKED);
        final boolean elementStacked = StringUtils.toBoolean(stacked);
        if (isStacked && !elementStacked) {
          // Set the stack property
          groupElement.setAttribute(ChartElement.STACKED, Boolean.TRUE.toString());
        }
        isStacked = isStacked || elementStacked;

        // Get the next group
        parentGroupElement = groupElement;
        groupElement = groupElement.getFirstChildItem();
        while (groupElement != null && !ChartElement.TAG_NAME_GROUP.equals(groupElement.getTagName())) {
          groupElement = groupElement.getNextItem();
        }

        // Make sure we found a new group element
        if (groupElement == null && !isStacked) {
          // Set the stacked on the parent
          parentGroupElement.setAttribute(ChartElement.STACKED, Boolean.TRUE.toString());
        }
      }
    }
  }

  /**
   * Returns the number of messages generated from the last validation test
   */
  public int getMessageCount() {
    return messages.size();
  }
}
