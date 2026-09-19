import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ProductService } from '../../../services/product/product-service';
import { StockMovement } from '../../../services/report/report-models';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products {
  //product
  products: any[] = [];
  selectedProduct: any = null;
  //stock
  selectedStockProduct: any = null;
  stockAmount = 0;
  // stock history
  selectedMovementProduct: any = null;
  stockMovements: StockMovement[] = [];
  //image
  selectedImage: File | null = null;
  selectedUpdateImage: File | null = null;
  updateImagePreviewUrl: string | null = null;

  updateProductData = {
    barcode: '',
    name: '',
    purchasePrice: 0,
    salePrice: 0,
  };

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

  constructor(
    private productService: ProductService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (res: any[]) => {
        this.products = res;
        console.log('PRODUCTS:', res);
        this.cdr.detectChanges();
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
  openStockHistoryModal(product: any): void {
    this.selectedMovementProduct = product;
    this.stockMovements = [];

    this.productService.getStockMovements(product.id).subscribe({
      next: (movements) => {
        this.stockMovements = movements;
        console.log('Stock movements:', movements);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Stock movements alınamadı:', err);
        alert('Stok hareketleri alınamadı.');
      },
    });
  }

  closeStockHistoryModal(): void {
    this.selectedMovementProduct = null;
    this.stockMovements = [];
  }
  deleteProduct(id: number): void {
    const confirmed = confirm('Bu ürünü silmek istediğinize emin misiniz?');

    if (!confirmed) {
      return;
    }

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
  openUpdateModal(product: any): void {
    this.selectedProduct = product;

    this.updateProductData = {
      barcode: product.barcode,
      name: product.name,
      purchasePrice: product.purchasePrice,
      salePrice: product.salePrice,
    };
    this.updateImagePreviewUrl = null;
    this.selectedUpdateImage = null;
  }
  openStockModal(product: any): void {
    this.selectedStockProduct = product;
    this.stockAmount = product.stockQuantity;
  }
  onUpdateImageSelected(event: any): void {
    const file = event.target.files[0];

    if (file) {
      this.selectedUpdateImage = file;

      this.updateImagePreviewUrl = URL.createObjectURL(file);
    }
  }

  updateProduct(): void {
    if (!this.selectedProduct) {
      return;
    }

    const formData = new FormData();

    formData.append(
      'product',
      new Blob([JSON.stringify(this.updateProductData)], { type: 'application/json' }),
    );

    if (this.selectedUpdateImage) {
      formData.append('image', this.selectedUpdateImage);
    }

    this.productService.updateProduct(this.selectedProduct.id, formData).subscribe({
      next: () => {
        alert('Ürün başarıyla güncellendi.');

        this.selectedProduct = null;
        this.selectedUpdateImage = null;
        this.updateImagePreviewUrl = null;

        this.getProducts();
      },
      error: (err) => {
        console.error(err);

        if (err.error?.message) {
          alert(err.error.message);
        } else {
          alert('Ürün güncellenemedi.');
        }
      },
    });
  }
  setStock(): void {
    if (!this.selectedStockProduct || this.stockAmount < 0) {
      return;
    }

    this.productService.adjustStock(this.selectedStockProduct.id, this.stockAmount).subscribe({
      next: () => {
        alert('Stok başarıyla güncellendi.');

        this.selectedStockProduct = null;
        this.stockAmount = 0;

        this.getProducts();
      },
      error: (err) => {
        console.error(err);
        alert('Stok güncellenemedi.');
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
