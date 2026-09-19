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
  cashRegistersLoading = false;
  cashId: number | null = null;
  cashName: string = 'Ana Kasa';
  cashOpen: boolean = false;
  cashRegisters: any[] = [];
  selectedCashId: number | null = null;
  //========================
  showPayment = false;
  cashGiven: number = 0;
  cardAmount: number = 0;
  //=======================
  barcodeInput: string = '';
  barcodeQuantity: number = 1;

  products: any[] = [];
  filteredProducts: any[] = [];

  searchText: string = '';

  cart: any[] = [];

  totalAmount: number = 0;
  //======================
  ngOnInit() {
    this.loadProducts();
    this.loadCashRegisters();
  }
  loadCashRegisters(): void {
    this.cashRegistersLoading = true;

    this.cashRegisterService.getAllCashRegisters().subscribe({
      next: (cashRegisters) => {
        console.log('Kasalar:', cashRegisters);

        this.cashRegisters = cashRegisters;

        const openCash = cashRegisters.find((cash) => cash.open);

        if (openCash) {
          this.cashId = openCash.id;
          this.cashName = openCash.name;
          this.cashOpen = true;
        }

        this.cashRegistersLoading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Kasalar alınamadı:', err);
        this.cashRegistersLoading = false;
      },
    });
  }
  openCash(): void {
    if (this.selectedCashId === null) {
      alert('Lütfen bir kasa seçin.');
      return;
    }

    this.cashRegisterService.openCash(this.selectedCashId).subscribe({
      next: () => {
        const selectedCash = this.cashRegisters.find((cash) => cash.id === this.selectedCashId);

        this.cashId = this.selectedCashId;
        this.cashName = selectedCash?.name ?? 'Kasa';
        this.cashOpen = true;

        console.log('Kasa açıldı:', selectedCash);

        this.loadProducts();
      },
      error: (err) => {
        console.error('Kasa açılamadı:', err);

        alert(err?.error?.message || 'Kasa açılırken bir hata oluştu.');
      },
    });
  }
  //=========================
  closeCash(): void {
    if (this.cashId === null) {
      alert('Açık kasa bulunamadı.');
      return;
    }

    const confirmed = confirm('Kasayı kapatmak istediğinizden emin misiniz?');

    if (!confirmed) {
      return;
    }

    this.cashRegisterService.closeCash(this.cashId).subscribe({
      next: () => {
        console.log('Kasa kapatıldı.');

        this.cashOpen = false;
        this.cashId = null;
        this.selectedCashId = null;
        this.cart = [];
        this.totalAmount = 0;

        alert('Kasa başarıyla kapatıldı.');

        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Kasa kapatılamadı:', err);

        alert(err?.error?.message || 'Kasa kapatılırken bir hata oluştu.');
      },
    });
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

  addByBarcode(): void {
  if (!this.barcodeInput.trim()) {
    return;
  }

  const quantity = Number(this.barcodeQuantity);

  if (!Number.isInteger(quantity) || quantity <= 0) {
    alert('Geçerli bir adet giriniz.');
    return;
  }

  this.productService.getByBarcode(this.barcodeInput).subscribe({
    next: (product) => {

      const existing = this.cart.find(
        item => item.id === product.id
      );

      if (existing) {
        existing.quantity += quantity;
      } else {
        this.cart.push({
          ...product,
          quantity: quantity,
        });
      }

      this.calculateTotal();

      // Barkodu temizle
      this.barcodeInput = '';

      // Her okutma işleminden sonra adet tekrar 1 olsun
      this.barcodeQuantity = 1;
    },

    error: () => {
      alert('Ürün bulunamadı.');
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
      },
    });
  }
}
