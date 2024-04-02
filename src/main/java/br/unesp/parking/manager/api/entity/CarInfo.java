package br.unesp.parking.manager.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "car_infos")
@Data
public class CarInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", nullable = false, length = 7)
    private String licensePlate;

    @Column(name = "car_brand", nullable = false, length = 45)
    private String carBrand;

    @Column(name = "car_model", nullable = false, length = 45)
    private String carModel;

    @Column(name = "car_color", nullable = false, length = 45)
    private String carColor;

    @OneToOne(mappedBy = "carInfo")
    private ClientParkingSpot clientParkingSpot;
}
