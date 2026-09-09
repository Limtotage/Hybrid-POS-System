import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CashRegisterService {
  private readonly baseUrl = 'http://localhost:8080/api/cash';

  constructor(private http: HttpClient) {}

  getMyOpenCash(): Observable<any> {
    return this.http.get(`${this.baseUrl}/my-open`);
  }

  openCash(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/open`, data);
  }

  closeCash(cashId: number, data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/close/${cashId}`, data);
  }
}
