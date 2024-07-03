package com.pharmago.PharmaGo.pharmacy.service;

import com.pharmago.PharmaGo.pharmacy.entity.Pharmacy;
import com.pharmago.PharmaGo.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(pharmacy)) {
            log.error("[PharmacyService updateAddress] Pharmacy with id {} not found", id);
            return;
        }

        pharmacy.changePharmacyAddress(address);
    }

    public void updateAddressWithoutTransactional(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);

        if (Objects.isNull(pharmacy)) {
            log.error("[PharmacyService updateAddress] Pharmacy with id {} not found", id);
            return;
        }

        pharmacy.changePharmacyAddress(address);
    }
}
