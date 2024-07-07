package com.pharmago.PharmaGo.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UriBuilderServiceTestToJava {

    private UriBuilderService uriBuilderService;

    @BeforeEach
    void setup() {
        uriBuilderService = new UriBuilderService();
    }

    @Test
    @DisplayName("한글 파라미터의 경우 정상적으로 인코딩이 되어야 한다")
    void encodeTest() {
        //given
        String address = "고양시 일산서구";
        Charset charset = StandardCharsets.UTF_8;

        //when
        URI uri = uriBuilderService.buildUriByAddress(address);
        String decoded = URLDecoder.decode(uri.toString(), charset);

        //then
        System.out.println("uri = " + uri);
        assertEquals("https://dapi.kakao.com/v2/local/search/address.json?query=고양시 일산서구", decoded);
    }

}