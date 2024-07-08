package com.pharmago.PharmaGo.api.controller;

import com.pharmago.PharmaGo.api.dto.AddressRequestDto;
import com.pharmago.PharmaGo.pharmacy.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {

    private final RecommendService recommendService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/search")
    public ModelAndView search(@ModelAttribute AddressRequestDto addressRequestDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        modelAndView.addObject("pharmacyList", recommendService.recommendPharmacyList(addressRequestDto.getAddress()));
        return modelAndView;
    }
}
