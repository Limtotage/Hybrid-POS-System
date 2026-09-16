#!/bin/bash

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"

echo "========================================"
echo "       HybridPOS System Starting"
echo "========================================"
echo ""

# ----------------------------------------
# Docker Services
# ----------------------------------------

echo "[1/11] PostgreSQL başlatılıyor..."
docker start hybridpos-postgres 2>/dev/null || echo "PostgreSQL zaten çalışıyor."

echo "[2/11] Redis başlatılıyor..."
docker start hybridpos-redis 2>/dev/null || echo "Redis zaten çalışıyor."

echo "[3/11] Kafka başlatılıyor..."
docker start hybridpos-kafka 2>/dev/null || echo "Kafka zaten çalışıyor."

sleep 3

# ----------------------------------------
# Config Server
# ----------------------------------------

echo "[4/11] Config Server başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/config-server' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

sleep 5

# ----------------------------------------
# Eureka
# ----------------------------------------

echo "[5/11] Eureka Server başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/eureka-server' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

sleep 7

# ----------------------------------------
# Auth
# ----------------------------------------

echo "[6/11] Auth Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/auth-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# Product
# ----------------------------------------

echo "[7/11] Product Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/product-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# Sale
# ----------------------------------------

echo "[8/11] Sale Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/sale-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# Cash
# ----------------------------------------

echo "[9/11] Cash Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/cash-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# Report
# ----------------------------------------

echo "[10/11] Report Service başlatılıyor..."
gnome-terminal -- bash -c "
cd '$PROJECT_DIR/report-service' &&
MAVEN_OPTS='-Xms128m -Xmx512m' ./mvnw spring-boot:run;
exec bash
"

# ----------------------------------------
# API Gateway
# ----------------------------------------

echo "[11/11] API Gateway başlatılıyor..."
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
echo "JVM Heap   : 128 MB - 512 MB"
echo "========================================"

sleep 2
