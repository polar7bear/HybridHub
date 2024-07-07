package com.pharmago.PharmaGo.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class UriBuilderService {

    private final static String KAKAO_ADDRESS_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    private final static String KAKAO_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddress(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_ADDRESS_SEARCH_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[UriBuilderService buildUri] address: {}, uri: {}", address, uri);
        return uri;
    }

    public URI buildUriByCategory(double latitude, double longitude, double radius, String category) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_CATEGORY_SEARCH_URL);
        uriBuilder.queryParam("category_group_name", category);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("radius", radius * 1000);
        uriBuilder.queryParam("sort", "distance");

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[UriBuilderService buildUri] address: {}, uri: {}", latitude, longitude);
        return uri;
    }
}
