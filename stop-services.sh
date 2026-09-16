#!/bin/bash

echo "========================================"
echo "       HybridPOS System Stopping"
echo "========================================"
echo ""

# ----------------------------------------
# API Gateway
# ----------------------------------------

echo "[1/10] API Gateway durduruluyor..."
docker stop hybridpos-api-gateway 2>/dev/null || echo "API Gateway zaten durmuş."

# ----------------------------------------
# Report Service
# ----------------------------------------

echo "[2/10] Report Service durduruluyor..."
docker stop hybridpos-report-service 2>/dev/null || echo "Report Service zaten durmuş."

# ----------------------------------------
# Cash Service
# ----------------------------------------

echo "[3/10] Cash Service durduruluyor..."
docker stop hybridpos-cash-service 2>/dev/null || echo "Cash Service zaten durmuş."

# ----------------------------------------
# Sale Service
# ----------------------------------------

echo "[4/10] Sale Service durduruluyor..."
docker stop hybridpos-sale-service 2>/dev/null || echo "Sale Service zaten durmuş."

# ----------------------------------------
# Product Service
# ----------------------------------------

echo "[5/10] Product Service durduruluyor..."
docker stop hybridpos-product-service 2>/dev/null || echo "Product Service zaten durmuş."

# ----------------------------------------
# Auth Service
# ----------------------------------------

echo "[6/10] Auth Service durduruluyor..."
docker stop hybridpos-auth-service 2>/dev/null || echo "Auth Service zaten durmuş."

# ----------------------------------------
# Eureka Server
# ----------------------------------------

echo "[7/10] Eureka Server durduruluyor..."
docker stop hybridpos-eureka-server 2>/dev/null || echo "Eureka Server zaten durmuş."

# ----------------------------------------
# Config Server
# ----------------------------------------

echo "[8/10] Config Server durduruluyor..."
docker stop hybridpos-config-server 2>/dev/null || echo "Config Server zaten durmuş."

# ----------------------------------------
# Kafka
# ----------------------------------------

echo "[9/10] Kafka durduruluyor..."
docker stop hybridpos-kafka 2>/dev/null || echo "Kafka zaten durmuş."

# ----------------------------------------
# Redis & PostgreSQL
# ----------------------------------------

echo "[10/10] Redis ve PostgreSQL durduruluyor..."

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
echo "All services are stopped."
echo "========================================"

sleep 2

