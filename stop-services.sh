#!/bin/bash

echo "========================================"
echo "       HybridPOS System Stopping"
echo "========================================"
echo ""

# ----------------------------------------
# Spring Boot Services
# ----------------------------------------

echo "[1/7] API Gateway durduruluyor..."
pkill -f "api-gateway.*spring-boot:run" 2>/dev/null

echo "[2/7] Report Service durduruluyor..."
pkill -f "report-service.*spring-boot:run" 2>/dev/null

echo "[3/7] Cash Service durduruluyor..."
pkill -f "cash-service.*spring-boot:run" 2>/dev/null

echo "[4/7] Sale Service durduruluyor..."
pkill -f "sale-service.*spring-boot:run" 2>/dev/null

echo "[5/7] Product Service durduruluyor..."
pkill -f "product-service.*spring-boot:run" 2>/dev/null

echo "[6/7] Auth Service durduruluyor..."
pkill -f "auth-service.*spring-boot:run" 2>/dev/null

echo "[7/7] Eureka Server durduruluyor..."
pkill -f "eureka-server.*spring-boot:run" 2>/dev/null

sleep 3

# ----------------------------------------
# Docker Services
# ----------------------------------------

echo ""
echo "Docker servisleri durduruluyor..."

docker stop hybridpos-kafka 2>/dev/null || echo "Kafka zaten durmuş."
docker stop hybridpos-redis 2>/dev/null || echo "Redis zaten durmuş."
docker stop hybridpos-postgres 2>/dev/null || echo "PostgreSQL zaten durmuş."

echo ""
echo "========================================"
echo "       HybridPOS Stopped"
echo "========================================"
echo ""
echo "PostgreSQL : stopped"
echo "Redis      : stopped"
echo "Kafka      : stopped"
echo "Eureka     : stopped"
echo "Auth       : stopped"
echo "Product    : stopped"
echo "Sale       : stopped"
echo "Cash       : stopped"
echo "Report     : stopped"
echo "Gateway    : stopped"
echo ""
echo "========================================"

sleep 2
