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
 * Copyright (c) 2002-2019 Hitachi Vantara..  All rights reserved.
 */
package org.pentaho.chart.model.util;

import com.thoughtworks.xstream.security.ForbiddenClassException;
import org.junit.Test;
import org.pentaho.chart.model.ChartDataDefinition;
import org.pentaho.chart.model.ChartModel;
import org.pentaho.chart.model.ChartTitle;
import static org.junit.Assert.assertEquals;

public class ChartSerializerTest {

  @Test
  public void deSerializeWithAllowedClassTest() {
    ChartModel model = new ChartModel();
    model.setTitle( new ChartTitle( "The Title" ) );
    String serialized = ChartSerializer.serialize( model, ChartSerializer.ChartSerializationFormat.JSON );
    ChartModel resModel = ChartSerializer.deSerialize( serialized, ChartSerializer.ChartSerializationFormat.JSON );
    assertEquals( "The Title", resModel.getTitle().getText() );
  }

  @Test( expected = ForbiddenClassException.class )
  public void deSerializeWithUnallowedClassTest() {
    String serialized = ChartSerializer.serialize( new MyModel(), ChartSerializer.ChartSerializationFormat.JSON );
    ChartSerializer.deSerialize( serialized, ChartSerializer.ChartSerializationFormat.JSON );
  }

  @Test
  public void deSerializeDataDefinitionWithAllowedClassTest() {
    ChartDataDefinition dataDefinition = new ChartDataDefinition();
    dataDefinition.setQuery( "queryexample" );
    String serialized = ChartSerializer.serializeDataDefinition( dataDefinition, ChartSerializer.ChartSerializationFormat.JSON );
    ChartDataDefinition resDataDefinition = ChartSerializer.deSerializeDataDefinition( serialized, ChartSerializer.ChartSerializationFormat.JSON );
    assertEquals( "queryexample", resDataDefinition.getQuery() );
  }

  @Test( expected = ForbiddenClassException.class )
  public void deSerializeDataDefinitionWithUnallowedClassTest() {
    String serialized = ChartSerializer.serializeDataDefinition( new MyChartDataDefinition(), ChartSerializer.ChartSerializationFormat.JSON );
    ChartSerializer.deSerializeDataDefinition( serialized, ChartSerializer.ChartSerializationFormat.JSON );
  }

  private class MyChartDataDefinition extends ChartDataDefinition {
    public MyChartDataDefinition() {
      super();
    }
  }

  private class MyModel extends ChartModel {
    public MyModel() {
      super();
    }
  }
}
