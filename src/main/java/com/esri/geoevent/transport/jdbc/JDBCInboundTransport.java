package com.esri.geoevent.transport.jdbc;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.component.RunningException;
import com.esri.ges.core.component.RunningState;
import com.esri.ges.framework.i18n.BundleLogger;
import com.esri.ges.framework.i18n.BundleLoggerFactory;
import com.esri.ges.transport.InboundTransportBase;
import com.esri.ges.transport.TransportDefinition;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCInboundTransport extends InboundTransportBase implements Runnable
{
  /**
   * Initialize the i18n Bundle Logger
   * 
   * See {@link BundleLogger} for more info.
   */
  private static final BundleLogger LOGGER = BundleLoggerFactory.getLogger(JDBCInboundTransport.class);
  
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

	public JDBCInboundTransport(TransportDefinition definition) throws ComponentException	{
		super(definition);
	}

  @Override
	public void run()
	{
    Connection conn = null;
    
		try	{
      
			applyProperties();
      Class.forName(databaseDriverClass);
      conn = DriverManager.getConnection(connectionUrl, userName, password);
      
			setRunningState(RunningState.STARTED);
			while( getRunningState() == RunningState.STARTED )
			{
				try
				{
          // TODO: provide run() implementation
					// byteListener.receive(byteBuffer, channelId);
          Thread.sleep(eventRate);
				}
				catch (BufferOverflowException boe)
				{
				  LOGGER.error("BUFFER_OVERFLOW_ERROR", boe);
				}
				catch (Exception e)
				{
				  LOGGER.error("UNEXPECTED_ERROR", e);
					stop();
				}
			}
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