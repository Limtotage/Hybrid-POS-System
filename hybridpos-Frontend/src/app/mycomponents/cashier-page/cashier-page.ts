import { Component } from '@angular/core';
import { ProductService } from '../../services/product/product-service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cashier-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './cashier-page.html',
  styleUrl: './cashier-page.css',
})
export class CashierPage {
  constructor(private productService: ProductService) {}

  shopId = 1;
  showPayment = false;
  cashGiven: number = 0;
  cardAmount: number = 0;
  paidAmount: number = 0;
  paymentType = 'cash';
  barcodeInput: string = '';
  products: any[] = [];
  filteredProducts: any[] = [];
  searchText: string = '';
  cart: any[] = [];
  totalAmount: number = 0;

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getAllProducts(this.shopId).subscribe((res) => {
      this.products = res;
      this.filteredProducts = res;
    });
  }
  filterProducts() {
    this.filteredProducts = this.products.filter((p) =>
      p.name.toLowerCase().includes(this.searchText.toLowerCase()),
    );
  }
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
    this.totalAmount = this.cart.reduce((sum, item) => sum + item.salePrice * item.quantity, 0);
  }

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
  openPayment() {
    if (this.cart.length === 0) {
      alert('Sepet boş.');
      return;
    }

    this.paidAmount = 0;
    this.paymentType = 'cash';
    this.showPayment = true;
  }
  closePayment() {
    this.showPayment = false;
  }
  get totalPaid(): number {
    return Number(this.cashGiven) + Number(this.cardAmount);
  }

  get change(): number {
    return this.totalPaid - this.totalAmount;
  }
  completeSale() {
    const totalPaid = this.totalPaid;

    if (totalPaid < this.totalAmount) {
      alert('Yetersiz ödeme! Satış tamamlanamaz.');
      return;
    }
    if (totalPaid > this.totalAmount) {
      alert('Fazla ödeme! Satış tamamlanamaz.');
      return;
    }

    const paymentData = {
      cart: this.cart,
      total: this.totalAmount,
      cash: this.cashGiven,
      card: this.cardAmount,
      change: this.change,
    };

    // backend'e gönderilebilir

    alert('Satış tamamlandı');

    this.cart = [];
    this.totalAmount = 0;
    this.cashGiven = 0;
    this.cardAmount = 0;
    this.closePayment();
  }
}
