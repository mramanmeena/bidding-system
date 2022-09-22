package com.example.biddingsystem.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/public/health")
    public String health() {
        return "Ok health";
    }
}
