import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CashRegisterService {

  private readonly baseUrl =
    'http://localhost:8080/api/cash-registers';

  constructor(private http: HttpClient) {}

  getAllCashRegisters(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  openCash(cashId: number): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/${cashId}/open`,
      {}
    );
  }

  closeCash(cashId: number): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/${cashId}/close`,
      {}
    );
  }
}
