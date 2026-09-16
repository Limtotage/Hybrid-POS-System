#!/bin/bash

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"
SERVICE_DIR="$PROJECT_DIR/sale-service"
IMAGE_NAME="hybridpos-sale-service:latest"

echo "========================================"
echo "       Sale Service Update"
echo "========================================"
echo ""

# ----------------------------------------
# 1. Maven Build
# ----------------------------------------

echo "[1/3] Sale Service build ediliyor..."
cd "$SERVICE_DIR" || exit 1

./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo ""
    echo "HATA: Maven build başarısız."
    exit 1
fi

echo ""
echo "Maven build başarılı."
echo ""

# ----------------------------------------
# 2. Docker Image Build
# ----------------------------------------

echo "[2/3] Docker image oluşturuluyor..."

docker build -t "$IMAGE_NAME" .

if [ $? -ne 0 ]; then
    echo ""
    echo "HATA: Docker image oluşturulamadı."
    exit 1
fi

echo ""
echo "Docker image başarılı şekilde oluşturuldu."
echo ""

# ----------------------------------------
# 3. Container Update
# ----------------------------------------

echo "[3/3] Sale Service container güncelleniyor..."

cd "$PROJECT_DIR" || exit 1

docker compose up -d --no-deps --force-recreate sale-service

if [ $? -ne 0 ]; then
    echo ""
    echo "HATA: Sale Service container güncellenemedi."
    exit 1
fi

echo ""
echo "========================================"
echo "       Sale Service Updated"
echo "========================================"
echo ""

docker compose ps sale-service

echo ""
echo "========================================"
sleep 2
