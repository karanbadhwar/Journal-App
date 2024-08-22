package com.Badhwar.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/health-check") //GET Call
    public String healthCheck()
    {
        return "OK";
    }
}
