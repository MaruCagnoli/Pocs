package com.poc.PocJPAyRedis.controllers;

import com.poc.PocJPAyRedis.services.RealStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("real-state")
public class RealStateController {

    @Autowired
    RealStateService realStateService;

    @GetMapping("/extract")
    public String extractInformation() {
        return realStateService.startExtractingInformation();
    }


}
