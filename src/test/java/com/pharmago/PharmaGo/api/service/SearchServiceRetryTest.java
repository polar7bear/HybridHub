package com.pharmago.PharmaGo.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmago.PharmaGo.AbstractIntegrationContainerTest;
import com.pharmago.PharmaGo.api.dto.DocumentDto;
import com.pharmago.PharmaGo.api.dto.KakaoApiResponseDto;
import com.pharmago.PharmaGo.api.dto.MetaDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class SearchServiceRetryTest extends AbstractIntegrationContainerTest {

    @Autowired
    private SearchService searchService;

    @MockBean
    private UriBuilderService uriBuilderService;

    private MockWebServer mockWebServer;

    private ObjectMapper objectMapper = new ObjectMapper();

    String address = "경기도 고양시 일산서구 대화동 호수로838번길";

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Retry가 성공 해야한다.")
    void searchAddressRetrySucess() throws JsonProcessingException, InterruptedException {
        // given
        MetaDto metaDto = new MetaDto(1);
        DocumentDto documentDto = DocumentDto.builder()
                .addressName(address)
                .build();
        KakaoApiResponseDto expectedResponse = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto));
        String uri = mockWebServer.url("/").toString();

        when(uriBuilderService.buildUriByAddress(address)).thenReturn(URI.create(uri));

        // when
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(expectedResponse)));

        KakaoApiResponseDto kakaoApiResult = searchService.searchAddress(address);

        // then
        assertEquals("GET", mockWebServer.takeRequest().getMethod());
        assertEquals(1, kakaoApiResult.getDocumentList().size());
        assertEquals(1, kakaoApiResult.getMetaDto().getTotalCount());
        assertEquals(address, kakaoApiResult.getDocumentList().get(0).getAddressName());

        verify(uriBuilderService, times(2)).buildUriByAddress(address);
    }

    @Test
    @DisplayName("리트라이가 2번 모두 실패할 경우 recover 메서드가 작동해야 한다.")
    void recover() {
        // given
        String uri = mockWebServer.url("/").toString();

        // when
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));

        KakaoApiResponseDto res = searchService.searchAddress(address);

        // then
        verify(uriBuilderService, times(2)).buildUriByAddress(address);
        assertNull(res);
    }
}