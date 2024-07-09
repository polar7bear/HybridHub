package com.pharmago.PharmaGo.direction.controller;

import com.pharmago.PharmaGo.direction.service.DirectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DirectionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DirectionService directionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService)).build();
    }

    @Test
    @DisplayName("GET /find/{encoded}")
    void test() throws Exception {
        // given
        String encoded = "a";
        String redirectUrl = "https://map.kakao.com/link/map/pharmacy,37.123,126.123";

        // when
        when(directionService.findDirectionUrlById(encoded)).thenReturn(redirectUrl);
        ResultActions resultActions = mockMvc.perform(get("/find/{encoded}", encoded));

        // then
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl))
                .andDo(print());


    }
}