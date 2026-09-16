#!/bin/bash

echo "========================================"
echo "       HybridPOS System Starting"
echo "========================================"
echo ""

# ----------------------------------------
# Docker Infrastructure
# ----------------------------------------

echo "[1/10] PostgreSQL başlatılıyor..."
docker start hybridpos-postgres 2>/dev/null || echo "PostgreSQL zaten çalışıyor."

echo "[2/10] Redis başlatılıyor..."
docker start hybridpos-redis 2>/dev/null || echo "Redis zaten çalışıyor."

echo "[3/10] Kafka başlatılıyor..."
docker start hybridpos-kafka 2>/dev/null || echo "Kafka zaten çalışıyor."

# ----------------------------------------
# Config Server
# ----------------------------------------

echo "[4/10] Config Server başlatılıyor..."
docker start hybridpos-config-server 2>/dev/null || echo "Config Server zaten çalışıyor."

# ----------------------------------------
# Eureka
# ----------------------------------------

echo "[5/10] Eureka Server başlatılıyor..."
docker start hybridpos-eureka-server 2>/dev/null || echo "Eureka Server zaten çalışıyor."

# Servislerin hazır olması için
sleep 5

# ----------------------------------------
# Auth
# ----------------------------------------

echo "[6/10] Auth Service başlatılıyor..."
docker start hybridpos-auth-service 2>/dev/null || echo "Auth Service zaten çalışıyor."

# ----------------------------------------
# Product
# ----------------------------------------

echo "[7/10] Product Service başlatılıyor..."
docker start hybridpos-product-service 2>/dev/null || echo "Product Service zaten çalışıyor."

# ----------------------------------------
# Sale
# ----------------------------------------

echo "[8/10] Sale Service başlatılıyor..."
docker start hybridpos-sale-service 2>/dev/null || echo "Sale Service zaten çalışıyor."

# ----------------------------------------
# Cash
# ----------------------------------------

echo "[9/10] Cash Service başlatılıyor..."
docker start hybridpos-cash-service 2>/dev/null || echo "Cash Service zaten çalışıyor."

# ----------------------------------------
# Report
# ----------------------------------------

echo "[10/10] Report Service başlatılıyor..."
docker start hybridpos-report-service 2>/dev/null || echo "Report Service zaten çalışıyor."

# ----------------------------------------
# API Gateway
# ----------------------------------------

echo ""
echo "API Gateway başlatılıyor..."
docker start hybridpos-api-gateway 2>/dev/null || echo "API Gateway zaten çalışıyor."

echo ""
echo "========================================"
echo "       HybridPOS Started"
echo "========================================"
echo ""
echo "PostgreSQL : 5432"
echo "Redis      : 6379"
echo "Kafka      : 9092"
echo "Config     : 8888"
echo "Eureka     : 8761"
echo "Auth       : 8081"
echo "Product    : 8082"
echo "Sale       : 8083"
echo "Cash       : 8084"
echo "Report     : 8085"
echo "Gateway    : 8080"
echo ""
echo "All services are running in Docker."
echo "========================================"

sleep 2


