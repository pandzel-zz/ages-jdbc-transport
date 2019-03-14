package com.esri.geoevent.transport.jdbc;

import com.esri.ges.core.property.LabeledValue;
import com.esri.ges.core.property.PropertyDefinition;
import com.esri.ges.core.property.PropertyException;
import com.esri.ges.core.property.PropertyType;
import com.esri.ges.transport.TransportDefinitionBase;
import com.esri.ges.transport.TransportType;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JDBC inbound transport definition.
 */
public class JDBCInboundTransportDefinition extends TransportDefinitionBase {
	private static final Log LOG = LogFactory.getLog(JDBCInboundTransportDefinition.class);
  
  public JDBCInboundTransportDefinition() {
    super(TransportType.INBOUND);
    
    try {
      PropertyDefinition databaseDriverClass = new PropertyDefinition(
              "databaseDriverClass", 
              PropertyType.String, 
              "", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_DATABASE_LABEL}", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_DATABASE_DESC}", 
              true, false, Arrays.asList(new LabeledValue[]{
                new LabeledValue("Oracle", "oracle.jdbc.driver.OracleDriver"),
                new LabeledValue("SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),
                new LabeledValue("Postgres", "org.postgresql.Driver"),
                new LabeledValue("MySql", "com.mysql.jdbc.Driver")
              }));
      PropertyDefinition connectionUrl = new PropertyDefinition(
              "connectionUrl", 
              PropertyType.String, 
              "", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_URL_LABEL}", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_URL_DESC}", 
              true, false);
      PropertyDefinition userName = new PropertyDefinition(
              "userName", 
              PropertyType.String, 
              "", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_USERNAME_LABEL}", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_USERNAME_DESC}", 
              true, false);
      PropertyDefinition password = new PropertyDefinition(
              "password", 
              PropertyType.Password, 
              "", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_PASSWORD_LABEL}", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_PASSWORD_DESC}", 
              true, false);
      PropertyDefinition statement = new PropertyDefinition(
              "statement", 
              PropertyType.String, 
              "", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_STATEMENT_LABEL}", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_STATEMENT_DESC}", 
              true, false);
      PropertyDefinition xField = new PropertyDefinition(
              "xFieldName", 
              PropertyType.String, 
              "X", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_XFLD_LABEL}", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_XFLD_DESC}", 
              true, false);
      PropertyDefinition yField = new PropertyDefinition(
              "yFieldName", 
              PropertyType.String, 
              "Y", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_YFLD_LABEL}", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_YFLD_DESC}", 
              true, false);
      PropertyDefinition eventRate = new PropertyDefinition(
              "eventRate", 
              PropertyType.Integer, 
              10, 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_EVENT_RATE_LABEL}", 
              "${com.esri.geoevent.transport.jdbc-transport.JDBC_EVENT_RATE_DESC}", 
              true, false);
      
      propertyDefinitions.put(databaseDriverClass.getPropertyName(), databaseDriverClass);
      propertyDefinitions.put(connectionUrl.getPropertyName(), connectionUrl);
      propertyDefinitions.put(userName.getPropertyName(), userName);
      propertyDefinitions.put(password.getPropertyName(), password);
      propertyDefinitions.put(statement.getPropertyName(), statement);
      propertyDefinitions.put(xField.getPropertyName(), xField);
      propertyDefinitions.put(yField.getPropertyName(), yField);
      propertyDefinitions.put(eventRate.getPropertyName(), eventRate);
      
    } catch (PropertyException ex) {
      LOG.error(ex.getMessage());
    }
  }

  @Override
  public String getVersion() {
    return "10.6.1";
  }

  @Override
  public String getDomain() {
    return "sample.transport.inbound";
  }

  @Override
  public String getDescription() {
    return "${com.esri.geoevent.transport.jdbc-transport.JDBC_DESC}";
  }

  @Override
  public String getLabel() {
    return "${com.esri.geoevent.transport.jdbc-transport.JDBC_LABEL}";
  }

  @Override
  public String getName() {
    return "JDBC";
  }
}
