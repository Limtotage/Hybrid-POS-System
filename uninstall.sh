#!/bin/bash

echo "========================================"
echo "     HybridPOS Development Cleanup"
echo "========================================"
echo ""

read -r -p "Development verileri tamamen silinecek. Devam? (yes/no): " CONFIRM

if [ "$CONFIRM" != "yes" ]; then
    echo ""
    echo "İşlem iptal edildi."
    exit 0
fi

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"

if [ ! -d "$PROJECT_DIR" ]; then
    echo "HATA: HybridPOS development compose klasörü bulunamadı."
    exit 1
fi

cd "$PROJECT_DIR" || exit 1

echo ""
echo "[1/3] Container'lar ve volume'lar kaldırılıyor..."

docker compose down -v --remove-orphans

if [ $? -ne 0 ]; then
    echo "HATA: Docker Compose temizliği başarısız."
    exit 1
fi

echo "✓ Container'lar kaldırıldı."
echo "✓ Development volume'ları kaldırıldı."

echo ""
echo "[2/3] HybridPOS image'ları kaldırılıyor..."

IMAGES=$(docker image ls --format "{{.Repository}}:{{.Tag}}" | grep '^hybridpos-' || true)

if [ -n "$IMAGES" ]; then
    echo "$IMAGES" | xargs docker image rm 2>/dev/null
    echo "✓ HybridPOS image'ları temizlendi."
else
    echo "- Silinecek HybridPOS image'ı bulunamadı."
fi

echo ""
echo "[3/3] Kontrol..."

echo ""
echo "Container'lar:"
docker ps -a --format "table {{.Names}}\t{{.Status}}"

echo ""
echo "HybridPOS image'ları:"
docker image ls | grep hybridpos || echo "  Yok"

echo ""
echo "========================================"
echo "   Development temizliği tamamlandı"
echo "========================================"
echo ""
