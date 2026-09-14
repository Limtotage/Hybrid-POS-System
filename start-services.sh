#!/bin/bash

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"

echo "========================================"
echo "       HybridPOS System Starting"
echo "========================================"
echo ""

# ----------------------------------------
# Docker Services
# ----------------------------------------

echo "[1/7] PostgreSQL başlatılıyor..."
docker start hybridpos-postgres 2>/dev/null || echo "PostgreSQL zaten çalışıyor."

echo "[2/7] Redis başlatılıyor..."
docker start hybridpos-redis 2>/dev/null || echo "Redis zaten çalışıyor."

sleep 2

# ----------------------------------------
# Eureka
# ----------------------------------------

echo "[3/7] Eureka Server başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/eureka-server' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

sleep 5

# ----------------------------------------
# Auth
# ----------------------------------------

echo "[4/7] Auth Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/auth-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# Product
# ----------------------------------------

echo "[5/7] Product Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/product-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# Sale
# ----------------------------------------

echo "[6/7] Sale Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/sale-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# Cash
# ----------------------------------------

echo "[7/7] Cash Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/cash-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# API Gateway
# ----------------------------------------

echo "API Gateway başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/api-gateway' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

echo ""
echo "========================================"
echo "       HybridPOS Started"
echo "========================================"
echo ""
echo "PostgreSQL : 5432"
echo "Redis      : 6379"
echo "Eureka     : 8761"
echo "Auth       : 8081"
echo "Product    : 8082"
echo "Sale       : 8083"
echo "Cash       : 8084"
echo "Gateway    : 8080"
echo ""
echo "JVM Heap   : 128 MB - 512 MB"
echo "========================================"