import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class IndexedDbService {
  private readonly dbName = 'hybridpos-db';
  private readonly dbVersion = 2;
  private readonly productStore = 'products';
  private readonly offlineSaleStore = 'offline-sales';

  private dbPromise: Promise<IDBDatabase> | null = null;

  constructor() {
    this.dbPromise = this.openDatabase();
  }

  private openDatabase(): Promise<IDBDatabase> {
    return new Promise((resolve, reject) => {
      const request = indexedDB.open(this.dbName, this.dbVersion);

      request.onerror = () => {
        reject(request.error);
      };

      request.onsuccess = () => {
        resolve(request.result);
      };

      request.onupgradeneeded = () => {
        const db = request.result;

        if (!db.objectStoreNames.contains(this.productStore)) {
          db.createObjectStore(this.productStore, {
            keyPath: 'id',
          });
        }

        if (!db.objectStoreNames.contains(this.offlineSaleStore)) {
          db.createObjectStore(this.offlineSaleStore, {
            keyPath: 'id',
          });
        }
      };
    });
  }
  async saveOfflineSale(sale: any): Promise<void> {
    const db = await this.dbPromise!;

    return new Promise((resolve, reject) => {
      const transaction = db.transaction(this.offlineSaleStore, 'readwrite');

      const store = transaction.objectStore(this.offlineSaleStore);

      store.put(sale);

      transaction.oncomplete = () => {
        resolve();
      };

      transaction.onerror = () => {
        reject(transaction.error);
      };
    });
  }
  async getOfflineSales(): Promise<any[]> {
    const db = await this.dbPromise!;

    return new Promise((resolve, reject) => {
      const transaction = db.transaction(this.offlineSaleStore, 'readonly');

      const store = transaction.objectStore(this.offlineSaleStore);

      const request = store.getAll();

      request.onsuccess = () => {
        resolve(request.result);
      };

      request.onerror = () => {
        reject(request.error);
      };
    });
  }
  async deleteOfflineSale(id: string): Promise<void> {
    const db = await this.dbPromise!;

    return new Promise((resolve, reject) => {
      const transaction = db.transaction(this.offlineSaleStore, 'readwrite');

      const store = transaction.objectStore(this.offlineSaleStore);

      store.delete(id);

      transaction.oncomplete = () => {
        resolve();
      };

      transaction.onerror = () => {
        reject(transaction.error);
      };
    });
  }

  async saveProducts(products: any[]): Promise<void> {
    const db = await this.dbPromise!;

    return new Promise((resolve, reject) => {
      const transaction = db.transaction(this.productStore, 'readwrite');

      const store = transaction.objectStore(this.productStore);

      products.forEach((product) => {
        store.put(product);
      });

      transaction.oncomplete = () => {
        resolve();
      };

      transaction.onerror = () => {
        reject(transaction.error);
      };
    });
  }

  async getProducts(): Promise<any[]> {
    const db = await this.dbPromise!;

    return new Promise((resolve, reject) => {
      const transaction = db.transaction(this.productStore, 'readonly');

      const store = transaction.objectStore(this.productStore);

      const request = store.getAll();

      request.onsuccess = () => {
        resolve(request.result);
      };

      request.onerror = () => {
        reject(request.error);
      };
    });
  }

  async getProductByBarcode(barcode: string): Promise<any | undefined> {
    const products = await this.getProducts();

    return products.find((product) => product.barcode === barcode);
  }
}
