package com.esri.geoevent.transport.jdbc;

import java.util.Collection;

/**
 * Feature collections.
 */
public class FeatureCollection {
  public final String type = "FeatureCollection";
  public final Feature [] features;

  public FeatureCollection(Feature[] features) {
    this.features = features;
  }

  public FeatureCollection(Collection<Feature> features) {
    this.features = features.toArray(new Feature[features.size()]);
  }
  
}
