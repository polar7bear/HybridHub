package com.pharmago.PharmaGo.pharmacy.service;

import com.pharmago.PharmaGo.pharmacy.cache.PharmacyRedisService;
import com.pharmago.PharmaGo.pharmacy.dto.PharmacyDto;
import com.pharmago.PharmaGo.pharmacy.entity.Pharmacy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

class PharmacySearchServiceTest {

    private PharmacySearchService pharmacySearchService;

    @Mock
    private PharmacyService pharmacyService;

    @Mock
    private PharmacyRedisService pharmacyRedisService;

    private List<Pharmacy> pharmacyList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pharmacySearchService = new PharmacySearchService(pharmacyService, pharmacyRedisService);
        pharmacyList = new ArrayList<>();

        pharmacyList.add(Pharmacy.builder()
                .id(1L)
                .pharmacyName("약국1")
                .latitude(37.123)
                .longitude(126.123)
                .build());
        pharmacyList.add(Pharmacy.builder()
                .id(2L)
                .pharmacyName("약국2")
                .latitude(37.456)
                .longitude(126.456)
                .build());
    }

    @Test
    @DisplayName("레디스 장애시 DB를 이용하여 약국 데이터를 조회한다.")
    void redisFailover() {

        // when
        when(pharmacyRedisService.findAll()).thenReturn(Collections.emptyList());
        when(pharmacyService.findAll()).thenReturn(pharmacyList);

        List<PharmacyDto> pharmacyDtos = pharmacySearchService.searchPharmacyDtoList();

        // then
        Assertions.assertEquals(2, pharmacyDtos.size());
    }
}