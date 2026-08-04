# 🚦 Distributed API Rate Limiter & Metrics Engine

A production-style backend middleware built using **Spring Boot**, **Redis**, **Lua Scripts**, and **MySQL** to protect REST APIs from excessive traffic, brute-force attacks, and abuse while providing real-time violation tracking.

---

## 📌 Overview

This project acts as a middleware that intercepts every incoming API request before it reaches the controller.

It performs atomic rate limiting using **Redis Lua Scripts**, blocks requests exceeding the configured threshold with **HTTP 429 (Too Many Requests)**, and asynchronously stores violation logs in MySQL without increasing response latency.

---

## 🏗️ Architecture

```
                    Incoming Request
                           │
                           ▼
                  RateLimitFilter
                           │
                           ▼
               RateLimiterService
                           │
                           ▼
               Redis Lua Script (Atomic)
                           │
              ┌────────────┴────────────┐
              │                         │
          Allowed                  Blocked
              │                         │
              ▼                         ▼
       API Controller           HTTP 429 Response
                                         │
                                         ▼
                             @Async LoggingService
                                         │
                                         ▼
                                   MySQL Database
                                         │
                                         ▼
                              Dashboard REST APIs
                                         │
                                         ▼
                          HTML / CSS / JavaScript UI
```

---

# ✨ Features

- ✅ Distributed API Rate Limiting
- ✅ Atomic Redis Lua Script Execution
- ✅ Sliding Window Counter
- ✅ HTTP 429 Response
- ✅ Asynchronous MySQL Logging
- ✅ Dashboard REST APIs
- ✅ Live Dashboard
- ✅ Spring Boot Middleware using Servlet Filter
- ✅ Production-ready layered architecture

---

# 🛠 Tech Stack

| Technology | Purpose |
|------------|----------|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Data JPA | Database Access |
| Redis | In-Memory Rate Limiting |
| Redis Lua Scripts | Atomic Operations |
| MySQL | Violation Logging |
| HTML/CSS/JavaScript | Dashboard |
| Maven | Build Tool |

---

# 📂 Project Structure

```
src
│
├── controller
│     ├── DashboardController
│     ├── RedisTestController
│     └── TestController
│
├── service
│     ├── RateLimiterService
│     └── LoggingService
│
├── repository
│     └── ViolationLogRepository
│
├── entity
│     └── ViolationLog
│
├── filter
│     └── RateLimitFilter
│
├── config
│     ├── RedisConfig
│     └── LuaConfig
│
├── resources
│     ├── application.properties
│     └── lua
│           └── ratelimiter.lua
│
└── static
      └── index.html
```

---

# ⚙️ Rate Limiting Algorithm

This project uses a **Sliding Window Counter** implemented using Redis.

For every incoming request:

1. Create a Redis key using the client IP.

```
rate_limit:<ip_address>
```

2. Increment the counter atomically.

3. If it is the first request, set an expiry time.

```
TTL = 60 seconds
```

4. Compare current counter with the configured limit.

If

```
Current Count <= 10
```

Request is allowed.

Otherwise

```
HTTP 429
```

is returned.

---

# 🔥 Why Redis?

Every API request performs a rate limit check.

Using MySQL for this operation would introduce unnecessary latency.

Redis provides:

- In-memory speed
- Sub-millisecond response
- High throughput
- TTL support

---

# 🔥 Why Lua Scripts?

Without Lua:

```
GET Counter

↓

Increment

↓

Check Limit
```

These are separate operations.

Two simultaneous requests can both pass the limit.

Lua executes all operations atomically:

```
Increment

↓

Set Expiry

↓

Compare Limit

↓

Return Result
```

No race conditions.

---

# 🔥 Why @Async Logging?

Writing to MySQL is slower than reading from Redis.

Instead of

```
Redis

↓

MySQL

↓

Return HTTP 429
```

the project performs

```
Redis

↓

Return HTTP 429

↓

Background Thread

↓

MySQL
```

This reduces response latency while still maintaining an audit trail.

---

# 📊 Dashboard APIs

## Get Total Violations

```
GET /dashboard/stats
```

---

## Get Recent Violations

```
GET /dashboard/violations
```

Returns latest 100 violations.

---

## Get Violations for Specific IP

```
GET /dashboard/stats/{ip}
```

---

# 🧪 Test API

```
GET /api/data
```

---

# 🚀 Running the Project

## Clone

```bash
git clone https://github.com/yourusername/rate-limiter.git
```

---

## Create Database

```sql
CREATE DATABASE ratelimiter;
```

---

## Configure

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ratelimiter
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.data.redis.host=localhost
spring.data.redis.port=6379
```

---

## Start Redis

```
redis-server
```

or

```
Memurai
```

---

## Run

```bash
mvn spring-boot:run
```

---

# 🧪 Testing Rate Limiting

Using curl

```bash
for i in {1..15}
do
curl http://localhost:8080/api/data
done
```

Expected

```
Requests 1-10

HTTP 200
```

```
Request 11

HTTP 429
```

---

# 📸 Dashboard

```
http://localhost:8080/index.html
```

Displays

- Total Violations
- Recent Violations
- Auto Refresh
- Live Monitoring

---

# 📈 Future Improvements

- JWT Authentication
- User-based Rate Limiting
- Role-based Limits
- Token Bucket Algorithm
- Distributed Redis Cluster
- Prometheus Metrics
- Grafana Dashboard
- Docker Support
- Kubernetes Deployment
- Swagger Documentation
- API Gateway Integration

---

# 🎯 Learning Outcomes

This project demonstrates knowledge of:

- Spring Boot Filters
- Middleware Development
- Redis
- Lua Scripting
- Atomic Operations
- Race Condition Prevention
- Asynchronous Programming
- Spring Data JPA
- REST API Development
- Layered Architecture
- Backend Performance Optimization

---

# 👨‍💻 Author

**Sai Dharan Y**

- GitHub: https://github.com/Saidharan-dev
- LinkedIn: https://www.linkedin.com/in/saidharan17

---