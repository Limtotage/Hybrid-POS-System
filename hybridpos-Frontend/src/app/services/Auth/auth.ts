import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private readonly TOKEN_KEY = "token";

  isLoggedIn(): boolean{
    return !!localStorage.getItem(this.TOKEN_KEY);
  }
  getToken(): string | null{
    return localStorage.getItem(this.TOKEN_KEY);
  }
  saveToken(token:string):void{
    localStorage.setItem(this.TOKEN_KEY,token);
  }
  logout(): void{
    localStorage.removeItem(this.TOKEN_KEY);
  }
  getRoleFromToken():string|null{
    const token = this.getToken();
    if(!token) return null;
    const payload = JSON.parse(atob(token.split('.')[1]));
    console.log(token);
    return payload.role || payload.authority || null;
  }
}
