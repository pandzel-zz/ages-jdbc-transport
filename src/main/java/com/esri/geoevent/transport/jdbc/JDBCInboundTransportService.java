package com.esri.geoevent.transport.jdbc;

import com.esri.geoevent.transport.jdbc.JDBCInboundTransport;
import com.esri.ges.core.component.ComponentException;
import com.esri.ges.transport.Transport;
import com.esri.ges.transport.TransportServiceBase;
import com.esri.ges.transport.util.XmlTransportDefinition;

public class JDBCInboundTransportService extends TransportServiceBase
{
  public JDBCInboundTransportService()
  {
    definition = new XmlTransportDefinition(getResourceAsStream("jdbc-inbound-transport-definition.xml"));
  }
  
  public Transport createTransport() throws ComponentException
  {
    return new JDBCInboundTransport(definition);
  }
}