import { ChangeDetectorRef, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ProductService } from '../../services/product/product-service';
import { SaleService } from '../../services/sales/sale-service';
import { CashRegisterService } from './cash-register/cash-register-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cashier-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './cashier-page.html',
  styleUrl: './cashier-page.css',
})
export class CashierPage {
  constructor(
    private productService: ProductService,
    private saleService: SaleService,
    private cashRegisterService: CashRegisterService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}
  //=========================
  cashId: number | null = null;
  cashName: string = 'Ana Kasa';
  openingCash: number = 0;
  closingCash: number = 0;
  cashOpen: boolean = false;
  //========================
  showPayment = false;
  cashGiven: number = 0;
  cardAmount: number = 0;
  //=======================
  barcodeInput: string = '';

  products: any[] = [];
  filteredProducts: any[] = [];

  searchText: string = '';

  cart: any[] = [];

  totalAmount: number = 0;
//======================
  ngOnInit() {
    this.loadProducts();
    this.checkOpenCash();
  }
  //=========================
  checkOpenCash() {
    this.cashRegisterService.getMyOpenCash().subscribe({
      next: (cash) => {
        console.log('Açık kasa bulundu:', cash);

        this.cashId = cash.id;
        this.cashName = cash.name;

        this.cashOpen = true;

        this.loadProducts();
      },

      error: () => {
        console.log('Açık kasa bulunamadı.');

        this.cashId = null;
        this.cashOpen = false;
      },
    });
  }
  //=========================
  openCash() {
    if (this.openingCash < 0) {
      alert('Kasadaki para negatif olamaz.');
      return;
    }

    const cashData = {
      openingCash: this.openingCash,
    };
    this.cashRegisterService.openCash(cashData).subscribe({
      next: (cash) => {
        console.log('Kasa açıldı:', cash);

        this.cashId = cash.id;

        this.cashOpen = true;

        this.loadProducts();
      },

      error: (err) => {
        console.error('OPEN CASH ERROR:', err);
        console.error('STATUS:', err.status);
        console.error('ERROR BODY:', err.error);
        console.error('MESSAGE:', err.message);

        alert('Kasa backendde açıldı ama frontend cevap alamadı. F12 Console/Network kontrol et.');
      },
    });
    this.cdr.detectChanges();
  }
  //=========================
  closeCash() {
    if (this.cashId === null) {
      alert('Açık kasa bulunamadı.');
      return;
    }

    if (this.closingCash < 0) {
      alert('Kasadaki para negatif olamaz.');
      return;
    }

    if (this.closingCash === 0) {
      const confirmed = confirm(
        'Kasada 0 TL görünüyor.\n\n' + 'Kasayı kapatmak istediğinizden emin misiniz?',
      );

      if (!confirmed) {
        return;
      }
    }

    const closeData = {
      closingCash: this.closingCash,
    };

    this.cashRegisterService.closeCash(this.cashId, closeData).subscribe({
      next: (cash) => {
        console.log('Kasa kapatıldı:', cash);

        this.cashOpen = false;

        this.cashId = null;

        this.closingCash = 0;

        this.cart = [];

        this.totalAmount = 0;

        alert('Kasa başarıyla kapatıldı.');
      },

      error: (err) => {
        console.error('Kasa kapatılamadı:', err);

        alert(err?.error?.message || 'Kasa kapatılırken bir hata oluştu.');
      },
    });
    this.router.navigate(['/login']);
  }
  //=========================

  loadProducts() {
    this.productService.getAllProducts().subscribe({
      next: (res) => {
        this.products = res;

        this.filteredProducts = res;
        this.cdr.detectChanges();
      },

      error: (err) => {
        console.error('Ürünler yüklenemedi', err);
      },
    });
    this.cdr.detectChanges();
  }

  filterProducts() {
    this.filteredProducts = this.products.filter((p) =>
      p.name.toLowerCase().includes(this.searchText.toLowerCase()),
    );
  }
  //=========================
  addToCart(product: any) {
    const existing = this.cart.find((i) => i.id === product.id);

    if (existing) {
      existing.quantity++;
    } else {
      this.cart.push({
        ...product,

        quantity: 1,
      });
    }

    this.calculateTotal();
  }

  increase(item: any) {
    item.quantity++;

    this.calculateTotal();
  }

  decrease(item: any) {
    item.quantity--;

    if (item.quantity <= 0) {
      this.cart = this.cart.filter((i) => i.id !== item.id);
    }

    this.calculateTotal();
  }

  calculateTotal() {
    this.totalAmount = this.cart.reduce(
      (sum, item) => sum + item.salePrice * item.quantity,

      0,
    );
  }
  //=========================

  addByBarcode() {
    this.productService.getByBarcode(this.barcodeInput).subscribe({
      next: (product) => {
        this.addToCart(product);

        this.barcodeInput = '';
      },

      error: () => {
        alert('Ürün bulunamadı');
      },
    });
  }
  //=========================
  openPayment() {
    if (this.cart.length === 0) {
      alert('Sepet boş.');

      return;
    }

    this.cashGiven = 0;

    this.cardAmount = 0;

    this.showPayment = true;
  }

  closePayment() {
    this.showPayment = false;
    this.cdr.detectChanges();
  }

  get totalPaid(): number {
    return Number(this.cashGiven) + Number(this.cardAmount);
  }

  get change(): number {
    return this.totalPaid - this.totalAmount;
  }
  //=========================

  completeSale() {
    if (this.cashId === null) {
      alert('Açık kasa bulunamadı.');

      return;
    }

    const totalPaid = this.totalPaid;

    if (totalPaid < this.totalAmount) {
      alert('Yetersiz ödeme! Satış tamamlanamaz.');

      return;
    }

    if (totalPaid > this.totalAmount) {
      alert('Fazla ödeme! Satış tamamlanamaz.');

      return;
    }

    const items = this.cart.map((item) => ({
      barcode: item.barcode,
      name: item.name,
      salePrice: item.salePrice,
      quantity: item.quantity,
    }));

    const paymentType =
      this.cashGiven > 0 && this.cardAmount > 0 ? 'MIXED' : this.cashGiven > 0 ? 'CASH' : 'CARD';

    const saleData = {
      items: items,
      paymentType: paymentType,
      cashPaid: this.cashGiven,
      cardPaid: this.cardAmount,
    };
    this.saleService.makeSale(this.cashId, saleData).subscribe({
      next: (res) => {
        console.log('SATIŞ NEXT ÇALIŞTI:', res);

        alert('Satış başarıyla tamamlandı');

        this.cart = [];
        this.totalAmount = 0;
        this.cashGiven = 0;
        this.cardAmount = 0;
        this.closePayment();
      },

      error: (err) => {
        console.error('SATIŞ ERROR:', err);
        console.error('STATUS:', err.status);
        console.error('ERROR BODY:', err.error);
        console.error('MESSAGE:', err.message);

        alert('Satış backendde gerçekleşmiş olabilir ama frontend response alamadı.');
      },
    });
  }
}
