import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}
  addProduct(shopId: number,data : any): Observable<any>{
    return this.http.post(`${this.baseUrl}/${shopId}`, data);
  }
  deleteProduct(data : any): Observable<any>{
    return this.http.delete(`${this.baseUrl}/${data.productid}`, data);
  }
  changePrice(data:any):Observable<any>{
    return this.http.put(`${this.baseUrl}/${data.productid}/price`,data);
  }
  getAllProducts(data:any):Observable<any>{
    return this.http.get(`${this.baseUrl}/${data.shopid}`);
  }
  getByBarcode(data:any):Observable<any>{
    return this.http.get(`${this.baseUrl}/barcode/${data.barcode}`);
  }
}
