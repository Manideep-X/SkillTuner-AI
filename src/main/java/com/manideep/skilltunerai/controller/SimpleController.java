package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping({"/health", "/info", "/status", "/checkup"})
public class SimpleController {

    @GetMapping
    public ResponseEntity<Map<String, String>> appHealth() {
        return ResponseEntity.ok(Map.of(
            "message", "The server is up and running! Requests can be made now."
        ));
    }

}
