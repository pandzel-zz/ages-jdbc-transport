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
import com.esri.ges.core.component.RunningException;
import com.esri.ges.transport.InboundTransportBase;
import com.esri.ges.transport.TransportDefinition;

/**
 * JDBC transport.
 */
public class JdbcTransport extends InboundTransportBase implements Runnable {

	public JdbcTransport(TransportDefinition definition) throws ComponentException
	{
		super(definition);
	}

	public void applyProperties() throws Exception {
    // TODO: provide applyProperties() implementation
	}

  @Override
  public void start() throws RunningException {
    // TODO: provide start() implementation
  }

  @Override
  public void run() {
    // TODO: provide run() implementation
  }
  
}
