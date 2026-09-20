# HybridPOS

**HybridPOS** is an offline-capable Point of Sale (POS) system designed for small and medium-sized businesses.

Sistem dokümantasyonunu Türkçe görüntülemek için: **[🇹🇷 Türkçe README](README-TR.md)**

The system provides product and inventory management, cash register operations, sales management, reporting, barcode scanner support, and offline sales functionality.

## ✨ Features

* 🖥️ Linux support
* 🪟 Windows support
* 🐳 Docker-based installation
* 📡 Offline sales
* 📦 Inventory management
* 🧾 Sales management
* 💰 Cash register management
* 📊 Reporting
* 🔎 Barcode scanner support
* 👤 Admin and Cashier roles
* 🔐 JWT-based authentication
* ⚡ Redis caching
* 📨 Kafka-based event communication
* 🗄️ PostgreSQL database
* 🌐 Microservice architecture

## 🏗️ Architecture

HybridPOS is built using a microservice architecture.

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

                    Infrastructure
              ┌──────────┐ ┌─────────┐ ┌─────────┐
              │PostgreSQL│ │  Redis  │ │ Eureka  │
              └──────────┘ └─────────┘ └─────────┘
```

## 🛠️ Technologies

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

### Infrastructure

* Docker
* Docker Compose
* Linux
* Windows + Docker Desktop

## 📡 Offline System

HybridPOS supports offline sales when the internet connection or backend services are temporarily unavailable.

The offline system provides:

* Local product cache using IndexedDB
* Offline barcode lookup
* Offline stock validation
* Offline sales
* Offline stock decrement
* Sale queue
* Automatic synchronization when the connection returns
* Duplicate sale prevention using `clientSaleId`
* Failed synchronization tracking
* Manual retry support

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
        Connection restored
                 │
                 ▼
               Sync
                 │
                 ▼
             Backend
```

## 👥 User Roles

### Admin

Admin users can:

* Manage cashiers
* Manage products
* Manage categories
* Manage stock
* Manage cash registers
* View reports
* Manage inventory movements
* Manage system data

### Cashier

Cashiers can:

* Open and close cash registers
* Search products
* Scan barcodes
* Create sales
* Process offline sales
* View failed offline sales
* Retry failed synchronization

## 📸 Screenshots

Screenshots of the application are available below.

### Login

<img width="1920" height="1080" alt="Login-Page" src="https://github.com/user-attachments/assets/5a6095be-9d51-42fe-aac9-bc7392f5b875" />

### Admin Dashboard

<img width="1920" height="1080" alt="Admin-Dasboard" src="https://github.com/user-attachments/assets/aae3db8d-5030-4469-88e4-060ef8a63240" />

### Products


<img width="1920" height="1080" alt="Admin-Product" src="https://github.com/user-attachments/assets/e3ba9318-e923-4ee1-ad87-f9bb66a86f67" />


### Cashier / POS


<img width="1920" height="1080" alt="Kasiyer-POS" src="https://github.com/user-attachments/assets/c233e575-9dc2-425b-b5d4-9a95dfabf1f4" />


### Sales

<img width="610" height="604" alt="Odeme-Ekrani" src="https://github.com/user-attachments/assets/2e16db1e-91d1-460f-8673-d67304b35094" />

### Reports

<img width="1920" height="1080" alt="Admin-Reports" src="https://github.com/user-attachments/assets/e8094f90-f0b4-4166-8aad-22c65eebf706" />


## 📦 Downloads

The first customer release is available through GitHub Releases.

### HybridPOS v1.0.0

**Linux**

[Download HybridPOS Linux v1.0.0](../../releases/tag/v1.0.0)

**Windows**

[Download HybridPOS Windows v1.0.0](../../releases/tag/v1.0.0)

The release contains:

* `HybridPOS-Linux-v1.0.0.tar.gz`
* `HybridPOS-Windows-v1.0.0.zip`

Both packages contain the required Docker images and installation files, so customers do not need to install Java, Node.js, PostgreSQL, Redis, Kafka, or other backend dependencies manually.

## 🚀 Installation

Detailed installation instructions are included inside each customer package.

### Linux

Download:

```text
HybridPOS-Linux-v1.0.0.tar.gz
```

Extract the package and run:

```bash
./install.sh
```

Then start HybridPOS using:

```bash
./start.sh
```

### Windows

Download:

```text
HybridPOS-Windows-v1.0.0.zip
```

Extract the package and run:

```powershell
.\install.ps1
```

Then start HybridPOS using:

```powershell
.\start.ps1
```

Docker Desktop must be running on Windows.

## 🔐 Default Admin Account

The first installation creates the default administrator account:

```text
Username: admin
Password: admin123
```

It is recommended to change the default credentials after the first login.

## 📋 Version

### v1.0.0

HybridPOS v1.0.0 is the first customer release.

This release includes:

* Linux support
* Windows support
* Docker-based installation
* Offline sales
* Barcode scanner support
* Inventory management
* Cash register management
* Reporting
* Admin and Cashier roles

## 📄 License

This project is currently maintained as a personal software project.

---

**HybridPOS — Offline-capable Point of Sale System**

