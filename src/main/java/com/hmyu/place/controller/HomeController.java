package com.hmyu.place.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public ResponseEntity<?> home() {
        String str = "welcome!";
        return ResponseEntity.ok(str);
    }
}
