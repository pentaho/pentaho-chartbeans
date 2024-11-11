/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/

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
