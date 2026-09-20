#!/bin/bash

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"

echo "========================================"
echo "       HybridPOS System Starting"
echo "========================================"
echo ""

cd "$PROJECT_DIR" || exit 1

# ========================================
# 1. INFRASTRUCTURE
# ========================================

echo "[1/6] PostgreSQL, Redis ve Kafka başlatılıyor..."
docker compose up -d postgres redis kafka

if [ $? -ne 0 ]; then
    echo "HATA: Infrastructure servisleri başlatılamadı."
    exit 1
fi

echo "PostgreSQL bekleniyor..."

until docker exec hybridpos-postgres pg_isready \
    -U shopAdmin \
    -d postgres >/dev/null 2>&1
do
    echo "  PostgreSQL henüz hazır değil..."
    sleep 2
done

echo "✓ PostgreSQL hazır."

echo ""
echo "Redis bekleniyor..."

until docker exec hybridpos-redis redis-cli ping >/dev/null 2>&1
do
    echo "  Redis henüz hazır değil..."
    sleep 2
done

echo "✓ Redis hazır."

echo ""
echo "Kafka bekleniyor..."
sleep 1
echo "✓ Kafka başlatıldı."

# ========================================
# 2. CONFIG SERVER
# ========================================

echo ""
echo "[2/6] Config Server başlatılıyor..."

docker compose up -d config-server

echo "Config Server bekleniyor..."

until curl -sf http://localhost:8888/actuator/health >/dev/null 2>&1
do
    echo "  Config Server henüz hazır değil..."
    sleep 2
done

echo "✓ Config Server hazır."

# ========================================
# 3. EUREKA
# ========================================

echo ""
echo "[3/6] Eureka Server başlatılıyor..."

docker compose up -d eureka-server

echo "Eureka bekleniyor..."

until curl -sf http://localhost:8761/actuator/health >/dev/null 2>&1
do
    echo "  Eureka henüz hazır değil..."
    sleep 2
done

echo "✓ Eureka hazır."

# ========================================
# 4. APPLICATION SERVICES
# ========================================

echo ""
echo "[4/6] Backend servisleri başlatılıyor..."

echo ""
echo "Auth Service..."
docker compose up -d auth-service

echo "Auth Service bekleniyor..."
sleep 3

echo "✓ Auth Service başlatıldı."

echo ""
echo "Product Service..."
docker compose up -d product-service

echo "Product Service bekleniyor..."
sleep 3

echo "✓ Product Service başlatıldı."

echo ""
echo "Cash Service..."
docker compose up -d cash-service

echo "Cash Service bekleniyor..."
sleep 3

echo "✓ Cash Service başlatıldı."

echo ""
echo "Report Service..."
docker compose up -d report-service

echo "Report Service bekleniyor..."
sleep 3

echo "✓ Report Service başlatıldı."

echo ""
echo "Sale Service..."
docker compose up -d sale-service

echo "Sale Service bekleniyor..."
sleep 3

echo "✓ Sale Service başlatıldı."

# ========================================
# 5. API GATEWAY
# ========================================

echo ""
echo "[5/6] API Gateway başlatılıyor..."

docker compose up -d api-gateway

echo "Gateway bekleniyor..."
sleep 3

echo "✓ API Gateway başlatıldı."
# ========================================
# 6. FRONTEND
# ========================================

echo ""
echo "[6/6] Frontend başlatılıyor..."

docker compose up -d frontend

if [ $? -ne 0 ]; then
    echo "HATA: Frontend başlatılamadı."
    exit 1
fi

echo "Frontend bekleniyor..."

until curl -sf http://localhost:4200 >/dev/null 2>&1
do
    echo "  Frontend henüz hazır değil..."
    sleep 2
done

echo "✓ Frontend hazır."

# ========================================
# STATUS
# ========================================

echo ""
echo "========================================"
echo "       HybridPOS System Started"
echo "========================================"
echo ""

docker compose ps

echo ""
echo "========================================"
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
echo "Frontend   : 4200"
echo "========================================"

echo ""
echo "Sistem başlatıldı."
echo ""
sleep 5
