package com.esri.geoevent.transport.jdbc;

import java.nio.BufferOverflowException;

import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.component.RunningException;
import com.esri.ges.core.component.RunningState;
import com.esri.ges.core.security.GeoEventServerCryptoService;
import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;
import com.esri.ges.transport.InboundTransportBase;
import com.esri.ges.transport.TransportDefinition;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;

public class JDBCInboundTransport extends InboundTransportBase implements Runnable
{
  /**
   * Initialize the i18n Bundle Logger
   * 
   * See {@link BundleLogger} for more info.
   */
  private static final BundleLogger LOGGER = BundleLoggerFactory.getLogger(JDBCInboundTransport.class);
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
  static {
    MAPPER.setSerializationInclusion(Include.NON_NULL);
  }
  
  private final GeoEventServerCryptoService cryptoService;
  
  // properties
  private String databaseDriverClass;
  private String connectionUrl;
  private String userName;
  private String password;
  private String statement;
  private String xFieldName;
  private String yFieldName;
	private int eventRate = 1;
  
  // instance variables
	private Thread thread = null;
	private String channelId = "1";

	public JDBCInboundTransport(GeoEventServerCryptoService cryptoService, TransportDefinition definition) throws ComponentException	{
		super(definition);
    this.cryptoService = cryptoService;
	}

  @Override
	public void run()
	{
    Connection conn = null;
    
		try	{
      
			applyProperties();
      
      Class.forName(databaseDriverClass);
      conn = DriverManager.getConnection(connectionUrl, userName, cryptoService.decrypt(password));
      
			setRunningState(RunningState.STARTED);
			while( getRunningState() == RunningState.STARTED ) {
				try {
          
          FeatureCollection fc = query(conn);
          byte [] bytes = MAPPER.writeValueAsBytes(fc);
          
          ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
          byteBuffer.put(bytes);
          byteBuffer.flip();
          
          byteListener.receive(byteBuffer, channelId);
          
          Thread.sleep(eventRate);
				}
				catch (BufferOverflowException boe) {
				  LOGGER.error("BUFFER_OVERFLOW_ERROR", boe);
				}
        catch (SQLException ex) {
				  LOGGER.error("SQL_ERROR", ex);
        }
				catch (Exception e) {
				  LOGGER.error("UNEXPECTED_ERROR", e);
					stop();
				}
			}
      setRunningState(RunningState.STOPPED);
		}
		catch (Throwable ex) {
		  LOGGER.error(ex.getMessage(), ex);
			setRunningState(RunningState.ERROR);
		}
    finally {
      if (conn!=null) {
        try {
          conn.close();
        } catch (SQLException ex) {}
      }
    }
	}
  
  private FeatureCollection query(Connection conn) throws SQLException {
    PreparedStatement stm = null;
    ResultSet rs = null;
    
    try {
      stm = conn.prepareStatement(statement);
      rs = stm.executeQuery();
      ResultSetMetaData metaData = rs.getMetaData();

      ArrayList<Feature> features = new ArrayList<>();
      while (rs.next()) {
        double x = rs.getDouble(xFieldName);
        double y = rs.getDouble(yFieldName);
        
        PointGeometry geometry = new PointGeometry(x, y);
        Feature feature = new Feature(geometry, null);
        
        features.add(feature);
      }
      
      FeatureCollection fc = new FeatureCollection(features);
      return fc;
    } finally {
      safeClose(rs);
      safeClose(stm);
    }
  }
  
  private void safeClose(Statement stm) {
    if (stm!=null) {
      try {
        stm.close();
      } catch (SQLException ex) {
        // IGNORE
      }
    }
  }
  
  private void safeClose(ResultSet rs) {
    if (rs!=null) {
      try {
        rs.close();
      } catch (SQLException ex) {
        // IGNORE
      }
    }
  }

  @Override
  public void start() throws RunningException
	{
    switch (getRunningState())
		{
		case STARTING:
		case STARTED:
		case STOPPING:
			return;
		}
		setRunningState(RunningState.STARTING);
		thread = new Thread(this);
		thread.start();
	}

	private void applyProperties() throws Exception {
		if (getProperty("databaseDriverClass").isValid()) {
			databaseDriverClass = (String) getProperty("databaseDriverClass").getValue();
		}
		if (getProperty("connectionUrl").isValid()) {
			connectionUrl = (String) getProperty("connectionUrl").getValue();
		}
		if (getProperty("userName").isValid()) {
			userName = (String) getProperty("userName").getValue();
		}
		if (getProperty("password").isValid()) {
			password = (String) getProperty("password").getValue();
		}
		if (getProperty("statement").isValid()) {
			statement = (String) getProperty("statement").getValue();
		}
		if (getProperty("xFieldName").isValid()) {
			xFieldName = (String) getProperty("xFieldName").getValue();
		}
		if (getProperty("yFieldName").isValid()) {
			yFieldName = (String) getProperty("yFieldName").getValue();
		}
		if (getProperty("eventRate").isValid()) {
			int value = (Integer) getProperty("eventRate").getValue();
			if( value > 0 && value != eventRate ) {
				eventRate = value;
			}
		}
	}
}