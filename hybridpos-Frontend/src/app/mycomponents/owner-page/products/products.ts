import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ProductService } from '../../../services/product/product-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-products',
  imports: [FormsModule, CommonModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products {
  products: any[] = [];
  shops: any[] = [];
  shopId = 1;
  newProduct = {
    barcode: '',
    name: '',
    purchasePrice: 0.0,
    salePrice: 0.0,
    stockQuantity: 0,
  };
  priceUpdate = {
    amount: 0,
    percent: false,
  };

  constructor(private productService: ProductService) {}
  ngOnInit() {
    this.GetProducts();
    this.GetShops();
  }
  GetShops() {
    this.productService.getShops().subscribe({
      next: (res: any[]) => {
        this.shops = res;
      },
      error: (err) => {
        alert('Dükkanlar Getirilemedi Getirilemedi.');
        console.error(err);
      },
    });
  }
  AddProduct(form: NgForm) {
    this.productService.addProduct(this.shopId, this.newProduct).subscribe({
      next: () => {
        alert('Ürün başarılıyla Eklendi.');
        form.resetForm();
        this.GetProducts();
      },
      error: (err) => {
        alert('Kayıt hatası');
        console.error(err);
      },
    });
  }
  GetProducts() {
    this.productService.getAllProducts(this.shopId).subscribe({
      next: (res: any[]) => {
        this.products = res;
      },
      error: (err) => {
        alert('Ürünler Getirilemedi.');
        console.error(err);
      },
    });
  }
  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe({
      next: () => {
        alert('Ürün silindi');
        this.GetProducts();
      },
      error: (err) => {
        alert('Ürün silinemedi.');
        console.error(err);
      },
    });
  }
  updatePrice(productId: number) {
    this.productService.changePrice(productId, this.priceUpdate).subscribe({
      next: () => {
        alert('Fiyat güncellendi');
        this.GetProducts();
        this.priceUpdate.amount = 0;
        this.priceUpdate.percent = false;
      },
      error: (err) => {
        alert('Fiyat güncelleme hatası');
        console.error(err);
      },
    });
  }
  barcodeInput: string = '';

  searchProduct() {
    this.productService.getByBarcode(this.barcodeInput).subscribe({
      next: (product) => {
        alert(`${product.name}---${product.salePrice}`);
        console.log(product);
      },
      error: (err) => {
        console.error('Ürün bulunamadı');
      },
    });
  }
}
