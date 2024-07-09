package com.pharmago.PharmaGo.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Base62ServiceTest {

    private Base62Service base62Service;

    @BeforeEach
    void setUp() {
        base62Service = new Base62Service();
    }

    @Test
    @DisplayName("정수가 정상적으로 encode 및 decode 되어야 한다.")
    void encodeTest() {
        // given
        Long og = 1L;

        // when
        String encoded = base62Service.encode(og);

        Long decoded = base62Service.decode(encoded);

        // then
        Assertions.assertEquals(og, decoded);
    }
}