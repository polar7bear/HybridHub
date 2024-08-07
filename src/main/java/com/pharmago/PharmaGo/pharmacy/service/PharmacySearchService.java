package com.pharmago.PharmaGo.pharmacy.service;

import com.pharmago.PharmaGo.pharmacy.cache.PharmacyRedisService;
import com.pharmago.PharmaGo.pharmacy.dto.PharmacyDto;
import com.pharmago.PharmaGo.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyService pharmacyService;
    private final PharmacyRedisService pharmacyRedisService;

    public List<PharmacyDto> searchPharmacyDtoList() {
        // redis
        List<PharmacyDto> list = pharmacyRedisService.findAll();

        if (!list.isEmpty()) {
            log.info("Found {} pharmacies in Redis", list.size());
            return list;
        }

        //db
        return pharmacyService.findAll()
                .stream()
                .map(this::convertToPharmacyDto)
                .collect(Collectors.toList());
    }

    private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy) {
        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyName(pharmacy.getPharmacyName())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }
}
