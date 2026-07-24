# RailFlow — Railway Reservation Platform

> A production-grade Spring Boot 3 + React Railway Reservation System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Redis](https://img.shields.io/badge/Redis-7-red)

## Overview

RailFlow is a full-featured railway reservation platform built with modern Java and Spring Boot.

**Day 45/45 progress** — docs: update README with complete setup and deployment instructions
*Last updated: 2026-07-24*

## Features

- JWT-based authentication with role-based access
- Real-time train search with Redis caching
- Seat booking with distributed locking
- FIFO waiting list with automatic promotion
- Kafka-based notification events
- React frontend with admin dashboard

## Tech Stack

| Layer    | Technology                     |
|----------|-------------------------------|
| Backend  | Spring Boot 3, Spring Security |
| Database | PostgreSQL + Flyway migrations |
| Cache    | Redis                          |
| Events   | Apache Kafka                   |
| Frontend | React 18, Vite                 |
| Auth     | JWT (JJWT 0.12.3)              |

## Getting Started

```bash
docker-compose up -d
mvn spring-boot:run
```

API runs at `http://localhost:8080`
