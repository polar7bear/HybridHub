package com.pharmago.PharmaGo.direction.service;

import com.pharmago.PharmaGo.api.dto.DocumentDto;
import com.pharmago.PharmaGo.api.service.SearchService;
import com.pharmago.PharmaGo.direction.entity.Direction;
import com.pharmago.PharmaGo.direction.repository.DirectionRepository;
import com.pharmago.PharmaGo.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {

    private final static int MAX_SEARCH_COUNT = 3;
    private final static double RADIUS_KM = 10.0;

    private final PharmacySearchService pharmacySearchService;

    private final DirectionRepository directionRepository;

    private final SearchService searchService;

    public List<Direction> saveAll(List<Direction> directions) {
        if (CollectionUtils.isEmpty(directions)) {
            return Collections.emptyList();
        }
        return directionRepository.saveAll(directions);
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto) {
        if (Objects.isNull(documentDto)) return Collections.emptyList();

        return pharmacySearchService.searchPharmacyDtoList()
                .stream()
                .map(pharmacyDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetPharmacyName(pharmacyDto.getPharmacyName())
                                .targetAddress(pharmacyDto.getPharmacyAddress())
                                .targetLatitude(pharmacyDto.getLatitude())
                                .targetLongitude(pharmacyDto.getLongitude())
                                .distance(calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), pharmacyDto.getLatitude(), pharmacyDto.getLongitude()))
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparingDouble(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    public List<Direction> buildDirectionListByCategory(DocumentDto documentDto) {
        if (Objects.isNull(documentDto)) return Collections.emptyList();

        return searchService.searchCategory(documentDto.getLatitude(), documentDto.getLongitude(), RADIUS_KM)
                .getDocumentList()
                .stream()
                .map(target ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetPharmacyName(target.getPlaceName())
                                .targetAddress(target.getAddressName())
                                .targetLatitude(target.getLatitude())
                                .targetLongitude(target.getLongitude())
                                .distance(target.getDistance() * 0.0001)
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    // Haversine formula 알고리즘
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371;

        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
