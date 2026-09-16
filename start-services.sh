#!/bin/bash

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"

echo "========================================"
echo "       HybridPOS System Starting"
echo "========================================"
echo ""

cd "$PROJECT_DIR" || exit 1

echo "[1/2] Docker Compose servisleri başlatılıyor..."
docker compose up -d

if [ $? -ne 0 ]; then
    echo ""
    echo "HATA: Docker Compose servisleri başlatılamadı."
    exit 1
fi

echo ""
echo "[2/2] Servislerin durumu kontrol ediliyor..."
echo ""

docker compose ps

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
echo "All services are running in Docker Compose."
echo "========================================"
sleep 2

