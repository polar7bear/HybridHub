package com.pharmago.PharmaGo.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class UriBuilderService {

    private final static String KAKAO_REQUEST_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUri(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_REQUEST_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[UriBuilderService buildUri] address: {}, uri: {}", address, uri);
        return uri;
    }
}
