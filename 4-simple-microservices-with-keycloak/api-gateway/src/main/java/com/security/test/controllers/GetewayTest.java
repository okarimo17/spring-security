package com.security.test.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetewayTest {

    @GetMapping("/test")
    public ResponseEntity<String> test()
    {
        return ResponseEntity.ok("test route content");
    }
}
