package com.pharmago.PharmaGo.pharmacy.cache;

import com.pharmago.PharmaGo.AbstractIntegrationContainerTest;
import com.pharmago.PharmaGo.pharmacy.dto.PharmacyDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class PharmacyRedisServiceTest extends AbstractIntegrationContainerTest {

    @Autowired
    private PharmacyRedisService pharmacyRedisService;

    @BeforeEach
    void setUp() {
        pharmacyRedisService.findAll().forEach(dto -> pharmacyRedisService.delete(dto.getId()));
    }

    @Test
    @DisplayName("레디스 save 성공")
    void save() {
        // given
        PharmacyDto dto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName("약국1")
                .pharmacyAddress("호수로838번길")
                .build();

        // when
        pharmacyRedisService.save(dto);
        List<PharmacyDto> list = pharmacyRedisService.findAll();

        // then
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(dto.getId(), list.getFirst().getId());
        Assertions.assertEquals(dto.getPharmacyName(), list.getFirst().getPharmacyName());
        Assertions.assertEquals(dto.getPharmacyAddress(), list.getFirst().getPharmacyAddress());

    }

    @Test
    @DisplayName("레디스 save 실패")
    void saveFail() {
        // given
        PharmacyDto dto = PharmacyDto.builder()
                .build();

        // when
        pharmacyRedisService.save(dto);
        List<PharmacyDto> list = pharmacyRedisService.findAll();

        // then
        Assertions.assertEquals(0, list.size());
        Assertions.assertNull(dto.getId());
    }

    @Test
    @DisplayName("레디스 delete 성공")
    void delete() {
        // given
        PharmacyDto dto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName("약국1")
                .pharmacyAddress("호수로838번길")
                .build();

        // when
        pharmacyRedisService.save(dto);
        pharmacyRedisService.delete(dto.getId());
        List<PharmacyDto> list = pharmacyRedisService.findAll();

        // then
        Assertions.assertEquals(0, list.size());
    }
}