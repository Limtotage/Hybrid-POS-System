import { Injectable } from '@angular/core';
import { SaleService } from '../sales/sale-service';
import { IndexedDbService } from '../database/indexed-db';

@Injectable({
  providedIn: 'root',
})
export class SyncService {
  constructor(
    private indexedDbService: IndexedDbService,
    private saleService: SaleService,
  ) {
    window.addEventListener('online', () => {
      console.log('🌐 İnternet bağlantısı geri geldi.');
      this.syncOfflineSales();
    });
  }

  async syncOfflineSales(): Promise<void> {
    try {
      const offlineSales = await this.indexedDbService.getOfflineSales();

      console.log('📦 Sync edilecek offline satış sayısı:', offlineSales.length);

      for (const sale of offlineSales) {
        await this.syncSale(sale);
      }
    } catch (error) {
      console.error('❌ Offline satış sync hatası:', error);
    }
  }
  async retryOfflineSale(sale: any): Promise<void> {
    await this.syncSale(sale);
  }

  private syncSale(sale: any): Promise<void> {
    return new Promise((resolve) => {
      const saleData = {
        clientSaleId: sale.clientSaleId,
        items: sale.items,
        paymentType: sale.paymentType,
        cashPaid: sale.cashPaid,
        cardPaid: sale.cardPaid,
      };

      this.saleService.makeSale(sale.cashRegisterId, saleData).subscribe({
        next: () => {
          console.log("✅ Offline satış backend'e gönderildi:", sale.id);

          this.indexedDbService
            .deleteOfflineSale(sale.id)
            .then(() => {
              console.log("🗑️ Offline satış IndexedDB'den silindi:", sale.id);

              resolve();
            })
            .catch((error) => {
              console.error('❌ Offline satış silinemedi:', error);

              resolve();
            });
        },

        error: (error) => {
          console.error('❌ Offline satış gönderilemedi:', sale.id, error);

          const retryCount = (sale.retryCount || 0) + 1;

          const errorMessage =
            error?.error?.message || error?.message || 'Satış senkronize edilemedi.';

          this.indexedDbService
            .updateOfflineSale(sale.id, {
              syncStatus: 'FAILED',
              syncError: errorMessage,
              retryCount: retryCount,
            })
            .then(() => {
              console.log('⚠️ Offline satış FAILED olarak işaretlendi:', sale.id, errorMessage);

              resolve();
            })
            .catch((updateError) => {
              console.error('❌ Sync durumu güncellenemedi:', updateError);

              resolve();
            });
        },
      });
    });
  }
}
