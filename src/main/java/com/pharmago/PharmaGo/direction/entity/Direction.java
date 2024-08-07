package com.pharmago.PharmaGo.direction.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "direction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputAddress;

    private double inputLatitude;

    private double inputLongitude;

    private String targetPharmacyName;

    private String targetAddress;

    private double targetLatitude;

    private double targetLongitude;

    private double distance;
}
