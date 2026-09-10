import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ProductService } from '../../../services/product/product-service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products {
  selectedImage: File | null = null;
  products: any[] = [];

  newProduct = {
    barcode: '',
    name: '',
    purchasePrice: 0,
    salePrice: 0,
    stockQuantity: 0,
  };

  priceUpdate = {
    amount: 0,
    percent: false,
  };

  barcodeInput = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (res: any[]) => {
        this.products = res;
      },

      error: (err) => {
        alert('Ürünler getirilemedi.');
        console.error(err);
      },
    });
  }

  addProduct(form: NgForm): void {
    const formData = new FormData();

    // Ürün bilgilerini JSON olarak ekle
    formData.append(
      'product',
      new Blob([JSON.stringify(this.newProduct)], { type: 'application/json' }),
    );

    // Resmi ekle
    if (this.selectedImage) {
      formData.append('image', this.selectedImage);
    }

    this.productService.addProduct(formData).subscribe({
      next: () => {
        alert('Ürün başarıyla eklendi.');

        form.resetForm({
          barcode: '',
          name: '',
          purchasePrice: 0,
          salePrice: 0,
          stockQuantity: 0,
        });

        this.selectedImage = null;

        this.getProducts();
      },

      error: (err) => {
        alert('Ürün eklenemedi.');
        console.error(err);
      },
    });
  }
  onImageSelected(event: any) {
    const file = event.target.files[0];

    if (file) {
      this.selectedImage = file;
    }
  }

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe({
      next: () => {
        alert('Ürün silindi.');
        this.getProducts();
      },

      error: (err) => {
        alert('Ürün silinemedi.');
        console.error(err);
      },
    });
  }

  updatePrice(productId: number): void {
    this.productService.changePrice(productId, this.priceUpdate).subscribe({
      next: () => {
        alert('Fiyat güncellendi.');

        this.getProducts();

        this.priceUpdate.amount = 0;
        this.priceUpdate.percent = false;
      },

      error: (err) => {
        alert('Fiyat güncellenemedi.');
        console.error(err);
      },
    });
  }

  searchProduct(): void {
    if (!this.barcodeInput.trim()) {
      return;
    }

    this.productService.getByBarcode(this.barcodeInput).subscribe({
      next: (product) => {
        alert(`${product.name} - ${product.salePrice} ₺`);
      },

      error: () => {
        alert('Ürün bulunamadı.');
      },
    });
  }
}
