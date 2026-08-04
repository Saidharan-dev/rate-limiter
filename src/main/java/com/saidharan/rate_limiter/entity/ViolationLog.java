package com.saidharan.rate_limiter.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "violation_logs")
public class ViolationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    private String endpoint;

    private String method;

    private LocalDateTime timestamp;

    public ViolationLog() {
    }

    public ViolationLog(String ipAddress,
                        String endpoint,
                        String method,
                        LocalDateTime timestamp) {
        this.ipAddress = ipAddress;
        this.endpoint = endpoint;
        this.method = method;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}