package com.pharmago.PharmaGo.pharmacy.controller;

import com.pharmago.PharmaGo.pharmacy.cache.PharmacyRedisService;
import com.pharmago.PharmaGo.pharmacy.dto.PharmacyDto;
import com.pharmago.PharmaGo.pharmacy.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;
    private final PharmacyRedisService pharmacyRedisService;

    // 데이터베이스에 있는 약국 데이터들을 레디스와 동기화

    @GetMapping("/redis/save")
    public String savePharmacy() {
        List<PharmacyDto> pharmacyList = pharmacyService.findAll().stream()
                .map(m -> PharmacyDto.builder()
                        .id(m.getId())
                        .pharmacyName(m.getPharmacyName())
                        .pharmacyAddress(m.getPharmacyAddress())
                        .latitude(m.getLatitude())
                        .longitude(m.getLongitude())
                        .build())
                .toList();

        pharmacyList.forEach(pharmacyRedisService::save);

        return "Redis and Database Synchronization success";
    }


}
