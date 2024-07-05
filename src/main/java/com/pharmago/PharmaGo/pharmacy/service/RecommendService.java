package com.pharmago.PharmaGo.pharmacy.service;

import com.pharmago.PharmaGo.api.dto.DocumentDto;
import com.pharmago.PharmaGo.api.dto.KakaoApiResponseDto;
import com.pharmago.PharmaGo.api.service.SearchService;
import com.pharmago.PharmaGo.direction.entity.Direction;
import com.pharmago.PharmaGo.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService {

    private final DirectionService directionService;
    private final SearchService searchService;

    public void recommendPharmacyList(String address) {
        KakaoApiResponseDto searchedDto = searchService.searchAddress(address);

        if (Objects.isNull(searchedDto) || CollectionUtils.isEmpty(searchedDto.getDocumentList())) {
            log.error("[RecommendService recommendPharmacyList] Search result is empty or null. address: {}", address);
            return;
        }

        DocumentDto documentDto = searchedDto.getDocumentList().get(0);
        List<Direction> directions = directionService.buildDirectionList(documentDto);
        directionService.saveAll(directions);
    }

}
