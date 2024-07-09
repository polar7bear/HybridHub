package com.pharmago.PharmaGo.pharmacy.service;

import com.pharmago.PharmaGo.api.dto.AddressResponseDto;
import com.pharmago.PharmaGo.api.dto.DocumentDto;
import com.pharmago.PharmaGo.api.dto.KakaoApiResponseDto;
import com.pharmago.PharmaGo.api.service.Base62Service;
import com.pharmago.PharmaGo.api.service.SearchService;
import com.pharmago.PharmaGo.direction.entity.Direction;
import com.pharmago.PharmaGo.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService {

    private final DirectionService directionService;
    private final SearchService searchService;

    private final Base62Service base62Service;

    @Value("${base.url}")
    private String baseUrl;

    public List<AddressResponseDto> recommendPharmacyList(String address) {
        KakaoApiResponseDto searchedDto = searchService.searchAddress(address);

        if (Objects.isNull(searchedDto) || CollectionUtils.isEmpty(searchedDto.getDocumentList())) {
            log.error("[RecommendService recommendPharmacyList] Search result is empty or null. address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = searchedDto.getDocumentList().getFirst();
        List<Direction> directions = directionService.buildDirectionList(documentDto);

        log.info("[RecommendService recommendPharmacyList] Directions: {}", directions.get(0).getTargetPharmacyName());

        return directionService.saveAll(directions)
                .stream()
                .map(dir -> AddressResponseDto.from(dir, baseUrl + base62Service.encode(dir.getId())))
                .collect(Collectors.toList());
    }

}
