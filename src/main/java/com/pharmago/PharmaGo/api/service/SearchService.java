package com.pharmago.PharmaGo.api.service;

import com.pharmago.PharmaGo.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
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

    private final static String CATEGORY = "PM9";

    @Value("${kakao.rest.api.key}")
    private String apiKey;

    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    public KakaoApiResponseDto searchAddress(String address) {

        if (ObjectUtils.isEmpty(address)) return null;

        URI uri = uriBuilderService.buildUriByAddress(address);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + apiKey);

        HttpEntity entity = new HttpEntity(httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoApiResponseDto.class).getBody();

    }

    @Recover
    public KakaoApiResponseDto recover(RuntimeException e, String address) {
        log.error("모든 Retry가 실패하였습니다. 주소: {}, 에러: {}", address, e.getMessage());
        return null;
    }

    public KakaoApiResponseDto searchCategory(double latitude, double longitude, double radius) {
        URI uri = uriBuilderService.buildUriByCategory(latitude, longitude, radius, CATEGORY);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + apiKey);

        HttpEntity entity = new HttpEntity(httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoApiResponseDto.class).getBody();
    }
}
