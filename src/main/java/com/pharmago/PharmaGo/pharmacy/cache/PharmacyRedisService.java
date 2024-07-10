package com.pharmago.PharmaGo.pharmacy.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmago.PharmaGo.pharmacy.dto.PharmacyDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRedisService {

    private final static String CACHE_KEY = "PHARMACY";

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    private String serializePharmacyDto(PharmacyDto pharmacyDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pharmacyDto);
    }

    private PharmacyDto deserializePharmacyDto(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, PharmacyDto.class);
    }

    public void save(PharmacyDto pharmacyDto) {

        if (Objects.isNull(pharmacyDto) || Objects.isNull(pharmacyDto.getId())) {
            log.error("Pharmacy DTO or id is null or empty");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY, pharmacyDto.getId().toString(), serializePharmacyDto(pharmacyDto));
            log.info("[PharmacyRedisService save Success: {}]", pharmacyDto.getId());
        } catch (Exception e) {
            log.error("PharmacyRedisService save] Error: {}", e.getMessage());
        }
    }

    public List<PharmacyDto> findAll() {

        try {
            List<PharmacyDto> list = new ArrayList<>();

            for (String json : hashOperations.entries(CACHE_KEY).values()) {
                PharmacyDto pharmacyDto = deserializePharmacyDto(json);
                list.add(pharmacyDto);
            }
            return list;
        } catch (Exception e) {
            log.error("[PharmacyRedisService findAll] Error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, id.toString());
        log.info("[PharmacyRedisService delete Success: {}]", id);
    }

}
