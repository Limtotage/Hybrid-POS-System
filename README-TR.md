# HybridPOS

**HybridPOS**, küçük ve orta ölçekli işletmeler için geliştirilmiş, offline çalışmayı destekleyen bir **Satış ve Kasa Yönetim (POS) sistemi**dir.

Sistem; ürün ve stok yönetimi, kasa işlemleri, satış yönetimi, raporlama, barkod okuyucu desteği ve internet bağlantısı olmadan satış yapabilme özellikleri sunar.

## ✨ Özellikler

* 🖥️ Linux desteği
* 🪟 Windows desteği
* 🐳 Docker tabanlı kurulum
* 📡 Offline satış
* 📦 Stok yönetimi
* 🧾 Satış yönetimi
* 💰 Kasa yönetimi
* 📊 Raporlama
* 🔎 Barkod okuyucu desteği
* 👤 Admin ve Kasiyer rolleri
* 🔐 JWT tabanlı kimlik doğrulama
* ⚡ Redis önbellekleme
* 📨 Kafka tabanlı event iletişimi
* 🗄️ PostgreSQL veritabanı
* 🌐 Microservice mimarisi

## 🏗️ Mimari

HybridPOS, microservice mimarisi kullanılarak geliştirilmiştir.

```text
                         ┌─────────────────┐
                         │    Angular UI   │
                         │    Frontend     │
                         └────────┬────────┘
                                  │
                                  ▼
                         ┌─────────────────┐
                         │   API Gateway   │
                         │     :8080       │
                         └────────┬────────┘
                                  │
              ┌───────────────────┼───────────────────┐
              │                   │                   │
              ▼                   ▼                   ▼
       ┌────────────┐      ┌────────────┐      ┌────────────┐
       │Auth Service│      │  Product   │      │   Sale     │
       │   :8081    │      │  Service   │      │  Service   │
       └────────────┘      │   :8082    │      │   :8083    │
                           └────────────┘      └──────┬─────┘
                                                      │
                                  ┌───────────────────┼──────────────┐
                                  │                   │              │
                                  ▼                   ▼              ▼
                           ┌────────────┐      ┌────────────┐  ┌───────────┐
                           │   Cash     │      │   Report   │  │   Kafka   │
                           │  Service   │      │  Service   │  │           │
                           │   :8084    │      │   :8085    │  └───────────┘
                           └────────────┘      └────────────┘

                    Altyapı
              ┌──────────┐ ┌─────────┐ ┌─────────┐
              │PostgreSQL│ │  Redis  │ │ Eureka  │
              └──────────┘ └─────────┘ └─────────┘
```

## 🛠️ Kullanılan Teknolojiler

### Backend

* Java 21
* Spring Boot
* Spring Cloud
* Spring Security
* Spring Data JPA
* JWT
* PostgreSQL
* Redis
* Apache Kafka
* Eureka
* API Gateway
* Config Server

### Frontend

* Angular 20
* TypeScript
* HTML / CSS
* IndexedDB

### Altyapı

* Docker
* Docker Compose
* Linux
* Windows + Docker Desktop

## 📡 Offline Sistem

HybridPOS, internet bağlantısı veya backend servisleri geçici olarak kullanılamadığında offline satış yapmayı destekler.

Offline sistem şunları sağlar:

* IndexedDB ile lokal ürün önbelleği
* Offline barkod sorgulama
* Offline stok kontrolü
* Offline satış
* Offline stok azaltma
* Satış kuyruğu
* Bağlantı tekrar geldiğinde otomatik senkronizasyon
* `clientSaleId` ile duplicate satış önleme
* Başarısız senkronizasyon takibi
* Manuel tekrar deneme desteği

```text
              ONLINE
                 │
                 ▼
             API Gateway
                 │
                 ▼
             Microservices


              OFFLINE
                 │
                 ▼
              IndexedDB
                 │
                 ▼
            Offline Queue
                 │
        Bağlantı tekrar geldi
                 │
                 ▼
              Sync
                 │
                 ▼
             Backend
```

## 👥 Kullanıcı Rolleri

### Admin

Admin kullanıcıları:

* Kasiyer yönetimi
* Ürün yönetimi
* Kategori yönetimi
* Stok yönetimi
* Kasa yönetimi
* Raporları görüntüleme
* Stok hareketlerini yönetme
* Sistem verilerini yönetme

işlemlerini gerçekleştirebilir.

### Kasiyer

Kasiyer kullanıcıları:

* Kasa açma ve kapatma
* Ürün arama
* Barkod okutma
* Satış oluşturma
* Offline satış yapma
* Başarısız offline satışları görüntüleme
* Başarısız satışları tekrar senkronize etme

işlemlerini gerçekleştirebilir.

## 📸 Ekran Görüntüleri

Uygulamaya ait ekran görüntüleri aşağıda bulunmaktadır.

### Giriş

![HybridPOS Giriş](screenshots/login.png)

### Admin Paneli

![HybridPOS Admin Paneli](screenshots/admin-dashboard.png)

### Ürünler

![HybridPOS Ürünler](screenshots/products.png)

### Kasa / POS

![HybridPOS Kasa](screenshots/cashier.png)

### Satışlar

![HybridPOS Satışlar](screenshots/sales.png)

### Raporlar

![HybridPOS Raporlar](screenshots/reports.png)

> Ekran görüntüleri proje sunumu güncellendikçe eklenmektedir.

## 📦 İndirme

İlk müşteri sürümü GitHub Releases üzerinden yayınlanmaktadır.

### HybridPOS v1.0.0

**Linux**

[HybridPOS Linux v1.0.0 İndir](../../releases/tag/v1.0.0)

**Windows**

[HybridPOS Windows v1.0.0 İndir](../../releases/tag/v1.0.0)

Release içerisinde:

* `HybridPOS-Linux-v1.0.0.tar.gz`
* `HybridPOS-Windows-v1.0.0.zip`

dosyaları bulunmaktadır.

Her iki paket de gerekli Docker image'larını ve kurulum dosyalarını içerir. Bu nedenle müşterinin Java, Node.js, PostgreSQL, Redis, Kafka veya diğer backend bağımlılıklarını manuel olarak kurmasına gerek yoktur.

## 🚀 Kurulum

Detaylı kurulum talimatları her müşteri paketinin içerisinde bulunmaktadır.

### Linux

Aşağıdaki paketi indirin:

```text
HybridPOS-Linux-v1.0.0.tar.gz
```

Paketi çıkardıktan sonra:

```bash
./install.sh
```

komutunu çalıştırın.

Daha sonra HybridPOS'u:

```bash
./start.sh
```

komutuyla başlatabilirsiniz.

### Windows

Aşağıdaki paketi indirin:

```text
HybridPOS-Windows-v1.0.0.zip
```

Paketi çıkardıktan sonra:

```powershell
.\install.ps1
```

komutunu çalıştırın.

Daha sonra HybridPOS'u:

```powershell
.\start.ps1
```

komutuyla başlatabilirsiniz.

Windows üzerinde Docker Desktop'ın çalışıyor olması gerekir.

## 🔐 Varsayılan Admin Hesabı

İlk kurulumda varsayılan yönetici hesabı oluşturulur:

```text
Kullanıcı adı: admin
Şifre: admin123
```

İlk girişten sonra varsayılan kullanıcı bilgilerinin değiştirilmesi önerilir.

## 📋 Sürüm

### v1.0.0

HybridPOS v1.0.0 ilk müşteri sürümüdür.

Bu sürüm:

* Linux desteği
* Windows desteği
* Docker tabanlı kurulum
* Offline satış
* Barkod okuyucu desteği
* Stok yönetimi
* Kasa yönetimi
* Raporlama
* Admin ve Kasiyer rolleri

özelliklerini içermektedir.

## 📄 Lisans

Bu proje şu anda kişisel bir yazılım projesi olarak geliştirilmektedir.

---

**HybridPOS — Offline çalışabilen Satış ve Kasa Yönetim Sistemi**

