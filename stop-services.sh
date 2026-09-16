#!/bin/bash

echo "========================================"
echo "       HybridPOS System Stopping"
echo "========================================"
echo ""

# ----------------------------------------
# Spring Boot Services
# ----------------------------------------

echo "[1/8] API Gateway durduruluyor..."
pkill -f "api-gateway.*spring-boot:run" 2>/dev/null

echo "[2/8] Report Service durduruluyor..."
pkill -f "report-service.*spring-boot:run" 2>/dev/null

echo "[3/8] Cash Service durduruluyor..."
pkill -f "cash-service.*spring-boot:run" 2>/dev/null

echo "[4/8] Sale Service durduruluyor..."
pkill -f "sale-service.*spring-boot:run" 2>/dev/null

echo "[5/8] Product Service durduruluyor..."
pkill -f "product-service.*spring-boot:run" 2>/dev/null

echo "[6/8] Auth Service durduruluyor..."
pkill -f "auth-service.*spring-boot:run" 2>/dev/null

echo "[7/8] Eureka Server durduruluyor..."
pkill -f "eureka-server.*spring-boot:run" 2>/dev/null

echo "[8/8] Config Server durduruluyor..."
pkill -f "config-server.*spring-boot:run" 2>/dev/null

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
echo "Config     : stopped"
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
