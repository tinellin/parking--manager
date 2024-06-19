package br.unesp.parking.manager.api.repository.projection;

public interface ParkingSpotProjection {
    Long getId();
    String getCode();
    String getStatus();
    String getType();
}
