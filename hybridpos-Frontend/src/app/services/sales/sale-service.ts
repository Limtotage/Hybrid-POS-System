import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SaleService {
  private readonly baseUrl = 'http://localhost:8080/api/sales';
  constructor(private http: HttpClient) {}

  makeSale(cashId: number, saleData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/${cashId}`, saleData);
  }
}
