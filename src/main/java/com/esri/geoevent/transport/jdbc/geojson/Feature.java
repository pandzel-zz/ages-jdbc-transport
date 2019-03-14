package com.esri.geoevent.transport.jdbc.geojson;

import java.util.Collections;
import java.util.Map;

/**
 * Feature.
 */
public class Feature {
  public final String type = "Feature";
  public final PointGeometry geometry;
  public final Map<String, String> properties;

  public Feature(PointGeometry geometry, Map<String, String> properties) {
    this.geometry = geometry;
    this.properties = properties!=null? properties: Collections.emptyMap();
  }
  
}
