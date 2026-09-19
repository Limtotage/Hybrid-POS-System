import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface AdminRequest {
  username: string;
  password?: string;
}

export interface AdminResponse {
  id: number;
  username: string;
  role: string;
  enabled: boolean;
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AdminSettingsService {

  private readonly baseUrl =
    'http://localhost:8080/api/admin';

  constructor(private http: HttpClient) {}

  updateAdmin(
    data: AdminRequest
  ): Observable<AdminResponse> {
    return this.http.put<AdminResponse>(
      `${this.baseUrl}/update-admin`,
      data
    );
  }
}
