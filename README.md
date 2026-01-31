# Offline-Online-POS-System

A hybrid **Offline & Online Point of Sale (POS)** system designed for retail businesses that require uninterrupted sales operations.  
The system works fully **offline-first** and can seamlessly synchronize with an **online backend** when internet connectivity is available.

---

## 🚀 Key Features

### 🔐 Authentication & Roles
- JWT-based authentication
- Owner & Cashier roles
- Owner auto-registration on first system startup
- Owner-managed cashier creation

### 🏪 Cash Register Management
- Multi-cash register (multi-kasa) support
- Cash register open / close operations
- Cash movements tracking per register
- Cash difference (Kasa Fark) reports

### 💰 Sales System
- Offline-first sales processing
- Local persistent database
- No internet dependency for sales
- Online synchronization (planned / optional)

### 📊 Reports & Analytics
- Cash difference reports
- Supplier (Toptancı) difference lists
- SQL-based statistical queries
- Daily / shift-based summaries

### 🔌 POS & Hardware Integration
- POS device integration (planned)
- Hardware-agnostic architecture

### 🌐 Offline & Online Hybrid Architecture
- Fully functional offline mode
- Online mode for:
  - Data synchronization
  - Central reporting
  - Multi-branch support (future)

---

## 🧱 Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security + JWT
- JPA / Hibernate
- MySQL (offline & online compatible)

### DevOps
- Docker
- Docker Compose
- Production-ready container setup

---

## 🔄 System Flow

### Initial Startup
1. Application starts
2. If no **Owner** exists → Owner registration is required
3. Owner logs in
4. Owner creates Cashier accounts

### Cashier Flow
1. Cashier logs in
2. Selects and opens a cash register
3. Performs sales (offline or online)
4. Closes register and generates reports

---

## 🗄️ Database Overview

- Users (Owner, Cashier)
- Cash Registers
- Sales
- Cash Movements
- Suppliers
- Difference Reports
- Sync Metadata (for online mode)

---

## 📦 Offline Strategy

- Local database persistence
- No external API dependency during sales
- Transactions stored locally
- Safe recovery after power or network loss

---

## 🌐 Online Strategy (Planned)

- Optional cloud synchronization
- Conflict-aware sync logic
- Centralized reporting dashboard
- Multi-store support

---

## 🐳 Docker Setup

The application is fully containerized and can be deployed using Docker for:
- Local environments
- On-premise installations
- Production servers

Offline mode works without any external services.

---

## 📌 Roadmap

- [ ] Online synchronization module
- [ ] POS device integration
- [ ] Admin dashboard UI
- [ ] Multi-branch architecture
- [ ] Advanced analytics & reporting

---

## 🧑‍💻 Author

**Abdullah YALIM**
