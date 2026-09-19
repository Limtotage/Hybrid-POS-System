import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Cashier {
  id: number;
  username: string;
  role: string;
  enabled: boolean;
}

export interface CashierRequest {
  username: string;
  password?: string;
}

@Injectable({
  providedIn: 'root',
})
export class CashierService {

  private readonly baseUrl =
    'http://localhost:8080/api/admin/cashiers';

  constructor(private http: HttpClient) {}

  getCashiers(): Observable<Cashier[]> {
    return this.http.get<Cashier[]>(this.baseUrl);
  }

  createCashier(data: CashierRequest): Observable<Cashier> {
    return this.http.post<Cashier>(this.baseUrl, data);
  }

  updateCashier(
    id: number,
    data: CashierRequest
  ): Observable<Cashier> {
    return this.http.put<Cashier>(
      `${this.baseUrl}/${id}`,
      data
    );
  }

  deleteCashier(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/${id}`
    );
  }
}
