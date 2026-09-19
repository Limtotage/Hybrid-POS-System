import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StockMovement } from '../report/report-models';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly baseUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  addProduct(data: any): Observable<any> {
    return this.http.post(this.baseUrl, data);
  }

  deleteProduct(productId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${productId}`);
  }

  changePrice(productId: number, data: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${productId}/price`, data);
  }
  updateProduct(productId: number, data: FormData): Observable<any> {
    return this.http.put(`${this.baseUrl}/${productId}`, data);
  }

  getAllProducts(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getByBarcode(barcode: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/barcode/${barcode}`);
  }

  increaseStock(productId: number, amount: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${productId}/stock`, { amount });
  }
  adjustStock(productId: number, amount: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${productId}/adjust-stock`, { amount });
  }
  getStockMovements(productId: number): Observable<StockMovement[]> {
  return this.http.get<StockMovement[]>(
    `${this.baseUrl}/${productId}/stock-movements`
  );
}
}
