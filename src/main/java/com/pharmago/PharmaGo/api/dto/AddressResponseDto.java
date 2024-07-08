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

    public static AddressResponseDto from(Direction direction) {
        return AddressResponseDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl("")
                .roadViewUrl("")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
