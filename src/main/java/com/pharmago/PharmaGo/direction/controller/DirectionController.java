package com.pharmago.PharmaGo.direction.controller;

import com.pharmago.PharmaGo.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class DirectionController {

    private static final Logger log = LoggerFactory.getLogger(DirectionController.class);
    private final DirectionService directionService;


    @GetMapping("/find/{encoded}")
    public String connectionSearchUrl(@PathVariable("encoded") String encoded) {
        String uri = directionService.findDirectionUrlById(encoded);
        log.info("[DirectionController] URI: {}", uri);

        return "redirect:" + uri;
    }

}
