#!/bin/bash

# ========================================
# SERVICE CONFIGURATION
# ========================================

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"
SERVICE_DIR="$PROJECT_DIR/sale-service"
IMAGE_NAME="hybridpos-sale-service:latest"
SERVICE_NAME="sale-service"


echo "========================================"
echo "       $SERVICE_NAME Update"
echo "========================================"
echo ""

# ----------------------------------------
# 1. Maven Build
# ----------------------------------------

echo "[1/3] $SERVICE_NAME build ediliyor..."
cd "$SERVICE_DIR" || exit 1

./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo ""
    echo "HATA: $SERVICE_NAME Maven build başarısız."
    exit 1
fi

echo ""
echo "$SERVICE_NAME Maven build başarılı."
echo ""

# ----------------------------------------
# 2. Docker Image Build
# ----------------------------------------

echo "[2/3] $SERVICE_NAME Docker image oluşturuluyor..."

docker build -t "$IMAGE_NAME" .

if [ $? -ne 0 ]; then
    echo ""
    echo "HATA: $SERVICE_NAME Docker image oluşturulamadı."
    exit 1
fi

echo ""
echo "$SERVICE_NAME Docker image başarılı şekilde oluşturuldu."
echo ""

# ----------------------------------------
# 3. Container Update
# ----------------------------------------

echo "[3/3] $SERVICE_NAME container güncelleniyor..."

cd "$PROJECT_DIR" || exit 1

docker compose up -d --no-deps --force-recreate "$SERVICE_NAME"

if [ $? -ne 0 ]; then
    echo ""
    echo "HATA: $SERVICE_NAME container güncellenemedi."
    exit 1
fi

echo ""
echo "========================================"
echo "       $SERVICE_NAME Updated"
echo "========================================"
echo ""

docker compose ps "$SERVICE_NAME"

echo ""
echo "========================================"
sleep 3
