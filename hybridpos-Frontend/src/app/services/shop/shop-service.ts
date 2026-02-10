import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ShopService {
  private baseUrl = 'http://localhost:8080/api/shops';

  constructor(private http: HttpClient) {}
  createShop(data : any): Observable<any>{
    return this.http.post(`${this.baseUrl}/create`, data);
  }
}
