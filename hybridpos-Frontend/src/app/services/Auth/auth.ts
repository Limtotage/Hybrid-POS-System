import { FormsModule } from '@angular/forms';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment.prod';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private readonly TOKEN_KEY = 'token';
  private baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  isLoggedIn(): boolean {
    return !!localStorage.getItem(this.TOKEN_KEY);
  }
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }
  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }
  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
  getRoleFromToken(): string | null {
    const token = this.getToken();

    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));

      const role = payload.roles?.[0]?.authority;

      if (!role) return null;

      return role.replace('ROLE_', '');
    } catch (e) {
      console.error('Token parse hatası', e);
      return null;
    }
  }
  register(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register-owner`, data);
  }
  createCashier(data : any): Observable<any>{
    return this.http.post(`${this.baseUrl}/create-cashier`, data);
  }
  login(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, data);
  }
}
