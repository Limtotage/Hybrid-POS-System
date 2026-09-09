import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private readonly TOKEN_KEY = 'token';
  private readonly baseUrl = 'http://localhost:8080/auth';

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

    if (!token) {
      return null;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));

      console.log('JWT Payload:', payload);

      // roles: [{ authority: "ROLE_ADMIN" }]
      if (payload.roles?.[0]?.authority) {
        return payload.roles[0].authority.replace('ROLE_', '');
      }

      // role: "ADMIN"
      if (payload.role) {
        return payload.role.replace('ROLE_', '');
      }

      // roles: ["ADMIN"]
      if (Array.isArray(payload.roles) && payload.roles[0]) {
        return payload.roles[0].replace('ROLE_', '');
      }

      return null;
    } catch (e) {
      console.error('Token parse hatası', e);
      return null;
    }
  }

  login(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, data);
  }
}
