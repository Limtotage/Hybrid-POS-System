import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}
  addProduct(shopId: number, data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${shopId}`, data);
  }
  deleteProduct(productId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${productId}`);
  }
  changePrice(productId: number, data: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${productId}/price`, data);
  }
  getAllProducts(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }
  getByBarcode(barcode: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/barcode/${barcode}`);
  }

  getShops(): Observable<any> {
    return this.http.get(`http://localhost:8080/api/shops`);
  }
}
