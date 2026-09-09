import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {

  private readonly baseUrl =
    'http://localhost:8080/api/products';

  constructor(
    private http: HttpClient
  ) {}

  addProduct(data: any): Observable<any> {

    return this.http.post(
      this.baseUrl,
      data
    );

  }

  deleteProduct(productId: number): Observable<any> {

    return this.http.delete(
      `${this.baseUrl}/${productId}`
    );

  }

  changePrice(
    productId: number,
    data: any
  ): Observable<any> {

    return this.http.put(
      `${this.baseUrl}/${productId}/price`,
      data
    );

  }

  getAllProducts(): Observable<any[]> {

    return this.http.get<any[]>(
      this.baseUrl
    );

  }

  getByBarcode(
    barcode: string
  ): Observable<any> {

    return this.http.get(
      `${this.baseUrl}/barcode/${barcode}`
    );

  }

  increaseStock(
    productId: number,
    amount: number
  ): Observable<any> {

    return this.http.post(
      `${this.baseUrl}/${productId}/stock`,
      { amount }
    );

  }
}
