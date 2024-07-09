package com.pharmago.PharmaGo.api.dto;

import com.pharmago.PharmaGo.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressResponseDto {

    private String pharmacyName;
    private String pharmacyAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;


    private final static String ROADVIEW_URL = "https://map.kakao.com/link/roadview/";


    public static AddressResponseDto from(Direction direction, String baseUrl) {
        return AddressResponseDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl(baseUrl)
                .roadViewUrl(ROADVIEW_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
