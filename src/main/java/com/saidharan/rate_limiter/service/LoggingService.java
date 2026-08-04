package com.saidharan.rate_limiter.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.saidharan.rate_limiter.entity.ViolationLog;
import com.saidharan.rate_limiter.repository.ViolationLogRepository;

@Service
public class LoggingService {

    @Autowired
    private ViolationLogRepository repository;
    @Async
    public void logViolation(String ip,
                             String endpoint,
                             String method) {

        ViolationLog log = new ViolationLog();

        log.setIpAddress(ip);
        log.setEndpoint(endpoint);
        log.setMethod(method);
        log.setTimestamp(LocalDateTime.now());

        repository.save(log);

        System.out.println("Violation Logged : " + ip);
    }

}