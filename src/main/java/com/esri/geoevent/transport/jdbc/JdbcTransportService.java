/*
 * Copyright 2019 Piotr Andzel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esri.geoevent.transport.jdbc;

import com.esri.ges.core.component.ComponentException;
import com.esri.ges.transport.Transport;
import com.esri.ges.transport.TransportServiceBase;
import com.esri.ges.transport.util.XmlTransportDefinition;

/**
 * JDBC transport service.
 */
public class JdbcTransportService extends TransportServiceBase {
  
  public JdbcTransportService() {
    definition = new XmlTransportDefinition(getResourceAsStream("jdbc-transport-definition.xml"));
  }
  
  public Transport createTransport() throws ComponentException {
    return new JdbcTransport(definition);
  }
  
}
