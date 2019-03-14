package com.esri.geoevent.transport.jdbc.geojson;

/**
 * Point geometry.
 */
public class PointGeometry {
  public final String type = "Point";
  public final double [] coordinates;

  public PointGeometry(double x, double y) {
    this.coordinates = new double[] {x, y};
  }

  @Override
  public String toString() {
    return String.format("{ \"type\": \"%s\", \"coordinates\": [%f, %f]}", type, coordinates[0], coordinates[1]);
  }
  
}
