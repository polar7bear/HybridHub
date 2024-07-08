package com.pharmago.PharmaGo.api.controller;

import com.pharmago.PharmaGo.api.dto.AddressResponseDto;
import com.pharmago.PharmaGo.pharmacy.service.RecommendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ViewControllerTest {

    @Mock
    private RecommendService recommendService;

    private MockMvc mockMvc;

    private List<AddressResponseDto> addresses;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ViewController(recommendService)).build();
        addresses = new ArrayList<>();
        addresses.add(
                AddressResponseDto.builder()
                        .pharmacyName("약국1")
                        .build());
        addresses.add(AddressResponseDto.builder()
                .pharmacyName("약국2")
                .build()
        );
    }

    @Test
    @DisplayName("GET /")
    void index() throws Exception {
        // expect
        mockMvc.perform(get("/"))
                .andExpect(handler().handlerType(ViewController.class))
                .andExpect(handler().methodName("index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print());
    }

    @Test
    @DisplayName("POST /search")
    void search() throws Exception {
        // given
        String address = "호수로838번길";
        List<AddressResponseDto> addressList = addresses;

        // when
        doAnswer(invocation -> {
            String arg = invocation.getArgument(0);
            assert arg.equals(address);
            return addressList;
        }).when(recommendService).recommendPharmacyList(any(String.class));

        ResultActions resultActions = mockMvc.perform(post("/search").param("address", address));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("pharmacyList"))
                .andExpect(model().attribute("pharmacyList", addressList))
                .andDo(print());

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(recommendService, times(1)).recommendPharmacyList(captor.capture());
        assert captor.getValue().equals(address);

    }
}