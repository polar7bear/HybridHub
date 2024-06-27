package com.pharmago.PharmaGo.api.service;

import com.pharmago.PharmaGo.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final RestTemplate restTemplate;
    private final UriBuilderService uriBuilderService;

    @Value("${kakao.rest-api-key}")
    private String apiKey;

    public KakaoApiResponseDto searchAddress(String address) {

        if (ObjectUtils.isEmpty(address)) return null;

        URI uri = uriBuilderService.buildUri(address);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + apiKey);

        HttpEntity entity = new HttpEntity(httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoApiResponseDto.class).getBody();

    }
}
