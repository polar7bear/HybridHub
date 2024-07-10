package com.pharmago.PharmaGo.pharmacy.cache;

import com.pharmago.PharmaGo.AbstractIntegrationContainerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;

public class RedisTemplateTest extends AbstractIntegrationContainerTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    @DisplayName("레디스 String 자료구조 테스트")
    void String() {
        // given
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String key = "key";
        String value = "value";

        // when
        valueOperations.set(key, value);

        // then
        Assertions.assertEquals(value, valueOperations.get(key));
    }

    @Test
    @DisplayName("레디스 Set 자료구조 테스트")
    void Set() {
        // given
        SetOperations setOperations = redisTemplate.opsForSet();

        // when
        setOperations.add("bike:racing:korea", "bike:1");
        setOperations.add("bike:racing:korea", "bike:2");
        setOperations.add("bike:racing:america", "bike:1");
        setOperations.add("bike:racing:america", "bike:2");
        setOperations.add("bike:racing:america", "bike:2", "bike:3");

        // then
        Assertions.assertTrue(setOperations.isMember("bike:racing:america", "bike:1"));
        Assertions.assertTrue(setOperations.isMember("bike:racing:america", "bike:2"));
        Assertions.assertTrue(setOperations.isMember("bike:racing:america", "bike:1"));
        Assertions.assertTrue(setOperations.isMember("bike:racing:america", "bike:2"));
        Assertions.assertTrue(setOperations.isMember("bike:racing:america", "bike:3"));
        Assertions.assertEquals(3, setOperations.size("bike:racing:america"));
    }

    @Test
    @DisplayName("레디스 Hash 자료구조 테스트")
    void Hash() {
        // given
        Map<String, String> car = new HashMap<>();
        car.put("model", "porsche");
        car.put("year", "2024");
        car.put("price", "270000");
        car.put("color", "green");

        // when
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll("car", car);
        Map car2 = hashOperations.entries("car");

        // then
        Assertions.assertEquals("porsche", hashOperations.get("car", "model"));
        Assertions.assertEquals("2024", hashOperations.get("car", "year"));
        Assertions.assertEquals("green", hashOperations.get("car", "color"));
        Assertions.assertEquals("270000", hashOperations.get("car", "price"));
        Assertions.assertFalse(hashOperations.hasKey("car", "name"));
        Assertions.assertEquals(4, hashOperations.size("car"));

        Assertions.assertTrue(car2.containsKey("color"));

    }
}
