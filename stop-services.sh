#!/bin/bash

PROJECT_DIR="$HOME/Desktop/Hybrid-POS-System"

echo "========================================"
echo "       HybridPOS System Stopping"
echo "========================================"
echo ""

cd "$PROJECT_DIR" || exit 1

echo "Docker Compose servisleri durduruluyor..."
docker compose stop

if [ $? -ne 0 ]; then
    echo ""
    echo "HATA: Servisler durdurulamadı."
    exit 1
fi

echo ""
echo "Servislerin son durumu:"
echo ""

docker compose ps

echo ""
echo "========================================"
echo "       HybridPOS Stopped"
echo "========================================"
echo ""
echo "Container'lar durduruldu."
echo "Volume ve veritabanı verileri korunuyor."
echo "========================================"
sleep 2

