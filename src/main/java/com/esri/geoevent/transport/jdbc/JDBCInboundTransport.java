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

	public void applyProperties() throws Exception {
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

	public void run()
	{
    // TODO: provide run() implementation
    /*
		try
		{
			applyProperties();
			setRunningState(RunningState.STARTED);
			while( getRunningState() == RunningState.STARTED )
			{
				try
				{
					for( int i = 0; i < eventSize; i++ )
						byteBuffer.put( (byte) i );
					byteBuffer.flip();
					byteListener.receive(byteBuffer, channelId);
					byteBuffer.compact();
				}
				catch (BufferOverflowException boe)
				{
				  LOGGER.error("BUFFER_OVERFLOW_ERROR", boe);
					byteBuffer.clear();
				}
				catch (Exception e)
				{
				  LOGGER.error("UNEXPECTED_ERROR", e);
					stop();
				}
			}
		}
		catch (Throwable ex)
		{
		  LOGGER.error(ex.getMessage(), ex);
			setRunningState(RunningState.ERROR);
		}
    */
	}

	@SuppressWarnings("incomplete-switch")
  public void start() throws RunningException
	{
    // TODO: provide start() implementation
    /*
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
    */
	}
}