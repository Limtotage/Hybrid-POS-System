#!/bin/bash

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"

echo "========================================"
echo "       HybridPOS System Setup"
echo "========================================"
echo ""

cd "$PROJECT_DIR" || exit 1

# ========================================
# 1. DOCKER NETWORK
# ========================================

echo "[1/4] Docker network kontrol ediliyor..."

if ! docker network inspect hybridpos-network >/dev/null 2>&1; then
    echo "HybridPOS network oluşturuluyor..."
    docker network create hybridpos-network

    if [ $? -ne 0 ]; then
        echo "HATA: Docker network oluşturulamadı."
        exit 1
    fi
else
    echo "✓ hybridpos-network zaten mevcut."
fi

echo ""

# ========================================
# 2. DOCKER VOLUMES
# ========================================

echo "[2/4] Docker volume'ları kontrol ediliyor..."

if ! docker volume inspect hybridpos_postgres_data >/dev/null 2>&1; then
    echo "PostgreSQL volume oluşturuluyor..."
    docker volume create hybridpos_postgres_data
else
    echo "✓ PostgreSQL volume zaten mevcut."
fi

echo ""

# Redis volume Compose tarafından mevcut yapıdaki
# external volume adıyla kullanılıyor.
REDIS_VOLUME="6feba73a0fc11a3aaaaf06cc0b06ec1922616730037c2a6ee85fbbd6124a3490"

if ! docker volume inspect "$REDIS_VOLUME" >/dev/null 2>&1; then
    echo "Redis volume oluşturuluyor..."
    docker volume create "$REDIS_VOLUME"
else
    echo "✓ Redis volume zaten mevcut."
fi

echo ""

# ========================================
# 3. BACKEND IMAGES
# ========================================

echo "[3/4] Backend Docker image'ları oluşturuluyor..."
echo ""

SERVICES=(
    "config-server"
    "eureka-server"
    "auth-service"
    "product-service"
    "sale-service"
    "cash-service"
    "report-service"
    "api-gateway"
)

for SERVICE in "${SERVICES[@]}"
do
    echo "----------------------------------------"
    echo "$SERVICE build ediliyor..."
    echo "----------------------------------------"

    cd "$PROJECT_DIR/$SERVICE" || exit 1

    ./mvnw clean package -DskipTests

    if [ $? -ne 0 ]; then
        echo ""
        echo "HATA: $SERVICE Maven build başarısız."
        exit 1
    fi

    case "$SERVICE" in
        config-server)
            IMAGE_NAME="hybridpos-config-server:latest"
            ;;
        eureka-server)
            IMAGE_NAME="hybridpos-eureka-server:latest"
            ;;
        auth-service)
            IMAGE_NAME="hybridpos-auth-service:latest"
            ;;
        product-service)
            IMAGE_NAME="hybridpos-product-service:latest"
            ;;
        sale-service)
            IMAGE_NAME="hybridpos-sale-service:latest"
            ;;
        cash-service)
            IMAGE_NAME="hybridpos-cash-service:latest"
            ;;
        report-service)
            IMAGE_NAME="hybridpos-report-service:latest"
            ;;
        api-gateway)
            IMAGE_NAME="hybridpos-api-gateway:latest"
            ;;
    esac

    docker build -t "$IMAGE_NAME" .

    if [ $? -ne 0 ]; then
        echo ""
        echo "HATA: $SERVICE Docker image oluşturulamadı."
        exit 1
    fi

    echo "✓ $SERVICE hazır."
    echo ""
done

# ========================================
# 4. FRONTEND IMAGE
# ========================================

echo "[4/4] Frontend Docker image oluşturuluyor..."
echo ""

cd "$PROJECT_DIR/hybridpos-Frontend" || exit 1

npm ci

if [ $? -ne 0 ]; then
    echo "HATA: Frontend npm install başarısız."
    exit 1
fi

npm run build

if [ $? -ne 0 ]; then
    echo "HATA: Frontend Angular build başarısız."
    exit 1
fi

docker build -t hybridpos-frontend:latest .

if [ $? -ne 0 ]; then
    echo "HATA: Frontend Docker image oluşturulamadı."
    exit 1
fi

echo ""
echo "✓ Frontend hazır."

# ========================================
# SETUP COMPLETE
# ========================================

echo ""
echo "========================================"
echo "       HybridPOS Setup Complete"
echo "========================================"
echo ""

echo "Docker image'ları:"
docker images | grep hybridpos

echo ""
echo "Sistemi başlatmak için:"
echo ""
echo "    ./start-services.sh"
echo ""

echo "========================================"

sleep 3
