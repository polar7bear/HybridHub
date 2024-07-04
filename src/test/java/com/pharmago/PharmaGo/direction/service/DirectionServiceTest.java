package com.pharmago.PharmaGo.direction.service;

import com.pharmago.PharmaGo.api.dto.DocumentDto;
import com.pharmago.PharmaGo.direction.entity.Direction;
import com.pharmago.PharmaGo.pharmacy.dto.PharmacyDto;
import com.pharmago.PharmaGo.pharmacy.service.PharmacySearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DirectionServiceTest {

    @Mock
    private PharmacySearchService pharmacySearchService;

    @InjectMocks
    private DirectionService directionService;

    private List<PharmacyDto> pharmacyList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pharmacyList = new ArrayList<>();
        pharmacyList.add(
                PharmacyDto.builder()
                        .id(1L)
                        .pharmacyName("이층약국")
                        .pharmacyAddress("서울특별시 은평구 연서로 9")
                        .latitude(37.6001182)
                        .longitude(126.9156678)
                        .build());

        pharmacyList.add(
                PharmacyDto.builder()
                        .id(2L)
                        .pharmacyName("기림약국")
                        .pharmacyAddress("서울특별시 은평구 응암로22길 8")
                        .latitude(37.5936246)
                        .longitude(126.9188497)
                        .build());
    }

    @Test
    @DisplayName("결과 값이 거리 순으로 정렬이 되어야 한다.")
    void test1() {
        // given
        String address = "서울시 은평구 백련산로 146";
        double inputLatitude = 37.5976;
        double inputLongitude = 126.9261;

        DocumentDto documentDto = DocumentDto.builder()
                .addressName(address)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build();
        // when
        when(pharmacySearchService.searchPharmacyDtoList()).thenReturn(pharmacyList);
        List<Direction> directions = directionService.buildDirectionList(documentDto);

        // then
        assertEquals("기림약국", directions.get(0).getTargetPharmacyName());
        assertEquals("이층약국", directions.get(1).getTargetPharmacyName());
        assertEquals(2, directions.size());
    }

    @Test
    @DisplayName("정해진 반경 10km 내로 검색이 되어야한다.")
    void test2() {
        // given
        String address = "서울시 은평구 백련산로 146";
        double inputLatitude = 37.5976;
        double inputLongitude = 126.9261;

        DocumentDto documentDto = DocumentDto.builder()
                .addressName(address)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build();

        pharmacyList.add(
                PharmacyDto.builder()
                        .id(3L)
                        .pharmacyName("현대약국")
                        .pharmacyAddress("경기도 가평군 조종면 현창로 41-1")
                        .latitude(37.8185154)
                        .longitude(127.3480829)
                        .build());

        // when
        when(pharmacySearchService.searchPharmacyDtoList()).thenReturn(pharmacyList);
        List<Direction> directions = directionService.buildDirectionList(documentDto);

        // then
        assertEquals("기림약국", directions.get(0).getTargetPharmacyName());
        assertEquals("이층약국", directions.get(1).getTargetPharmacyName());
        assertEquals(2, directions.size());

    }

}