package com.pharmago.PharmaGo.pharmacy.service;

import com.pharmago.PharmaGo.AbstractIntegrationContainerTest;
import com.pharmago.PharmaGo.pharmacy.entity.Pharmacy;
import com.pharmago.PharmaGo.pharmacy.repository.PharmacyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PharmacyServiceTest extends AbstractIntegrationContainerTest {

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @BeforeEach
    void setup() {
        pharmacyRepository.deleteAll();
    }

    @Test
    @DisplayName("JPA dirty checking success")
    void dirtyCheckTest1() {

        // given
        String address = "올림픽대로";
        String modifiedAddress = "강변북로";
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
        Pharmacy save = pharmacyRepository.save(pharma);
        pharmacyService.updateAddress(save.getId(), modifiedAddress);

        List<Pharmacy> res = pharmacyRepository.findAll();

        // then
        assertEquals(modifiedAddress, res.get(0).getPharmacyAddress());
    }

    @Test
    @DisplayName("JPA dirty checking fail test")
    void dirtyCheckTest2() {

        // given
        String address = "올림픽대로";
        String modifiedAddress = "강변북로";
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
        Pharmacy save = pharmacyRepository.save(pharma);
        pharmacyService.updateAddressWithoutTransactional(save.getId(), modifiedAddress);

        List<Pharmacy> res = pharmacyRepository.findAll();

        // then
        assertEquals(address, res.get(0).getPharmacyAddress());
    }

}