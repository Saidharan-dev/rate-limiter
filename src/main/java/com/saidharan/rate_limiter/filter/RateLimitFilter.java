package com.saidharan.rate_limiter.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saidharan.rate_limiter.service.LoggingService;
import com.saidharan.rate_limiter.service.RateLimiterService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.Filter;
import org.springframework.http.HttpStatus;
@Component
public class RateLimitFilter implements Filter {

    @Autowired
    private RateLimiterService rateLimiterService;
    
    @Autowired
    private LoggingService loggingService;
    @Override
public void doFilter(
        jakarta.servlet.ServletRequest request,
        jakarta.servlet.ServletResponse response,
        FilterChain chain)
        throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    String path = httpRequest.getRequestURI();

if (!path.equals("/api/data") && !path.equals("/api/submit")) {
    chain.doFilter(request, response);
    return;
}

    String ip = httpRequest.getRemoteAddr();

    if (!rateLimiterService.isAllowed(ip)) {

    loggingService.logViolation(
            ip,
            path,
            httpRequest.getMethod()
    );

    httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

    httpResponse.getWriter().write("Too Many Requests!");

    return;
}

    chain.doFilter(request, response);

}

}