package com.pharmago.PharmaGo.api.dto;

import com.pharmago.PharmaGo.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Builder
public class AddressResponseDto {

    private String pharmacyName;
    private String pharmacyAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;


    private final static String ROADVIEW_URL = "https://map.kakao.com/link/roadview/";
    private final static String DIRECTION_URL = "https://map.kakao.com/link/map/";

    public static AddressResponseDto from(Direction direction) {

        String directionUrl = DIRECTION_URL + direction.getTargetPharmacyName() + "," + direction.getTargetLatitude() + "," + direction.getTargetLongitude();
        String uri = UriComponentsBuilder.fromHttpUrl(directionUrl).toUriString();

        return AddressResponseDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl(uri)
                .roadViewUrl(ROADVIEW_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
