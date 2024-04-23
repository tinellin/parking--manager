package br.unesp.parking.manager.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "car_infos")
@Data
public class CarInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", unique = true, nullable = false, length = 7)
    private String licensePlate;

    @Column(name = "car_brand", nullable = false, length = 45)
    private String carBrand;

    @Column(name = "car_model", nullable = false, length = 45)
    private String carModel;

    @Column(name = "car_color", nullable = false, length = 45)
    private String carColor;

//    @OneToMany(mappedBy = "carInfo")
//    private CustomerParkingSpot customerParkingSpot;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public String toString() {
        return "CarInfo{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carColor='" + carColor + '\'' +
                '}';
    }
}
