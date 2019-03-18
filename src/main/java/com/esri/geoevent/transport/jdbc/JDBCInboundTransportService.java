package com.esri.geoevent.transport.jdbc;

import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.security.GeoEventServerCryptoService;
import com.esri.ges.transport.Transport;
import com.esri.ges.transport.TransportServiceBase;

public class JDBCInboundTransportService extends TransportServiceBase
{
  private GeoEventServerCryptoService cryptoService;
  
  public JDBCInboundTransportService()
  {
    definition = new JDBCInboundTransportDefinition();
  }
  
  public Transport createTransport() throws ComponentException
  {
    return new JDBCInboundTransport(cryptoService, definition);
  }

  public void setCryptoService(GeoEventServerCryptoService cryptoService) {
    this.cryptoService = cryptoService;
  }
}