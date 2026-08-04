package com.saidharan.rate_limiter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saidharan.rate_limiter.entity.ViolationLog;
import com.saidharan.rate_limiter.repository.ViolationLogRepository;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ViolationLogRepository repository;

    // Returns total number of blocked requests
    @GetMapping("/stats")
    public long getTotalViolations() {
        return repository.count();
    }

    // Returns the latest 100 blocked requests
    @GetMapping("/violations")
    public List<ViolationLog> getRecentViolations() {
        return repository.findTop100ByOrderByTimestampDesc();
    }

    // Returns number of violations for a specific IP
    @GetMapping("/stats/{ip}")
    public long getViolationsByIp(@PathVariable String ip) {
        return repository.countByIpAddress(ip);
    }
}