import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ProductService } from '../../../services/product/product-service';

@Component({
  selector: 'app-products',
  imports: [FormsModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products {
  shopId=1;
  newProduct = {
    barcode : '',
    name : '',
    purchasePrice:0.00,
    salePrice:0.00,
    stockQuantity:0,
  };
  constructor(
    private productService :ProductService,
  ){}
  AddProduct(form: NgForm) {
    this.productService.addProduct(this.shopId,this.newProduct).subscribe({
      next: () => {
        alert('Ürün başarılıyla Eklendi.');
        form.resetForm();
      },
      error: (err) => {
        alert('Kayıt hatası');
        console.error(err);
      },
    });
  }
}
