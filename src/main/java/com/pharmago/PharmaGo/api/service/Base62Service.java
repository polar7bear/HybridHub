package com.pharmago.PharmaGo.api.service;

import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Base62Service {

    private final Base62 base62 = Base62.createInstance();

    public String encode(Long input) {
        return new String(base62.encode(String.valueOf(input).getBytes()));
    }

    public Long decode(String input) {
        String result = new String(base62.decode(input.getBytes()));
        return Long.parseLong(result);
    }
}
