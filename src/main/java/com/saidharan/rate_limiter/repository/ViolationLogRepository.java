package com.saidharan.rate_limiter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saidharan.rate_limiter.entity.ViolationLog;

@Repository
public interface ViolationLogRepository extends JpaRepository<ViolationLog, Long> {

}