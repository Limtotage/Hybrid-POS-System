import { ChangeDetectorRef, Component, HostListener, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import Quagga from '@ericblade/quagga2'; // Quagga2 Import Edildi

import { ProductService } from '../../services/product/product-service';
import { SaleService } from '../../services/sales/sale-service';
import { CashRegisterService } from './cash-register/cash-register-service';

@Component({
  selector: 'app-cashier-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './cashier-page.html',
  styleUrl: './cashier-page.css',
})
export class CashierPage implements OnInit {
  constructor(
    private productService: ProductService,
    private saleService: SaleService,
    private cashRegisterService: CashRegisterService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  // CASH REGISTER
  cashRegistersLoading = false;
  cashId: number | null = null;
  cashName = 'Ana Kasa';
  cashOpen = false;
  cashRegisters: any[] = [];
  selectedCashId: number | null = null;

  // PAYMENT
  showPayment = false;
  cashGiven = 0;
  cardAmount = 0;

  // BARCODE
  barcodeQuantity = 1;
  barcodeBuffer = '';
  barcodeInput = '';
  lastKeyTime = 0;
  // PRODUCTS / CART
  products: any[] = [];
  filteredProducts: any[] = [];
  searchText = '';
  cart: any[] = [];
  totalAmount = 0;

  ngOnInit(): void {
    this.loadProducts();
    this.loadCashRegisters();
  }
  // =========================================================
  // GLOBAL KLAVYE DİNLENMESİ (USB BARKOD OKUYUCU)
  // =========================================================
  @HostListener('window:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent): void {
    const activeElement = document.activeElement;

    if (
      activeElement &&
      (activeElement.tagName === 'INPUT' || activeElement.tagName === 'TEXTAREA')
    ) {
      const inputElement = activeElement as HTMLInputElement;
      if (inputElement.id !== 'barcode-input-field') {
        return;
      }
    }

    const currentTime = Date.now();

    if (currentTime - this.lastKeyTime > 100) {
      this.barcodeBuffer = '';
    }
    this.lastKeyTime = currentTime;

    if (event.key === 'Enter') {
      event.preventDefault();

      if (this.barcodeBuffer.trim().length >= 3) {
        console.log('================================');
        console.log('🎯 USB BARKOD OKUNDU:', this.barcodeBuffer);
        console.log('================================');

        this.barcodeInput = this.barcodeBuffer.trim();
        this.addByBarcode();
        this.barcodeBuffer = '';
      }
    } else if (event.key.length === 1) {
      this.barcodeBuffer += event.key;
    }
  }

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
        const existing = this.cart.find((item) => item.id === product.id);

        if (existing) {
          existing.quantity += quantity;
        } else {
          this.cart.push({
            ...product,
            quantity,
          });
        }

        this.calculateTotal();

        this.barcodeInput = '';
        this.barcodeQuantity = 1;

        this.cdr.detectChanges();
      },

      error: () => {
        alert('Ürün bulunamadı.');
      },
    });
  }

  // CASH REGISTER METHODS
  loadCashRegisters(): void {
    this.cashRegistersLoading = true;
    this.cashRegisterService.getAllCashRegisters().subscribe({
      next: (cashRegisters) => {
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
        this.loadProducts();
      },
      error: (err) => {
        alert(err?.error?.message || 'Kasa açılırken bir hata oluştu.');
      },
    });
  }

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
        this.cashOpen = false;
        this.cashId = null;
        this.selectedCashId = null;
        this.cart = [];
        this.totalAmount = 0;
        alert('Kasa başarıyla kapatıldı.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert(err?.error?.message || 'Kasa kapatılırken bir hata oluştu.');
      },
    });
  }

  // PRODUCTS & CART
  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (res) => {
        this.products = res;
        this.filteredProducts = res;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Ürünler yüklenemedi:', err);
      },
    });
  }

  filterProducts(): void {
    this.filteredProducts = this.products.filter((product) =>
      product.name.toLowerCase().includes(this.searchText.toLowerCase()),
    );
  }

  addToCart(product: any): void {
    const existing = this.cart.find((item) => item.id === product.id);

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

  increase(item: any): void {
    item.quantity++;
    this.calculateTotal();
  }

  decrease(item: any): void {
    item.quantity--;

    if (item.quantity <= 0) {
      this.cart = this.cart.filter((i) => i.id !== item.id);
    }

    this.calculateTotal();
  }

  calculateTotal(): void {
    this.totalAmount = this.cart.reduce((sum, item) => sum + item.salePrice * item.quantity, 0);
  }

  // PAYMENT & SALE
  openPayment(): void {
    if (this.cart.length === 0) {
      alert('Sepet boş.');
      return;
    }
    this.cashGiven = 0;
    this.cardAmount = 0;
    this.showPayment = true;
  }

  closePayment(): void {
    this.showPayment = false;
    this.cdr.detectChanges();
  }

  get totalPaid(): number {
    return Number(this.cashGiven) + Number(this.cardAmount);
  }

  get change(): number {
    return this.totalPaid - this.totalAmount;
  }

  completeSale(): void {
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
      items,
      paymentType,
      cashPaid: this.cashGiven,
      cardPaid: this.cardAmount,
    };

    this.saleService.makeSale(this.cashId, saleData).subscribe({
      next: (res) => {
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
