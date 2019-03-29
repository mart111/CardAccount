package com.example.demo.restfulwebservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexRestController {

    @GetMapping("/api")
    public ResponseEntity<String> startPage() {
        return ResponseEntity.ok("Welcome to api");
    }


}
