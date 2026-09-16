#!/bin/bash

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"

echo "========================================"
echo "       HybridPOS All Services Update"
echo "========================================"
echo ""

# ----------------------------------------
# Service Update Function
# ----------------------------------------

update_service() {

    SERVICE_NAME="$1"
    SERVICE_DIR="$2"
    IMAGE_NAME="$3"

    echo ""
    echo "========================================"
    echo "       Updating: $SERVICE_NAME"
    echo "========================================"
    echo ""

    # ------------------------------------
    # 1. Maven Build
    # ------------------------------------

    echo "[1/3] $SERVICE_NAME build ediliyor..."

    cd "$PROJECT_DIR/$SERVICE_DIR" || exit 1

    ./mvnw clean package -DskipTests

    if [ $? -ne 0 ]; then
        echo ""
        echo "HATA: $SERVICE_NAME Maven build başarısız."
        exit 1
    fi

    echo ""
    echo "$SERVICE_NAME Maven build başarılı."
    echo ""

    # ------------------------------------
    # 2. Docker Image Build
    # ------------------------------------

    echo "[2/3] $SERVICE_NAME Docker image oluşturuluyor..."

    docker build -t "$IMAGE_NAME:latest" .

    if [ $? -ne 0 ]; then
        echo ""
        echo "HATA: $SERVICE_NAME Docker image oluşturulamadı."
        exit 1
    fi

    echo ""
    echo "$SERVICE_NAME Docker image başarılı şekilde oluşturuldu."
    echo ""

    # ------------------------------------
    # 3. Container Update
    # ------------------------------------

    echo "[3/3] $SERVICE_NAME container güncelleniyor..."

    cd "$PROJECT_DIR" || exit 1

    docker compose up -d --no-deps --force-recreate "$SERVICE_NAME"

    if [ $? -ne 0 ]; then
        echo ""
        echo "HATA: $SERVICE_NAME container güncellenemedi."
        exit 1
    fi

    echo ""
    echo "$SERVICE_NAME başarıyla güncellendi."
    echo ""

    docker compose ps "$SERVICE_NAME"

    sleep 2
}


# ----------------------------------------
# Update Services
# ----------------------------------------

update_service \
    "auth-service" \
    "auth-service" \
    "hybridpos-auth-service"

update_service \
    "product-service" \
    "product-service" \
    "hybridpos-product-service"

update_service \
    "sale-service" \
    "sale-service" \
    "hybridpos-sale-service"

update_service \
    "cash-service" \
    "cash-service" \
    "hybridpos-cash-service"

update_service \
    "report-service" \
    "report-service" \
    "hybridpos-report-service"

update_service \
    "api-gateway" \
    "api-gateway" \
    "hybridpos-api-gateway"


# ----------------------------------------
# Final Status
# ----------------------------------------

echo ""
echo "========================================"
echo "       All Services Updated"
echo "========================================"
echo ""

cd "$PROJECT_DIR" || exit 1

docker compose ps

echo ""
echo "========================================"
sleep 2
