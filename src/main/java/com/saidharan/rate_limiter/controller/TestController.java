
package com.saidharan.rate_limiter.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController {
    @GetMapping("/api/data")
    public String test() {
        return "Rate Limiter Test Successful!";
    }
}