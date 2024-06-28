package com.pharmago.PharmaGo.pharmacy.repository;

import com.pharmago.PharmaGo.AbstractIntegrationContainerTest;
import com.pharmago.PharmaGo.pharmacy.entity.Pharmacy;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PharmacyRepositoryTest extends AbstractIntegrationContainerTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Test
    public void DB세이브() {
        // given
        String address = "올림픽대로";
        String name = "세화약국";
        double lat = 123.456;
        double log = 456.789;

        Pharmacy pharma = Pharmacy.builder()
                .pharmacyName(name)
                .pharmacyAddress(address)
                .latitude(lat)
                .longitude(log)
                .build();
        // when
        Pharmacy res = pharmacyRepository.save(pharma);

        // then
        Assertions.assertEquals(name, res.getPharmacyName());
        Assertions.assertEquals(address, res.getPharmacyAddress());
        Assertions.assertEquals(lat, res.getLatitude());
        Assertions.assertEquals(log, res.getLongitude());

    }

}