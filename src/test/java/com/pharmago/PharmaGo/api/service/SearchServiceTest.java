package com.pharmago.PharmaGo.api.service;

import com.pharmago.PharmaGo.AbstractIntegrationContainerTest;
import com.pharmago.PharmaGo.api.dto.KakaoApiResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class SearchServiceTest extends AbstractIntegrationContainerTest {

    @Autowired
    private SearchService searchService;


    @Test
    @DisplayName("주소 값이 null 이라면, return 값도 null 이어야 한다.")
    void searchAddress_1() {
        // given
        String address = null;
        // when
        KakaoApiResponseDto dto = searchService.searchAddress(address);
        // then
        assertNull(dto);
    }

    @Test
    @DisplayName("주소 값이 유효하다면, documents가 정상적으로 반환되어야한다.")
    void searchAddress_2() {
        // given
        String address = "호수로838번길";

        // when
        KakaoApiResponseDto dto = searchService.searchAddress(address);

        // then
        assertFalse(dto.getDocumentList().isEmpty());
        assertTrue(dto.getMetaDto().getTotalCount() > 0);
        assertNotNull(dto.getDocumentList().get(0).getAddressName());
    }

    @ParameterizedTest
    @CsvSource({
            "서울 특별시 성북구 종암동, true",
            "서울 성북구 종암동 91, true",
            "서울 대학로, true",
            "서울 성북구 종암동 잘못된 주소, false",
            "광진구 구의동 251-45, true",
            "광진구 구의동 251-45523423, false",
            "'', false"
    })
    @DisplayName("주소를 입력하면 위도, 경도가 정상적으로 반환되어야 한다.")
    void latlonTest(String inputAddress, boolean expected) {
        // given
        boolean actualResult;

        // when
        KakaoApiResponseDto res = searchService.searchAddress(inputAddress);

        // then
        if (res == null) {
            actualResult = false;
        } else {
            actualResult = !res.getDocumentList().isEmpty();
        }

        assertEquals(expected, actualResult);
    }
}